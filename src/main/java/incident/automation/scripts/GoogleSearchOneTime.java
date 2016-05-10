package incident.automation.scripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class GoogleSearchOneTime {

	WebDriver driver = null;
	
	@BeforeTest
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "/home/dirksm/drivers/chromedriver");
		
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test
	@Parameters({"searchURL", "searchTerm"})
	public void executeTest(String searchURL, String searchTerm) {
		
		driver.get(searchURL);
		GoogleSearchOneTime.executeSearch(driver, searchTerm);
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	public static void execute(String searchURL, String[] searchTerms) {

		System.setProperty("webdriver.chrome.driver", "/home/dirksm/drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.get(searchURL);
			for (String term : searchTerms) {
				GoogleSearchOneTime.executeSearch(driver, term);
			}
		driver.quit();
		
	}
	
	public static void executeSearch(WebDriver driver, String searchTerm) {
		WebElement searchBox = driver.findElement(By.id("lst-ib"));
		searchBox.clear();
		searchBox.sendKeys(searchTerm);
		
		WebElement searchButton = driver.findElement(By.name("btnG"));
		searchButton.click();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String pageTitle = driver.getTitle();
		
		System.out.println("page title: " + pageTitle);
		
		boolean titleContainsTerm = pageTitle.contains(searchTerm);
		Assert.assertEquals(titleContainsTerm, true);
		
		System.out.println("contains term: " + titleContainsTerm);
	}
}
