package org.example.frontend.seleniumIT;

import org.example.TestFrontendApplicationRunner;
import org.example.frontend.seleniumIT.pageobject.IndexPO;
import org.example.frontend.seleniumIT.pageobject.LoginPO;
import org.example.frontend.seleniumIT.pageobject.MovieDetailsPO;
import org.example.frontend.seleniumIT.pageobject.SignUpPO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestFrontendApplicationRunner.class, webEnvironment = RANDOM_PORT)
public class TestDefaultMoviesIT {

    private static final String INFO_MSG_ID = "infoMsgId";

    @LocalServerPort
    private int port;

    private IndexPO home;
    private MovieDetailsPO reviews;
    private LoginPO loginPO;
    private SignUpPO signUpPO;

    @BeforeEach
    public void init() {
        WebDriver driver = SeleniumDriverHandler.getChromeDriver();
        home = new IndexPO(driver, "http://localhost:", port);
        reviews = new MovieDetailsPO(driver, "http://localhost:", port);
        loginPO = new LoginPO(driver, "http://localhost:", port);
        signUpPO = new SignUpPO(driver, "http://localhost:", port);
    }

    @AfterEach
    public void afterTest() {
        home.driver.close();
    }

    @Test
    public void testDefaultMovies() {
        home.toStartingPage();

        assertTrue(home.isOnPage());

        String firstMovie = home.getText("movieTable:0:movieTitleId");
        String secondMovie = home.getText("movieTable:1:movieTitleId");
        String thirdMovie = home.getText("movieTable:2:movieTitleId");

        assertEquals("The Shawshank Redemption", firstMovie);
        assertEquals("The Godfather", secondMovie);
        assertEquals("The Dark Knight", thirdMovie);
    }

    @Test
    public void testWriteReview() {
        // Check is on correct page
        home.toStartingPage();
        assertTrue(home.isOnPage());

        // go to details of first movie
        home.clickAndWait("movieTable:0:detailsId");
        assertTrue(reviews.isOnPage());

        // Check if not logged in if cant see review text input
        assertFalse(reviews.elementExists("createText"));

        // Create new user and log in
        reviews.clickAndWait("signupBtnId");
        assertTrue(signUpPO.isOnPage());

        signUpPO.setText("usernameTextId", "Robot");
        signUpPO.setText("passwordTextId", "hunter2");

        signUpPO.clickAndWait("submitBtnId");
        assertTrue(home.isOnPage());

        // Go back to details
        home.clickAndWait("movieTable:0:detailsId");
        assertTrue(reviews.isOnPage());

        // Write a review
        reviews.setText("createReviewForm:createText", "Beep boop, im a robot!");
        reviews.clickAndWait("createReviewForm:createButton");

        // log out
        reviews.clickAndWait("logoutBtnId");

        // Go back to details
        reviews.clickAndWait("movieTable:0:detailsId");

        // Check if the new review is there
        assertEquals("Beep boop, im a robot!", reviews.getText("movieTable:0:reviewTextId"));
    }

    @Test
    public void testStars() {
        // Check is on correct page
        home.toStartingPage();
        assertTrue(home.isOnPage());

        // Check avg stars of first movie
        String oldAvgStars = home.getText("movieTable:0:avgStarsId");

        // Create new user and log in
        reviews.clickAndWait("signupBtnId");
        assertTrue(signUpPO.isOnPage());

        signUpPO.setText("usernameTextId", "Robot3000");
        signUpPO.setText("passwordTextId", "hunter2");

        signUpPO.clickAndWait("submitBtnId");
        assertTrue(home.isOnPage());

        // Go back to details
        home.clickAndWait("movieTable:0:detailsId");
        assertTrue(reviews.isOnPage());

        // Write a review
        reviews.setText("createReviewForm:createText", "Beep boop, im a robot!");
        reviews.chooseFromDropDown("createReviewForm:starsDropDownId", 4);

        reviews.clickAndWait("createReviewForm:createButton");

        // log out
        reviews.clickAndWait("logoutBtnId");


        // Check avg stars of first movie again
        String newAvgStars = home.getText("movieTable:0:avgStarsId");

        // Check if avg has changed
        assertTrue(Double.parseDouble(newAvgStars)<Double.parseDouble(oldAvgStars));
    }

    @Test
    public void testSorting() throws ParseException {
        home.toStartingPage();

        // Log in
        home.clickAndWait("loginBtnId");

        loginPO.setText("username", "User001");
        loginPO.setText("password", "123");

        loginPO.clickAndWait("submitBtnId");
        assertTrue(home.isOnPage());

        // Go to details
        home.clickAndWait("movieTable:2:detailsId");
        assertTrue(reviews.isOnPage());

        // Write a review
        reviews.setText("createReviewForm:createText", "Beep boop, im a robot!");
        reviews.chooseFromDropDown("createReviewForm:starsDropDownId", 3);

        reviews.clickAndWait("createReviewForm:createButton");

        // log out
        reviews.clickAndWait("logoutBtnId");

        // Log in
        home.clickAndWait("loginBtnId");

        loginPO.setText("username", "User002");
        loginPO.setText("password", "123");

        loginPO.clickAndWait("submitBtnId");
        assertTrue(home.isOnPage());


        // Go to details
        home.clickAndWait("movieTable:2:detailsId");
        assertTrue(reviews.isOnPage());

        // Write a review
        reviews.setText("createReviewForm:createText", "Beep boop, im also a robot!");
        reviews.chooseFromDropDown("createReviewForm:starsDropDownId", 0);

        reviews.clickAndWait("createReviewForm:createButton");

        // log out
        reviews.clickAndWait("logoutBtnId");

        // Log in
        home.clickAndWait("loginBtnId");

        loginPO.setText("username", "User003");
        loginPO.setText("password", "123");

        loginPO.clickAndWait("submitBtnId");
        assertTrue(home.isOnPage());

        // Go to details
        home.clickAndWait("movieTable:2:detailsId");
        assertTrue(reviews.isOnPage());

        // Write a review
        reviews.setText("createReviewForm:createText", "Beep boop, im not a robot!");
        reviews.chooseFromDropDown("createReviewForm:starsDropDownId", 4);

        reviews.clickAndWait("createReviewForm:createButton");

        // Sort by stars
        reviews.clickRadioButton("radioFormId:radioBtnsId:1");
        reviews.clickAndWait("radioFormId:radioSubmitBtnId");

        // Assert sorted
        String starsFirstReview = reviews.getText("movieTable:0:starTextId");
        String starsSecondReview = reviews.getText("movieTable:1:starTextId");
        String starsThirdReview = reviews.getText("movieTable:2:starTextId");

        assertTrue(Integer.parseInt(starsFirstReview) > Integer.parseInt(starsSecondReview));
        assertTrue(Integer.parseInt(starsSecondReview) > Integer.parseInt(starsThirdReview));


        // Sort by date
        reviews.clickRadioButton("radioFormId:radioBtnsId:0");
        reviews.clickAndWait("radioFormId:radioSubmitBtnId");
        // Assert sorted
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date firstDate = formatter.parse(reviews.getText("movieTable:0:dateId"));
        Date secondDate = formatter.parse(reviews.getText("movieTable:1:dateId"));
        Date thirdDate = formatter.parse(reviews.getText("movieTable:2:dateId"));


        assertTrue(firstDate.after(secondDate));
        assertTrue(secondDate.after(thirdDate));
    }

}

