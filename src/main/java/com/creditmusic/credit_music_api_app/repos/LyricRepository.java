package com.creditmusic.credit_music_api_app.repos;

import com.creditmusic.credit_music_api_app.domain.Lyric;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LyricRepository extends JpaRepository<Lyric, Long> {
}
