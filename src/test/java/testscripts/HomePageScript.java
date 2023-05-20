package testscripts;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.HomePage;
import pageobjects.LoginPage;

public class HomePageScript extends common.Base{
    public HomePage objHomePage;
    public LoginPage objLoginPage;

    @BeforeClass
    public void initHomePageScript(){
        objHomePage = new HomePage(driver, wait);
        objLoginPage = new LoginPage(driver, wait);
    }

    @BeforeMethod
    public void goToHomePage(){
        driver.get(baseURL);
    }

    @Test //Verify that click in project link is exactly
    public void verifyLinkLogin(){
        objHomePage.clickLinkProject();
        Assert.assertTrue(objLoginPage.isExistLabelLogin());
    }
}
