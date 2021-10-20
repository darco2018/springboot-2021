package com.example.demo3jpa;

import com.example.demo3jpa.entity.User;
import com.example.demo3jpa.service.UserDAOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component // will be managed by Spring
public class UserServiceDaoCLRunner implements CommandLineRunner {
    @Autowired
    private UserDAOService userDAOService;

    private static Logger logger = LoggerFactory.getLogger(UserServiceDaoCLRunner.class);

    @Override
    public void run(String... args)  {
        User user = new User("Jack From-UserServiceDaoCLRunner","Admin");
        long id = userDAOService.insert(user);
        logger.info("New user created by UserServiceDaoCLRunner: " + user); // user.toString() is called
    }
}

/* TRANSACTION (inserting new user)
* Creating new transaction with name [com.example.demo3jpa.service.UserDAOService.insert]
* Opened new EntityManager [SessionImpl(302207211<open>)] for JPA transaction
* Exposing JPA transaction as JDBC
* Initiating transaction commit
* Committing JPA transaction on EntityManager [SessionImpl(302207211<open>)]
* Closing JPA EntityManager [SessionImpl(302207211<open>)] after transaction
* sout: New user created: User{id=2, name='Jack', role='Admin'}
* */