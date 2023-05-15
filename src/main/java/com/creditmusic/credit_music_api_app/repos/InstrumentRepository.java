package com.creditmusic.credit_music_api_app.repos;

import com.creditmusic.credit_music_api_app.domain.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
}
