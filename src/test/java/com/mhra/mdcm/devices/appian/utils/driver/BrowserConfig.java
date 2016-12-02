package com.mhra.mdcm.devices.appian.utils.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Use this or the cucumber.xml don't use both
 * @author tayyibah
 *
 */
@Configuration
public class BrowserConfig {
	
	//@Autowired
    //public WebDriver driver;
	public String browser;

	@Bean
    public WebDriver getDriver() {

		if(browser == null){
			browser = System.getProperty("current.browser");
			browser = browser.toLowerCase();
		}
		
    	if(browser!=null){
			System.out.println("Browser : " + browser);

			//Firefox
			if(browser.equals("ff") || browser.equals("firefox")){
				DesiredCapabilities gcCap = getFirefoxDesiredCapabilities(false);
        		return new FirefoxDriver(gcCap);
    		}

			//Chrome
			else if(browser.equals("gc") || browser.equals("chrome")){
				DesiredCapabilities gcCap = getGoogleChromeDesiredCapabilities();
				return new ChromeDriver(gcCap);
			}
			//IE
			else if(browser.equals("ie") || browser.equals("internetexplorer")){
				DesiredCapabilities ieCap = getIEDesiredCapabilities();
				return new InternetExplorerDriver(ieCap);
			}else if(browser.equals("ie2") || browser.equals("internetexplorer2")){
				DesiredCapabilities ieCap = getIEDesiredCapabilities();
				return new EdgeDriver(ieCap);
			}
			//PhantomJs
			else if(browser.equals("pjs") || browser.equals("phantom")|| browser.equals("phantomjs")){
				DesiredCapabilities ieCap = getPJSDesiredCapabilities();
				return new PhantomJSDriver(ieCap);
			}
			//Defaults to project default IE
			else{
				DesiredCapabilities ieCap = getIEDesiredCapabilities();
				return new InternetExplorerDriver(ieCap);
    		}
    	}
		else{
			System.out.println("Using DEFAULT BROWSER IE, -Dcurrent.browser not set");
			DesiredCapabilities ieCap = getIEDesiredCapabilities();
			return new InternetExplorerDriver(ieCap);
    	}
    }

	private DesiredCapabilities getHtmlUnitDesiredCapabilities() {
		DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
		capabilities.setBrowserName("Mozilla/5.0 (X11; Linux x86_64; rv:49.0) Gecko/20100101 Firefox/49.0");
		capabilities.setJavascriptEnabled(true);
		return capabilities;
	}

	private DesiredCapabilities getFirefoxDesiredCapabilities(boolean isMarionette) {
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		if(isMarionette) {
			//System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\firefox\\geckodriver.exe");
			capabilities.setCapability("marionette", true);
		}
		return capabilities;
	}

	private DesiredCapabilities getGoogleChromeDesiredCapabilities() {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		options.addArguments("disable-popup-blocking");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		return capabilities;
	}

	/**
	 * You dont need to launch:
	 * 	- Selenium Server or PhantomJS
	 * 	- Should work out of the box
	 * @return
     */
	private DesiredCapabilities getPJSDesiredCapabilities() {
		String path = System.getProperty("phantomjs.binary.path");
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setJavascriptEnabled(true);
		caps.setCapability("takesScreenshot", true);
		caps.setCapability("browserName", "phantomjs");
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {
				//"--webdriver=8910",
				"--ssl-protocol=any",
				"--ignore-ssl-errors=true",
				"--webdriver-logfile=/bu/log/phantomjsdriver.log",
				"--webdriver-loglevel=NONE"
		});

		//Only required if not in the path
		caps.setCapability(	PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, path );
		return caps;
	}

	private DesiredCapabilities getIEDesiredCapabilities() {
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();

		ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
		ieCapabilities.setCapability("enablePersistentHover", false);

		ieCapabilities.setCapability("nativeEvents", true);
		ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
		ieCapabilities.setCapability("disable-popup-blocking", false);
		return ieCapabilities;
	}



}