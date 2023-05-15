package com.creditmusic.credit_music_api_app.rest;

import com.creditmusic.credit_music_api_app.model.LanguageDTO;
import com.creditmusic.credit_music_api_app.service.LanguageService;
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
@RequestMapping(value = "/api/languages", produces = MediaType.APPLICATION_JSON_VALUE)
public class LanguageResource {

    private final LanguageService languageService;

    public LanguageResource(final LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity<List<LanguageDTO>> getAllLanguages() {
        return ResponseEntity.ok(languageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageDTO> getLanguage(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(languageService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLanguage(@RequestBody @Valid final LanguageDTO languageDTO) {
        final Long createdId = languageService.create(languageDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateLanguage(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LanguageDTO languageDTO) {
        languageService.update(id, languageDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLanguage(@PathVariable(name = "id") final Long id) {
        languageService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
