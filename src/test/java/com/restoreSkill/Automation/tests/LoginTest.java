package com.restoreSkill.Automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.restoreSkill.Automation.base.BaseTest;
import com.restoreSkill.Automation.dataproviders.LoginDataProvider;
import com.restoreSkill.Automation.pages.login.LoginPage;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = LoginDataProvider.class)
    public void verifyValidLogin(String username, String password) throws InterruptedException {
    	//driver.get("https://www.google.com/");
    	Thread.sleep(3000);
    	//implicit and explicit wait
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
    }
}
