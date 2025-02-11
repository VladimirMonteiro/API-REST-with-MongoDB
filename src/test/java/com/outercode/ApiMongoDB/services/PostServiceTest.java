package com.outercode.ApiMongoDB.services;

import com.outercode.ApiMongoDB.domain.Post;
import com.outercode.ApiMongoDB.dto.AuthorDTO;
import com.outercode.ApiMongoDB.repositories.PostRepository;
import com.outercode.ApiMongoDB.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    public static final String ID = "1";
    public static final Date DATE = new Date(1640995200000L);
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final AuthorDTO AUTHOR_DTO = new AuthorDTO("2", "Monteiro");

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    private Post post;
    private Optional<Post> optionalPost;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPost();
    }

    @Test
    void shouldBeReturnPostSuccess() {
        when(postRepository.findById(anyString())).thenReturn(optionalPost);

        Post response = postService.findById(ID);

        verify(postRepository, times(1)).findById(ID);
        assertNotNull(post);

        assertEquals(Post.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(DATE, response.getDate());
        assertEquals(TITLE, response.getTitle());
        assertEquals(BODY, response.getBody());
        assertEquals(AUTHOR_DTO, response.getAuthor());
    }

    @Test
    void shouldBeReturnObjectNotFoundIfPostNotFound() {
        when(postRepository.findById(anyString())).thenThrow(new ObjectNotFoundException("Post not found."));

        try {
            postService.findById(ID);

        } catch (Exception e) {

            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals("Post not found.", e.getMessage());
        }
    }


    void startPost() {
        this.post = new Post(ID, DATE, TITLE, BODY, AUTHOR_DTO);
        this.optionalPost = Optional.of(post);
    }
}