package org.chinmay.utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {

    private static final String[] CHROMIUM_ARGS = {
            "--headless", "--no-sandbox", "--disable-dev-shm-usage",
            "--remote-allow-origins=*", "--window-size=1920,1080"
    };

    private static final String[] FIREFOX_ARGS = {
            "--headless", "--window-size=1920,1080"
    };

    public static WebDriver createDriver() {
        String browser = System.getProperty("browser", "firefox").toLowerCase();

        switch (browser) {
            case "chrome":
                return new ChromeDriver(_getChromeOptions());
            case "firefox":
                return new FirefoxDriver(_getFirefoxOptions());
            case "edge":
                return new EdgeDriver(_getEdgeOptions());
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
    }

    private static FirefoxOptions _getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments(FIREFOX_ARGS);
        return options;
    }

    private static EdgeOptions _getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments(CHROMIUM_ARGS);
        return options;
    }

    private static ChromeOptions _getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(CHROMIUM_ARGS);
        return options;
    }
}
