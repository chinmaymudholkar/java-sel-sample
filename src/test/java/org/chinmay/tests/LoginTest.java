package org.chinmay.tests;

import org.chinmay.pages.LoginPage;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.reflect.Method;
import io.qameta.allure.*;

/**
 * Test class for Swag Labs Login functionality
 */
@Epic("Authentication")
@Feature("Login")
public class LoginTest {

        // ==================== FIELDS ====================
        private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
        private ThreadLocal<LoginPage> loginPage = new ThreadLocal<>();
        private Dotenv dotenv = Dotenv.load();
        private static final Logger logger = LogManager.getLogger(LoginTest.class);

        // ==================== SETUP / TEARDOWN ====================
        @BeforeMethod
        public void setUp(Method method) {
                logger.info("Starting test: " + method.getName());
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

                // Initialize WebDriver
                driver.set(new ChromeDriver(options));

                // Initialize Page Object
                loginPage.set(new LoginPage(driver.get()));

                // Navigate to login page
                loginPage.get().navigateToLoginPage();
        }

        @AfterMethod
        public void tearDown(Method method) {
                logger.info("Finished test: " + method.getName());
                if (driver.get() != null) {
                        driver.get().quit();
                        driver.remove();
                }
        }

        // ==================== HELPER METHODS ====================
        public WebDriver getDriver() {
                return driver.get();
        }

        // ==================== DATA PROVIDERS ====================
        @DataProvider(name = "validCredentials")
        public Object[][] getValidCredentials() {
                return new Object[][] {
                                { dotenv.get("STANDARD_USER"), dotenv.get("PASSWORD") },
                                { dotenv.get("PROBLEM_USER"), dotenv.get("PASSWORD") },
                                { dotenv.get("PERFORMANCE_GLITCH_USER"), dotenv.get("PASSWORD") }
                };
        }

        @DataProvider(name = "invalidCredentials")
        public Object[][] getInvalidCredentials() {
                return new Object[][] {
                                // Invalid Username
                                { "invalid_user", "secret_sauce",
                                                "Epic sadface: Username and password do not match any user in this service" },
                                // Invalid Password
                                { "standard_user", "wrong_password",
                                                "Epic sadface: Username and password do not match any user in this service" },
                                // Empty Fields
                                { "", "", "Epic sadface: Username is required" },
                                { "standard_user", "", "Epic sadface: Password is required" },
                                { "", "secret_sauce", "Epic sadface: Username is required" },
                                // Locked Out User
                                { dotenv.get("LOCKED_OUT_USER"), dotenv.get("PASSWORD"),
                                                "Epic sadface: Sorry, this user has been locked out." }
                };
        }

        // ==================== TEST CASES ====================
        @Test(priority = 10, groups = { "positive",
                        "smoke" }, dataProvider = "validCredentials", description = "Verify successful login with valid credentials")
        @Story("Valid Login")
        @Description("Verify that users with valid credentials can log in successfully and are redirected to the inventory page.")
        @Severity(SeverityLevel.CRITICAL)
        public void testLoginSuccess(String username, String password) {
                loginPage.get().login(username, password);

                Assert.assertTrue(loginPage.get().isLoginSuccessful(),
                                "Login should be successful for user: " + username);
                Assert.assertTrue(loginPage.get().getCurrentUrl().contains("inventory.html"),
                                "Should be redirected to inventory page");
        }

        @Test(priority = 20, groups = {
                        "negative" }, dataProvider = "invalidCredentials", description = "Verify login fails with invalid credentials")
        @Story("Invalid Login")
        @Description("Verify that login fails with appropriate error messages when invalid credentials are provided.")
        @Severity(SeverityLevel.NORMAL)
        public void testLoginFailure(String username, String password, String expectedError) {
                loginPage.get().login(username, password);

                Assert.assertTrue(loginPage.get().isErrorMessageDisplayed(), "Error message should be displayed");
                Assert.assertTrue(loginPage.get().getErrorMessage().contains(expectedError),
                                "Expected error message: " + expectedError);
                Assert.assertFalse(loginPage.get().isLoginSuccessful(), "Login should not be successful");
        }
}
