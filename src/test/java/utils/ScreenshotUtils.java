package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Attachment;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {

    private static final String SCREENSHOT_DIRECTORY = "target/screenshots";

    private ScreenshotUtils() {
    }

    public static void takeScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            return;
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String screenshotName = testName + "_" + timestamp + ".png";

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destination = new File(SCREENSHOT_DIRECTORY + "/" + screenshotName);
        destination.getParentFile().mkdirs();

        try {
            Files.copy(
                    screenshot.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

            attachScreenshotToAllure(driver);

            System.out.println("Screenshot saved to: " + destination.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("Could not save screenshot: " + destination.getAbsolutePath(), e);
        }
    }
    @Attachment(value = "Failure Screenshot", type = "image/png")
    private static byte[] attachScreenshotToAllure(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}