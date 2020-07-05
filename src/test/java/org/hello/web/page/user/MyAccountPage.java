package org.hello.web.page.user;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.hello.core.framework.web.page.BasePage;
import org.hello.core.framework.web.utils.WebUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage<MyAccountPage> {

    private Logger log = Logger.getLogger(getClass());
    private String accountPageUrl="";

    public MyAccountPage(WebDriver wd) {
        super(wd);
    }

    @Override
    public String getURL()
    {
        return accountPageUrl;
    }

    @FindBy(css="a.logout")
    WebElement logoutLink;


    @Step("Click link on Account page")
    public void clickLinkOnMyAcctPage(String linkName)
    {
        log.info("Clicking the link '"+linkName+"' on my account page");
        WebElement linkWomen= driver.findElement(By.linkText(linkName));
        WebUtils.clickWithWaitForElement(driver,linkWomen,10);
    }

    @Step("Logout the user")
    public void logoutUser()
    {
        if(WebUtils.isElementPresent(logoutLink))
        {
            WebUtils.clickWithWaitForElement(driver,logoutLink , 30);
        }
    }
}
