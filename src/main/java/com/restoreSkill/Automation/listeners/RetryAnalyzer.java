package com.restoreSkill.Automation.listeners;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retries failed tests up to {@link #MAX_RETRY} additional attempts (4 runs total if all fail).
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    public static final int MAX_RETRY = 3;

    private static final Logger LOG = LoggerFactory.getLogger(RetryAnalyzer.class);

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean retry(ITestResult result) {
        if (result.getStatus() != ITestResult.FAILURE) {
            return false;
        }
        int attempt = counter.getAndIncrement();
        if (attempt < MAX_RETRY) {
            LOG.warn("Retrying failed test [{}] — attempt {} of {} (method: {}.{})",
                    result.getName(),
                    attempt + 1,
                    MAX_RETRY,
                    result.getTestClass().getName(),
                    result.getMethod().getMethodName());
            return true;
        }
        LOG.error("Giving up after {} retries for [{}]", MAX_RETRY, result.getName());
        return false;
    }
}
