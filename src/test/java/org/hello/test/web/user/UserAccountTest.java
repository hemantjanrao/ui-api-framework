package org.hello.test.web.user;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.commons.lang3.RandomStringUtils;
import org.hello.core.framework.annotation.Groups;
import org.hello.core.framework.annotation.TestID;
import org.hello.core.framework.base.BaseWebTest;
import org.hello.core.framework.utils.FileUtils;
import org.hello.web.page.user.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

@Epic("Web Automation Task")
public class UserAccountTest extends BaseWebTest {

    LoginPage loginPage = null;
    ProductPage productPage = null;
    ProductOrderPage productOrderPage = null;
    SignUpPage signUpPage = null;
    HomePage homePage = null;
    MyAccountPage myAccountPage = null;

//    @BeforeClass(alwaysRun=true)
//    public void beforeClass() {
//
//        homePage = new HomePage(driver);
//        loginPage = new LoginPage(driver);
//        signUpPage = new SignUpPage(driver);
//        productPage = new ProductPage(driver);
//        productOrderPage = new ProductOrderPage(driver);
//        myAccountPage = new MyAccountPage(driver);
//    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws IOException {
        return parseExcelDataToDataProvider(
                FileUtils.getResourcePath(UserAccountTest.class, "TestData.xls"),"loginTestData");
    }

    @Story("User Account Login")
    @Test(groups= {Groups.CATEGORY_SMOKE},dataProvider="loginData",description="Verify user login with existing user")
    @Description("Verify user login with existing user")
    @TestID("d4777d40-adab-45e1-b38b-e84a21601005")
    public void logInTest(String fullName,String existingUserEmail,String existingUserPassword) {

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        myAccountPage = new MyAccountPage(driver);

        homePage.navigateTo();
        homePage.clickLoginLink();
        loginPage.navigateTo();
        loginPage.fillEmailField(existingUserEmail);
        loginPage.fillPasswordField(existingUserPassword);
        loginPage.clickSubmitBtn();
        loginPage.verifySuccessfulLogin(fullName);
        myAccountPage.logoutUser();
    }


    @Story("User Account Sign up")
    @Test(groups= {Groups.CATEGORY_SANITY},description="Verify creating user using SignUp form")
    @Description("Verify creating user using SignUp form")
    @TestID("563b3302-0a1f-4b46-823b-95cf631347ed")
    public void signInTest() {
        homePage = new HomePage(driver);
        signUpPage = new SignUpPage(driver);
        myAccountPage = new MyAccountPage(driver);


        String email = "auto_"+ RandomStringUtils.randomAlphabetic(5)+"@testing.com";
        String name = "Firstname";
        String surname = "Lastname";
        String password="qwerty";
        String company="company";
        String address ="Qwerty, 123";
        String address_line2="sgafdgh";
        String city="Qwerty";
        String state="Colorado";
        int postalCode =12345;
        String additionalInfo="qwerty";
        String phoneNumber = "12345123123";
        String mobilePhoneNumber = "12345123123";
        String addressAlias="qwerty";
        homePage.navigateTo();
        homePage.clickLoginLink();
        signUpPage.fillCreateActEmailField(email);
        signUpPage.clickCreateAcountBtn();
        signUpPage.fillCustomerFirstNameField(name);
        signUpPage.fillCustomerLastNameField(surname);
        signUpPage.fillpasswordField(password);
        signUpPage.selectDateOfBirth(1,1, 2000);
        signUpPage.fillCustomerCompanyField(company);
        signUpPage.fillCustomerCompleteAddress(address, address_line2);
        signUpPage.fillCustomerCityField(city);
        signUpPage.selectStateFiled(state);
        signUpPage.fillCustomerPostalAddressCode(postalCode);
        signUpPage.fillAdditionalInfoField(additionalInfo);
        signUpPage.fillCustomerHomePhnNoField(phoneNumber);
        signUpPage.fillCustomerMobilePhnNoField(mobilePhoneNumber);
        signUpPage.fillAddressAliasField(addressAlias);
        signUpPage.clickRegisterBtn();
        signUpPage.verifyTheSignupProcess(name,surname);
        myAccountPage.logoutUser();
    }
}
