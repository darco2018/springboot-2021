package com.ust.webmini.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/* when we mark an Exception class with @ResponseStatus,
Spring always calls HttpServletResponse.sendError(),
whether we set reason or not. */

/* As we have handlers for UserNotFoundEception i nUserController, the @ResponseStatus(HttpStatus.NOT_FOUND)
* will be overwritten in the handler @ResponseStatus(value= HttpStatus.BAD_REQUEST)
*
* So does it make sense to have @ResponseStatus(HttpStatus.NOT_FOUND)  here?
* Yes, because if UserNotFoundEception is thrown in some other controller, and that controller doesn't have a handler,
* the app will respond with @ResponseStatus(HttpStatus.NOT_FOUND) defined here below*/
@ResponseStatus(HttpStatus.NOT_FOUND) // when this exception is thrown, app will respond with this 404 status
public class UserNotFoundEception extends RuntimeException {

    Logger logger = LoggerFactory.getLogger(UserNotFoundEception.class);

    public UserNotFoundEception(String msg){
        super(msg); // msg is set in UserPErsistance class
        logger.info("%%%%%%%%%% Creating UserNotFoundEception");
    }
}
