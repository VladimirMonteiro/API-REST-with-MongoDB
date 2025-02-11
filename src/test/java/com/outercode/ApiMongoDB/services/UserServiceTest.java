package com.outercode.ApiMongoDB.services;

import com.outercode.ApiMongoDB.domain.User;
import com.outercode.ApiMongoDB.dto.UserDTO;
import com.outercode.ApiMongoDB.repositories.UserRepository;
import com.outercode.ApiMongoDB.services.exceptions.ObjectNotFoundException;
import com.outercode.ApiMongoDB.services.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserServiceTest {

    public static final String ID = "1";
    public static final String NAME = "Vladimir";
    public static final String EMAIL = "vladimir@gmail.com";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void shouldBeReturnOneListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> response = userService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.getFirst().getClass());
        assertEquals(ID, response.getFirst().getId());
        assertEquals(NAME, response.getFirst().getName());
        assertEquals(EMAIL, response.getFirst().getEmail());
    }

    @Test
    void shouldBeReturnOneUserInstance() {
        when(userRepository.findById(anyString())).thenReturn(optionalUser);

        User response = userService.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());

    }

    @Test
    void shouldBeReturnObjectNotFoundException() {
        when(userRepository.findById(anyString())).thenThrow(new ObjectNotFoundException("User not found."));

        try {
            userService.findById(ID);

        } catch (Exception e) {
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals("User not found.", e.getMessage());
        }
    }

    @Test
    void shouldBeInsertANewUser() {
        when(userRepository.insert((User) any())).thenReturn(user);

        User response = userService.insert(user);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(NAME, response.getName());
        assertEquals(ID, response.getId());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void shouldBeReturnUserAlreadyExistsExceptionCaseUserAlreadyExists() {
        when(userRepository.findByEmail(anyString())).thenThrow(new UserAlreadyExistsException("User already exists."));

        try {
            userService.insert(user);

        } catch (Exception e) {

            assertEquals(UserAlreadyExistsException.class, e.getClass());
            assertEquals("User already exists.", e.getMessage());

        }
    }

    @Test
    void shouldBeDeleteUser() {
        doNothing().when(userRepository).deleteById(anyString());
        when(userRepository.findById(anyString())).thenReturn(optionalUser);

        userService.delete(ID);
        verify(userRepository, times(1)).findById(ID);
        verify(userRepository, times(1)).deleteById(ID);
    }

    @Test
    void shouldBeReturnObjectNotFoundExceptionIfUserNotFound() {
        when(userRepository.findById(anyString())).thenThrow(new ObjectNotFoundException("User not found."));

        try {

            userService.delete(ID);

        } catch (Exception e) {
            verify(userRepository, times(1)).findById(ID);
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals("User not found.", e.getMessage());

        }
    }

    @Test
    void shouldBeReturnOneInstanceOfUser() {

        User response = userService.fromDTO(userDTO);
        assertNotNull(response);

        assertEquals(response.getClass(), User.class);
        assertEquals(response.getName(), NAME);
        assertEquals(ID, response.getId());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void shouldBeUpdateUser() {
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        User response = userService.update(user);

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).save(user);
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(NAME, response.getName());
        assertEquals(ID, response.getId());
        assertEquals(EMAIL, response.getEmail());
    }

    private void startUser() {
        this.user = new User(ID, NAME, EMAIL);
        this.optionalUser = Optional.of(user);
        this.userDTO = new UserDTO(ID, NAME, EMAIL);
    }
}