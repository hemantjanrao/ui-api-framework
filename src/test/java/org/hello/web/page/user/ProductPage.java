package org.hello.web.page.user;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.hello.core.framework.web.page.BasePage;
import org.hello.core.framework.web.utils.WebUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;


public class ProductPage extends BasePage<ProductPage> {

    private Logger log = Logger.getLogger(getClass());
    String productPageUrl="";

    public ProductPage(WebDriver wd) {
        super(wd);
    }

    @Override
    public String getURL()
    {
        return productPageUrl;
    }

    /***
     * Page Elements
     */
    @FindBy(name="Submit")
    WebElement btnAddToCart;

    @FindBy(css="div[id='layer_cart'] a")
    WebElement btnProceedToChkoutOnCartPopUp;

    @FindBy(css="div[id='uniform-group_1'] select")
    WebElement selectSizeLocator;

    @FindBy(id="bigpic")
    WebElement imgBigPic;

    @FindBy(xpath="//a[@title='Next']")
    WebElement nextImage;

    @FindBy(xpath="//a[@title='Previous']")
    WebElement previousImage;

    /***
     *
     * Page methods/Steps
     */

    @Step("Selecting the product: [{0}] from page")
    public void selectProductFromPage(String productName)
    {
        log.info("Selecting the product from page");
        try
        {
            WebElement product = driver.findElement(By.xpath("//a[@title='"+productName+"'and @class='product-name']"));
            WebUtils.waitForElementToBeDisplayed(driver, product, 30);

            product.click();
        }
        catch(NoSuchElementException | StaleElementReferenceException e)
        {
            throw new ElementNotVisibleException("Product -'"+productName+"' is not visible/present");
        }
    }

    public void scrollImages(String productName){
        log.info("Scrolling the images");

        try
        {
            WebUtils.waitForElementToBeDisplayed(driver, imgBigPic, 30);
            imgBigPic.click();
        }
        catch(NoSuchElementException | StaleElementReferenceException e)
        {
            throw new ElementNotVisibleException("Product -'"+productName+"' is not visible/present");
        }
    }

    public void gotoNextImage(){
        log.info("Next Image");

        try
        {
            WebUtils.waitForElementToBeDisplayed(driver, nextImage, 30);
            nextImage.click();
        }
        catch(NoSuchElementException | StaleElementReferenceException e)
        {
            throw new ElementNotVisibleException("Image not found");
        }
    }

    public void gotoPreviousImage(){
        log.info("Next Image");

        try
        {
            WebUtils.waitForElementToBeDisplayed(driver, previousImage, 30);
            previousImage.click();
        }
        catch(NoSuchElementException | StaleElementReferenceException e)
        {
            throw new ElementNotVisibleException("Image not found");
        }
    }



    @Step("Click on 'Add To Cart' Product Page")
    public void clickAddToCartBtn()
    {
        log.info("Clicking 'Add To Cart' Product Page");
        WebUtils.clickWithWaitForElement(driver,btnAddToCart,10);
    }

    @Step("Selecting the size: [{0}]")
    public void selectProductSize(String size)
    {
        log.info("Selecting the product size");
        WebUtils.selectDropDownValue(selectSizeLocator,size.trim().toUpperCase());
    }


    @Step("Click link: [Proceed TO Checkout] on Product Page")
    public void clickProceedToChkoutBtn()
    {
        log.info("Clicking  'Proceed TO Checkout' on Product Page");
        WebUtils.clickWithWaitForElement(driver,btnProceedToChkoutOnCartPopUp,60);

    }
}
