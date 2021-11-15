package com.ust.webmini.user;

import com.ust.webmini.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserPersistenceService {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    private AtomicLong counter = new AtomicLong(1);
    private List<User> usersInMemoryDb = new ArrayList<>(Arrays.asList( // prevents List from being fixed-size
            new User(counter.getAndIncrement(), "elle@gmail.com", "pass123", "ADMIN", "Elle"),
            new User(counter.getAndIncrement(), "tracy@gmail.com", "pass123", "ADMIN", "Tracy"),
            new User(counter.getAndIncrement(), "Sam@gmail.com", "pass123", "ADMIN", "Sam")));


    public List<User> getUsersFromDb() {
        return usersInMemoryDb;
    }

    public User getUserByIdFromDb(long userId) {
        User user = _getValidUser(userId); // if invalid, throws UserNotFoundException
        return user;
    }
    // ------------ HELPERS FOR VALIDATION --------------------------

    // uniform response for all cases when user not found
    private User _getValidUser(long id) {

        try {
            for (User user : usersInMemoryDb) {
                if (user.getId() == id) {
                    return user;
                }
            }
        } catch (Exception e) { // catches db exceptions
            e.printStackTrace();

        }
        logger.info("UserNotFoundEception. Cannot retrieve from db user with id " + id + " passed in the URI");
        throw new UserNotFoundEception("UserNotFoundEception. Cannot retrieve from db user with id " + id + " passed in the URI");

    }
/* UNNECESSARY: Spring will throw MethodArgumentTypeMismatchException (nested NumberFormatException): Failed to convert value of type 'java.lang.String' to required type 'long';
   // Just use long on method parameter in controller
    private long _getValidIdAsLong(String userId) {

        try {
            return Long.parseLong(userId);
        } catch (NumberFormatException e) {
            logger.info("NumberFormatException. Invalid format of id passed in the URI: " + userId);

            throw new UserNotFoundEception("NumberFormatException. Invalid format of id passed in the URI: " + userId);
        }
    }*/

    private void validateUserOrThrowIllegalArgExc(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User passed in the repsnse body is null");
        }
    }

    public User saveUser(User user) {
        //  { "name": "Darek", "id": 1 } in Postman set: Body/raw/ application/json
        logger.info("-----> POST: Received user " + user);
        validateUserOrThrowIllegalArgExc(user);
        logger.info("-----> POST: user passed validation");

        user.setId(counter.getAndIncrement());
        usersInMemoryDb.add(user);
        return user;
    }

    public User updateUser(User user) {
        validateUserOrThrowIllegalArgExc(user);

        User userFromDb = _getValidUser(user.getId()); // if invalid, throws UserNotFoundException

        // can be replaced with repo.save(user)
        usersInMemoryDb.remove(userFromDb);
        userFromDb = user;
        usersInMemoryDb.add(userFromDb);
        return user;
    }

    public void deleteUser(long userId) {
        ;
        User user = _getValidUser(userId);
        usersInMemoryDb.remove(user);
    }

    public void deleteAllUsers() {
        usersInMemoryDb.clear();
    }
}
