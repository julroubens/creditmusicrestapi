package com.creditmusic.credit_music_rest_api.service;

import com.creditmusic.credit_music_rest_api.domain.Language;
import com.creditmusic.credit_music_rest_api.domain.Lyric;
import com.creditmusic.credit_music_rest_api.domain.Song;
import com.creditmusic.credit_music_rest_api.model.LyricDTO;
import com.creditmusic.credit_music_rest_api.repos.LanguageRepository;
import com.creditmusic.credit_music_rest_api.repos.LyricRepository;
import com.creditmusic.credit_music_rest_api.repos.SongRepository;
import com.creditmusic.credit_music_rest_api.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LyricService {

    private final LyricRepository lyricRepository;
    private final LanguageRepository languageRepository;
    private final SongRepository songRepository;

    public LyricService(final LyricRepository lyricRepository,
            final LanguageRepository languageRepository, final SongRepository songRepository) {
        this.lyricRepository = lyricRepository;
        this.languageRepository = languageRepository;
        this.songRepository = songRepository;
    }

    public List<LyricDTO> findAll() {
        final List<Lyric> lyrics = lyricRepository.findAll(Sort.by("id"));
        return lyrics.stream()
                .map(lyric -> mapToDTO(lyric, new LyricDTO()))
                .toList();
    }

    public LyricDTO get(final Long id) {
        return lyricRepository.findById(id)
                .map(lyric -> mapToDTO(lyric, new LyricDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LyricDTO lyricDTO) {
        final Lyric lyric = new Lyric();
        mapToEntity(lyricDTO, lyric);
        return lyricRepository.save(lyric).getId();
    }

    public void update(final Long id, final LyricDTO lyricDTO) {
        final Lyric lyric = lyricRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(lyricDTO, lyric);
        lyricRepository.save(lyric);
    }

    public void delete(final Long id) {
        lyricRepository.deleteById(id);
    }

    private LyricDTO mapToDTO(final Lyric lyric, final LyricDTO lyricDTO) {
        lyricDTO.setId(lyric.getId());
        lyricDTO.setTitle(lyric.getTitle());
        lyricDTO.setLyric(lyric.getLyric());
        lyricDTO.setStatus(lyric.getStatus());
        lyricDTO.setLanguage(lyric.getLanguage() == null ? null : lyric.getLanguage().getId());
        lyricDTO.setSong(lyric.getSong() == null ? null : lyric.getSong().getId());
        return lyricDTO;
    }

    private Lyric mapToEntity(final LyricDTO lyricDTO, final Lyric lyric) {
        lyric.setTitle(lyricDTO.getTitle());
        lyric.setLyric(lyricDTO.getLyric());
        lyric.setStatus(lyricDTO.getStatus());
        final Language language = lyricDTO.getLanguage() == null ? null : languageRepository.findById(lyricDTO.getLanguage())
                .orElseThrow(() -> new NotFoundException("language not found"));
        lyric.setLanguage(language);
        final Song song = lyricDTO.getSong() == null ? null : songRepository.findById(lyricDTO.getSong())
                .orElseThrow(() -> new NotFoundException("song not found"));
        lyric.setSong(song);
        return lyric;
    }

}
