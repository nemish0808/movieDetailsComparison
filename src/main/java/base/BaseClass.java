package base;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import utilities.ReadConfig;


public class BaseClass {

	ReadConfig rcg= new ReadConfig();
	
	public static WebDriver driver;
	String browser=rcg.getBrowserName();
	
	public WebDriver setUpDriver()  {

		if(browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//Drivers/chromedriver.exe");
			driver=new ChromeDriver();
			return driver;
		}
		else {
			System.setProperty("webdriver.edge.driver", "C:\\driver\\msedgedriver.exe");
			driver=new EdgeDriver();
			return driver;
		}
		

	}
}
