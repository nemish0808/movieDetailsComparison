package pages;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import utilities.ReadConfig;

public class DetailsPage {
	
	@FindBy(how=How.XPATH, using="//div/input[contains(@class,'imdb-header-search__input')]")
	WebElement imdbSearchBar;
	@FindBy(how=How.XPATH, using="//section[@data-testid='Details']//span[text()='Details']")
	WebElement imdbDetailsSection;
	@FindBy(how=How.XPATH, using="//li[@data-testid='title-details-releasedate']//li/a")
	WebElement imdbReleaseDate;
	@FindBy(how=How.XPATH, using="//li[@data-testid='title-details-origin']//li/a")
	WebElement imdbCountryOrigin;
	@FindBy(how=How.XPATH, using="//div[@class='search-input']//input")
	WebElement wikipediaSearchBar;
	@FindBy(how=How.XPATH, using="//table[@class='infobox vevent']//div[text()='Release date']/parent::th//following-sibling::td")
	WebElement wikipediaReleaseDate;
	@FindBy(how=How.XPATH, using="//table[@class='infobox vevent']//th[text()='Country']/following-sibling::td")
	WebElement wikipediaCountryOrigin;
	@FindBys({
		@FindBy(how=How.XPATH, using="//div[@class='suggestions-dropdown']//h3")
	}) List<WebElement> wikipediaSearchResult;
	
	@FindBys({
		@FindBy(how=How.XPATH, using="//ul[@role='listbox']/li/descendant::div[contains(@class,'searchResult__constTitle')]")
		
	}) List<WebElement> imdbSearchResult;
	
	WebDriver driver;
	ReadConfig rcg;
	
	
	public DetailsPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
		rcg=new ReadConfig();
	}
	
	
//	String wikiUrl=rcg.getwikipediaUrl();
	public boolean getDetailsFromImdb(HashMap<String, String> country, HashMap<String, String> releaseDate) {
		boolean res=true;
		WebDriverWait wait=new WebDriverWait(driver, 10);
		String movieName=rcg.getMovieName();
		Actions action=new Actions(driver);
		String imdbUrl=rcg.getimdbUrl();
		try {
			driver.get(imdbUrl);
			imdbSearchBar.sendKeys(movieName);
			
			boolean flag=false;
			wait.until(ExpectedConditions.visibilityOfAllElements(imdbSearchResult));
			for(int i=0;i<imdbSearchResult.size();i++) {
				if(imdbSearchResult.get(i).getText().contains(movieName)) {
					imdbSearchResult.get(i).click();
					flag=true;
					break;
				}
				
			}
			if(!flag) {
				System.out.println("Please enter the correct movie name");
				return false;
			}
			action.moveToElement(imdbCountryOrigin).build().perform();
			
			String imdbDate=imdbReleaseDate.getText().replaceAll(",", "").split("\\(")[0].trim();
			String arrStr[]=imdbDate.split(" ");
			StringBuffer sb=new StringBuffer();
			sb.append(arrStr[1]+" ");
			sb.append(arrStr[0]+" ");
			sb.append(arrStr[2]);
			imdbDate=sb.toString();
			System.out.println(imdbDate);
			String countryImdb=imdbCountryOrigin.getText();
			country.put("imdbCountry", countryImdb);
			releaseDate.put("imdbDate", imdbDate);	
		}
		catch(Exception e) {
			res=false;
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public boolean getDetailsFromWikipedia(HashMap<String, String> country, HashMap<String, String> releaseDate) {
		boolean res=true;
		WebDriverWait wait=new WebDriverWait(driver, 10);
		String movieName=rcg.getMovieName();
		Actions action=new Actions(driver);
		String wikipediaUrl=rcg.getwikipediaUrl();
		try {
			driver.get(wikipediaUrl);
			wikipediaSearchBar.sendKeys(movieName);
			boolean flag=false;
			wait.until(ExpectedConditions.visibilityOfAllElements(wikipediaSearchResult));
			for(int i=0;i<wikipediaSearchResult.size();i++) {
				System.out.println(wikipediaSearchResult.get(i).getAttribute("innerText"));
				if(movieName.contains(wikipediaSearchResult.get(i).getAttribute("innerText"))) {
					wikipediaSearchResult.get(i).click();
					flag=true;
					break;
				}
			}
			if(!flag) {
				System.out.println("Please enter the correct movie name in Wikipedia Search bar");
				return false;
			}
			action.moveToElement(wikipediaCountryOrigin).build().perform();
			country.put("wikipediaCoutry", wikipediaCountryOrigin.getText());
			releaseDate.put("wikipediaDate", wikipediaReleaseDate.getText());
			
		}
		catch(Exception e) {
			res=false;
			System.out.println(e.getMessage());
		}
		return res;
	}

}
