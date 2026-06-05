package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ManagerLeaveDashboardPage extends BasePage {

    private static final String MANAGER_PAGE_PATH = "/page/manager-leave-dashboard";

    private final By searchInput = By.xpath(
            "//input[contains(@placeholder, 'Search') or contains(@aria-label, 'Search')]"
    );

    private final By searchButton = By.xpath(
            "//button[contains(normalize-space(), 'SEARCH') or contains(normalize-space(), 'Search')]"
    );

    private final By managerDashboardTitle = By.xpath(
            "//*[contains(normalize-space(), 'Manager Leave Dashboard')]"
    );

    public ManagerLeaveDashboardPage(WebDriver driver) {
        super(driver);
    }

    public void openManagerDashboard(String siteUrl) {
        driver.get(siteUrl + MANAGER_PAGE_PATH);
        waitUntilLoaded();
    }

    public void waitUntilLoaded() {
        waitUntilVisible(managerDashboardTitle);
        waitUntilClickable(searchInput);
    }

    public void searchPendingRequest(String searchValue) {
        typeAndTab(searchInput, searchValue);
        click(searchButton);
    }

    public boolean isRequestVisible(String employeeName) {
        By request = By.xpath("//*[contains(normalize-space(), '" + employeeName + "')]");
        return isElementDisplayed(request);
    }

    public String getBodyText() {
        return driver.findElement(By.tagName("body")).getText();
    }
}