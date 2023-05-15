package com.creditmusic.credit_music_api_app.service;

import com.creditmusic.credit_music_api_app.domain.Instrument;
import com.creditmusic.credit_music_api_app.model.InstrumentDTO;
import com.creditmusic.credit_music_api_app.repos.InstrumentRepository;
import com.creditmusic.credit_music_api_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;

    public InstrumentService(final InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    public List<InstrumentDTO> findAll() {
        final List<Instrument> instruments = instrumentRepository.findAll(Sort.by("id"));
        return instruments.stream()
                .map((instrument) -> mapToDTO(instrument, new InstrumentDTO()))
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
        instrumentRepository.deleteById(id);
    }

    private InstrumentDTO mapToDTO(final Instrument instrument, final InstrumentDTO instrumentDTO) {
        instrumentDTO.setId(instrument.getId());
        instrumentDTO.setName(instrument.getName());
        instrumentDTO.setImg(instrument.getImg());
        return instrumentDTO;
    }

    private Instrument mapToEntity(final InstrumentDTO instrumentDTO, final Instrument instrument) {
        instrument.setName(instrumentDTO.getName());
        instrument.setImg(instrumentDTO.getImg());
        return instrument;
    }

}
