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
    @Step("Click on element: {0}")
    protected void click(WebElement element) {
        waitForElementToBeClickable(element);
        try {
            String text = element.getText();
            // If text is empty, try to get value or just log "element"
            if (text.isEmpty()) {
                text = element.getAttribute("value");
            }
            if (text == null || text.isEmpty()) {
                text = "element";
            }
            logger.info("Clicking on: " + text);
        } catch (Exception e) {
            logger.info("Clicking on element");
        }
        element.click();
    }

    /**
     * Type text into element
     */
    @Step("Type '{1}' into element: {0}")
    protected void type(WebElement element, String text) {
        waitForElementToBeClickable(element);
        element.clear();
        logger.info("Typing: '" + text + "' into element");
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
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
