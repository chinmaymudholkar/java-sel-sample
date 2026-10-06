package io.github.chinmaymudholkar.listeners;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.ByteArrayInputStream;
import io.github.chinmaymudholkar.tests.LoginTest;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof LoginTest) {
            WebDriver driver = ((LoginTest) testInstance).getDriver();
            if (driver != null) {
                Allure.addAttachment("Screenshot on Failure",
                        new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
            }
        }
    }
}
