package org.hello.web.page.user;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.hello.core.framework.web.page.BasePage;
import org.hello.core.framework.web.utils.WebUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;


public class ProductOrderPage extends BasePage<ProductOrderPage> {

    public ProductOrderPage(WebDriver wd) {
        super(wd);
    }
    private Logger log = Logger.getLogger(getClass());

    @Override
    public String getURL() {
        return null;
    }

    /**
     * Page Elements
     */

    @FindBy(css="p[class*='cart_navigation'] a[class*='standard-checkout']")
    WebElement btnProceedToChkout;

    @FindBy(name="processAddress")
    WebElement btnProceedToChkoutAdress;

    @FindBy(id="uniform-cgv")
    WebElement termOfSvcChckBox;

    @FindBy(name="processCarrier")
    WebElement btnProceedToChkoutCarrier;

    @FindBy(className="bankwire")
    WebElement payByBankWireLink;

    @FindBy(css="p[id='cart_navigation'] button")
    WebElement btnConfirmOrder;

    @FindBy(css=".page-heading")
    WebElement successfulOrderLocator;

    @FindBy(css="li[class*='step_done_last four']")
    WebElement lastShippingStepLocator;

    @FindBy(css="li[id='step_end']")
    WebElement endStepPaymentLocator;

    @FindBy(css="p[class='cheque-indent'] strong")
    WebElement confirOrderMsgLocator;

    @Step("Click  'Proceed To Checkout' on Product Order Page")
    public void clickProceedToChkoutBtn()
    {
        log.info("Clicking 'Proceed To Checkout' on Product Order Page");
        WebUtils.clickWithWaitForElement(driver,btnProceedToChkout,10);
    }


    @Step("Click  'Proceed To Checkout' on Order Page to accept the Address")
    public void clickProceedToChkoutBtnProgressAddress()
    {
        log.info("Clicking  'Proceed To Checkout' on Order Page to accept the Address");
        WebUtils.clickWithWaitForElement(driver,btnProceedToChkoutAdress,10);
    }

    @Step("Accept the Term of service on Order page")
    public void clickTermsOfService()
    {
        log.info("Accepting the 'Term of service' on Order page");
        WebUtils.clickWithWaitForElement(driver,termOfSvcChckBox,10);
    }

    @Step("Click  'Proceed To Checkout' on Order Page to accept the Carrier")
    public void clickProceedToChkOutProcessCarrier()
    {
        log.info("Clicking  'Proceed To Checkout' on Order Page to accept the Carrier");
        WebUtils.clickWithWaitForElement(driver,btnProceedToChkoutCarrier,10);
    }

    @Step("Select the payment 'Pay By Bank Wire' method")
    public void selectPayByBankWirePaymentMethod()
    {
        log.info("Selecting the payment method 'Pay By Bank Wire'");
        WebUtils.clickWithWaitForElement(driver,payByBankWireLink,10);
    }


    @Step("Confirm the order")
    public void clickConfirmOrder()
    {
        log.info("Confirming the order");
        WebUtils.clickWithWaitForElement(driver,btnConfirmOrder,10);
        WebUtils.waitForElementToBeDisplayed(driver, successfulOrderLocator,10);
    }

    @Step("Verifying the completion of order")
    public void verifyOrderCompletion()
    {
        log.info("Verifying the completion of order");
        Assert.assertEquals(WebUtils.getTextValue(successfulOrderLocator), "ORDER CONFIRMATION","Order confirmation message is not as expected");
        Assert.assertTrue(WebUtils.isElementPresent(lastShippingStepLocator),"Confirmed Shipping step is not present");
        Assert.assertTrue(WebUtils.isElementPresent(endStepPaymentLocator),"Current payement end step is not present");
        Assert.assertTrue(WebUtils.getTextValue(confirOrderMsgLocator).contains("Your order on My Store is complete."), "Order Complete message is not as expected");
        Assert.assertTrue(driver.getCurrentUrl().contains("controller=order-confirmation"),"Current url is not of order-confirmation");

    }
}
