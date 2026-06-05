package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManagerLeaveDashboardPage;
import utils.ConfigReader;
import utils.ManualLoginHelper;

public class ManagerDashboardSearchTest extends BaseTest {

    private static final String SITE_PATH = "/suite/sites/leave-request-automation-poc";

    @Test(groups = {"manager", "smoke"})
    public void shouldSearchPendingLeaveRequestOnManagerDashboard() {
        String siteUrl = ConfigReader.get("appian.site.url");
        String employeeName = "Sarah Mitchell";

        driver.get(siteUrl);

        ManualLoginHelper.waitUntilSiteIsLoadedAfterManualLogin(driver, SITE_PATH);

        ManagerLeaveDashboardPage managerDashboardPage = new ManagerLeaveDashboardPage(driver);
        managerDashboardPage.openManagerDashboard(siteUrl);
        managerDashboardPage.searchPendingRequest(employeeName);

        Assert.assertTrue(
                managerDashboardPage.isRequestVisible(employeeName),
                "Expected employee was not visible in manager dashboard search results: " + employeeName
        );
    }
}