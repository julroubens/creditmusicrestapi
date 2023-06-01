package com.creditmusic.credit_music_rest_api.service;

import com.creditmusic.credit_music_rest_api.domain.Instrument;
import com.creditmusic.credit_music_rest_api.model.InstrumentDTO;
import com.creditmusic.credit_music_rest_api.repos.EntityRepository;
import com.creditmusic.credit_music_rest_api.repos.InstrumentRepository;
import com.creditmusic.credit_music_rest_api.repos.SongRepository;
import com.creditmusic.credit_music_rest_api.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;
    private final EntityRepository entityRepository;
    private final SongRepository songRepository;

    public InstrumentService(final InstrumentRepository instrumentRepository,
            final EntityRepository entityRepository, final SongRepository songRepository) {
        this.instrumentRepository = instrumentRepository;
        this.entityRepository = entityRepository;
        this.songRepository = songRepository;
    }

    public List<InstrumentDTO> findAll() {
        final List<Instrument> instruments = instrumentRepository.findAll(Sort.by("id"));
        return instruments.stream()
                .map(instrument -> mapToDTO(instrument, new InstrumentDTO()))
                .toList();
    }

    public InstrumentDTO get(final Long id) {
        return instrumentRepository.findById(id)
                .map(instrument -> mapToDTO(instrument, new InstrumentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final InstrumentDTO instrumentDTO) {
        final Instrument instrument = new Instrument();
        mapToEntity(instrumentDTO, instrument);
        return instrumentRepository.save(instrument).getId();
    }

    public void update(final Long id, final InstrumentDTO instrumentDTO) {
        final Instrument instrument = instrumentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(instrumentDTO, instrument);
        instrumentRepository.save(instrument);
    }

    public void delete(final Long id) {
        final Instrument instrument = instrumentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        entityRepository.findAllByInstrument(instrument)
                .forEach(entity -> entity.getInstrument().remove(instrument));
        songRepository.findAllByInstrument(instrument)
                .forEach(song -> song.getInstrument().remove(instrument));
        instrumentRepository.delete(instrument);
    }

    private InstrumentDTO mapToDTO(final Instrument instrument, final InstrumentDTO instrumentDTO) {
        instrumentDTO.setId(instrument.getId());
        instrumentDTO.setName(instrument.getName());
        instrumentDTO.setImg(instrument.getImg());
        instrumentDTO.setStatus(instrument.getStatus());
        return instrumentDTO;
    }

    private Instrument mapToEntity(final InstrumentDTO instrumentDTO, final Instrument instrument) {
        instrument.setName(instrumentDTO.getName());
        instrument.setImg(instrumentDTO.getImg());
        instrument.setStatus(instrumentDTO.getStatus());
        return instrument;
    }

}
