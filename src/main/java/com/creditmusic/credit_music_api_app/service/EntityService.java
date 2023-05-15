package com.creditmusic.credit_music_api_app.service;

import com.creditmusic.credit_music_api_app.domain.Entity;
import com.creditmusic.credit_music_api_app.domain.Instrument;
import com.creditmusic.credit_music_api_app.model.EntityDTO;
import com.creditmusic.credit_music_api_app.repos.EntityRepository;
import com.creditmusic.credit_music_api_app.repos.InstrumentRepository;
import com.creditmusic.credit_music_api_app.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class EntityService {

    private final EntityRepository entityRepository;
    private final InstrumentRepository instrumentRepository;

    public EntityService(final EntityRepository entityRepository,
            final InstrumentRepository instrumentRepository) {
        this.entityRepository = entityRepository;
        this.instrumentRepository = instrumentRepository;
    }

    public List<EntityDTO> findAll() {
        final List<Entity> entitys = entityRepository.findAll(Sort.by("id"));
        return entitys.stream()
                .map((entity) -> mapToDTO(entity, new EntityDTO()))
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
        entityRepository.deleteById(id);
    }

    private EntityDTO mapToDTO(final Entity entity, final EntityDTO entityDTO) {
        entityDTO.setId(entity.getId());
        entityDTO.setName(entity.getName());
        entityDTO.setType(entity.getType());
        entityDTO.setInstrument(entity.getInstrument() == null ? null : entity.getInstrument().stream()
                .map(instrument -> instrument.getId())
                .toList());
        return entityDTO;
    }

    private Entity mapToEntity(final EntityDTO entityDTO, final Entity entity) {
        entity.setName(entityDTO.getName());
        entity.setType(entityDTO.getType());
        final List<Instrument> instrument = instrumentRepository.findAllById(
                entityDTO.getInstrument() == null ? Collections.emptyList() : entityDTO.getInstrument());
        if (instrument.size() != (entityDTO.getInstrument() == null ? 0 : entityDTO.getInstrument().size())) {
            throw new NotFoundException("one of instrument not found");
        }
        entity.setInstrument(instrument.stream().collect(Collectors.toSet()));
        return entity;
    }

}
