package com.creditmusic.credit_music_rest_api.repos;

import com.creditmusic.credit_music_rest_api.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlbumRepository extends JpaRepository<Album, Long> {
}
