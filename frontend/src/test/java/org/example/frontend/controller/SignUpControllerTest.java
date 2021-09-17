package org.example.frontend.controller;

import org.example.TestFrontendApplicationRunner;
import org.example.frontend.controller.SignUpController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestFrontendApplicationRunner.class)
public class SignUpControllerTest {

    @Autowired
    SignUpController signUpController = new SignUpController();

    @Test
    public void shouldSignUpUser() {
        // will redirect to "/index.jsf?faces-redirect=true" if success!
        String username = "User";
        String password = "password";

        signUpController.setUsername(username);
        signUpController.setPassword(password);

        String url = signUpController.signUpUser();

        assertEquals("/index.jsf?faces-redirect=true", url);
    }

    @Test
    public void shouldSignUpCorrectUserNameAndPassword() {
        // will redirect to "/index.jsf?faces-redirect=true" if success!
        String username = "User2";
        String password = "password2";

        signUpController.setUsername(username);
        signUpController.setPassword(password);

        assertEquals("User2", signUpController.getUsername());
        assertEquals("password2", signUpController.getPassword());

        signUpController.signUpUser();
    }

    @Test
    public void shouldNotSignUpUserWithNoUsernameOrPassword() {
        // will redirect to "/signup.jsf?faces-redirect=true&error=true" if not success!
        String username = "";
        String password = "";

        signUpController.setUsername(username);
        signUpController.setPassword(password);

        String url = signUpController.signUpUser();

        assertEquals("/signup.jsf?faces-redirect=true&error=true", url);
    }
}
