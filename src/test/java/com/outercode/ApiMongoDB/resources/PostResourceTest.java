package com.outercode.ApiMongoDB.resources;

import com.outercode.ApiMongoDB.domain.Post;
import com.outercode.ApiMongoDB.dto.AuthorDTO;
import com.outercode.ApiMongoDB.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PostResourceTest {

    public static final String ID = "1";
    public static final Date DATE = new Date(1640995200000L);
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final AuthorDTO AUTHOR_DTO = new AuthorDTO("2", "Monteiro");

    @InjectMocks
    private PostResource postController;

    @Mock
    private PostService postService;

    private Post post;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPost();
    }

    @Test
    void findById() {
        when(postService.findById(anyString())).thenReturn(post);

        ResponseEntity<Post> response = postController.findById(ID);

        verify(postService, times(1)).findById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Post.class, response.getBody().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(DATE, response.getBody().getDate());
        assertEquals(TITLE, response.getBody().getTitle());
        assertEquals(BODY, response.getBody().getBody());
        assertEquals(AUTHOR_DTO, response.getBody().getAuthor());
    }

    void startPost() {
        this.post = new Post(ID, DATE, TITLE, BODY, AUTHOR_DTO);
    }
}