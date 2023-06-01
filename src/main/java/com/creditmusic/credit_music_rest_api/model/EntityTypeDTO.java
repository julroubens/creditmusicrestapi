package com.creditmusic.credit_music_rest_api.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EntityTypeDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    private Boolean status;

}
