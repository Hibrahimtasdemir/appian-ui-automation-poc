package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EmployeeLeaveDashboardPage;
import pages.NewLeaveRequestPage;
import utils.ConfigReader;
import utils.ManualLoginHelper;

public class RequiredFieldValidationTest extends BaseTest {

    private static final String SITE_PATH = "/suite/sites/leave-request-automation-poc";

    @Test(groups = {"validation", "leave-request"})
    public void shouldShowValidationWhenCreateIsClickedWithEmptyRequiredFields() {
        String siteUrl = ConfigReader.get("appian.site.url");

        driver.get(siteUrl);

        ManualLoginHelper.waitUntilSiteIsLoadedAfterManualLogin(driver, SITE_PATH);

        EmployeeLeaveDashboardPage dashboardPage = new EmployeeLeaveDashboardPage(driver);
        dashboardPage.waitUntilLoaded();
        dashboardPage.clickNewLeaveRequest();

        NewLeaveRequestPage newLeaveRequestPage = new NewLeaveRequestPage(driver);
        newLeaveRequestPage.waitUntilLoaded();

        newLeaveRequestPage.clickCreate();

        Assert.assertTrue(
                newLeaveRequestPage.isFormStillVisible(),
                "Form should remain visible after clicking CREATE with empty required fields."
        );

        System.out.println("Body text after empty CREATE:");
        System.out.println(newLeaveRequestPage.getBodyText());

        Assert.assertTrue(
                newLeaveRequestPage.hasValidationMessage(),
                "Expected a required field validation message, but no validation text was found."
        );
    }
}