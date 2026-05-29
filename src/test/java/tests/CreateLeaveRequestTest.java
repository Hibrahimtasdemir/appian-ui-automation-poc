package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EmployeeLeaveDashboardPage;
import pages.NewLeaveRequestPage;
import utils.ConfigReader;
import utils.ManualLoginHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateLeaveRequestTest extends BaseTest {

    private static final String SITE_PATH = "/suite/sites/leave-request-automation-poc";

    @Test(groups = {"e2e", "leave-request"})
    public void shouldCreateNewLeaveRequest() {
        String siteUrl = ConfigReader.get("appian.site.url");

        String employeeName = generateUniqueEmployeeName();
        String reason = "Automation test request";
        String startDate = "06/15/2026";
        String endDate = "06/19/2026";

        driver.get(siteUrl);

        ManualLoginHelper.waitUntilSiteIsLoadedAfterManualLogin(driver, SITE_PATH);

        EmployeeLeaveDashboardPage dashboardPage = new EmployeeLeaveDashboardPage(driver);
        dashboardPage.waitUntilLoaded();
        dashboardPage.clickNewLeaveRequest();

        NewLeaveRequestPage newLeaveRequestPage = new NewLeaveRequestPage(driver);
        newLeaveRequestPage.waitUntilLoaded();

        newLeaveRequestPage.fillEmployeeName(employeeName);
        newLeaveRequestPage.fillReason(reason);
        newLeaveRequestPage.fillEndDate(endDate);
        newLeaveRequestPage.fillStartDate(startDate);
        newLeaveRequestPage.selectIsActiveIfVisible();
        newLeaveRequestPage.clickCreate();
        newLeaveRequestPage.waitUntilFormIsClosedOrDashboardIsVisible();

        dashboardPage.waitUntilLoaded();
        dashboardPage.refreshIfAvailable();
        dashboardPage.searchRequest(employeeName);

        Assert.assertTrue(
                dashboardPage.isRequestVisible(employeeName),
                "Created leave request was not visible in dashboard after search. Employee name: " + employeeName
        );
    }

    private String generateUniqueEmployeeName() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "Ibrahim Auto Test " + timestamp;
    }
}