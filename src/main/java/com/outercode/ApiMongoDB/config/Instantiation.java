package com.outercode.ApiMongoDB.config;

import com.outercode.ApiMongoDB.domain.User;
import com.outercode.ApiMongoDB.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Instantiation implements CommandLineRunner {

    private final UserRepository userRepository;

    public Instantiation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        userRepository.deleteAll();

        User maria = new User(null, "Maria souza", "maria@gmail.com");
        User joao = new User(null, "Joao souza", "Joao@gmail.com");
        User lucas = new User(null, "Lucas souza", "Lucas@gmail.com");
        userRepository.saveAll(Arrays.asList(maria, joao, lucas));
    }
}
