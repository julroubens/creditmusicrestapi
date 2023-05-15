package com.creditmusic.credit_music_api_app.repos;

import com.creditmusic.credit_music_api_app.domain.Entity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EntityRepository extends JpaRepository<Entity, Long> {
}
