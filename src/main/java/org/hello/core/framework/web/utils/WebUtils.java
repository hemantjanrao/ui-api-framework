package org.hello.core.framework.web.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebUtils {

    /**
     * Wait for element to be present
     * @param driver
     * @param element
     * @param timeout
     */
    public static void waitForElementToBeDisplayed(WebDriver driver, WebElement element, long timeout) {
        try {
            new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
        }
        catch(TimeoutException e) {
            throw new ElementNotVisibleException("Timeout"+element+" is not visible/present.");
        }
    }

    /**
     * Method to get the text value from element
     * @param elem
     * @return
     */
    public static String getTextValue(WebElement elem){
        if(isElementPresent(elem)) {
            return elem.getText();
        }
        return null;
    }


    /**
     * Method to check the presence of element
     * @param element
     * @return
     */
    public static boolean isElementPresent(WebElement element) {
        try {
            return element.isDisplayed();
        }
        catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }


    /**
     * Method to click on element after waiting for specified time
     * @param elem
     * @param timeout
     */
    public static void clickWithWaitForElement(WebDriver driver, WebElement elem, long timeout) {
        waitForElementToBeDisplayed(driver, elem, timeout);
        elem.click();
    }

    public static void selectDropDownValue(WebElement element, String value) {

        Select select = new Select(element);
        long count = select.getOptions().stream().map(elem->elem.getAttribute("value")).filter(s-> s.trim().equals(value)).count();
        if(count>1) {
            throw new AssertionError(String.format("unable to select the '%s' value, there are %s matches",value,count));
        }
        else if(count==0) {
            count = select.getOptions().stream().map(WebElement::getText).filter(s-> s.trim().equals(value)).count();
            if(count!=1) {
                throw new AssertionError(String.format("unable to select the '%s' value, there are %s matches",value,count));
            }
            select.selectByVisibleText(value);
        }
        else if(count==1) {
            select.selectByValue(value);
        }
    }
}
