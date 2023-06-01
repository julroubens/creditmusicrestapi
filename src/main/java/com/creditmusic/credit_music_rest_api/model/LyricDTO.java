package com.creditmusic.credit_music_rest_api.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LyricDTO {

    private Long id;

    @Size(max = 255)
    private String title;

    private String lyric;

    private Boolean status;

    private Long language;

    private Long song;

}
