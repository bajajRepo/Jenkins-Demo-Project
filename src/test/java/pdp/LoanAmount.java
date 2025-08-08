package pdp;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoanAmount extends BaseClass{

	private By loanAmountTextfield = By.xpath("//input[@aria-label='loan-amount-input']");
	private By errormsg = By.xpath("//div[@class='fs-12 flex invalid-text fw-400 input-max-width']");
	
	
	@Test(priority = 1)
	public void loanAmountLessErrorMsg() throws InterruptedException {
		By locator = By.xpath("//div[@class='fw-500 fs-14 input-desc']");
        int maxAttempts = 30;
        int scrollOffset = 200;
        boolean isVisible = false;
 
        for (int i = 0; i < maxAttempts; i++) {
            try {
                WebElement element = driver.findElement(locator);
 
                // Scroll to center
                ((JavascriptExecutor) driver).executeScript(
                        "const rect = arguments[0].getBoundingClientRect();" +
                        		"window.scrollBy({top: rect.top - window.innerHeight / 2, behavior: 'smooth'});",element);
 
                Thread.sleep(1500); // Allow time to scroll
 
                // Check visibility using WebDriverWait
                new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
 
                isVisible = true;
                break;
 
            } catch (Exception e) {
                // Fallback scroll down a bit if not visible
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, arguments[0]);", scrollOffset);
                Thread.sleep(500); // Wait before next scroll
            }
        }
		
		Thread.sleep(2000);
		driver.findElement(loanAmountTextfield).clear();
		driver.findElement(loanAmountTextfield).sendKeys("234");
		
		String actualResult = driver.findElement(errormsg).getText();
		String expected_rlt = "Please enter a valid loan amount ₹25,000 to ₹1,000 Cr";
		Assert.assertEquals(actualResult, expected_rlt);
		
		
	}
	
	
	@Test(priority = 2)
	public void loanAmountGreaterErrorMsg() throws InterruptedException {
		

		driver.findElement(loanAmountTextfield).clear();
		driver.findElement(loanAmountTextfield).sendKeys("300002345000");
		
		String actual_rlt = driver.findElement(errormsg).getText();
		String expected_rlt = "Please enter a valid loan amount ₹25,000 to ₹1,000 Cr";
		Assert.assertEquals(actual_rlt, expected_rlt, "Error message does not appear");
		
	}
}
