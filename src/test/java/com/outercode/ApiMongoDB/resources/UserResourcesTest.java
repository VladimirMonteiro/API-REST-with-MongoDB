package com.outercode.ApiMongoDB.resources;

import com.outercode.ApiMongoDB.domain.User;
import com.outercode.ApiMongoDB.dto.UserDTO;
import com.outercode.ApiMongoDB.resources.exceptions.StandardError;
import com.outercode.ApiMongoDB.services.UserService;
import com.outercode.ApiMongoDB.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
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

    private User user;
    private UserDTO userDTO;

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
    void insert() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void findPosts() {
    }

    private void startUser() {
        this.user = new User(ID, NAME, EMAIL);
        this.userDTO = new UserDTO(ID, NAME, EMAIL);
    }
}