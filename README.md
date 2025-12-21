# Swag Labs Selenium TestNG Project

A Maven-based test automation project using Selenium WebDriver and TestNG to test the Swag Labs login functionality with both positive and negative scenarios.

## Project Overview

This project demonstrates automated testing of the [Swag Labs](https://www.saucedemo.com/) login page with comprehensive test coverage including:
- Positive test scenarios (valid login attempts)
- Negative test scenarios (invalid credentials, empty fields, locked users)
- Page Factory design pattern for maintainable test code
- TestNG framework for test organization and execution including parallel execution


### Prerequisites

1. Java JDK 21
2. Maven 3.11+
3. Firefox Browser
4. Git: For cloning the repository

## Setup

### 1. Clone the Repository

```bash
git clone https://github.com/chinmaymudholkar/java-sel-sample.git
cd java-sel-sample
```

### 2. Install Dependencies

```bash
mvn clean install
```

This will download all required dependencies including Selenium and TestNG.

## Running Tests

### Run All Tests

```bash
mvn clean test
```
This will run all tests in the project, 5 at a time in parallel (configured in testng.xml).

### Run Specific Test Groups

**Run only positive tests:**

```bash
mvn clean test -Dgroups=positive
```
This will run only positive tests in the project, 5 at a time in parallel (configured in testng.xml).

### Run Specific Test Suite from testng.xml

```bash
mvn clean test -DsuiteXmlFile=testng.xml
```
### Run Tests on Different Browsers
The project supports cross-browser testing on **Firefox**, **Chrome**, and **Edge**.

**Default (Firefox):**
```bash
mvn clean test
```

**Chrome:**
```bash
mvn clean test -Dbrowser=chrome
```

**Edge:**
```bash
mvn clean test -Dbrowser=edge
```
## Reporting

This project uses **Allure Reporting** for comprehensive test execution reports.

### Generating Reports
To generate and view the Allure report:
```bash
mvn allure:serve
```

This will run the tests (if not already run) and open the report in your default browser.

To generate the report files without opening:

```bash
mvn allure:report
```
The report will be generated in `target/site/allure-maven-plugin`.

### Screenshots on Failure
The project is configured to automatically capture screenshots when a test step fails. These screenshots are attached to the corresponding test case in the Allure report.

### Configuration

Create a `.env` file in the root directory (copy from `.env.example`) and add your Swag Labs credentials:

```bash
cp .env.example .env
```

The `.env` file should contain:

```properties
STANDARD_USER=standard_user
PROBLEM_USER=problem_user
PERFORMANCE_GLITCH_USER=performance_glitch_user
LOCKED_OUT_USER=locked_out_user
PASSWORD=secret_sauce
```

## GitHub Actions

This project is configured to run tests on GitHub Actions on a schedule (first Monday of the month at 2:00 AM) and on push and pull requests.


## Test Coverage

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

## Quick Start

After cloning the repository, you can quickly run all tests with:

```bash
mvn clean test
```

## Author

Created as a demonstration of Selenium WebDriver with TestNG using Page Factory pattern by Chinmay Mudholkar.  Feel free to clone and use as per your requirements.
