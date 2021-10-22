package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/* 1. Calls quote API at http://quotes.rest/qod.json
* and displays them at http://localhost:8080/api/quotes/daily
* */



@SpringBootApplication
public class RestTemp1 {

	/* Prior to Spring 5, there was RestTemplate for client-side HTTP access. RestTemplate, which is part
	of the Spring MVC project, enables communication with HTTP servers and enforces RESTful principles.

   Other options to perform HTTP operations from Spring Boot applications include the Apache HttpClient
   library. These options are based upon the Java Servlet API, which is blocking (aka not reactive).

   With Spring Framework 5, you now have a new reactive WebClient that provides a higher level,
   common API over HTTP client libraries*/

    /* RestTemplate uses JSON message converter to automatically unmarshall JSON into a simple Java object.
     * We need to model a Java class which models the response*/

    public static void main(String[] args) {
        SpringApplication.run(RestTemp1.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
