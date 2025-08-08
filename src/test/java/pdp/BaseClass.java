package pdp;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	protected static WebDriver driver;
	protected static WebDriverWait wait;
	protected static JavascriptExecutor js;
	protected static Actions actions;

	@BeforeSuite
	public void setUpSuite() throws InterruptedException {
		if (driver == null) {
			WebDriverManager.chromedriver().setup();

			Map<String, Object> mobileEmulation = new HashMap<>();
			mobileEmulation.put("deviceName", "Samsung Galaxy S8+");

			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("mobileEmulation", mobileEmulation);
			options.addArguments("--force-device-scale-factor=1.00");
			options.addArguments("--disable-notifications");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--remote-allow-origins=*");

			driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.get("https://qa-oneweb.bajajfinserv.in/loan-against-bonds");
			js = (JavascriptExecutor) driver;
			driver.manage().window().maximize();
			actions = new Actions(driver);
			wait = new WebDriverWait(driver, 10);
			Thread.sleep(2000);
		}
	}

	@AfterSuite
	public void tearDownSuite() {
		if (driver != null) {
			//driver.quit();
		}
	}
}
