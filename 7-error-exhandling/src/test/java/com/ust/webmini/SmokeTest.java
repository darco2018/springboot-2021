package com.ust.webmini;

import com.ust.webmini.user.UserController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmokeTest {

	@Autowired
	private UserController userController;

	@Test
	void contextLoads() {
	}

	@Test
	void userController_ok() {
		Assertions.assertThat(userController).isNotNull();
	}

}
