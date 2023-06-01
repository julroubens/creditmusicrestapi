package com.creditmusic.credit_music_rest_api.service;

import com.creditmusic.credit_music_rest_api.domain.Comment;
import com.creditmusic.credit_music_rest_api.domain.Song;
import com.creditmusic.credit_music_rest_api.domain.User;
import com.creditmusic.credit_music_rest_api.model.CommentDTO;
import com.creditmusic.credit_music_rest_api.repos.CommentRepository;
import com.creditmusic.credit_music_rest_api.repos.SongRepository;
import com.creditmusic.credit_music_rest_api.repos.UserRepository;
import com.creditmusic.credit_music_rest_api.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    public CommentService(final CommentRepository commentRepository,
            final SongRepository songRepository, final UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    public List<CommentDTO> findAll() {
        final List<Comment> comments = commentRepository.findAll(Sort.by("id"));
        return comments.stream()
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .toList();
    }

    public CommentDTO get(final Long id) {
        return commentRepository.findById(id)
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CommentDTO commentDTO) {
        final Comment comment = new Comment();
        mapToEntity(commentDTO, comment);
        return commentRepository.save(comment).getId();
    }

    public void update(final Long id, final CommentDTO commentDTO) {
        final Comment comment = commentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(commentDTO, comment);
        commentRepository.save(comment);
    }

    public void delete(final Long id) {
        commentRepository.deleteById(id);
    }

    private CommentDTO mapToDTO(final Comment comment, final CommentDTO commentDTO) {
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setStatus(comment.getStatus());
        commentDTO.setSong(comment.getSong() == null ? null : comment.getSong().getId());
        commentDTO.setUserComent(comment.getUserComent() == null ? null : comment.getUserComent().getId());
        return commentDTO;
    }

    private Comment mapToEntity(final CommentDTO commentDTO, final Comment comment) {
        comment.setContent(commentDTO.getContent());
        comment.setStatus(commentDTO.getStatus());
        final Song song = commentDTO.getSong() == null ? null : songRepository.findById(commentDTO.getSong())
                .orElseThrow(() -> new NotFoundException("song not found"));
        comment.setSong(song);
        final User userComent = commentDTO.getUserComent() == null ? null : userRepository.findById(commentDTO.getUserComent())
                .orElseThrow(() -> new NotFoundException("userComent not found"));
        comment.setUserComent(userComent);
        return comment;
    }

}
