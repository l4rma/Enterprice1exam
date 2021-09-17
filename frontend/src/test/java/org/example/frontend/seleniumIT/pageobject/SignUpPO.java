package org.example.frontend.seleniumIT.pageobject;

import org.example.frontend.seleniumIT.PageObject;
import org.openqa.selenium.WebDriver;

public class SignUpPO extends PageObject {
    public SignUpPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public SignUpPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("The Movie Room : Sign up");
    }
}
