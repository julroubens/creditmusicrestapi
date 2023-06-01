package com.creditmusic.credit_music_rest_api.service;

import com.creditmusic.credit_music_rest_api.domain.EntityType;
import com.creditmusic.credit_music_rest_api.model.EntityTypeDTO;
import com.creditmusic.credit_music_rest_api.repos.EntityRepository;
import com.creditmusic.credit_music_rest_api.repos.EntityTypeRepository;
import com.creditmusic.credit_music_rest_api.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class EntityTypeService {

    private final EntityTypeRepository entityTypeRepository;
    private final EntityRepository entityRepository;

    public EntityTypeService(final EntityTypeRepository entityTypeRepository,
            final EntityRepository entityRepository) {
        this.entityTypeRepository = entityTypeRepository;
        this.entityRepository = entityRepository;
    }

    public List<EntityTypeDTO> findAll() {
        final List<EntityType> entityTypes = entityTypeRepository.findAll(Sort.by("id"));
        return entityTypes.stream()
                .map(entityType -> mapToDTO(entityType, new EntityTypeDTO()))
                .toList();
    }

    public EntityTypeDTO get(final Long id) {
        return entityTypeRepository.findById(id)
                .map(entityType -> mapToDTO(entityType, new EntityTypeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EntityTypeDTO entityTypeDTO) {
        final EntityType entityType = new EntityType();
        mapToEntity(entityTypeDTO, entityType);
        return entityTypeRepository.save(entityType).getId();
    }

    public void update(final Long id, final EntityTypeDTO entityTypeDTO) {
        final EntityType entityType = entityTypeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(entityTypeDTO, entityType);
        entityTypeRepository.save(entityType);
    }

    public void delete(final Long id) {
        final EntityType entityType = entityTypeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        entityRepository.findAllByType(entityType)
                .forEach(entity -> entity.getType().remove(entityType));
        entityTypeRepository.delete(entityType);
    }

    private EntityTypeDTO mapToDTO(final EntityType entityType, final EntityTypeDTO entityTypeDTO) {
        entityTypeDTO.setId(entityType.getId());
        entityTypeDTO.setName(entityType.getName());
        entityTypeDTO.setStatus(entityType.getStatus());
        return entityTypeDTO;
    }

    private EntityType mapToEntity(final EntityTypeDTO entityTypeDTO, final EntityType entityType) {
        entityType.setName(entityTypeDTO.getName());
        entityType.setStatus(entityTypeDTO.getStatus());
        return entityType;
    }

}
