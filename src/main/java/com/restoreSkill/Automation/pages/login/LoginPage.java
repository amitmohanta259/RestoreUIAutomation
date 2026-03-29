package com.restoreSkill.Automation.pages.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.restoreSkill.Automation.locators.login.LoginPageLocators;
import com.restoreSkill.Automation.utils.ConfigReader;

public class LoginPage {
    private final WebDriver driver;
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    //WebElement usernameInput1 = driver.findElement("//input[@name='username']");
    
    
    @FindBy(xpath = LoginPageLocators.USERNAME_INPUT)
    private WebElement usernameInput;

    @FindBy(xpath = LoginPageLocators.PASSWORD_INPUT)
    private WebElement passwordInput;

    @FindBy(xpath = LoginPageLocators.LOGIN_BUTTON)
    private WebElement loginButton;

    @FindBy(xpath = LoginPageLocators.DASHBOARD_HEADER)
    private WebElement dashboardHeader;

   

    public void open() {
        driver.get(ConfigReader.getLoginUrl());
    }

    public void loginWith(String username, String password) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    public boolean isDashboardVisible() {
        return dashboardHeader.isDisplayed();
    }
}
