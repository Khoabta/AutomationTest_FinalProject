package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RerunFailTestCase implements IRetryAnalyzer {

    int count = 0;
    int retryLimit = 1;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (count < retryLimit){
            count++;
            return true;
        }
        return false;
    }
}
