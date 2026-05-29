package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EmployeeLeaveDashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

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
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    public void waitUntilLoaded() {
        wait.until(ExpectedConditions.elementToBeClickable(newLeaveRequestButton));
    }

    public void clickNewLeaveRequest() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(newLeaveRequestButton));

        scrollToCenter(button);

        button.click();
    }

    public void searchRequest(String employeeName) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchInput));

        input.click();
        input.sendKeys(Keys.CONTROL, "a");
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(employeeName);
        input.sendKeys(Keys.TAB);

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(searchButton));

        scrollToCenter(button);

        button.click();
    }

    public void refreshIfAvailable() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement button = shortWait.until(ExpectedConditions.elementToBeClickable(refreshButton));

            scrollToCenter(button);

            button.click();

        } catch (Exception ignored) {
            // Refresh button may not be available or visible. Continue without failing.
        }
    }

    public boolean isRequestVisible(String employeeName) {
        By createdRequest = By.xpath("//*[contains(normalize-space(), '" + employeeName + "')]");
        WebElement request = wait.until(ExpectedConditions.visibilityOfElementLocated(createdRequest));

        return request.isDisplayed();
    }

    private void scrollToCenter(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );
    }
}