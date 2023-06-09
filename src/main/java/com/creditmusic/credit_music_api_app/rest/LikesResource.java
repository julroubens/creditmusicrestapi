package com.creditmusic.credit_music_api_app.rest;

import com.creditmusic.credit_music_api_app.model.LikesDTO;
import com.creditmusic.credit_music_api_app.service.LikesService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/likess", produces = MediaType.APPLICATION_JSON_VALUE)
public class LikesResource {

    private final LikesService likesService;

    public LikesResource(final LikesService likesService) {
        this.likesService = likesService;
    }

    @GetMapping
    public ResponseEntity<List<LikesDTO>> getAllLikess() {
        return ResponseEntity.ok(likesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LikesDTO> getLikes(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(likesService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLikes(@RequestBody @Valid final LikesDTO likesDTO) {
        final Long createdId = likesService.create(likesDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateLikes(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LikesDTO likesDTO) {
        likesService.update(id, likesDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLikes(@PathVariable(name = "id") final Long id) {
        likesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
