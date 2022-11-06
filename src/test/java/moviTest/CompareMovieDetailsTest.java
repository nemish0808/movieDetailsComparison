package moviTest;

import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.WebDriver;


import base.BaseClass;
import pages.DetailsPage;
import utilities.ReadConfig;



public class CompareMovieDetailsTest {
	WebDriver driver;
	ReadConfig rcg;
	BaseClass baseinfo;
	Assertion HardAssert=new Assertion();
	DetailsPage detailsPage;
	HashMap<String, String> country=new HashMap<String, String>();
	HashMap<String, String> releaseDate=new HashMap<String, String>();
	
	@BeforeClass
	public void beforeClass(){

		baseinfo=new BaseClass(); 
		rcg=new ReadConfig();
		driver=baseinfo.setUpDriver();
		driver.manage().window().maximize();
		detailsPage=new DetailsPage(driver);
		
	}
	@Test
	public void movieDetails() {
		boolean res1=detailsPage.getDetailsFromImdb(country, releaseDate);
		boolean res2=detailsPage.getDetailsFromWikipedia(country, releaseDate);
		if(res1 && res2) {
			Set<String> countryValues=new HashSet<String>(country.values());
			Set<String> dateValues=new HashSet<String>(releaseDate.values());
			if(countryValues.size()==1 && dateValues.size()==1) {
				Reporter.log("The details matched from imdb as well as wikipedia");
			}
			
		}
		else if(!res1) {
			HardAssert.fail("Error in fetching details from imdb page");
			System.out.println("1");
		}
		else {
			System.out.println("2");
			HardAssert.fail("Error in fetching details from wikipedia page");
		}
	}
	@AfterTest
	public void closeDriver() {
		driver.quit();
	}
	

}
