package com.creditmusic.credit_music_rest_api.rest;

import com.creditmusic.credit_music_rest_api.model.EntityDTO;
import com.creditmusic.credit_music_rest_api.service.EntityService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/entitys", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntityResource {

    private final EntityService entityService;

    public EntityResource(final EntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping
    public ResponseEntity<List<EntityDTO>> getAllEntitys() {
        return ResponseEntity.ok(entityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityDTO> getEntity(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(entityService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEntity(@RequestBody @Valid final EntityDTO entityDTO) {
        final Long createdId = entityService.create(entityDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEntity(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final EntityDTO entityDTO) {
        entityService.update(id, entityDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEntity(@PathVariable(name = "id") final Long id) {
        entityService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
