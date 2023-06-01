package com.creditmusic.credit_music_rest_api.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentDTO {

    private Long id;

    @Size(max = 255)
    private String content;

    private Boolean status;

    private Long song;

    private Long userComent;

}
