package org.chinmay.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import io.qameta.allure.Step;

/**
 * Page Object Model for Swag Labs Login Page
 */
public class LoginPage extends BasePage {

    // Page URL
    private static final String PAGE_URL = "https://www.saucedemo.com/";

    // Page Elements using @FindBy annotations
    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    @FindBy(className = "app_logo")
    private WebElement appLogo;

    @FindBy(className = "inventory_container")
    private WebElement inventoryContainer;

    /**
     * Constructor
     */
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigate to login page
     */
    @Step("Navigate to Login Page")
    public void navigateToLoginPage() {
        logger.info("Navigating to login page: " + PAGE_URL);
        driver.get(PAGE_URL);
    }

    /**
     * Enter username
     */
    public void enterUsername(String username) {
        type(usernameField, username);
    }

    /**
     * Enter password
     */
    public void enterPassword(String password) {
        type(passwordField, password);
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        click(loginButton);
    }

    /**
     * Perform login with username and password
     */
    @Step("Login with username: {0} and password: {1}")
    public void login(String username, String password) {
        logger.info("Attempting login with username: " + username);
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Get error message text
     */
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    /**
     * Check if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    /**
     * Check if login was successful by verifying inventory page is displayed
     */
    public boolean isLoginSuccessful() {
        try {
            return isDisplayed(inventoryContainer) && getCurrentUrl().contains("inventory.html");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if on login page
     */
    public boolean isOnLoginPage() {
        return isDisplayed(appLogo) && isDisplayed(loginButton);
    }
}
