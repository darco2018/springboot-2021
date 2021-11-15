package com.ust.webmini.help;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExtraController {

    /////////////// EXAMPLE OF HOW WE CAN RETURN ResponsEentities with different Status Code depending on the value of RequestParam ///////////
    @GetMapping("/age") // age?yearOfBirth=
    ResponseEntity<String> age (@RequestParam("yearOfBirth") int yearOfBirth) {
        // org.springframework.web.bind.MissingServletRequestParameterException is thrown if you dont provide the param age?yearOfBirth=1967
        // age?yearOfBirth=dupa throws NumberFormatException  resources/templates/error.html
        // Both go to --> resources/templates/error.html
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");

        if (yearOfBirth > 2021 || yearOfBirth < 1900) {
            return new ResponseEntity<>(
                    "Year of birth cannot be in the future or so far in the past", headers,
                    HttpStatus.BAD_REQUEST);
        }
/*
You can build ResponseEntity with Builder too:
        ResponseEntity
                .status(HttpStatus.OK) // or  .badRequest()
                .header("Custom-Header", "foo")
                .body("Year of birth cannot be in the future"); // body() must be the last call!!!
*/

        return new ResponseEntity<>("Your age is " + (2021 - yearOfBirth), headers, HttpStatus.OK);
    }

}
