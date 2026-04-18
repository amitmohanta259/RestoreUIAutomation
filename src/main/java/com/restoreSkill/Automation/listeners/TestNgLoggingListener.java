package com.restoreSkill.Automation.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Logs TestNG test lifecycle events (start, success, failure, skip).
 */
public class TestNgLoggingListener implements ITestListener {

    private static final Logger LOG = LoggerFactory.getLogger(TestNgLoggingListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        LOG.info("START: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info("PASS:  {}.{} ({} ms)", result.getTestClass().getName(), result.getMethod().getMethodName(),
                result.getEndMillis() - result.getStartMillis());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOG.error("FAIL:  {}.{} — {}", result.getTestClass().getName(), result.getMethod().getMethodName(),
                result.getThrowable() != null ? result.getThrowable().getMessage() : "unknown");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.warn("SKIP:  {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName());
    }
}
