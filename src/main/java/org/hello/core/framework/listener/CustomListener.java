package org.hello.core.framework.listener;

import org.apache.log4j.Logger;
import org.hello.core.framework.base.BaseWebTest;
import org.testng.*;

public class CustomListener implements IInvokedMethodListener {

    protected Logger log = Logger.getLogger(getClass());

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        log.info("Listener is activated");
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result)
    {
        Reporter.setCurrentTestResult(result);
        BaseWebTest baseTest = (BaseWebTest) result.getInstance();
        ITestNGMethod testNgMethod = method.getTestMethod();
        if(result.getStatus()==ITestResult.FAILURE)
        {
            String methodName = testNgMethod.getMethodName();
            log.warn(String.format("'%s' method is failed, saving screenshot", methodName));
            baseTest.takeScreenShot(methodName);
        }
    }
}