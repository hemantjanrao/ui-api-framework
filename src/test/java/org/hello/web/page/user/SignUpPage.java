package org.hello.web.page.user;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.hello.core.framework.web.page.BasePage;
import org.hello.core.framework.web.utils.WebUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;


public class SignUpPage extends BasePage {

    public SignUpPage(WebDriver wd) {
        super(wd);
    }
    private Logger log = Logger.getLogger(getClass());

    @Override
    public String getURL() {
        return null;
    }


    @FindBy(id="email_create")
    WebElement emailField;

    @FindBy(id="SubmitCreate")
    WebElement createAccountBtn;

    @FindBy(id="customer_firstname")
    WebElement firstNameField;

    @FindBy(id="customer_lastname")
    WebElement lastNameField;

    @FindBy(id="passwd")
    WebElement passwordField;

    @FindBy(id="days")
    WebElement dateDaysField;

    @FindBy(id="months")
    WebElement dateMonthsField;

    @FindBy(id="years")
    WebElement dateYearsField;

    @FindBy(id="company")
    WebElement companyField;

    @FindBy(id="address1")
    WebElement addressField;

    @FindBy(id="address2")
    WebElement addressLine2Field;

    @FindBy(id="city")
    WebElement cityField;

    @FindBy(id="id_state")
    WebElement stateField;

    @FindBy(id="id_country")
    WebElement countryField;

    @FindBy(id="postcode")
    WebElement postalCodeField;

    @FindBy(id="other")
    WebElement additionalInfoField;

    @FindBy(id="phone")
    WebElement homePhoneNoField;

    @FindBy(id="phone_mobile")
    WebElement mobilePhoneNoField;

    @FindBy(id="alias")
    WebElement addressAliasField;

    @FindBy(id="submitAccount")
    WebElement registerBtn;

    @FindBy(css="h1")
    WebElement successfulSignUpLocator;

    @FindBy(className="account")
    WebElement useraccountName;

    @FindBy(className="info-account")
    WebElement accountWelcomeMsg;

    @FindBy(className="logout")
    WebElement logoutLink;



    @Step("Enter the email : [{0}] field on Signup page to create account")
    public void fillCreateActEmailField(String email)
    {
        log.info("Entering the email on Signup page to create account");
        emailField.sendKeys(email);
    }

    @Step("Enter the customer firstname: [{0}] field on Signup page")
    public void fillCustomerFirstNameField(String firstname)
    {
        log.info("Entering the firstname on Signup page to create account");
        firstNameField.sendKeys(firstname);
    }

    @Step("Enter the customer lastname: [{0}] field on Signup page")
    public void fillCustomerLastNameField(String lastName)
    {
        log.info("Entering the lastname on Signup page to create account");
        lastNameField.sendKeys(lastName);
    }


    @Step("Enter the customer password: [{0}] field on Signup page")
    public void fillpasswordField(String password)
    {
        log.info("Entering the password on Signup page to create account");
        passwordField.sendKeys(password);
    }

    @Step("Enter the customer company: [{0}] field on Signup page")
    public void fillCustomerCompanyField(String company)
    {
        log.info("Entering the company on Signup page to create account");
        companyField.sendKeys(company);
    }


    @Step("Enter the customer complete address. Address1:[{0}] and Address2:[{1}]")
    public void fillCustomerCompleteAddress(String address, String addressLine2)
    {
        log.info("Entering the complete address on Signup page to create account");
        addressField.sendKeys(address);
        addressLine2Field.sendKeys(addressLine2);
    }

    @Step("Enter the customer city: [{0}] ")
    public void fillCustomerCityField(String city)
    {
        log.info("Entering the city");
        cityField.sendKeys(city);
    }


    @Step("Enter the customer address postal code:[{0}]")
    public void fillCustomerPostalAddressCode(int postalCode)
    {
        log.info("Entering the postal code on Signup page to create account");
        postalCodeField.sendKeys(String.valueOf(postalCode));
    }

    @Step("Enter the customer additional info:[{0}]")
    public void fillAdditionalInfoField(String addtionalInfo)
    {
        log.info("Entering the additional info on Signup page to create account");
        additionalInfoField.sendKeys(addtionalInfo);
    }


    @Step("Enter the customer phone number:[{0}] on SignUp Page")
    public void fillCustomerHomePhnNoField(String phoneNumber)
    {
        log.info("Entering the home phone on Signup page to create account");
        homePhoneNoField.sendKeys(phoneNumber);
    }

    @Step("Enter the customer mobile phone number:[{0}] on SignUp Page")
    public void fillCustomerMobilePhnNoField(String phoneNumber)
    {
        log.info("Entering the mobile phone on Signup page to create account");
        mobilePhoneNoField.sendKeys(phoneNumber);
    }


    @Step("Enter the Address Alias:[{0}] on SignUp Page")
    public void fillAddressAliasField(String addressAlias)
    {
        log.info("Entering the address alias on Signup page to create account");
        addressAliasField.sendKeys(addressAlias);
    }


    @Step("Click on 'Create an account' button")
    public void clickCreateAcountBtn()
    {
        log.info("Clicking on 'Create an account' button");
        createAccountBtn.click();
    }

    @Step("Submit account create")
    public void clickRegisterBtn()
    {
        log.info("Clicking on 'Register' button");
        registerBtn.click();
    }

    @Step("Enter 'Date Of Birth' field on SignUp Page")
    public void selectDateOfBirth(int day, int month,int year)
    {
        log.info("Entering 'Date Of Birth' field on SignUp Page");
        WebUtils.selectDropDownValue(dateDaysField, String.valueOf(day));
        WebUtils.selectDropDownValue(dateMonthsField, String.valueOf(month));
        WebUtils.selectDropDownValue(dateYearsField, String.valueOf(year));
    }

    @Step("Enter the state:[{0}] on SignUp Page")
    public void selectStateFiled(String state)
    {
        log.info("Entering the state field on SignUp Page");
        WebUtils.selectDropDownValue(stateField, state);
    }

    @Step("Enter the country:[{0}] on SignUp Page")
    public void selectCountryFiled(String country)
    {
        log.info("Entering the country:[{0}] on SignUp Page");
        WebUtils.selectDropDownValue(countryField, country);
    }

    @Step("Verify the singup")
    public void verifyTheSignupProcess( String name, String surname)
    {
        log.info("Verifying the successful SignUp");
        Assert.assertEquals(WebUtils.getTextValue(successfulSignUpLocator), "MY ACCOUNT","My account header is not present after signup");
        Assert.assertEquals(WebUtils.getTextValue(useraccountName), name + " " + surname);
        Assert.assertTrue(WebUtils.getTextValue(accountWelcomeMsg).contains("Welcome to your account."));
        Assert.assertTrue(WebUtils.isElementPresent(logoutLink));
        Assert.assertTrue(driver.getCurrentUrl().contains("controller=my-account"));
    }
}
