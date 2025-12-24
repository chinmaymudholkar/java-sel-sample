package org.chinmay.tests;

import org.chinmay.pages.InventoryPage;
import org.chinmay.pages.LoginPage;
import io.github.cdimascio.dotenv.Dotenv;

import org.openqa.selenium.WebDriver;

import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.reflect.Method;
import io.qameta.allure.*;
import org.assertj.core.api.Assertions;

import org.chinmay.utils.DriverFactory;

/**
 * Test class for Swag Labs Login functionality
 */
@Epic("Authentication")
@Feature("Login")
public class LoginTest {

        // ==================== FIELDS ====================
        private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
        private final ThreadLocal<LoginPage> loginPage = new ThreadLocal<>();
        private final ThreadLocal<InventoryPage> inventoryPage = new ThreadLocal<>();
        private final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        private static final Logger logger = LogManager.getLogger(LoginTest.class);

        // ==================== SETUP / TEARDOWN ====================
        @BeforeMethod
        public void setUp(Method method) {
                logger.info("Starting test: {}", method.getName());

                // Initialize WebDriver using Factory
                driver.set(DriverFactory.createDriver());

                // Initialize Page Object
                loginPage.set(new LoginPage(driver.get()));

                // Navigate to login page
                loginPage.get().navigateToLoginPage();

                // Initialize Page Object
                inventoryPage.set(new InventoryPage(driver.get()));
        }

        @AfterMethod
        public void tearDown(Method method) {
                logger.info("Finished test: {}", method.getName());
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

                Assertions.assertThat(loginPage.get().isLoginSuccessful())
                                .as("Login should be successful for user: %s", username)
                                .isTrue();
                Assertions.assertThat(loginPage.get().getCurrentUrl())
                                .as("Should be redirected to inventory page")
                                .contains("inventory.html");

                inventoryPage.get().performLogout();
        }

        @Test(priority = 20, groups = {
                        "negative" }, dataProvider = "invalidCredentials", description = "Verify login fails with invalid credentials")
        @Story("Invalid Login")
        @Description("Verify that login fails with appropriate error messages when invalid credentials are provided.")
        @Severity(SeverityLevel.NORMAL)
        public void testLoginFailure(String username, String password, String expectedError) {
                loginPage.get().login(username, password);

                Assertions.assertThat(loginPage.get().isErrorMessageDisplayed())
                                .as("Error message should be displayed")
                                .isTrue();
                Assertions.assertThat(loginPage.get().getErrorMessage())
                                .as("Error message should match expected")
                                .contains(expectedError);
                Assertions.assertThat(loginPage.get().isLoginSuccessful())
                                .as("Login should not be successful")
                                .isFalse();
        }

        @Test(priority = 30)
        @Story("Failing test")
        @Description("This test is designed to fail")
        @Severity(SeverityLevel.CRITICAL)
        public void testDesignedToFail() {
            loginPage.get().login(dotenv.get("STANDARD_USER"), dotenv.get("PASSWORD"));
            Assertions.assertThat(loginPage.get().getCurrentUrl())
                    .as("Should be redirected to inventory page")
                    .contains("inventory1.html"); //Fail here
        }
}
