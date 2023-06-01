package com.creditmusic.credit_music_rest_api.service;

import com.creditmusic.credit_music_rest_api.domain.Likes;
import com.creditmusic.credit_music_rest_api.domain.Song;
import com.creditmusic.credit_music_rest_api.domain.User;
import com.creditmusic.credit_music_rest_api.model.LikesDTO;
import com.creditmusic.credit_music_rest_api.repos.LikesRepository;
import com.creditmusic.credit_music_rest_api.repos.SongRepository;
import com.creditmusic.credit_music_rest_api.repos.UserRepository;
import com.creditmusic.credit_music_rest_api.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    public LikesService(final LikesRepository likesRepository, final SongRepository songRepository,
            final UserRepository userRepository) {
        this.likesRepository = likesRepository;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    public List<LikesDTO> findAll() {
        final List<Likes> likess = likesRepository.findAll(Sort.by("id"));
        return likess.stream()
                .map(likes -> mapToDTO(likes, new LikesDTO()))
                .toList();
    }

    public LikesDTO get(final Long id) {
        return likesRepository.findById(id)
                .map(likes -> mapToDTO(likes, new LikesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LikesDTO likesDTO) {
        final Likes likes = new Likes();
        mapToEntity(likesDTO, likes);
        return likesRepository.save(likes).getId();
    }

    public void update(final Long id, final LikesDTO likesDTO) {
        final Likes likes = likesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(likesDTO, likes);
        likesRepository.save(likes);
    }

    public void delete(final Long id) {
        likesRepository.deleteById(id);
    }

    private LikesDTO mapToDTO(final Likes likes, final LikesDTO likesDTO) {
        likesDTO.setId(likes.getId());
        likesDTO.setStatus(likes.getStatus());
        likesDTO.setLikeSong(likes.getLikeSong() == null ? null : likes.getLikeSong().getId());
        likesDTO.setLikeUser(likes.getLikeUser() == null ? null : likes.getLikeUser().getId());
        return likesDTO;
    }

    private Likes mapToEntity(final LikesDTO likesDTO, final Likes likes) {
        likes.setStatus(likesDTO.getStatus());
        final Song likeSong = likesDTO.getLikeSong() == null ? null : songRepository.findById(likesDTO.getLikeSong())
                .orElseThrow(() -> new NotFoundException("likeSong not found"));
        likes.setLikeSong(likeSong);
        final User likeUser = likesDTO.getLikeUser() == null ? null : userRepository.findById(likesDTO.getLikeUser())
                .orElseThrow(() -> new NotFoundException("likeUser not found"));
        likes.setLikeUser(likeUser);
        return likes;
    }

}
