package com.creditmusic.credit_music_api_app.service;

import com.creditmusic.credit_music_api_app.domain.Language;
import com.creditmusic.credit_music_api_app.domain.Lyric;
import com.creditmusic.credit_music_api_app.model.LyricDTO;
import com.creditmusic.credit_music_api_app.repos.LanguageRepository;
import com.creditmusic.credit_music_api_app.repos.LyricRepository;
import com.creditmusic.credit_music_api_app.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class LyricService {

    private final LyricRepository lyricRepository;
    private final LanguageRepository languageRepository;

    public LyricService(final LyricRepository lyricRepository,
            final LanguageRepository languageRepository) {
        this.lyricRepository = lyricRepository;
        this.languageRepository = languageRepository;
    }

    public List<LyricDTO> findAll() {
        final List<Lyric> lyrics = lyricRepository.findAll(Sort.by("id"));
        return lyrics.stream()
                .map((lyric) -> mapToDTO(lyric, new LyricDTO()))
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
        lyricDTO.setLanguage(lyric.getLanguage() == null ? null : lyric.getLanguage().stream()
                .map(language -> language.getId())
                .toList());
        return lyricDTO;
    }

    private Lyric mapToEntity(final LyricDTO lyricDTO, final Lyric lyric) {
        lyric.setTitle(lyricDTO.getTitle());
        lyric.setLyric(lyricDTO.getLyric());
        final List<Language> language = languageRepository.findAllById(
                lyricDTO.getLanguage() == null ? Collections.emptyList() : lyricDTO.getLanguage());
        if (language.size() != (lyricDTO.getLanguage() == null ? 0 : lyricDTO.getLanguage().size())) {
            throw new NotFoundException("one of language not found");
        }
        lyric.setLanguage(language.stream().collect(Collectors.toSet()));
        return lyric;
    }

}
