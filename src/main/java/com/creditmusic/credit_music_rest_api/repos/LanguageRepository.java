package com.creditmusic.credit_music_rest_api.repos;

import com.creditmusic.credit_music_rest_api.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LanguageRepository extends JpaRepository<Language, Long> {
}
