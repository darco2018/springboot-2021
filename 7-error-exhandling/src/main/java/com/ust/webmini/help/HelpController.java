package com.ust.webmini.help;

import com.ust.webmini.Item;
import com.ust.webmini.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;

@Validated
//@RequiredArgsConstructor
@RestController
public class HelpController {
    @NonNull
    private final SomeService someService;

     public HelpController(SomeService someService){
         this.someService = someService;
     }

    @GetMapping("help")
    public String help() {
         return this.someService.getTip();
    }

    @GetMapping("help/{size}")
    public String help(@PathVariable @Max(10)  long size) {
         return "Size is " + size;
    }
    /* For size > 10 response is:
    "status": 500,
    "error": "Internal Server Error",
    "message": "help.size: must be less than or equal to 10"
    "trace": "javax.validation.ConstraintViolationException: help.size: must be less than or equal to 10
     */

    @GetMapping("help/doStuff")
    public String doStuff() {
        User user = new User("whatever@dom.com","sd", "ADMIN", "hj");
        // javax.validationConstraintViolationException: doStuff.user.password: must not be blank, doStuff.user.password: must not be null,
        // doStuff.user.email: must be a well-formed email address]
        someService.doStuff(user);

        return "Successfully entered help-doStuff"; // doStuff() will print User's email to console, but the returned String will
        //be returned as JSON because it's  @RestController
    }

    @GetMapping("help/item")
    public String item() {
        Item item = new Item(null);
        someService.doStuff(item);

        return "Successfully entered help-item";
    }

    /////////////

    @GetMapping("msg")
    public String method() {
        String aa = "aaaabbbcccccc";
        return "Welcome to users\\msg !!! 222222222222222222";
    }

    @PutMapping("onlyput") // browser should throw METHOD NOT ALLOWED, whicz will display resources/templates/error.html
    public String routeForPut() {
        return "Should display templates/error.html";
    }
}
