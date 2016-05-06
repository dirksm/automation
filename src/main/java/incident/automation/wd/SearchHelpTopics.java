package incident.automation.wd;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SearchHelpTopics {

	WebDriver driver = null;
	
	@BeforeTest
	public void setup() {
		driver = new FirefoxDriver();
		driver.get("https://www.google.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test
	public void searchHelpTopicsTest() {
		
		
		WebElement searchField = driver.findElement(By.id("lst-ib"));
		searchField.clear();
		searchField.sendKeys("google");
		
		WebElement searchButton = driver.findElement(By.name("btnG"));
		searchButton.click();
		
		// Close the popup
		WebElement popup = null;
		try {
			popup = driver.findElement(By.linkText("×"));
		} catch (NoSuchElementException nsee) {
			try {
				popup = driver.findElement(By.linkText("&times;"));
			} catch (NoSuchElementException nse) {
				System.err.println("Exception finding popup: " + nse.getMessage());
			}
		}
		if (popup != null) {
			popup.click();
			System.out.println("Closing popup...");
		}
		
		// Get the options button
		WebElement optionsBtn = driver.findElement(By.id("abar_button_opt"));
		System.out.println("Clicking options button...");
		optionsBtn.click();
		// Click "Search Help"
		driver.findElement(By.linkText("Search help")).click();
		
		String attrValueOfFirstHelpTopic = driver.findElement(By.xpath("//article/nav[@class='accordion-homepage']/section[h2[text()='Popular articles']]/div/div")).getAttribute("style");
		Assert.assertTrue(attrValueOfFirstHelpTopic.equals("margin-top: 0px;"));
		
		System.out.println("attrValueOfFirstHelpTopic: "+attrValueOfFirstHelpTopic);
		
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
