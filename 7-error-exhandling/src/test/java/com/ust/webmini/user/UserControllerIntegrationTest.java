package com.ust.webmini.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String USERS_ENDPOINT = "http://localhost:";

    private User user;
    private User savedUser;
    private Long savedUserId;

    @BeforeEach
    private void setup() {

        // my persistanceService pre-loads 3 users
        restTemplate.delete(USERS_ENDPOINT + port + "/users/");

        user = new User("koles@wodo.com", "pasdaf33", "ADMIN", "PolarZiemo");
        savedUser = restTemplate.postForObject(USERS_ENDPOINT + port + "/users", user, User.class);
        savedUserId = savedUser.getId();
    }

    @Test
    public void givenValidId_GETuser_returnsUser() throws Exception {
        // act
        User foundUser = restTemplate.getForObject(USERS_ENDPOINT + port + "/users/" + savedUserId, User.class);

        // assert
        assertEquals(foundUser.getUsername(), user.getUsername());
    }

    @Test
    public void givenValidId_GETuser_returnsUser_v2_withResponseEntity() throws Exception {
        // act
        ResponseEntity<User> responseEntity = restTemplate.getForEntity(USERS_ENDPOINT + port + "/users/" + savedUserId,
                User.class);

        // assert
        User foundUser = responseEntity.getBody();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(foundUser.getUsername(), user.getUsername());
    }

    @Test
    public void givenUser_POSTuser_returnsSavedUser() throws Exception {
        // arrange
        User newUser = new User("posrtf34@onet.com", "riczkWarsaz", "ADMIN", "Username85");

        // act
        User found = restTemplate.postForObject(USERS_ENDPOINT + port + "/users", newUser, User.class);

        // assert
        assertNotNull(found);
        assertNotNull(found.getId());
        assertEquals(newUser.getUsername(), found.getUsername());
    }

    @Test
    public void givenUser_PUTuser_returnsUpdatedUser() throws Exception {
        // arrange
        User updatedUser = new User(savedUserId, "koles@wodo.com", "pasdaf33", "ADMIN", "Ziemeczkox");

        // act
        restTemplate.put(USERS_ENDPOINT + port + "/users", updatedUser, User.class);

        // assert
        ResponseEntity<User> responseEntity = restTemplate.getForEntity(USERS_ENDPOINT + port + "/users/" + savedUserId,
                User.class);
        User found = responseEntity.getBody();

        assertNotNull(found);
        assertNotNull(found.getId());
        assertEquals(updatedUser.getId(), found.getId());
        assertEquals(updatedUser.getUsername(), found.getUsername());
        assertNotEquals(user.getUsername(), found.getUsername());
    }

    @Test
    public void whenGETallUsers_returnsListOfUsers_withResponseEntity() throws Exception {
        // arrange
        User secondUser = new User("truskMe@skype.de", "bramkaszCfel", "USER", "footBALLgod");
        restTemplate.postForObject(USERS_ENDPOINT + port + "/users", secondUser, User.class);

        // act
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(USERS_ENDPOINT + port + "/users/",
                User[].class);

        User[] usersArr = responseEntity.getBody();
        List<User> users = Arrays.asList(usersArr);
        List<User> list = Stream.of(usersArr).collect(Collectors.toList());

        // assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(list);
        assertTrue(list.size() >= 2);

        Assertions.assertThat(list).extracting("username").contains("footBALLgod", "PolarZiemo");

        Assertions.assertThat(list).extracting("email", "role", "username").contains(
                tuple("truskMe@skype.de", "USER", "footBALLgod"), tuple("koles@wodo.com", "ADMIN", "PolarZiemo"));
    }

    @Test
    public void givenValidId_DELETEuser_deletesUser() throws Exception {

        // arrange
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(USERS_ENDPOINT + port + "/users/",
                User[].class);

        User[] initialUsers = responseEntity.getBody();
        int numberOfUsersBeforeDelete = initialUsers.length;

        // act
        restTemplate.delete(USERS_ENDPOINT + port + "/users/" + savedUserId);

        // assert
        ResponseEntity<User[]> responseEntity2 = restTemplate.getForEntity(USERS_ENDPOINT + port + "/users/",
                User[].class);
        User[] usersArr = responseEntity2.getBody();
        List<User> listAfterDelete = Stream.of(usersArr).collect(Collectors.toList());

        assertEquals(listAfterDelete.size(), numberOfUsersBeforeDelete - 1);
        Assertions.assertThat(listAfterDelete).extracting("username").doesNotContain(user.getUsername());
    }
}
