package com.outercode.ApiMongoDB.resources;

import com.outercode.ApiMongoDB.domain.Post;
import com.outercode.ApiMongoDB.resources.exceptions.StandardError;
import com.outercode.ApiMongoDB.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/posts", produces = "application/json")
public class PostResource {

    private final PostService postService;

    public PostResource(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find post by id", description = "Return post using id.")
    @ApiResponse(responseCode = "200", description = "Returned post")
    @ApiResponse(responseCode = "404", description = "Post not found.", content = @Content(schema = @Schema(implementation = StandardError.class)))
    public ResponseEntity<Post> findById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.findById(id));
    }
}
