package testscripts;

import common.Base;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.LogoutPage;
import utils.ReadDataFromExcel;

public class LogoutPageScript extends Base {
    public HomePage objHomePage;
    public LogoutPage objLogoutPage;

    @BeforeClass
    public void setupLoginPage() throws Exception {
        objHomePage = new HomePage(driver, wait);
        objLogoutPage = new LogoutPage(driver, wait);
    }

    @BeforeMethod
    public void reloadLoginPage() throws InterruptedException {
        driver.get(baseURL);
        objHomePage.clickLinkProject();
        objLogoutPage.loginPage();
    }

    @Test
    public void testLogoutFuntion() throws InterruptedException {
        objLogoutPage.clickLogoutTitle();
        Thread.sleep(2000);
        objLogoutPage.clickButtonOk();
        Assert.assertTrue(objLogoutPage.isHomePage());
        Thread.sleep(2000);
    }
}
