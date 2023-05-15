package com.creditmusic.credit_music_api_app.model;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EntityDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String type;

    private List<Long> instrument;

}
