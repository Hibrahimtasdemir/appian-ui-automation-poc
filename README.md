# Appian UI Automation POC

This project is a proof-of-concept UI automation framework for an Appian-based Leave Request application.

The goal of this project is to practice and demonstrate how an Appian Site can be tested with Java, Selenium WebDriver, Maven, and TestNG using a clean Page Object Model structure.

## Application Under Test

The sample Appian application is a simple Leave Request workflow created in Appian Community Edition.

Current implemented areas:

1. Employee Leave Dashboard
2. New Leave Request form
3. Required field validation
4. Invalid date range validation
5. Manager Leave Dashboard search

## Tech Stack

* Java 21
* Maven
* Selenium WebDriver 4
* TestNG
* WebDriverManager
* Page Object Model
* Appian Community Edition

## Framework Features

Current framework capabilities:

* Clean Page Object Model structure
* Shared `BasePage` for common UI actions
* `DriverFactory` for WebDriver creation
* `ConfigReader` for reading configuration values
* `ManualLoginHelper` for manual Appian login handling
* `LeaveRequestTestData` for test data generation
* Screenshot capture on test failure
* TestNG groups for test organization
* Separate TestNG suite files for E2E, validation, and manager tests
* Maven Surefire integration with configurable TestNG suite file
* Browser session reuse across a TestNG suite to reduce repeated manual login

## Project Structure

```text
src/test/java
├── base
│   └── BaseTest.java
├── drivers
│   └── DriverFactory.java
├── pages
│   ├── BasePage.java
│   ├── EmployeeLeaveDashboardPage.java
│   ├── ManagerLeaveDashboardPage.java
│   └── NewLeaveRequestPage.java
├── testdata
│   └── LeaveRequestTestData.java
├── tests
│   ├── CreateLeaveRequestTest.java
│   ├── InvalidDateRangeValidationTest.java
│   ├── ManagerDashboardSearchTest.java
│   ├── OpenSiteAfterManualLoginTest.java
│   ├── RequiredFieldValidationTest.java
│   └── SmokeOpenSiteTest.java
└── utils
    ├── ConfigReader.java
    ├── ManualLoginHelper.java
    └── ScreenshotUtils.java
```

## Current Test Coverage

### 1. Create New Leave Request

The main E2E test verifies that a user can create a new leave request from the Employee Leave Dashboard.

Test class:

```text
tests.CreateLeaveRequestTest
```

Test groups:

```text
e2e
leave-request
```

Covered steps:

* Open Appian Site
* Wait for manual login
* Click **New Leave Request**
* Fill `employeeName`
* Fill `reason`
* Fill `endDate`
* Fill `startDate`
* Select `isActive` if visible
* Click **CREATE**
* Search for the created request
* Verify the created request is visible in the dashboard

### 2. Required Field Validation

This test verifies that the New Leave Request form is not submitted when required fields are empty.

Test class:

```text
tests.RequiredFieldValidationTest
```

Test groups:

```text
validation
leave-request
```

Covered steps:

* Open Appian Site
* Wait for manual login
* Open New Leave Request form
* Click **CREATE** without filling required fields
* Verify the form remains visible
* Verify a validation message is displayed

### 3. Invalid Date Range Validation

This test verifies that the form shows validation behavior when the start date is after the end date.

Test class:

```text
tests.InvalidDateRangeValidationTest
```

Test groups:

```text
validation
leave-request
```

Covered steps:

* Open Appian Site
* Wait for manual login
* Open New Leave Request form
* Fill valid employee name and reason
* Enter an invalid date range
* Click **CREATE**
* Verify the form remains visible
* Verify a date-related validation message is displayed

### 4. Manager Dashboard Search

This test verifies that the Manager Leave Dashboard can be opened and searched.

Test class:

```text
tests.ManagerDashboardSearchTest
```

Test groups:

```text
manager
smoke
```

Covered steps:

* Open Appian Site
* Wait for manual login
* Open Manager Leave Dashboard directly
* Search for a known pending leave request
* Verify the expected employee is visible in the search results

## Configuration

Configuration file:

```text
src/test/resources/config.properties
```

Example:

```properties
appian.site.url=https://de.appian.community/suite/sites/leave-request-automation-poc
browser=chrome
```

No username or password is stored in the project.

## TestNG Suites

The project uses separate TestNG suite files.

```text
testng.xml              Full stable suite
testng-e2e.xml          E2E / happy path tests
testng-validation.xml   Validation / negative tests
testng-manager.xml      Manager dashboard tests
```

## Running Tests

Run the full stable suite:

```bash
mvn clean test
```

Run only E2E tests:

```bash
mvn clean test "-DsuiteXmlFile=testng-e2e.xml"
```

Run only validation tests:

```bash
mvn clean test "-DsuiteXmlFile=testng-validation.xml"
```

Run only manager dashboard tests:

```bash
mvn clean test "-DsuiteXmlFile=testng-manager.xml"
```

PowerShell note:

The `-D...` parameter should be wrapped in quotes when running from PowerShell.

Example:

```bash
mvn clean test "-DsuiteXmlFile=testng-manager.xml"
```

## Manual Login Handling

The test currently requires manual login because the Appian Community login flow may require an email verification code.

After the browser opens:

1. Complete the login manually.
2. Enter the email verification code if required.
3. The test will continue automatically after the Appian Site is loaded.

To reduce repeated login effort, the framework currently opens the browser once per TestNG suite and reuses the same browser session across tests in that suite.

## Screenshot on Failure

If a test fails, a screenshot is automatically saved under:

```text
target/screenshots
```

Screenshot handling is implemented in:

```text
utils.ScreenshotUtils
```

This helps with debugging dynamic Appian UI behavior.

## Important Notes About Appian UI Automation

Appian UI components can behave differently from standard HTML forms. For this reason, the framework uses a few Appian-friendly interaction patterns:

* Explicit waits instead of implicit waits
* Scroll to element before clicking
* `CTRL + A`, `BACKSPACE`, value entry, and `TAB` for input fields
* Additional `ENTER` after clicking the `CREATE` button

These patterns help Appian commit entered values and trigger UI actions more reliably.

## Appian Selenium API Exploration

Appian Selenium API was explored in a separate experimental branch.

Findings:

* The Appian Selenium API dependency could be added successfully.
* `SitesFixture` could be loaded and attached to the existing Selenium WebDriver.
* However, the generated Appian Community Edition UI used in this POC was not reliably compatible with the API's built-in locator strategies.
* The API could not reliably identify some generated Site actions and form fields.
* For this reason, the stable master branch currently continues with Selenium WebDriver and Page Object Model.

The experimental Appian Selenium API work is intentionally not part of the stable master branch.

## Known Limitations

* Login is currently handled manually because Appian Community requires email verification.
* The status field of the Leave Request is not yet validated.
* Manager approval/rejection flow is not automated yet because the generated Manager Dashboard task cards are not interactive in the current POC.
* The current Manager Dashboard automation focuses on search/display validation.
* Suite-level browser reuse improves local execution but may reduce test isolation. This should be reviewed before CI/CD usage.

## Next Improvements

Planned improvements:

* Add more Manager Dashboard validations
* Investigate whether a real approval/rejection task flow can be configured in Appian
* Add more validation tests
* Add search tests for Employee Leave Dashboard
* Add test reports
* Add CI/CD pipeline execution
* Revisit login/session handling for CI/CD readiness
* Re-evaluate Appian Selenium API if the Appian UI structure becomes more compatible
