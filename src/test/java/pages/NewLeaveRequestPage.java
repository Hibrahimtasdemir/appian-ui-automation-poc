package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public class NewLeaveRequestPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By employeeNameInput =
            By.xpath("//label[contains(normalize-space(), 'employeeName')]/following::input[1]");

    private final By reasonInput =
            By.xpath("//label[contains(normalize-space(), 'reason')]/following::input[1]");

    private final By endDateInput =
            By.xpath("//label[contains(normalize-space(), 'endDate')]/following::input[1]");

    private final By startDateInput =
            By.xpath("//label[contains(normalize-space(), 'startDate')]/following::input[1]");

    private final By isActiveCheckbox =
            By.xpath("//label[contains(normalize-space(), 'isActive')]/preceding::input[@type='checkbox'][1]");

    private final By createButton =
            By.xpath("//button[contains(normalize-space(), 'CREATE') or contains(normalize-space(), 'Create')]");

    private final By submitLeaveRequestDialog =
            By.xpath("//*[contains(normalize-space(), 'Submit Leave Request')]");

    public NewLeaveRequestPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    public void waitUntilLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
        wait.until(ExpectedConditions.visibilityOfElementLocated(reasonInput));
        wait.until(ExpectedConditions.visibilityOfElementLocated(endDateInput));
        wait.until(ExpectedConditions.visibilityOfElementLocated(startDateInput));
    }

    public void fillEmployeeName(String employeeName) {
        type(employeeNameInput, employeeName);
    }

    public void fillReason(String reason) {
        type(reasonInput, reason);
    }

    public void fillStartDate(String startDate) {
        type(startDateInput, startDate);
    }

    public void fillEndDate(String endDate) {
        type(endDateInput, endDate);
    }

    public void selectIsActiveIfVisible() {
        try {
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(isActiveCheckbox));

            if (!checkbox.isSelected()) {
                checkbox.click();
            }

        } catch (Exception ignored) {
            // isActive is optional for now. If not found or not clickable, continue.
        }
    }

    public void clickCreate() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(createButton));

        scrollToCenter(button);

        button.click();
        button.sendKeys(Keys.ENTER);
    }

    public void waitUntilFormIsClosedOrDashboardIsVisible() {
        By dashboardSearchInput =
                By.xpath("//input[contains(@placeholder, 'Search') or contains(@aria-label, 'Search')]");

        By newLeaveRequestButton =
                By.xpath("//button[contains(normalize-space(), 'New Leave Request')]");

        wait.until(webDriver ->
                webDriver.findElements(dashboardSearchInput).size() > 0
                        || webDriver.findElements(newLeaveRequestButton).size() > 0
                        || webDriver.findElements(submitLeaveRequestDialog)
                        .stream()
                        .noneMatch(WebElement::isDisplayed)
        );
    }

    public String getBodyText() {
        return driver.findElement(By.tagName("body")).getText();
    }

    public void takeScreenshot(String fileName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("target/" + fileName);
            destination.getParentFile().mkdirs();

            Files.copy(
                    screenshot.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println("Screenshot saved to: " + destination.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("Could not save screenshot", e);
        }
    }

    private void type(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

        element.click();
        element.sendKeys(Keys.CONTROL, "a");
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(value);
        element.sendKeys(Keys.TAB);
    }

    private void scrollToCenter(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );
    }
}