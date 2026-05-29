package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class ManualLoginHelper {

    private static final Duration MANUAL_LOGIN_TIMEOUT = Duration.ofMinutes(3);

    private ManualLoginHelper() {
    }

    public static void waitUntilSiteIsLoadedAfterManualLogin(WebDriver driver, String expectedSitePath) {
        WebDriverWait wait = new WebDriverWait(driver, MANUAL_LOGIN_TIMEOUT);

        System.out.println("If the login page is displayed, please complete login manually in the opened browser.");
        System.out.println("Waiting until URL contains: " + expectedSitePath);

        wait.until(webDriver ->
                webDriver.getCurrentUrl() != null
                        && webDriver.getCurrentUrl().contains(expectedSitePath)
        );

        System.out.println("Login completed.");
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }
}