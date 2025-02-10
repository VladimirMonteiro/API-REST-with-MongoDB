package com.outercode.ApiMongoDB.services;

import com.outercode.ApiMongoDB.domain.Post;
import com.outercode.ApiMongoDB.repositories.PostRepository;
import com.outercode.ApiMongoDB.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post findById(String id) {
        return postRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Post not found."));
    }
}
