package com.creditmusic.credit_music_rest_api.service;

import com.creditmusic.credit_music_rest_api.domain.Category;
import com.creditmusic.credit_music_rest_api.model.CategoryDTO;
import com.creditmusic.credit_music_rest_api.repos.CategoryRepository;
import com.creditmusic.credit_music_rest_api.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> findAll() {
        final List<Category> categorys = categoryRepository.findAll(Sort.by("id"));
        return categorys.stream()
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .toList();
    }

    public CategoryDTO get(final Long id) {
        return categoryRepository.findById(id)
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CategoryDTO categoryDTO) {
        final Category category = new Category();
        mapToEntity(categoryDTO, category);
        return categoryRepository.save(category).getId();
    }

    public void update(final Long id, final CategoryDTO categoryDTO) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categoryDTO, category);
        categoryRepository.save(category);
    }

    public void delete(final Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO mapToDTO(final Category category, final CategoryDTO categoryDTO) {
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setStatus(category.getStatus());
        return categoryDTO;
    }

    private Category mapToEntity(final CategoryDTO categoryDTO, final Category category) {
        category.setName(categoryDTO.getName());
        category.setStatus(categoryDTO.getStatus());
        return category;
    }

}
