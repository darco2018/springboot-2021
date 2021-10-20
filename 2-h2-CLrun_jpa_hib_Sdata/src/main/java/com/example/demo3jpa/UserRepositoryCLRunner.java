package com.example.demo3jpa;

import com.example.demo3jpa.entity.User;
import com.example.demo3jpa.service.UserDAOService;
import com.example.demo3jpa.service.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Order(2)
@Component // will be managed by Spring
public class UserRepositoryCLRunner implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(UserRepositoryCLRunner.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args)  {
        logger.info("@@@@@@@@ ------ Application started with command-line arguments: {} . \n " +
                "To kill this application, press Ctrl + C.", Arrays.toString(args));

        // Operation 1: save ------------------------------------------
        User user = new User("Randolph From-UserRepositoryCLRunner", "Dev");
        // returns user;  save() can be used for create & update
        user = userRepository.save(user); // JpaRepository doesnt have persist() method
        logger.info("New user created by UserRepositoryCLRunner: " + user); // user.toString() is called


        // operation 2: findById --------------------------------------
        Optional<User> found = userRepository.findById(1L);
        logger.info("------------------ User retrieved with findById(): " + found); // Optional[User{id=1, name='Jack'}]

        Optional<User> notFound = userRepository.findById(5L);
        logger.info("------------------ User NOT retrieved with findById(): " + notFound); // Optional.empty


        // operation 3: findAll --------------------------------------
        List<User> users = userRepository.findAll();
        users.forEach(u -> System.out.println("---------xyz----------> " + u));
        users.forEach(System.out::println);
    }
}
