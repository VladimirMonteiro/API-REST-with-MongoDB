package com.outercode.ApiMongoDB.repositories;

import com.outercode.ApiMongoDB.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
