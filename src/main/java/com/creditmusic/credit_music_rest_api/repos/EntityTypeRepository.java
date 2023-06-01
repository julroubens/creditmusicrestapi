package com.creditmusic.credit_music_rest_api.repos;

import com.creditmusic.credit_music_rest_api.domain.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EntityTypeRepository extends JpaRepository<EntityType, Long> {
}
