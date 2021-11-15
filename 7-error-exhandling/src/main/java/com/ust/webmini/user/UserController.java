package com.ust.webmini.user;

import com.ust.webmini.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @Controller is a specialization of @Component
@RestController // @Controller plus @ResponseBody; >> returns a string or a domain object, not a view like a @Controller
// simply returns the object and object data is directly written into HTTP response as JSON or XML.

@RequestMapping("users") // careful with /
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    private UserPersistenceService userPersistenceService;

    public UserController(UserPersistenceService userPersistenceService) {
        this.userPersistenceService = userPersistenceService;
    }

    //------------------ GET  -----------------------------------

    @GetMapping // works for ("") & ("/") by default. Will not work if you set it here as ("/")
    public List<User> getAllUsers() {
        return userPersistenceService.getUsersFromDb();
    }

    //                                                 (=application/json)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable(name = "id") long userId) {
        logger.info("Displayed only if id is long. Entered GET users/{id} with userId: " + userId);
        // MethodArgumentTypeMismatchException (nested NumberFormatException): Failed to convert value of type 'java.lang.String' to required type 'long';

        //if(id>0)throw new UserNotFoundException();
        //problem1: what if id is not long?
        return userPersistenceService.getUserByIdFromDb(userId);
    }

    //------------------ POST, PUT, DELETE, PATCH  -----------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User postUser(@RequestBody User user) {
        // problem1: what if body is null or empty?
        // problem2: what if user has non-existant properties?
        User saved = userPersistenceService.saveUser(user);
        return saved;
    }

    @PutMapping // full resource
    @ResponseStatus(HttpStatus.OK) // 204 NO CONTENT for void
    public User updateUserPut(@RequestBody User user) {
        // problem1: what if body is null or empty?
        // problem2: what if user has non-existant properties?
        return userPersistenceService.updateUser(user);
    }

    @PatchMapping // some props to change
    @ResponseStatus(HttpStatus.OK) // 204 NO CONTENT for void
    public String updateUserPatch(@RequestParam long id, @RequestParam String username) {
        // find user with id and set new name
        return "not implemented for " + username;
    }

    @DeleteMapping(value = "/{id}") // careful with {...}
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 NO CONTENT for void
    public void deleteUser(@PathVariable(name = "id") long userId) {
        //problem1: what if id is not long?
        //console: [org.springframework.web.method.annotation.MethodArgumentTypeMismatchException:
        //Postman: "status": 400, "error": "Bad Request",
        userPersistenceService.deleteUser(userId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 NO CONTENT for void
    public void deleteAll() {
        userPersistenceService.deleteAllUsers();
    }

    @RequestMapping(path = "extra", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String showExtra(@RequestParam String text) {
        String content = "Here is your extra text: " + text;

        return "{  \"msg\":  \"" + content + "\" }";
    }

//////////////////// INFO ON ERROR & EXCEPTION HANDLING /////////////////////////////////////

    // Spring by default sends some information about the error in response body.
    // When we take control of handling the error, the response body will be EMPTY.

 /*   Note, that when we set reason, Spring calls HttpServletResponse.sendError().
   Therefore, it will send an HTML error page to the client, which makes it a bad fit for REST endpoints!!!
   */
// a major drawback – the @ExceptionHandler ->  annotated method is only active for that particular Controller,
// not globally for the entire application.
    /*We can work around this limitation by having all Controllers extend a Base Controller class – however, this can be
     a problem for applications where, for whatever reason, this isn't possible. For example, the Controllers may already
      extend from another base class which may be in another jar or not directly modifiable, or may themselves not be
      directly modifiable.*/


    ///////////// MY HANDLERS: ONE FOR USERNOTFOUNDEXCEPTION & ONE FOR ALL OTHER EXCEPTIONS /////////////

    // This will be activated when app throws UserNotFoundEception, eg for http://localhost:8080/users/12
    // Response will have http status 400 bad request
    @ExceptionHandler(UserNotFoundEception.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST) // overwrites @ResponseStatus(HttpStatus.NOT_FOUND) of UserNotFoundEception
    // @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "...")  It's ALREADY set in UserNotFoundEception.java
    public void handleUserNotFoundException() {
        logger.info("@@@@@@@@@@@@ SENDing 400(BAD_REQUEST) from handleUserNotFoundException()!!!");
    }


    // catches all exceptions but not those handled by us, so it will not work if app throws UserNotFoundException.
    // This will be activated when app throws NumberFormatException,
    // eg for http://localhost:8080/users/dupa
    // and will produce a response with status 500 INTERNAL_SERVER_ERROR.
    // As the exception is handled here, it doesn't go to HttpServletResponse.sendError(), so will not show error.html
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR) //500
    public void handleOtherExceptionsThanUserNotFoundEx() {
            logger.info(" >>>>>>>>>> SENDing 500(INTERNAL_SERVER_ERROR) from handleOtherExceptionsThanUserNotFoundEx");
    }

}
