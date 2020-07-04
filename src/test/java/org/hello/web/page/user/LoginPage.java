package org.hello.web.page.user;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.hello.core.framework.web.page.BasePage;
import org.hello.core.framework.web.utils.WebUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class LoginPage extends BasePage<LoginPage> {

    /**
     * Page web elements
     */

    @FindBy(id="email")
    WebElement emailTextField;
    @FindBy(id="passwd")
    WebElement passwordField;
    @FindBy(id="SubmitLogin")
    WebElement loginSubmitBtn;
    @FindBy(css="h1")
    WebElement successfulLgnLocator;
    @FindBy(className="account")
    WebElement fullNameAfterLoginLocator;
    @FindBy(className="info-account")
    WebElement accountWelcomeMsg;
    @FindBy(className="logout")
    WebElement logoutLnk;
    private Logger log = Logger.getLogger(getClass());

    public LoginPage(WebDriver wd) {
        super(wd);
    }

    @Override
    public String getURL() {
        return "?controller=authentication&back=my-account";
    }

    @Step("Enter the email : [{0}]")
    public void fillEmailField(String email) {
        log.info("Entering the email address");
        emailTextField.sendKeys(email);
    }

    @Step("Enter the password : [{0}]")
    public void fillPasswordField(String password) {
        log.info("Entering the password");
        passwordField.sendKeys(password);
    }

    @Step("Submit the login form")
    public void clickSubmitBtn() {
        log.info("Clicking the login button");
        loginSubmitBtn.click();
        WebUtils.waitForElementToBeDisplayed(driver, successfulLgnLocator, 10);
    }

    @Step("Verify the successful Login")
    public void verifySuccessfulLogin(String fullName) {
        log.info("Verifying the successful login");
        Assert.assertEquals(WebUtils.getTextValue(successfulLgnLocator),"MY ACCOUNT","My account header is present after login");
        Assert.assertEquals(WebUtils.getTextValue(fullNameAfterLoginLocator),fullName);
        Assert.assertTrue(WebUtils.getTextValue(accountWelcomeMsg).contains("Welcome to your account."));
        Assert.assertTrue(WebUtils.isElementPresent(logoutLnk),"Logout link not present after login");
        Assert.assertTrue(driver.getCurrentUrl().contains("controller=my-account"),"Current url is not as expected after successful login");
    }
}
