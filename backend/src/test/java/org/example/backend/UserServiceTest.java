package org.example.backend;

import org.example.backend.entity.User;
import org.example.backend.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService = new UserService();


    @Test
    public void shouldEncryptPassword() {
        PasswordConfig passwordConfig = new PasswordConfig();
        String password = "passord123";
        String encoded = passwordConfig.passwordEncoder().encode(password);
        assertNotEquals(password, encoded);
    }

    @Test
    public void shouldCreateUserWithService() {
        boolean created = userService.createUser("person", "passord123");
        assertEquals(created, true);
    }

}
