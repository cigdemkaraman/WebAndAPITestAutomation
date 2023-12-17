package driver;

import com.thoughtworks.gauge.Operator;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class Driver {
    protected static WebDriver driver;
    public static WebDriverWait webDriverWait;
    public final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeScenario(tags = {"tagSelenium"}, tagAggregation = Operator.OR)
    public void initializeDriver() throws Exception {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-notifications");
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        driver.get("https://www.beymen.com/");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(90));
        BasicConfigurator.configure();

    }

    @AfterScenario(tags = {"tagSelenium"}, tagAggregation = Operator.OR)
    public static void quit() {
        driver.quit();

    }
}
