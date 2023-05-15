package com.creditmusic.credit_music_api_app.repos;

import com.creditmusic.credit_music_api_app.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
}
