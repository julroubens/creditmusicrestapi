package com.creditmusic.credit_music_rest_api.service;

import com.creditmusic.credit_music_rest_api.domain.Language;
import com.creditmusic.credit_music_rest_api.model.LanguageDTO;
import com.creditmusic.credit_music_rest_api.repos.LanguageRepository;
import com.creditmusic.credit_music_rest_api.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(final LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<LanguageDTO> findAll() {
        final List<Language> languages = languageRepository.findAll(Sort.by("id"));
        return languages.stream()
                .map(language -> mapToDTO(language, new LanguageDTO()))
                .toList();
    }

    public LanguageDTO get(final Long id) {
        return languageRepository.findById(id)
                .map(language -> mapToDTO(language, new LanguageDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LanguageDTO languageDTO) {
        final Language language = new Language();
        mapToEntity(languageDTO, language);
        return languageRepository.save(language).getId();
    }

    public void update(final Long id, final LanguageDTO languageDTO) {
        final Language language = languageRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(languageDTO, language);
        languageRepository.save(language);
    }

    public void delete(final Long id) {
        languageRepository.deleteById(id);
    }

    private LanguageDTO mapToDTO(final Language language, final LanguageDTO languageDTO) {
        languageDTO.setId(language.getId());
        languageDTO.setName(language.getName());
        languageDTO.setTrigram(language.getTrigram());
        languageDTO.setStatus(language.getStatus());
        return languageDTO;
    }

    private Language mapToEntity(final LanguageDTO languageDTO, final Language language) {
        language.setName(languageDTO.getName());
        language.setTrigram(languageDTO.getTrigram());
        language.setStatus(languageDTO.getStatus());
        return language;
    }

}
