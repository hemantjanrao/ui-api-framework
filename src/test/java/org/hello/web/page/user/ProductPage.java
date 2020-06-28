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
            product.click();
        }
        catch(NoSuchElementException | StaleElementReferenceException e)
        {
            throw new ElementNotVisibleException("Product -'"+productName+"' is not visible/present");
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
        WebUtils.clickWithWaitForElement(driver,btnProceedToChkoutOnCartPopUp,10);

    }
}
