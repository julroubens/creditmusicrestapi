package com.creditmusic.credit_music_rest_api.repos;

import com.creditmusic.credit_music_rest_api.domain.Lyric;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LyricRepository extends JpaRepository<Lyric, Long> {
}
