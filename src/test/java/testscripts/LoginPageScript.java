package testscripts;

import common.Base;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import utils.ReadDataFromExcel;

import java.util.HashMap;
import java.util.List;

public class LoginPageScript extends Base {
    public HomePage objHomePage;
    public LoginPage objLoginPage;
    private String colUsername = "Username";
    private String colPassword = "Password";
    private String colMsg = "Expected Message";
    private String colXpath = "Xpath Message";

    @BeforeClass
    public void setupLoginPage() throws Exception {
        objHomePage = new HomePage(driver, wait);
        objLoginPage = new LoginPage(driver, wait);
        String pathProject = System.getProperty("user.dir");
        ReadDataFromExcel.setExcelFile(pathProject + "/testdata/LoginPage.xlsx", "Sheet1");
    }

    @BeforeMethod (onlyForGroups = "Success")
    public void reloadLoginPage(){
        driver.get(baseURL);
        objHomePage.clickLinkProject();
    }

    @AfterMethod (onlyForGroups = "Success")
        public void closeSession() throws InterruptedException {
        objLoginPage.clickLogoutTitle();
        Thread.sleep(1000);
        objLoginPage.clickButtonOk();
        Assert.assertTrue(objLoginPage.isHomePage());
        Thread.sleep(1000);
    }

    @BeforeMethod (onlyForGroups = "Invalid")
    public void reloadLoginPage2() throws Exception {
        driver.get(baseURL);
        objHomePage.clickLinkProject();
    }

    @Test (groups = "Success")
    public void TC01_LoginSuccess() throws Exception {
        String testCase = "TC_01";
        HashMap mapData = ReadDataFromExcel.getDataFromExcelUsingHashmap(testCase);
        objLoginPage.inputDataLogin(mapData.get(colUsername).toString(), mapData.get(colPassword).toString());
        Assert.assertEquals(mapData.get(colMsg).toString(), objLoginPage.getTitleLogout());
    }

    /**
     * Sử dụng vòng lặp for để duyệt các trường hợp Invalid để chạy test case
     * Tạo 1 List để lưu các TestCaseID khi input type of test case là Invalid
     * Dùng For loop để duyệt các values trong List
     * mapData sẽ lưu trữ tất cả dữ liệu của dòng TestCaseID tương ứng ở vị trí i khi i chạy trong List<Values>
     * driver.navigate().refresh(); : refresh lại browser mỗi khi 1 testcase hoàn thành
     * @throws Exception
     */
    @Test (groups = "Invalid")
    public void testByForLoop() throws Exception {
        String typeOfTC = "Invalid";
        List<String> listTestCaseID = ReadDataFromExcel.getTestCaseIDByTypeOfTestCase(typeOfTC);
        HashMap mapData = new HashMap<>();
        for (int i = 0; i < listTestCaseID.size(); i++){
            mapData = ReadDataFromExcel.getDataFromExcelUsingHashmap(listTestCaseID.get(i));
            objLoginPage.inputDataLogin(mapData.get(colUsername).toString(), mapData.get(colPassword).toString());
            Assert.assertEquals(objLoginPage.getWrongMessage((String) mapData.get(colXpath)), mapData.get(colMsg).toString());
            driver.navigate().refresh();
        }
    }
}
