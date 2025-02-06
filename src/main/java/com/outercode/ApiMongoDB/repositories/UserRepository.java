package com.outercode.ApiMongoDB.repositories;

import com.outercode.ApiMongoDB.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
}
