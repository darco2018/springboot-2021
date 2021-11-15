package com.ust.webmini.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ust.webmini.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
//@Transactional - use with JPA import org.springframework.transaction.annotation.Transactional;
public class UserControllerIntegrationNoServerTest {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserPersistenceService userPersistenceService;

    private User user1;
    private User user2;
    private String user1AsJson;
    private String user2AsJson;
    private long user1_ID;
    private long user2_ID;

    @BeforeEach
    public void setup() throws Exception {
    // ---- remove this part & replace with transactional in JPA ------------
        userPersistenceService.deleteAllUsers();
     // ---- remove this part & replace with transactional in JPA ------------

        user1 = new User("apKaisa5@gmail.com", "purpleStack", "ADMIN", "Kaisa Stronks");
        user2 = new User("YassQRAQAE@gmail.com", "Ctr7QAQAQAA", "USER", "Yasuo Wind");
        user1AsJson = asJsonString(user1);
        user2AsJson = asJsonString(user2);

        addUser(user1AsJson);
        addUser(user2AsJson);


        // ---- remove this part & replace with transactional in JPA ------------
        // careful: userPersistenceService pre-loads 3 users when starting app
        List<User> list = userPersistenceService.getUsersFromDb();

        user1_ID = list.get(0).getId();
        user2_ID = list.get(1).getId();
        // ---- remove this part & replace with transactional in JPA ------------
    }

    @Test
    public void givenInvalidId_DELETEuser_returns400() throws Exception {
        //arrange
        long id = 132L;

        //act
        mockMvc.perform(
                delete("/users/" + id)
                        .characterEncoding("utf-8")
        )
                //assert
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenGETallUsers_returnsListOfUsers() throws Exception {

        // int noOfUsersPreloadedFromFileAndCLRunner = 3; Tests are not transactional - keeps users added elsewhere

        // act
        mockMvc.perform(get("/users"))

                // assert
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$").exists())
                // .andExpect(jsonPath("$", hasSize(noOfUsersPreloadedFromFileAndCLRunner + 2)))
                .andExpect(MockMvcResultMatchers.content().string(containsString("Kaisa Stronks")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("Yasuo Wind")));
    }

    @Test
    public void givenInvalidId_GETuser_returns400() throws Exception {
        //arrange
        long id = 132L;

        //act
        mockMvc.perform(
                get("/users/" + id)
                        .characterEncoding("utf-8")
        )
                //assert
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void givenValidId_DELETEuser_returns204() throws Exception {
        //arrange

        //act
        mockMvc.perform(
                delete("/users/" + user1_ID)
                        .characterEncoding("utf-8")
        )
                //assert
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenUser_PUTuser_returnsUpdatedUser() throws Exception {
        //arrange
        User newUser = new User(user1_ID, "LeaugeOfDraven@gmail.com", "Ctr7QAQAQAA", "USER", "Yasuo Wind");
        String newUserAsJson = asJsonString(newUser);

        //act
        mockMvc.perform(
                put("/users")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(newUserAsJson)
        )

                //assert
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(content().string(newUserAsJson));
    }

    @Test
    public void givenValidId_GETuser_returnsTheSameUserEachTime_v2_withMvcResult() throws Exception {

        //act
        MvcResult result1 = mockMvc.perform(
                get("/users/" + user1_ID)
                        .characterEncoding("utf-8")
        ).andReturn();

        MvcResult result2 = mockMvc.perform(
                get("/users/" + user1_ID)
                        .characterEncoding("utf-8")
        ).andReturn();

        //assert
        MockHttpServletResponse response1 = result1.getResponse();
        String userAsJsonString1 = response1.getContentAsString();
        User savedUser1 = objectMapper.readValue(userAsJsonString1, User.class);

        MockHttpServletResponse response2 = result2.getResponse();
        String userAsJsonString2 = response2.getContentAsString();
        User savedUser2 = objectMapper.readValue(userAsJsonString2, User.class);

        assertEquals(savedUser1, savedUser2);
        assertEquals(savedUser1.getId(), savedUser2.getId());
        assertEquals(savedUser1.getPassword(), savedUser2.getPassword());
    }


    @Test
    public void givenDifferentIds_GETuser_shouldReturnCorrectUsersEachTime() throws Exception {

        // act
        MvcResult result_1 = mockMvc.perform(get("/users/" + user1_ID).characterEncoding("utf-8")).andReturn();
        MvcResult result_2 = mockMvc.perform(get("/users/" + user2_ID).characterEncoding("utf-8")).andReturn();

        MvcResult result_3 = mockMvc.perform(get("/users/" + user1_ID).characterEncoding("utf-8")).andReturn();
        MvcResult result_4 = mockMvc.perform(get("/users/" + user2_ID).characterEncoding("utf-8")).andReturn();

        // assert
        User user1 = objectMapper.readValue(result_1.getResponse().getContentAsString(), User.class);
        User user3 = objectMapper.readValue(result_3.getResponse().getContentAsString(), User.class);
        User user2 = objectMapper.readValue(result_2.getResponse().getContentAsString(), User.class);

        User user4 = objectMapper.readValue(result_4.getResponse().getContentAsString(), User.class);

        assertEquals(user2, user4);
        assertEquals(user1, user3);
        assertEquals(2, 2);
    }

    // ------------------helpers---------------------
    private String asJsonString(User user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }

    }

    private void addUser(String user) {
        try {
            mockMvc.perform(post("/users")
                    .characterEncoding("utf-8")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(user));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
