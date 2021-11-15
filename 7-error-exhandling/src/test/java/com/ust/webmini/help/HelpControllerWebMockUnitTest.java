package com.ust.webmini.help;

import com.ust.webmini.help.HelpController;
import com.ust.webmini.help.SomeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*Spring Boot instantiates only the web layer rather than the whole context.
In an application with multiple controllers, you can even ask for only one to be instantiated by using,
for example, @WebMvcTest(HomeController.class).*/

/*If we leave away the controllers parameter, Spring Boot will include all controllers in the application context.
Thus, we need to include or mock away all beans any controller depends on. This makes for a much more complex test
 setup with more dependencies, but saves runtime since all controller tests will re-use the same application context.
 */

//As of Spring Boot 2.1, we no longer need to load the SpringExtension because it's included as a meta
// annotation in the Spring Boot test annotations like @DataJpaTest, @WebMvcTest, and @SpringBootTest.
@WebMvcTest(HelpController.class)
public class HelpControllerWebMockUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // We use @MockBean to create and inject a mock
    private SomeService someService;

    //private SomeService someService = Mockito.mock(SomeService.class);
    //private HelpController helpController = new HelpController(someService);

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        when(someService.getTip())
                .thenReturn("You'd better learn Spring!");

        this.mockMvc.perform( get("/help")  )
                // response part
                .andDo(print())
                 .andExpect(status().isOk())
                //.andExpect(MockMvcResultMatchers.jsonPath() / cookie() / handler() / model() / forwarded/redirectedUrl() /view() / request() / header() / flash() /
               .andExpect(content().string(containsString("You'd better learn Spring!")));
    }

}