package com.restoreSkill.Automation.dataproviders;

import org.testng.annotations.DataProvider;

import com.restoreSkill.Automation.utils.ExcelUtils;

public class LoginDataProvider {
    private static final String LOGIN_DATA_PATH = "src/test/resources/testdata/LoginData.xlsx";
    private static final String SHEET_NAME = "LoginData";

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
        return ExcelUtils.readSheetData(LOGIN_DATA_PATH, SHEET_NAME);
    }
}
