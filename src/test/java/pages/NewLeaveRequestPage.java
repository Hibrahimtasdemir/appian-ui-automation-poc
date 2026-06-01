package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class NewLeaveRequestPage extends BasePage {

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
        super(driver);
    }

    public void waitUntilLoaded() {
        waitUntilVisible(employeeNameInput);
        waitUntilVisible(reasonInput);
        waitUntilVisible(endDateInput);
        waitUntilVisible(startDateInput);
    }

    public void fillEmployeeName(String employeeName) {
        typeAndTab(employeeNameInput, employeeName);
    }

    public void fillReason(String reason) {
        typeAndTab(reasonInput, reason);
    }

    public void fillStartDate(String startDate) {
        typeAndTab(startDateInput, startDate);
    }

    public void fillEndDate(String endDate) {
        typeAndTab(endDateInput, endDate);
    }

    public void selectIsActiveIfVisible() {
        try {
            WebElement checkbox = waitUntilClickable(isActiveCheckbox);

            if (!checkbox.isSelected()) {
                checkbox.click();
            }

        } catch (Exception ignored) {
            // isActive is optional for now. If not found or not clickable, continue.
        }
    }

    public void clickCreate() {
        WebElement button = waitUntilClickable(createButton);

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
}