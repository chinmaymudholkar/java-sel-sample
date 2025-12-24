package org.chinmay.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryPage extends BasePage {

    @FindBy(css = "#react-burger-menu-btn")
    private WebElement burgerMenu;

    @FindBy(css = "#logout_sidebar_link")
    private WebElement logoutLink;

    public InventoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Logging out")
    public void performLogout() {
        click(burgerMenu);
        waitForSeconds(2);
        click(logoutLink);
    }
}
