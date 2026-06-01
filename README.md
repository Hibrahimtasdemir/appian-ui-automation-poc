# Appian UI Automation POC

This project is a proof-of-concept UI automation framework for an Appian-based Leave Request application.

The goal of this project is to practice and demonstrate how an Appian Site can be tested with Java, Selenium WebDriver, Maven, and TestNG using a clean Page Object Model structure.

## Application Under Test

The sample Appian application is a simple Leave Request workflow created in Appian Community Edition.

Current implemented flow:

1. User opens the Appian Site.
2. User logs in manually.
3. User opens the Employee Leave Dashboard.
4. User clicks **New Leave Request**.
5. User fills in the leave request form.
6. User submits the request.
7. The created request is searched and verified in the dashboard.

## Tech Stack

- Java 21
- Maven
- Selenium WebDriver 4
- TestNG
- WebDriverManager
- Page Object Model
- Appian Community Edition

## Project Structure

```text
src/test/java
├── base
│   └── BaseTest.java
├── pages
│   ├── BasePage.java
│   ├── EmployeeLeaveDashboardPage.java
│   └── NewLeaveRequestPage.java
├── testdata
│   └── LeaveRequestTestData.java
├── tests
│   ├── CreateLeaveRequestTest.java
│   ├── OpenSiteAfterManualLoginTest.java
│   └── SmokeOpenSiteTest.java
└── utils
    ├── ConfigReader.java
    └── ManualLoginHelper.java

Current Test Coverage
Create New Leave Request

The main E2E test currently verifies that a user can create a new leave request from the Employee Leave Dashboard.

Test class:

tests.CreateLeaveRequestTest

Covered steps:

- Open Appian Site
- Wait for manual login
- Click New Leave Request
- Fill employeeName
- Fill reason
- Fill endDate
- Fill startDate
- Select isActive if visible
- Click CREATE
- Search for the created request
- Verify the created request is visible in the dashboard

Configuration

Configuration file:

src/test/resources/config.properties

Example:

appian.site.url=https://de.appian.community/suite/sites/leave-request-automation-poc
browser=chrome

No username or password is stored in the project.

## Running Tests

Run the stable E2E test with:

mvn clean test

The test currently requires manual login because the Appian Community login flow may require email verification.

After the browser opens:

Complete the login manually.
Enter the email verification code if required.
The test will continue automatically after the Appian Site is loaded.
Screenshot on Failure

If a test fails, a screenshot is automatically saved under:

target/screenshots

This helps with debugging dynamic Appian UI behavior.

Known Limitations
Login is currently handled manually because Appian Community requires email verification.
The status field of the Leave Request is not yet validated.
The current focus is UI automation of the create request flow.
Appian Selenium API integration is planned as the next enhancement.
Next Improvements

Planned improvements:

Add Appian Selenium API support
Add more Page Object classes
Add test groups such as smoke, e2e, and regression
Add validation tests for required fields
Add negative test scenarios
Improve Appian application logic for default request status
Add CI/CD pipeline execution