package com.ust.webmini.help;


import com.ust.webmini.user.User;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

// start the application and listen for a connection (as it would do in production)
// and then send an HTTP request and assert the response.

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HelpContollerIntergrationTest {

    // start the server with a random port (useful to avoid conflicts in test environments) and the injection of
    // the port with @LocalServerPort
    @LocalServerPort
    private int port;

    // Spring Boot has automatically provided a TestRestTemplate for you. All you have to do is add @Autowired to it.
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SomeService someService;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/help", String.class))
                .contains("You'd better learn Spring!");
    }

    @Test
    public void hibsernateServiceTest() {
        // ConstraintViolationException
        User user = new User("aa",null, "ADMIN", "whatever");
        someService.doStuff(user);
        assertThat(1).isEqualTo(1);
    }

    @Test
    public void doStuffshouldReturnConstraintViolationException() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/help/doStuff", String.class))
                .contains("Successfully entered help-doStuff");
    }

    @Test
    public void doStuffWithITEM_shouldReturnConstraintViolationException() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/help/item", String.class))
                .contains("Successfully entered help-item");
    }
}