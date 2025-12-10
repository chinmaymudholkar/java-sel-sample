package org.chinmay.tests;

import org.chinmay.pages.LoginPage;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Test class for Swag Labs Login functionality
 */
public class LoginTest {

        private WebDriver driver;
        private LoginPage loginPage;
        private Dotenv dotenv;

        @BeforeMethod
        public void setUp() {
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
                driver = new ChromeDriver(options);

                // Initialize Page Object
                loginPage = new LoginPage(driver);

                // Initialize Dotenv
                dotenv = Dotenv.load();

                // Navigate to login page
                loginPage.navigateToLoginPage();
        }

        @AfterMethod
        public void tearDown() {
                if (driver != null) {
                        driver.quit();
                }
        }

        /**
         * Data Provider for valid login credentials
         */
        @DataProvider(name = "validCredentials")
        public Object[][] getValidCredentials() {
                return new Object[][] {
                                { dotenv.get("STANDARD_USER"), dotenv.get("PASSWORD") },
                                { dotenv.get("PROBLEM_USER"), dotenv.get("PASSWORD") },
                                { dotenv.get("PERFORMANCE_GLITCH_USER"), dotenv.get("PASSWORD") }
                };
        }

        /**
         * Data Provider for invalid login credentials
         */
        @DataProvider(name = "invalidCredentials")
        public Object[][] getInvalidCredentials() {
                return new Object[][] {
                                { "invalid_user", "secret_sauce",
                                                "Epic sadface: Username and password do not match any user in this service" },
                                { "standard_user", "wrong_password",
                                                "Epic sadface: Username and password do not match any user in this service" },
                                { "", "", "Epic sadface: Username is required" },
                                { "standard_user", "", "Epic sadface: Password is required" },
                                { "", "secret_sauce", "Epic sadface: Username is required" }
                };
        }

        /**
         * Data Provider for locked out user
         */
        @DataProvider(name = "lockedOutUser")
        public Object[][] getLockedOutUser() {
                return new Object[][] {
                                { dotenv.get("LOCKED_OUT_USER"), dotenv.get("PASSWORD"),
                                                "Epic sadface: Sorry, this user has been locked out." }
                };
        }

        // ==================== POSITIVE TEST CASES ====================

        @Test(priority = 1, groups = { "positive", "smoke" }, description = "Verify login with standard user")
        public void testLoginWithStandardUser() {
                loginPage.login(dotenv.get("STANDARD_USER"), dotenv.get("PASSWORD"));

                Assert.assertTrue(loginPage.isLoginSuccessful(),
                                "Login should be successful with valid credentials");
                Assert.assertTrue(loginPage.getCurrentUrl().contains("inventory.html"),
                                "Should be redirected to inventory page");
        }

        @Test(priority = 2, groups = {
                        "positive" }, dataProvider = "validCredentials", description = "Verify login with multiple valid users")
        public void testLoginWithValidUsers(String username, String password) {
                loginPage.login(username, password);

                Assert.assertTrue(loginPage.isLoginSuccessful(),
                                "Login should be successful for user: " + username);
        }

        // ==================== NEGATIVE TEST CASES ====================

        @Test(priority = 3, groups = {
                        "negative" }, dataProvider = "invalidCredentials", description = "Verify login fails with invalid credentials")
        public void testLoginWithInvalidCredentials(String username, String password, String expectedError) {
                loginPage.login(username, password);

                Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                                "Error message should be displayed for invalid credentials");
                Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError),
                                "Expected error message: " + expectedError);
                Assert.assertFalse(loginPage.isLoginSuccessful(),
                                "Login should not be successful with invalid credentials");
        }

        @Test(priority = 4, groups = {
                        "negative" }, dataProvider = "lockedOutUser", description = "Verify locked out user cannot login")
        public void testLoginWithLockedOutUser(String username, String password, String expectedError) {
                loginPage.login(username, password);

                Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                                "Error message should be displayed for locked out user");
                Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError),
                                "Expected error message: " + expectedError);
                Assert.assertFalse(loginPage.isLoginSuccessful(),
                                "Locked out user should not be able to login");
        }

        @Test(priority = 5, groups = { "negative" }, description = "Verify login with empty username")
        public void testLoginWithEmptyUsername() {
                loginPage.enterUsername("");
                loginPage.enterPassword("secret_sauce");
                loginPage.clickLoginButton();

                Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                                "Error message should be displayed when username is empty");
                Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"),
                                "Should show username required error");
        }

        @Test(priority = 6, groups = { "negative" }, description = "Verify login with empty password")
        public void testLoginWithEmptyPassword() {
                loginPage.enterUsername("standard_user");
                loginPage.enterPassword("");
                loginPage.clickLoginButton();

                Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                                "Error message should be displayed when password is empty");
                Assert.assertTrue(loginPage.getErrorMessage().contains("Password is required"),
                                "Should show password required error");
        }

        @Test(priority = 7, groups = { "negative" }, description = "Verify login with both fields empty")
        public void testLoginWithEmptyFields() {
                loginPage.clickLoginButton();

                Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                                "Error message should be displayed when both fields are empty");
                Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"),
                                "Should show username required error");
        }

        @Test(priority = 8, groups = { "negative" }, description = "Verify login with invalid username")
        public void testLoginWithInvalidUsername() {
                loginPage.login("invalid_user_123", "secret_sauce");

                Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                                "Error message should be displayed for invalid username");
                Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                                "Should show credentials mismatch error");
        }

        @Test(priority = 9, groups = { "negative" }, description = "Verify login with invalid password")
        public void testLoginWithInvalidPassword() {
                loginPage.login("standard_user", "wrong_password_123");

                Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                                "Error message should be displayed for invalid password");
                Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                                "Should show credentials mismatch error");
        }
}
