package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EmployeeLeaveDashboardPage;
import pages.NewLeaveRequestPage;
import testdata.LeaveRequestTestData;
import utils.ConfigReader;
import utils.ManualLoginHelper;

public class CreateLeaveRequestTest extends BaseTest {

    private static final String SITE_PATH = "/suite/sites/leave-request-automation-poc";

    @Test(groups = {"e2e", "leave-request"})
    public void shouldCreateNewLeaveRequest() {
        String siteUrl = ConfigReader.get("appian.site.url");
        LeaveRequestTestData testData = LeaveRequestTestData.validLeaveRequest();

        driver.get(siteUrl);

        ManualLoginHelper.waitUntilSiteIsLoadedAfterManualLogin(driver, SITE_PATH);

        EmployeeLeaveDashboardPage dashboardPage = new EmployeeLeaveDashboardPage(driver);
        dashboardPage.waitUntilLoaded();
        dashboardPage.clickNewLeaveRequest();

        NewLeaveRequestPage newLeaveRequestPage = new NewLeaveRequestPage(driver);
        newLeaveRequestPage.waitUntilLoaded();

        newLeaveRequestPage.fillEmployeeName(testData.getEmployeeName());
        newLeaveRequestPage.fillReason(testData.getReason());
        newLeaveRequestPage.fillEndDate(testData.getEndDate());
        newLeaveRequestPage.fillStartDate(testData.getStartDate());
        newLeaveRequestPage.selectIsActiveIfVisible();
        newLeaveRequestPage.clickCreate();
        newLeaveRequestPage.waitUntilFormIsClosedOrDashboardIsVisible();

        dashboardPage.waitUntilLoaded();
        dashboardPage.refreshIfAvailable();
        dashboardPage.searchRequest(testData.getEmployeeName());

        Assert.assertTrue(
                dashboardPage.isRequestVisible(testData.getEmployeeName()),
                "Created leave request was not visible in dashboard after search. Employee name: "
                        + testData.getEmployeeName()
        );
    }
}