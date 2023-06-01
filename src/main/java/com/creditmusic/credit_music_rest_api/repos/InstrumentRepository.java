package com.creditmusic.credit_music_rest_api.repos;

import com.creditmusic.credit_music_rest_api.domain.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
}
