package com.outercode.ApiMongoDB.repositories;

import com.outercode.ApiMongoDB.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
