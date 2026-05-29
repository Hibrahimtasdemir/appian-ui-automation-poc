package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class SmokeOpenSiteTest extends BaseTest {

    @Test(groups = {"smoke", "debug"})
    public void shouldOpenAppianSiteOrLoginPage() {
        String siteUrl = ConfigReader.get("appian.site.url");

        driver.get(siteUrl);

        String currentUrl = driver.getCurrentUrl();
        String pageTitle = driver.getTitle();

        System.out.println("Current URL: " + currentUrl);
        System.out.println("Page Title: " + pageTitle);

        boolean isAppianSiteOpened =
                currentUrl.contains("suite/sites/leave-request-automation-poc");

        boolean isRedirectedToAppianLogin =
                currentUrl.contains("login.appian.com")
                        || currentUrl.contains("/sso/")
                        || pageTitle.toLowerCase().contains("login");

        Assert.assertTrue(
                isAppianSiteOpened || isRedirectedToAppianLogin,
                "Neither Appian site nor Appian login page was opened. Current URL: " + currentUrl
        );
    }
}