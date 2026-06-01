package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.ManualLoginHelper;

import java.time.Duration;

public class OpenSiteAfterManualLoginTest extends BaseTest {

    @Test(groups = {"smoke", "manual-login"})
    public void shouldOpenEmployeeLeaveDashboardAfterManualLogin() {
        String siteUrl = ConfigReader.get("appian.site.url");

        driver.get(siteUrl);

        ManualLoginHelper.waitUntilSiteIsLoadedAfterManualLogin(
                driver,
                "/suite/sites/leave-request-automation-poc"
        );

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        WebElement newLeaveRequestButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[.//span[contains(normalize-space(), 'New Leave Request')] or contains(normalize-space(), 'New Leave Request')]")
                )
        );

        Assert.assertTrue(
                newLeaveRequestButton.isDisplayed(),
                "New Leave Request button was not displayed."
        );
    }
}