package com.ust.webmini.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)

public class UserControllerWebMockUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // We use @MockBean to create and inject a mock
    private UserPersistenceService userPersistenceService;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    public void when_GETall_returnsListOfUsers() throws Exception {
        User alice = new User(1L, "alice@gmail.com", "pass123", "ADMIN", "alice");
        User bob = new User(2L, "elle@gmail.com", "pass123", "ADMIN", "bob");
        List<User> allUsers = Arrays.asList(alice, bob);

        when(userPersistenceService.getUsersFromDb())
                .thenReturn(allUsers);
        //.thenReturn(java.util.Collections.singletonList(user));
        //.thenReturn(Collections.emptyList());

        this.mockMvc.perform(
                get("/users")
                // declare json bin request body:
                // .contentType(MediaType.APPLICATION_JSON)
                // .content("{\"users\" : {{\"name\" : \"alex\"},{\"name\" : \"bob\"}}}")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2))) // list size 1
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("@.[0].username").isString())
                .andExpect(jsonPath("@.[0].username").isNotEmpty())
                .andExpect(jsonPath("@.[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("@.[0].username").value("alice"))
                .andExpect(MockMvcResultMatchers.jsonPath("@.[1].username", org.hamcrest.CoreMatchers.is("bob")))
                .andExpect(MockMvcResultMatchers.jsonPath("@.[0].password", org.hamcrest.CoreMatchers.notNullValue()))

                .andExpect(MockMvcResultMatchers.jsonPath("@.[0]", org.hamcrest.CoreMatchers.isA(LinkedHashMap.class))) // user json
                .andExpect(MockMvcResultMatchers.jsonPath("@.[0]", org.hamcrest.CoreMatchers.instanceOf(LinkedHashMap.class))); // user json
                //.andExpect(content().string(containsString("You'd better learn Spring!")));
    }

    @Test
    public void when_GETone_returnsUser() throws Exception {
        User alice = new User(1L, "newUser@gmail.com", "123", "USER", "alice");

        when(userPersistenceService.getUserByIdFromDb(1L))
                .thenReturn(alice);

        this.mockMvc.perform(
                // build the request here on htttpmethod()
                get("/users/1")
                        // declare json bin request body:
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(content().string(containsString("alice")));
    }

    @Test
    public void givenValidId_whenGETone_returnUser() throws Exception{
        //arrange
        User user = new User (2L,"krzychu@onet.pl", "DerekSzmitd", "ADMIN", "Pawelelek");
        String userJson = objectMapper.writeValueAsString(user);

        org.mockito.Mockito.when(userPersistenceService.getUserByIdFromDb(2L))
                .thenReturn(user);

        //act
        mockMvc.perform(
                get("/users/2")
                        .characterEncoding("utf-8")
        )
                //assert
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(content().string(containsString("Pawelelek")))
                .andExpect(content().string(userJson));
    }

    @Test
    public void givenUser_whenPOST_returnsSavedUser() throws Exception{
        //arrange
        User user = new User ("krzychu@onet.pl", "DerekSzmitd", "ADMIN", "Pawelelek");
        String clientUserAsJson = objectMapper.writeValueAsString(user);

        User savedUser = new User (1L,"krzychu@onet.pl", "DerekSzmitd", "ADMIN", "Pawelelek");
        String savedUserAsJson = objectMapper.writeValueAsString(savedUser);

        org.mockito.Mockito.when(userPersistenceService.saveUser(user))
                .thenReturn(savedUser);

        //act
        mockMvc.perform(
                post("/users")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(clientUserAsJson)
                 /*.content("{" +
                                "    \"email\":     \"newUser@gmail.com\"," +
                                "    \"password\":  \"123\"," +
                                "    \"role\":      \"USER\"," +
                                "    \"username\":  \"michael\"" +
                                "}")*/
        )

                //assert
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(content().string(savedUserAsJson));
    }

    @Test
    public void givenUser_whenPUT_returnsUpdatedUser() throws Exception{
        //arrange
        User user = new User (1L,"krzychu@onet.pl", "DerekSzmitd", "ADMIN", "Pawelelek Sagt");
        String userAsJson = objectMapper.writeValueAsString(user);

        org.mockito.Mockito.when(userPersistenceService.updateUser(user))
                .thenReturn(user);

        //act
        mockMvc.perform(
                put("/users")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(userAsJson)
        )

                //assert
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(content().string(userAsJson));
    }

    @Test
    public void givenInvalidId_whenGETone_returns400() throws Exception{
        //arrange
        long id = 132L;
        doThrow(new UserNotFoundEception("Cannot find user with id " + id))
                .when(userPersistenceService).getUserByIdFromDb(id);

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
    public void givenValidId_whenDelete_returns200() throws Exception{
        //arrange
        long id = 2L;
        doNothing().when(userPersistenceService).deleteUser(id);// when void return type

        //act
        mockMvc.perform(
                delete("/users/" + id)
                        .characterEncoding("utf-8")
        )

                //assert
                .andDo(print())
                .andExpect(status().isNoContent()); // 204
    }

    @Test
    public void givenInvalidId_whenDelete_returns400() throws Exception{
        //arrange
        long id = 132L;
        doThrow(new UserNotFoundEception("Cannot delete user with id " + id))
                .when(userPersistenceService).deleteUser(id);

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

    //-----------------------------------------------------
    @Test
    public void givenUser_whenPOST_returnsSavedUser_2_usingMvcResult() throws Exception{
        //arrange
        User user = new User ("krzychu@onet.pl", "DerekSzmitd", "ADMIN", "Pawelelek Sagt");
        String clientUserAsJson = objectMapper.writeValueAsString(user);

        User savedUser = new User (1L,"krzychu@onet.pl", "DerekSzmitd", "ADMIN", "Pawelelek Sagt");
        String savedUserAsJson = objectMapper.writeValueAsString(savedUser);

        org.mockito.Mockito.when(userPersistenceService.saveUser(user))
                .thenReturn(savedUser);

        //act
        MvcResult result = mockMvc.perform(
                post("/users")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(clientUserAsJson)
        ).andReturn();

        //assert
        // using MockHttpServletResponse response we can have direct access to the response by
        // result.getResponse() and we can make assertions just like we did with
        // .andExpect(...)
        System.out.println(result.getHandler()); // com.tomek.web_jpa_2.user.UserController#addUser(User)
        MockHttpServletResponse response =  result.getResponse();
        String body = response.getContentAsString();
        assertEquals(savedUserAsJson, body); // .andExpect(content().string(savedUserAsJson))
        System.out.println(response.getContentType());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        assertEquals("application/json",response.getHeader("Content-Type"));

		/*.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").exists())
		.andExpect(content().string(savedUserAsJson));*/

    }


    @Test
    public void givenValidId_whenGETone_returnUser_2_withMvcResult() throws Exception{
        //arrange
        User user = new User (2L,"krzychu@onet.pl", "DerekSzmitd", "ADMIN", "Pawelelek Sagt");

        org.mockito.Mockito.when(userPersistenceService.getUserByIdFromDb(2L))
                .thenReturn(user);

        //act
        MvcResult result = mockMvc.perform(
                get("/users/2")
                        .characterEncoding("utf-8")
        ).andReturn();

        MockHttpServletResponse response =  result.getResponse();
        String userAsJsonString = response.getContentAsString();
        User savedUser = objectMapper.readValue(userAsJsonString, User.class);
        assertEquals(user, savedUser);
        assertEquals(user.getId(), savedUser.getId());
        assertEquals(user.getPassword(), savedUser.getPassword());
        //assert
		/*.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(userJson));*/
    }












    @Test
    public void givenStringAsRequestParam_shouldReturnJSONString() throws Exception {

        this.mockMvc.perform(
                // build the request here on htttpmethod()
                get("/users/extra")
                        .param("text", "kury")
                        .contentType(MediaType.TEXT_PLAIN_VALUE)
                        .characterEncoding("utf-8")
        )
                // check repsonse here
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Length", "44"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value(("Here is your extra text: " + "kury")))
                .andExpect(content().json("{'msg':'Here is your extra text: kury'}"))
                .andExpect(content().string(containsString("Here is your extra text: " + "kury")));
    }
}
