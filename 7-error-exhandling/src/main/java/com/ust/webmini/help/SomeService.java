package com.ust.webmini.help;

import com.ust.webmini.Item;
import com.ust.webmini.user.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
@Service
public class SomeService {
    public String getTip() {
        return "You'd better learn Spring!";
    }

    public void doStuff(@Valid User user) {
        System.out.println("email: " + user.getEmail());
        /*
        // add BindingResult result as method parama in controller
        if(result.hasErrors()) {
            System.out.println("ERROR email" + user.getEmail());
        } else {
            System.out.println("OK email" + user.getEmail());
        }*/

    }

    public void doStuff(@Valid Item item) {
        System.out.println("SERVICE with @Valid Item. ITEM's content is : " + item.getContent());

    }
}
