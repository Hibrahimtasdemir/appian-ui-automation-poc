package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AppianApiDependencySmokeTest {

    @Test(groups = {"debug", "appian-api"})
    public void shouldLoadAppianSeleniumApiSitesFixture() throws ClassNotFoundException {
        Class<?> sitesFixtureClass = Class.forName(
                "com.appiancorp.ps.automatedtest.fixture.SitesFixture"
        );

        Assert.assertNotNull(
                sitesFixtureClass,
                "SitesFixture class could not be loaded from Appian Selenium API dependency."
        );

        System.out.println("Loaded Appian Selenium API class: " + sitesFixtureClass.getName());
    }
}