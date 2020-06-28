package org.hello.core.framework.web.page;

import org.hello.core.framework.utils.Environment;
import org.hello.core.framework.utils.PropertyUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage<T extends BasePage> {
    protected WebDriver driver;

    /**
     * Abstract Method to get the url of the page.
     * @return the url of the page.
     */
    public abstract String getURL();

    /**
     * Base Page constructor.
     *
     * @param driver				Webdriver object
     * @param waitForPageLoad	boolean to specify if wait for page load or not.
     */
    public BasePage(WebDriver driver, Boolean waitForPageLoad) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    /**
     * Base Page constructor.
     *
     * @param wd	Webdriver object
     */
    public BasePage(WebDriver wd) {
        this(wd,false);
    }

    /**
     * Method to navigate to page url directly.
     *
     * @return the calling object
     */
    public T navigateTo() {
        String url = PropertyUtils.get(Environment.WEB_URL)+this.getURL();
        if(driver.getCurrentUrl().equals(url)) {
            driver.navigate().refresh();
        }
        else {
            driver.get(url);

        }
        return (T)this;

    }
}
