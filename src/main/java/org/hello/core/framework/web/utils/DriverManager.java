package org.hello.core.framework.web.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.hello.core.framework.constants.ManagedBrowsers;
import org.hello.core.framework.utils.Environment;
import org.hello.core.framework.utils.PropertyUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.internal.ElementScrollBehavior;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Utility class to create and provide the required WebDriver.
 * with all required capabilities.
 *
 * @author Manoj Sharma
 *
 */


public class DriverManager {

	public static ThreadLocal<WebDriver> drivers = new ThreadLocal<WebDriver>();

	private static Logger log = Logger.getLogger(DriverManager.class);

	/**
	 * Method to get required Webdriver based on the configuration file.
	 *
	 * @return WebDriver| null
	 */
	public synchronized static WebDriver getWebDriver() {
		ManagedBrowsers browser = ManagedBrowsers.valueOf(PropertyUtils.get(Environment.WEB_BROWSER).toUpperCase());
		WebDriver driver = getWebdriver(browser);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		drivers.set(driver);

		return getDriver();
	}

	public static synchronized WebDriver getDriver() {
		while (drivers.get() == null) {
			getWebDriver();
		}
		return drivers.get();
	}

	/**
	 * Method to get required Webdriver based on the type of browser.
	 *
	 * @param browser Enum to specify the type of browser
	 * @return WebDriver| null
	 */
	public static synchronized WebDriver getWebdriver(ManagedBrowsers browser) {
		boolean isSeleniumGridEnabled = PropertyUtils.getBoolean(Environment.WEB_IS_GRID_ENABLED);
		DesiredCapabilities cap = null;

		switch (browser) {
		case FIREFOX:

			case GECKO:
				WebDriverManager.firefoxdriver().setup();
			cap = getFireFoxDesiredCapabilities();
			cap.setCapability(FirefoxDriver.MARIONETTE, true);

			try {
				if (isSeleniumGridEnabled) {
					log.info(String.format("Grid is enabled [%s]", getSeleniumGridHubUrl()));
					return getRemoteDriver(cap);
				}
				else {
					return new FirefoxDriver(cap);
				}
			}
			catch (Exception e) {
				log.warn("Error creating driver", e);
				throw new RuntimeException("Failed to get the driver", e);
			}

		case CHROME:
			WebDriverManager.chromedriver().setup();
			cap = getChromeCapabilities();
			try {
				if (isSeleniumGridEnabled) {
					log.info(String.format("Grid is enabled [%s]", getSeleniumGridHubUrl()));
					return getRemoteDriver(cap);
				}
				else {
					return new ChromeDriver(cap);
				}

			}
			catch (Exception e) {
				log.warn("Error creating driver", e);
				throw new RuntimeException("Failed to get the driver", e);
			}
		}
		return null;
	}

	/**
	 * Method to get the desired capabilities for firefox.
	 *
	 * @return DesiredCapabilities
	 */
	private static DesiredCapabilities getFireFoxDesiredCapabilities() {
		String neverAskSaveToDiskAndOpenFileValues = "application/octet-stream, application/x-zip-compressed, " +
				"application/zip-compressed, application/zip, multipart/x-zip, application/x-compressed, " +
				"application/msword, text/plain, image/gif, image/png, application/pdf, application/excel, " +
				"application/vnd.ms-excel, application/x-excel, application/x-msexcel, text/csv";
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("app.update.enabled", false);
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.dir", getDownloadDirectory());
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.panel.shown", true);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", neverAskSaveToDiskAndOpenFileValues);
		profile.setPreference("browser.helperApps.neverAsk.openFile", neverAskSaveToDiskAndOpenFileValues);
		DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();
		firefoxCapabilities.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, ElementScrollBehavior.BOTTOM);
		firefoxCapabilities.setCapability(FirefoxDriver.PROFILE, profile);
		return firefoxCapabilities;
	}

	/**
	 * Method to get the desired capabilities for chrome.
	 *
	 * @return DesiredCapabilities
	 */
	private static DesiredCapabilities getChromeCapabilities() {
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		ChromeOptions option = new ChromeOptions();
		Map<String, Object> pref = new HashMap<>();
		pref.put("download.prompt_for_download", false);
		pref.put("download.default_directory", getDownloadDirectory());
		option.setExperimentalOption("prefs", pref);
		option.addArguments("--start-maximized");
		cap.setCapability(ChromeOptions.CAPABILITY, option);
		return cap;
	}

	/**
	 *
	 * @return
	 */
	public static String getDownloadDirectory() {
		return System.getProperty("user.dir") + File.separator + "target";
	}

	/**
	 * Method to get the selenium grid hub url based on configuration file.
	 *
	 * @return URL selenium grid url.
	 * @throws RuntimeException
	 */
	private static URL getSeleniumGridHubUrl() {
		try {
			return new URL("http://" + PropertyUtils.get(Environment.WEB_SELENIUM_GRID) + "/wd/hub");
		}
		catch (MalformedURLException e) {
			throw new RuntimeException("Selenium grid hub URL is not valid", e);
		}
	}

	/**
	 * Function to get the remote Webdriver in case of running automation using
	 * selenium grid.
	 *
	 * @param cap  Desired capabilities to be set for the browser driver
	 * @return RemoteWebDriver
	 *
	 * @throws RuntimeException
	 */
	private static RemoteWebDriver getRemoteDriver(DesiredCapabilities cap) {
		try {
			RemoteWebDriver remoteWebDriver = new RemoteWebDriver(getSeleniumGridHubUrl(), cap);
			remoteWebDriver.setFileDetector(new LocalFileDetector());
			return remoteWebDriver;
		}
		catch (Exception e) {
			throw new RuntimeException("Error creating Remote webriver");
		}

	}

}
