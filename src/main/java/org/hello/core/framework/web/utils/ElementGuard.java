package org.hello.core.framework.web.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Proxy;

public class ElementGuard {
    public static WebElement guard(WebDriver driver, WebElement element) {
        ElementProxy proxy = new ElementProxy(driver, element);
        return (WebElement) Proxy.newProxyInstance(ElementProxy.class.getClassLoader(),
                new Class[] { WebElement.class },
                proxy);
    }
}
