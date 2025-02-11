package com.outercode.ApiMongoDB.resources;

import com.outercode.ApiMongoDB.domain.Post;
import com.outercode.ApiMongoDB.domain.User;
import com.outercode.ApiMongoDB.dto.AuthorDTO;
import com.outercode.ApiMongoDB.dto.UserDTO;

import com.outercode.ApiMongoDB.services.PostService;
import com.outercode.ApiMongoDB.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


class UserResourcesTest {


    public static final String ID = "1";
    public static final String NAME = "Vladimir";
    public static final String EMAIL = "vladimir@gmail.com";

    @InjectMocks
    private UserResources userController;

    @Mock
    private UserService userService;

    @Mock
    private PostService postService;

    private User user;
    private UserDTO userDTO;
    private Post post;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void shouldBeReturnAListOfUserDTO() {
        when(userService.findAll()).thenReturn(List.of(user));

        ResponseEntity<List<UserDTO>> response = userController.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());

        verify(userService, times(1)).findAll();
        assertEquals(1, response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertInstanceOf(List.class, response.getBody());
        assertEquals(UserDTO.class, response.getBody().getFirst().getClass());

        assertEquals(ID, response.getBody().getFirst().id());
        assertEquals(NAME, response.getBody().getFirst().name());
        assertEquals(EMAIL, response.getBody().getFirst().email());
    }

    @Test
    void shouldBeFindUserByIdReturnSuccess() {
        when(userService.findById(anyString())).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().id());
        assertEquals(NAME, response.getBody().name());
        assertEquals(EMAIL, response.getBody().email());
    }


    @Test
    void shouldBeReturnSuccessThenInsertAUser() {
        // Simulando o contexto da requisição HTTP
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/users/insert");  // Use a URL correta para seu caso
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // Defina o comportamento do mock do UserService
        User user = new User(); // Supondo que você tenha uma instância de User aqui
        user.setId(ID);
        when(userService.insert(any())).thenReturn(user);

        ResponseEntity<Void> response = userController.insert(userDTO);

        // Verifica se o método insert foi chamado
        verify(userService, times(1)).insert(any());

        // Verifica se a resposta foi 201 CREATED e a URI foi gerada corretamente
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        URI location = response.getHeaders().getLocation();
        assertNotNull(location);
        assertEquals("/users/insert/1", location.getPath()); // Verifica se o URI foi construído corretamente
    }

    @Test
    void delete() {
        doNothing().when(userService).delete(anyString());

        ResponseEntity<Void> response = userController.delete(ID);

        verify(userService, times(1)).delete(ID);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
    }

    @Test
    void shouldBeReturnSuccessUpdate() {
        when(userService.update(any())).thenReturn(user);
        when(userService.fromDTO(any())).thenReturn(user);

        ResponseEntity<Void> response = userController.update(userDTO, ID);

       verify(userService, times(1)).update(user);
       verify(userService, times(1)).fromDTO(userDTO);

       assertNotNull(response);
       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertEquals(ResponseEntity.class, response.getClass());
    }

    @Test
    void findPosts() {
        when(userService.findById(anyString())).thenReturn(user);

        ResponseEntity<List<Post>> response = userController.findPosts(ID);

        verify(userService, times(1)).findById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertInstanceOf(List.class, response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(ResponseEntity.class, response.getClass());

        assertEquals(ID, response.getBody().getFirst().getId());
        assertEquals(new Date(1640995200000L), response.getBody().getFirst().getDate());
        assertEquals("title", response.getBody().getFirst().getTitle());
        assertEquals("body", response.getBody().getFirst().getBody());
        assertEquals(new AuthorDTO(ID, "Vladimir"), response.getBody().getFirst().getAuthor());
    }

    private void startUser() {
        this.user = new User(ID, NAME, EMAIL);
        this.userDTO = new UserDTO(ID, NAME, EMAIL);
        long specificMillis = 1640995200000L; // Data: 01/01/2022 00:00:00
        this.post = new Post(ID, new Date(specificMillis), "title", "body", new AuthorDTO(ID, "Vladimir"));
        user.setPosts(List.of(post));
    }
}