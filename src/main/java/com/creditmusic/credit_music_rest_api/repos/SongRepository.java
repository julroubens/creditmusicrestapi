package com.creditmusic.credit_music_rest_api.repos;

import com.creditmusic.credit_music_rest_api.domain.Entity;
import com.creditmusic.credit_music_rest_api.domain.Instrument;
import com.creditmusic.credit_music_rest_api.domain.Song;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findAllByIntity(Entity entity);

    List<Song> findAllByInstrument(Instrument instrument);

}
