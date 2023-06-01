package com.creditmusic.credit_music_rest_api.repos;

import com.creditmusic.credit_music_rest_api.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikesRepository extends JpaRepository<Likes, Long> {
}
