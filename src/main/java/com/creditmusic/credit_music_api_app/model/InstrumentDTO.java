package com.creditmusic.credit_music_api_app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InstrumentDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String img;

}
