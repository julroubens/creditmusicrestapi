package com.creditmusic.credit_music_rest_api.service;

import com.creditmusic.credit_music_rest_api.domain.Entity;
import com.creditmusic.credit_music_rest_api.domain.EntityType;
import com.creditmusic.credit_music_rest_api.domain.Instrument;
import com.creditmusic.credit_music_rest_api.model.EntityDTO;
import com.creditmusic.credit_music_rest_api.repos.EntityRepository;
import com.creditmusic.credit_music_rest_api.repos.EntityTypeRepository;
import com.creditmusic.credit_music_rest_api.repos.InstrumentRepository;
import com.creditmusic.credit_music_rest_api.repos.SongRepository;
import com.creditmusic.credit_music_rest_api.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class EntityService {

    private final EntityRepository entityRepository;
    private final InstrumentRepository instrumentRepository;
    private final EntityTypeRepository entityTypeRepository;
    private final SongRepository songRepository;

    public EntityService(final EntityRepository entityRepository,
            final InstrumentRepository instrumentRepository,
            final EntityTypeRepository entityTypeRepository, final SongRepository songRepository) {
        this.entityRepository = entityRepository;
        this.instrumentRepository = instrumentRepository;
        this.entityTypeRepository = entityTypeRepository;
        this.songRepository = songRepository;
    }

    public List<EntityDTO> findAll() {
        final List<Entity> entitys = entityRepository.findAll(Sort.by("id"));
        return entitys.stream()
                .map(entity -> mapToDTO(entity, new EntityDTO()))
                .toList();
    }

    public EntityDTO get(final Long id) {
        return entityRepository.findById(id)
                .map(entity -> mapToDTO(entity, new EntityDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EntityDTO entityDTO) {
        final Entity entity = new Entity();
        mapToEntity(entityDTO, entity);
        return entityRepository.save(entity).getId();
    }

    public void update(final Long id, final EntityDTO entityDTO) {
        final Entity entity = entityRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(entityDTO, entity);
        entityRepository.save(entity);
    }

    public void delete(final Long id) {
        final Entity entity = entityRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        songRepository.findAllByIntity(entity)
                .forEach(song -> song.getIntity().remove(entity));
        entityRepository.delete(entity);
    }

    private EntityDTO mapToDTO(final Entity entity, final EntityDTO entityDTO) {
        entityDTO.setId(entity.getId());
        entityDTO.setName(entity.getName());
        entityDTO.setStatus(entity.getStatus());
        entityDTO.setInstrument(entity.getInstrument().stream()
                .map(instrument -> instrument.getId())
                .toList());
        entityDTO.setType(entity.getType().stream()
                .map(entityType -> entityType.getId())
                .toList());
        return entityDTO;
    }

    private Entity mapToEntity(final EntityDTO entityDTO, final Entity entity) {
        entity.setName(entityDTO.getName());
        entity.setStatus(entityDTO.getStatus());
        final List<Instrument> instrument = instrumentRepository.findAllById(
                entityDTO.getInstrument() == null ? Collections.emptyList() : entityDTO.getInstrument());
        if (instrument.size() != (entityDTO.getInstrument() == null ? 0 : entityDTO.getInstrument().size())) {
            throw new NotFoundException("one of instrument not found");
        }
        entity.setInstrument(instrument.stream().collect(Collectors.toSet()));
        final List<EntityType> type = entityTypeRepository.findAllById(
                entityDTO.getType() == null ? Collections.emptyList() : entityDTO.getType());
        if (type.size() != (entityDTO.getType() == null ? 0 : entityDTO.getType().size())) {
            throw new NotFoundException("one of type not found");
        }
        entity.setType(type.stream().collect(Collectors.toSet()));
        return entity;
    }

}
