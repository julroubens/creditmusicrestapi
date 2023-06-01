package com.creditmusic.credit_music_rest_api.rest;

import com.creditmusic.credit_music_rest_api.model.CommentDTO;
import com.creditmusic.credit_music_rest_api.service.CommentService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentResource {

    private final CommentService commentService;

    public CommentResource(final CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(commentService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createComment(@RequestBody @Valid final CommentDTO commentDTO) {
        final Long createdId = commentService.create(commentDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CommentDTO commentDTO) {
        commentService.update(id, commentDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "id") final Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
