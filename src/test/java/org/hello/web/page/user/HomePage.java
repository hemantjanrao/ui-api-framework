package org.hello.web.page.user;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.hello.core.framework.web.page.BasePage;
import org.hello.core.framework.web.utils.WebUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage<HomePage> {

    private Logger log = Logger.getLogger(getClass());

    private String homePageUrl = "";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getURL() {
        return homePageUrl;
    }


    @FindBy(className="login")
    WebElement loginLink;

    @Step("Click on Login Link on Home page")
    public void clickLoginLink()
    {
        log.info("Clicking 'Login Link' on Home page");
        WebUtils.clickWithWaitForElement(driver,loginLink,10);
    }
}
