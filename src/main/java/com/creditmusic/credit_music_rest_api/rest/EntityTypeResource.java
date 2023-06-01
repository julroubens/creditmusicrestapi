package com.creditmusic.credit_music_rest_api.rest;

import com.creditmusic.credit_music_rest_api.model.EntityTypeDTO;
import com.creditmusic.credit_music_rest_api.service.EntityTypeService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/entityTypes", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntityTypeResource {

    private final EntityTypeService entityTypeService;

    public EntityTypeResource(final EntityTypeService entityTypeService) {
        this.entityTypeService = entityTypeService;
    }

    @GetMapping
    public ResponseEntity<List<EntityTypeDTO>> getAllEntityTypes() {
        return ResponseEntity.ok(entityTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityTypeDTO> getEntityType(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(entityTypeService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEntityType(
            @RequestBody @Valid final EntityTypeDTO entityTypeDTO) {
        final Long createdId = entityTypeService.create(entityTypeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEntityType(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final EntityTypeDTO entityTypeDTO) {
        entityTypeService.update(id, entityTypeDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEntityType(@PathVariable(name = "id") final Long id) {
        entityTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
