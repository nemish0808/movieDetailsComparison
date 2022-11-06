package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ReadConfig {
	static Properties prop=new Properties();
	public  String wikipediaUrl;
	public String imdbUrl;
	public String browserName;
	public String movieName;
	


	public ReadConfig() {
		String path=System.getProperty("user.dir");
		File src=new File(path+"\\src\\main\\resources\\configuration.properties");

		try {

			InputStream input=new FileInputStream(src);
			prop.load(input);
		} 


		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public  String getwikipediaUrl() {
		wikipediaUrl=prop.getProperty("wikipediaurl");
		return wikipediaUrl;

	}
	public String getimdbUrl() {
		imdbUrl=prop.getProperty("imdbUrl");
		 return imdbUrl;
	}
	
	public String getBrowserName() {
		browserName=prop.getProperty("browserName");
		return browserName;
	}
	
	public String getMovieName() {
		movieName=prop.getProperty("movieName");
		return movieName;
	}
}