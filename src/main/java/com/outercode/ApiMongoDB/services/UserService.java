package com.outercode.ApiMongoDB.services;

import com.outercode.ApiMongoDB.domain.User;
import com.outercode.ApiMongoDB.dto.UserDTO;
import com.outercode.ApiMongoDB.repositories.UserRepository;
import com.outercode.ApiMongoDB.services.exceptions.ObjectNotFoundException;
import com.outercode.ApiMongoDB.services.exceptions.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new ObjectNotFoundException("User not found."));
    }

    public User insert(User obj) {
        Optional<User> user = userRepository.findByEmail(obj.getEmail());

        if (user.isPresent()) throw new UserAlreadyExistsException("User already exists.");

        return userRepository.insert(obj);
    }

    public void delete(String id) {
        this.findById(id);
        userRepository.deleteById(id);
    }

    public User fromDTO(UserDTO obj) {
        return new User(obj.id(), obj.name(), obj.email());
    }

    public User update(User obj) {
        User newObj = this.findById(obj.getId());
        updatedData(newObj, obj);
        return userRepository.save(newObj);
    }

    private void updatedData(User newObj, User obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }
}
