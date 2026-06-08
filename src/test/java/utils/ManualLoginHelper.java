package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class ManualLoginHelper {

    private static final Duration MANUAL_LOGIN_TIMEOUT = Duration.ofMinutes(3);

    private ManualLoginHelper() {
    }

    public static void waitUntilSiteIsLoadedAfterManualLogin(WebDriver driver, String expectedSitePath) {
        boolean sessionReuseEnabled = Boolean.parseBoolean(
                ConfigReader.getOrDefault("appian.session.reuse.enabled", "false")
        );

        if (sessionReuseEnabled) {
            openHomePageBeforeTargetSite(driver);
        }

        WebDriverWait wait = new WebDriverWait(driver, MANUAL_LOGIN_TIMEOUT);

        System.out.println("If the login page is displayed, please complete login manually in the opened browser.");
        System.out.println("Waiting until URL contains: " + expectedSitePath);

        wait.until(webDriver ->
                webDriver.getCurrentUrl() != null
                        && webDriver.getCurrentUrl().contains(expectedSitePath)
        );

        System.out.println("Appian site is loaded.");
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }

    private static void openHomePageBeforeTargetSite(WebDriver driver) {
        String homeUrl = ConfigReader.getOrDefault(
                "appian.home.url",
                "https://de.appian.community/suite/sites/home"
        );

        String siteUrl = ConfigReader.get("appian.site.url");

        System.out.println("Session reuse is enabled.");
        System.out.println("Opening Appian home first: " + homeUrl);

        driver.get(homeUrl);

        waitForPotentialManualLoginOnHome(driver);

        System.out.println("Opening target Appian site: " + siteUrl);
        driver.get(siteUrl);
    }

    private static void waitForPotentialManualLoginOnHome(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, MANUAL_LOGIN_TIMEOUT);

        wait.until(webDriver -> {
            String currentUrl = webDriver.getCurrentUrl();

            if (currentUrl == null) {
                return false;
            }

            boolean stillOnLoginPage =
                    currentUrl.contains("login")
                            || currentUrl.contains("sso")
                            || currentUrl.contains("saml");

            if (stillOnLoginPage) {
                System.out.println("Login page detected. Please complete login manually.");
                return false;
            }

            return currentUrl.contains("/suite")
                    || currentUrl.contains("/suite/sites/home");
        });
    }
}