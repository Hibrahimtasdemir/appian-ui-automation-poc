package drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.ConfigReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.Duration;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver() {
        String browser = ConfigReader.getOrDefault("browser", "chrome");

        if (browser.equalsIgnoreCase("chrome")) {
            return createChromeDriver();
        }

        throw new RuntimeException("Unsupported browser: " + browser);
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        boolean chromeProfileEnabled = Boolean.parseBoolean(
                ConfigReader.getOrDefault("chrome.profile.enabled", "false")
        );

        if (chromeProfileEnabled) {
            String userDataDir = ConfigReader.getOrDefault(
                    "chrome.user.data.dir",
                    ".chrome-profile/appian"
            );

            Path profilePath = Paths.get(userDataDir).toAbsolutePath();

            options.addArguments("--user-data-dir=" + profilePath);
            options.addArguments("--no-first-run");
            options.addArguments("--no-default-browser-check");

            System.out.println("Using Chrome profile directory: " + profilePath);
        }

        WebDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        return driver;
    }
}