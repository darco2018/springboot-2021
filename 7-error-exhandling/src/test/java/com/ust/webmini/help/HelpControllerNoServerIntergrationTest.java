package com.ust.webmini.help;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/*Another useful approach is to not start the server at all but to test only the layer below that,
where Spring handles the incoming HTTP request and hands it off to your controller. That way,
almost of the full stack is used, and your code will be called in exactly the same way as if
it were processing a real HTTP request but without the cost of starting the server.
To do that, use Spring’s MockMvc and ask for that to be injected for you by using the @AutoConfigureMockMvc
 on the test case. */

@SpringBootTest
@AutoConfigureMockMvc // !!!
// In this test, the full Spring application context is started but without the server.
// /We can narrow the tests to only the web layer by using @WebMvcTest
public class HelpControllerNoServerIntergrationTest {

    @Autowired
    private MockMvc mockMvc; // replaces TestRestTemplate; server/real HTTP request is mocked

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/help"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andExpect(MockMvcResultMatchers.jsonPath() / cookie() / handler() / model() / forwarded/redirectedUrl() /view() / request() / header() / flash() /
                .andExpect(MockMvcResultMatchers.content().string(containsString("You'd better learn Spring!")));
    }
}
