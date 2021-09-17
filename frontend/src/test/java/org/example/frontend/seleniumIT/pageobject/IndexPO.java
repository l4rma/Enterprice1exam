package org.example.frontend.seleniumIT.pageobject;

import org.example.frontend.seleniumIT.PageObject;
import org.openqa.selenium.WebDriver;

public class IndexPO extends PageObject {


    public IndexPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public IndexPO(PageObject other) {
        super(other);
    }

    public void toStartingPage(){

        driver.get("http://localhost:"+port);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("The Movie Room");
    }
}
