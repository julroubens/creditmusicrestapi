package com.creditmusic.credit_music_rest_api.repos;

import com.creditmusic.credit_music_rest_api.domain.Entity;
import com.creditmusic.credit_music_rest_api.domain.EntityType;
import com.creditmusic.credit_music_rest_api.domain.Instrument;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EntityRepository extends JpaRepository<Entity, Long> {

    List<Entity> findAllByInstrument(Instrument instrument);

    List<Entity> findAllByType(EntityType entityType);

}
