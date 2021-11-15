package com.ust.webmini;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
public class Item {

    @NotNull
    String content;

    public Item(String content){
        this.content = content;
        System.out.println("---> Msg from Item constructor: you just created new Item()");
        System.out.println("---> ... and the content is " + this.content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
