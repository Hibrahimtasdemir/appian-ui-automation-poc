package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EmployeeLeaveDashboardPage extends BasePage {

    private final By newLeaveRequestButton = By.xpath(
            "//button[.//span[contains(normalize-space(), 'New Leave Request')] or contains(normalize-space(), 'New Leave Request')]"
    );

    private final By searchInput = By.xpath(
            "//input[contains(@placeholder, 'Search') or contains(@aria-label, 'Search')]"
    );

    private final By searchButton = By.xpath(
            "//button[contains(normalize-space(), 'SEARCH') or contains(normalize-space(), 'Search')]"
    );

    private final By refreshButton = By.xpath(
            "//button[contains(@aria-label, 'Refresh') or contains(@title, 'Refresh')]"
    );

    public EmployeeLeaveDashboardPage(WebDriver driver) {
        super(driver);
    }

    public void waitUntilLoaded() {
        waitUntilClickable(newLeaveRequestButton);
    }

    public void clickNewLeaveRequest() {
        click(newLeaveRequestButton);
    }

    public void searchRequest(String employeeName) {
        typeAndTab(searchInput, employeeName);
        click(searchButton);
    }

    public void refreshIfAvailable() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement button = shortWait.until(
                    org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(refreshButton)
            );

            scrollToCenter(button);
            button.click();

        } catch (Exception ignored) {
            // Refresh button may not be available or visible. Continue without failing.
        }
    }

    public boolean isRequestVisible(String employeeName) {
        By createdRequest = By.xpath("//*[contains(normalize-space(), '" + employeeName + "')]");
        return isElementDisplayed(createdRequest);
    }
}