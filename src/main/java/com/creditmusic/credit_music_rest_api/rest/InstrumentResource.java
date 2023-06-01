package com.creditmusic.credit_music_rest_api.rest;

import com.creditmusic.credit_music_rest_api.model.InstrumentDTO;
import com.creditmusic.credit_music_rest_api.service.InstrumentService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/instruments", produces = MediaType.APPLICATION_JSON_VALUE)
public class InstrumentResource {

    private final InstrumentService instrumentService;

    public InstrumentResource(final InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    @GetMapping
    public ResponseEntity<List<InstrumentDTO>> getAllInstruments() {
        return ResponseEntity.ok(instrumentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstrumentDTO> getInstrument(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(instrumentService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createInstrument(
            @RequestBody @Valid final InstrumentDTO instrumentDTO) {
        final Long createdId = instrumentService.create(instrumentDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateInstrument(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final InstrumentDTO instrumentDTO) {
        instrumentService.update(id, instrumentDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteInstrument(@PathVariable(name = "id") final Long id) {
        instrumentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
