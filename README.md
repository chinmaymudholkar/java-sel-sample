# Swag Labs Selenium TestNG Project

A Maven-based test automation project using Selenium WebDriver and TestNG to test the Swag Labs login functionality with both positive and negative scenarios.

## Project Overview

This project demonstrates automated testing of the [Swag Labs](https://www.saucedemo.com/) login page with comprehensive test coverage including:
- Positive test scenarios (valid login attempts)
- Negative test scenarios (invalid credentials, empty fields, locked users)
- Page Factory design pattern for maintainable test code
- TestNG framework for test organization and execution
- WebDriverManager for automatic browser driver management

## Prerequisites

- **Java JDK**: Version 21 or higher
- **Maven**: Version 3.6 or higher
- **Chrome/Chromium Browser**: Version 142 or compatible
- **Git**: For cloning the repository

## Project Structure

```
java-sel-sample/
├── pom.xml                          # Maven configuration
├── testng.xml                       # TestNG suite configuration
├── README.md                        # Project documentation
└── src/
    ├── main/
    │   └── java/
    │       └── org/
    │           └── chinmay/
    │               └── pages/
    │                   ├── BasePage.java      # Base page with common methods
    │                   └── LoginPage.java     # Login page object
    └── test/
        └── java/
            └── org/
                └── chinmay/
                    └── tests/
                        └── LoginTest.java     # Login test cases
```

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/chinmaymudholkar/java-sel-sample.git
cd java-sel-sample
```

### 2. Install Dependencies

```bash
mvn clean install
```

This will download all required dependencies including Selenium, TestNG, and WebDriverManager.

## Running Tests

### Run All Tests

```bash
mvn clean test
```

### Run Specific Test Groups

**Run only positive tests:**
```bash
mvn clean test -Dgroups=positive
```

**Run only negative tests:**
```bash
mvn clean test -Dgroups=negative
```

**Run smoke tests:**
```bash
mvn clean test -Dgroups=smoke
```

### Run Specific Test Suite from testng.xml

```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

## Test Scenarios Covered

### Positive Test Scenarios

1. **Login with Standard User** - Verify successful login with `standard_user`
2. **Login with Multiple Valid Users** - Test login with different valid user accounts:
   - `standard_user`
   - `problem_user`
   - `performance_glitch_user`

### Negative Test Scenarios

1. **Invalid Username** - Verify error message when using non-existent username
2. **Invalid Password** - Verify error message when using wrong password
3. **Empty Username** - Verify error when username field is empty
4. **Empty Password** - Verify error when password field is empty
5. **Both Fields Empty** - Verify error when both fields are empty
6. **Locked Out User** - Verify locked out user cannot login (`locked_out_user`)
7. **Invalid Credentials Combinations** - Test various invalid username/password combinations

## Valid Test Credentials

The following credentials are valid for Swag Labs:

| Username | Password |
|----------|----------|
| standard_user | secret_sauce |
| locked_out_user | secret_sauce |
| problem_user | secret_sauce |
| performance_glitch_user | secret_sauce |

## Page Factory Pattern

This project follows the Page Factory design pattern:

- **BasePage.java**: Contains common methods used across all pages (wait utilities, click, type, etc.)
- **LoginPage.java**: Encapsulates login page elements and actions
- **LoginTest.java**: Contains test methods that use the page objects

### Benefits of Page Factory:
- Better code organization and maintainability
- Reduced code duplication
- Easy to update when UI changes
- Improved test readability

## Technologies Used

- **Java 21**: Programming language
- **Maven**: Build and dependency management
- **Selenium WebDriver 4.16.1**: Browser automation
- **TestNG 7.8.0**: Testing framework
- **WebDriverManager 5.6.3**: Automatic driver management
- **Chromium 142**: Browser for test execution (headless mode)

## Quick Start

After cloning the repository, you can quickly run all tests with:

```bash
mvn clean test
```

This will:
1. Clean any previous build artifacts
2. Compile the source code
3. Download the correct ChromeDriver version
4. Execute all 15 test cases in headless mode
5. Generate test reports in `target/surefire-reports/`

## Test Reports

After running tests, TestNG generates HTML reports in:
```
target/surefire-reports/index.html
```

Open this file in a browser to view detailed test execution results.

## Author

Created as a demonstration of Selenium WebDriver with TestNG using Page Factory pattern by Chinmay Mudholkar.  Feel free to clone and use as per your requirements.
