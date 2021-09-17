package org.example.frontend.seleniumIT.pageobject;

import org.example.frontend.seleniumIT.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class MovieDetailsPO extends PageObject {


    public MovieDetailsPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public MovieDetailsPO(PageObject other) {
        super(other);
    }

    public void chooseFromDropDown(String id, int index) {
        Select dropdown = new Select(driver.findElement(By.id(id)));
        dropdown.selectByIndex(index);
    }

    public void clickRadioButton(String id) {
        WebElement radio = driver.findElement(By.id(id));

        radio.click();
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("The Movie Room : Reviews");
    }
}
