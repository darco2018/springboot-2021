package com.ust.webmini.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// @entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class User {

    // @id & Generationtype
    private Long id;

   @Email @NotNull @NotBlank
    private String email;

    @NotNull @NotBlank
    private String password;

    @NotNull
    private String role;

    @NotNull @NotBlank
    private String username;

    @Valid
    public User(String email, String password, String role, String username) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.username = username;
    }



}


