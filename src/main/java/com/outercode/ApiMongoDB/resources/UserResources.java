package com.outercode.ApiMongoDB.resources;

import com.outercode.ApiMongoDB.domain.User;
import com.outercode.ApiMongoDB.dto.UserDTO;
import com.outercode.ApiMongoDB.resources.exceptions.StandardError;
import com.outercode.ApiMongoDB.services.UserService;
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

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UserResources {

    private final UserService userService;

    public UserResources(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Find all users", description = "Return all users")
    @ApiResponse(responseCode = "200", description = "Users returned.")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> userDTO = userService.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(),user.getEmail())).toList();
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find user by id", description = "Return user using id")
    @ApiResponse(responseCode = "200", description = "User returned with successful.")
    @ApiResponse(responseCode = "404", description = "User not found.", content = @Content(schema = @Schema(implementation = StandardError.class)))
    public ResponseEntity<UserDTO> findById(@PathVariable("id") String id) {
        User user = userService.findById(id);
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
}
