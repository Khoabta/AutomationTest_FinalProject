package common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class Base {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public String baseURL = "http://commands.test-code.com";
    public static String pathProject = System.getProperty("user.dir");

    @BeforeTest
    @Parameters("browser")
    public void initializeBrowser (String browser){

        switch (browser) {
            case "Chrome":
                System.setProperty("webdriver.chrome.driver", pathProject + "/libs/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "Firefox":
                System.setProperty("webdriver.gecko.driver", pathProject + "/libs/geckodriver.exe");
                System.setProperty("webdriver.firefox.bin", "C:/Program Files/Mozilla Firefox/firefox.exe");
                driver = new FirefoxDriver();
                break;
            case "MicrosoftEdge":
                System.setProperty("webdriver.edge.driver", pathProject + "/libs/msedgedriver.exe");
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + browser);
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterTest
    public void closeBrowser(){
        driver.close();
    }
}
