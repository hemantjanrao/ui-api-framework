package org.hello.core.framework.web.utils;

import org.hello.core.framework.utils.Environment;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class ElementProxy implements InvocationHandler {

    private final WebElement element;
    private final WebDriver driver;

    public ElementProxy(WebDriver driver, WebElement element) {
        this.element = element;
        this.driver = driver;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        this.checkAvailability();

        Object result = method.invoke(element, args);

        return result;
    }

    public void checkAvailability(){

        ((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView(true);", element);

        try {
            await("Element is not displayed").atMost(60, SECONDS)
                    .until(element::isDisplayed);
        }
        catch(Exception e) {
            new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(element));
            throw new ElementNotVisibleException("Timeout"+element+" is not visible/present.");
        }
    }
}
