package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EmployeeLeaveDashboardPage;
import utils.ConfigReader;
import utils.ManualLoginHelper;

public class EmployeeDashboardSearchTest extends BaseTest {

    private static final String SITE_PATH = "/suite/sites/leave-request-automation-poc";

    @Test(groups = {"employee", "smoke"})
    public void shouldSearchLeaveRequestOnEmployeeDashboard() {
        String siteUrl = ConfigReader.get("appian.site.url");
        String employeeName = "Emily Chen";

        driver.get(siteUrl);

        ManualLoginHelper.waitUntilSiteIsLoadedAfterManualLogin(driver, SITE_PATH);

        EmployeeLeaveDashboardPage employeeDashboardPage = new EmployeeLeaveDashboardPage(driver);
        employeeDashboardPage.waitUntilLoaded();

        employeeDashboardPage.searchRequest(employeeName);

        Assert.assertTrue(
                employeeDashboardPage.isRequestVisible(employeeName),
                "Expected employee was not visible in employee dashboard search results: " + employeeName
        );
    }
}