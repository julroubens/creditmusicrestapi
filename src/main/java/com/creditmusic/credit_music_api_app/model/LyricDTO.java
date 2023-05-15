package com.creditmusic.credit_music_api_app.model;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LyricDTO {

    private Long id;

    @Size(max = 255)
    private String title;

    private String lyric;

    private List<Long> language;

}
