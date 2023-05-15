package com.creditmusic.credit_music_api_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentDTO {

    private Long id;

    @Size(max = 255)
    private String content;

    private Long song;

    private Long userComent;

}
