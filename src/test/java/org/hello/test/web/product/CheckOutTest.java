package org.hello.test.web.product;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
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
public class CheckOutTest extends BaseWebTest {

    private LoginPage loginPage = null;
    private ProductPage productPage = null;
    private ProductOrderPage productOrderPage = null;
    private HomePage homePage = null;
    private MyAccountPage myAccountPage = null;

    @BeforeClass(alwaysRun=true)
    public void beforeClass() {

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        productOrderPage = new ProductOrderPage(driver);
        myAccountPage = new MyAccountPage(driver);
    }

    @DataProvider(name = "productData")
    public Object[][] productData() throws IOException, IOException {
        return parseExcelDataToDataProvider(
                FileUtils.getResourcePath(CheckOutTest.class, "TestData.xls"),"productTestData");
    }

    @Story("Product Checkout")
    @Test(groups= {Groups.CATEGORY_SANITY},dataProvider="productData",description="Verify user can checkout and place order after login")
    @Description("Verify user can checkout and place order after login")
    @TestID("34e55483-9660-4229-bc4b-82f00116f3c2")
    public void verifyProductCheckout(String productName,String productSize) {
        String existingUserEmail = "auto_new@testing.com";
        String existingUserPassword = "qwerty";
        homePage.navigateTo();
        homePage.clickLoginLink();
        loginPage.fillEmailField(existingUserEmail);
        loginPage.fillPasswordField(existingUserPassword);
        loginPage.clickSubmitBtn();
        myAccountPage.clickLinkOnMyAcctPage("Women");
        productPage.selectProductFromPage(productName);
        productPage.selectProductSize(productSize);
        productPage.clickAddToCartBtn();
        productPage.clickProceedToChkoutBtn();
        productOrderPage.clickProceedToChkoutBtn();
        productOrderPage.clickProceedToChkoutBtnProgressAddress();
        productOrderPage.clickTermsOfService();
        productOrderPage.clickProceedToChkOutProcessCarrier();
        productOrderPage.selectPayByBankWirePaymentMethod();
        productOrderPage.clickConfirmOrder();
        productOrderPage.verifyOrderCompletion();
        myAccountPage.logoutUser();

    }
}
