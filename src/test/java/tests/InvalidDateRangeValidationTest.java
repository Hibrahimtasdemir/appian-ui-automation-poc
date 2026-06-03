package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EmployeeLeaveDashboardPage;
import pages.NewLeaveRequestPage;
import testdata.LeaveRequestTestData;
import utils.ConfigReader;
import utils.ManualLoginHelper;

public class InvalidDateRangeValidationTest extends BaseTest {

    private static final String SITE_PATH = "/suite/sites/leave-request-automation-poc";

    @Test(groups = {"validation", "leave-request"})
    public void shouldShowValidationWhenStartDateIsAfterEndDate() {
        String siteUrl = ConfigReader.get("appian.site.url");
        LeaveRequestTestData testData = LeaveRequestTestData.invalidDateRangeRequest();

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

        Assert.assertTrue(
                newLeaveRequestPage.isFormStillVisible(),
                "Form should remain visible when start date is after end date."
        );

        System.out.println("Body text after invalid date range CREATE:");
        System.out.println(newLeaveRequestPage.getBodyText());

        Assert.assertTrue(
                newLeaveRequestPage.hasDateRangeValidationMessage(),
                "Expected a date range validation message, but no related validation text was found."
        );
    }
}