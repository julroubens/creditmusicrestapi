package com.creditmusic.credit_music_rest_api.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LikesDTO {

    private Long id;
    private Boolean status;
    private Long likeSong;
    private Long likeUser;

}
