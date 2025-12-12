package org.chinmay.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    public static WebDriver createDriver() {
        // Setup WebDriver using WebDriverManager with specific browser version
        WebDriverManager.chromedriver().browserVersion("142").setup();

        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/usr/sbin/chromium"); // Explicitly set Chromium binary path
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");

        // Initialize and return WebDriver
        return new ChromeDriver(options);
    }
}
