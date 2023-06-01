package com.creditmusic.credit_music_rest_api.service;

import com.creditmusic.credit_music_rest_api.domain.Album;
import com.creditmusic.credit_music_rest_api.domain.Category;
import com.creditmusic.credit_music_rest_api.domain.Entity;
import com.creditmusic.credit_music_rest_api.domain.Instrument;
import com.creditmusic.credit_music_rest_api.domain.Song;
import com.creditmusic.credit_music_rest_api.model.SongDTO;
import com.creditmusic.credit_music_rest_api.repos.AlbumRepository;
import com.creditmusic.credit_music_rest_api.repos.CategoryRepository;
import com.creditmusic.credit_music_rest_api.repos.EntityRepository;
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
public class SongService {

    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final CategoryRepository categoryRepository;
    private final EntityRepository entityRepository;
    private final InstrumentRepository instrumentRepository;

    public SongService(final SongRepository songRepository, final AlbumRepository albumRepository,
            final CategoryRepository categoryRepository, final EntityRepository entityRepository,
            final InstrumentRepository instrumentRepository) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.categoryRepository = categoryRepository;
        this.entityRepository = entityRepository;
        this.instrumentRepository = instrumentRepository;
    }

    public List<SongDTO> findAll() {
        final List<Song> songs = songRepository.findAll(Sort.by("id"));
        return songs.stream()
                .map(song -> mapToDTO(song, new SongDTO()))
                .toList();
    }

    public SongDTO get(final Long id) {
        return songRepository.findById(id)
                .map(song -> mapToDTO(song, new SongDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SongDTO songDTO) {
        final Song song = new Song();
        mapToEntity(songDTO, song);
        return songRepository.save(song).getId();
    }

    public void update(final Long id, final SongDTO songDTO) {
        final Song song = songRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(songDTO, song);
        songRepository.save(song);
    }

    public void delete(final Long id) {
        songRepository.deleteById(id);
    }

    private SongDTO mapToDTO(final Song song, final SongDTO songDTO) {
        songDTO.setId(song.getId());
        songDTO.setTitle(song.getTitle());
        songDTO.setArtist(song.getArtist());
        songDTO.setReleaseDate(song.getReleaseDate());
        songDTO.setImg(song.getImg());
        songDTO.setStatus(song.getStatus());
        songDTO.setAlbum(song.getAlbum() == null ? null : song.getAlbum().getId());
        songDTO.setCategory(song.getCategory() == null ? null : song.getCategory().getId());
        songDTO.setIntity(song.getIntity().stream()
                .map(entity -> entity.getId())
                .toList());
        songDTO.setInstrument(song.getInstrument().stream()
                .map(instrument -> instrument.getId())
                .toList());
        return songDTO;
    }

    private Song mapToEntity(final SongDTO songDTO, final Song song) {
        song.setTitle(songDTO.getTitle());
        song.setArtist(songDTO.getArtist());
        song.setReleaseDate(songDTO.getReleaseDate());
        song.setImg(songDTO.getImg());
        song.setStatus(songDTO.getStatus());
        final Album album = songDTO.getAlbum() == null ? null : albumRepository.findById(songDTO.getAlbum())
                .orElseThrow(() -> new NotFoundException("album not found"));
        song.setAlbum(album);
        final Category category = songDTO.getCategory() == null ? null : categoryRepository.findById(songDTO.getCategory())
                .orElseThrow(() -> new NotFoundException("category not found"));
        song.setCategory(category);
        final List<Entity> intity = entityRepository.findAllById(
                songDTO.getIntity() == null ? Collections.emptyList() : songDTO.getIntity());
        if (intity.size() != (songDTO.getIntity() == null ? 0 : songDTO.getIntity().size())) {
            throw new NotFoundException("one of intity not found");
        }
        song.setIntity(intity.stream().collect(Collectors.toSet()));
        final List<Instrument> instrument = instrumentRepository.findAllById(
                songDTO.getInstrument() == null ? Collections.emptyList() : songDTO.getInstrument());
        if (instrument.size() != (songDTO.getInstrument() == null ? 0 : songDTO.getInstrument().size())) {
            throw new NotFoundException("one of instrument not found");
        }
        song.setInstrument(instrument.stream().collect(Collectors.toSet()));
        return song;
    }

}
