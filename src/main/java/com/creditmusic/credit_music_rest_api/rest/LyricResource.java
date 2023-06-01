package com.creditmusic.credit_music_rest_api.rest;

import com.creditmusic.credit_music_rest_api.model.LyricDTO;
import com.creditmusic.credit_music_rest_api.service.LyricService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/lyrics", produces = MediaType.APPLICATION_JSON_VALUE)
public class LyricResource {

    private final LyricService lyricService;

    public LyricResource(final LyricService lyricService) {
        this.lyricService = lyricService;
    }

    @GetMapping
    public ResponseEntity<List<LyricDTO>> getAllLyrics() {
        return ResponseEntity.ok(lyricService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LyricDTO> getLyric(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(lyricService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLyric(@RequestBody @Valid final LyricDTO lyricDTO) {
        final Long createdId = lyricService.create(lyricDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateLyric(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LyricDTO lyricDTO) {
        lyricService.update(id, lyricDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLyric(@PathVariable(name = "id") final Long id) {
        lyricService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
