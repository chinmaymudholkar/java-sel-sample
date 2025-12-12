package org.chinmay.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

/**
 * Base Page class containing common methods for all page objects
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger logger = LogManager.getLogger(this.getClass());

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Wait for element to be visible
     */
    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element to be clickable
     */
    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Click on element
     */
    /**
     * Get element name using reflection
     */
    private String getElementName(WebElement element) {
        try {
            for (java.lang.reflect.Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object fieldValue = field.get(this);
                if (fieldValue == element) {
                    return field.getName();
                }
            }
        } catch (Exception e) {
            logger.debug("Error getting element name via reflection", e);
        }
        return "element";
    }

    /**
     * Click on element
     */
    @Step("Click on element")
    protected void click(WebElement element) {
        String elementName = getElementName(element);
        io.qameta.allure.Allure.getLifecycle().updateStep(step -> step.setName("Click on element: " + elementName));
        waitForElementToBeClickable(element);
        logger.info("Clicking: " + elementName);
        element.click();
    }

    /**
     * Type text into element
     */
    @Step("Type '{1}' into element")
    protected void type(WebElement element, String text) {
        String elementName = getElementName(element);
        io.qameta.allure.Allure.getLifecycle()
                .updateStep(step -> step.setName("Type '" + text + "' into element: " + elementName));
        waitForElementToBeClickable(element);
        element.clear();
        logger.info("Typing: '" + text + "' into " + elementName);
        element.sendKeys(text);
    }

    /**
     * Get text from element
     */
    protected String getText(WebElement element) {
        waitForElementToBeVisible(element);
        return element.getText();
    }

    /**
     * Check if element is displayed
     */
    protected boolean isDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
