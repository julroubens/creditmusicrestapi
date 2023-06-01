package com.creditmusic.credit_music_rest_api.repos;

import com.creditmusic.credit_music_rest_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
