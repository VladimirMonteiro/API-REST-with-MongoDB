package com.outercode.ApiMongoDB.resources.exceptions;

import com.outercode.ApiMongoDB.services.exceptions.ObjectNotFoundException;
import com.outercode.ApiMongoDB.services.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;


import static org.junit.jupiter.api.Assertions.*;


class ResourceExceptionHandlerTest {


    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldBeReturnResponseEntityOfObjectNotFoundException() {
        ResponseEntity<StandardError> response = exceptionHandler
                .objectNotFound(new ObjectNotFoundException("User not found."), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals("User not found.", response.getBody().getMessage());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals("Não encontrado", response.getBody().getError());
    }

    @Test
    void shouldBeReturnResponseEntityOfUserAlreadyExists() {
        ResponseEntity<StandardError> response = exceptionHandler
                .userAlreadyExists(new UserAlreadyExistsException("User already exists."), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals("User already exists.", response.getBody().getMessage());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals("Already exists", response.getBody().getError());
    }
}