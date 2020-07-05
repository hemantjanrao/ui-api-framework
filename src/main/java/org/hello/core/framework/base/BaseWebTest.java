package org.hello.core.framework.base;

import io.qameta.allure.Attachment;
import org.apache.commons.lang3.RandomStringUtils;
import org.hello.core.framework.listener.CustomListener;
import org.hello.core.framework.web.utils.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Listeners({CustomListener.class})
public class BaseWebTest extends BaseTest {

    @BeforeClass(alwaysRun = true)
    public void initializeDriver() {
        log.info("Start initializing the driver");
        try {
            driver = DriverManager.getWebDriver();
        } catch (Exception e) {
            Assert.fail("Error creating driver", e);
        }

        log.info("Finish initializing the driver");
    }

    @AfterClass(alwaysRun = true)
    public void destroyDriver() {
        try {
            log.info("Destroying the driver");
            driver.quit();
        } catch (UnhandledAlertException e) {
            driver.switchTo().alert().accept();
        }
    }

    /**
     * Method to take screen shot, save as file. Also attach it to allure report
     *
     * @param testName name of the test running
     */
    @Attachment(value = "Web Page Screenshot", type = "image/png")
    public byte[] takeScreenShot(String testName) {
        if (Objects.isNull(driver)) {
            log.warn("WebDriver is null, unable to save screenshot");
            return null;
        }
        try {
            TakesScreenshot shot = (TakesScreenshot) driver;
            File file = shot.getScreenshotAs(OutputType.FILE);
            byte[] scrnShot = shot.getScreenshotAs(OutputType.BYTES);
            String fileName = String.format("snapshot%s%s%s.png", testName, LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddmmyyyy")), RandomStringUtils.randomAlphabetic(3));
            Path path = Paths.get("target/screenshot", testName, fileName);
            Files.createDirectories(path.getParent());
            Files.copy(file.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
            return scrnShot;
        } catch (IOException e) {
            log.error("Screenshot saving failed", e);
        }
        return null;
    }
}
