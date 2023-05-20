package testscripts;

import common.Base;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.AddProductPage;
import pageobjects.HomePage;
import utils.ReadDataFromExcel;
import utils.RerunFailTestCase;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class AddProductPageScript extends Base {
    HomePage objHomePage;
    AddProductPage objAddProductPage;
    Base objBase;
    private String productNameCol = "Product Name *";
    private String introduceCol = "Introduce *";
    private String detailsCol = "Details";
    private String priceCol = "Price *";
    private String discountCol = "Discount Percent";
    private String msgCol = "Expected Message";
    private String xpathCol = "Xpath message";
    private HashMap mapData;

    @BeforeClass
    public void setupLoginPage() throws Exception {
        objHomePage = new HomePage(driver, wait);
        objAddProductPage = new AddProductPage(driver, wait);
        String pathProject = System.getProperty("user.dir");
        ReadDataFromExcel.setExcelFile(pathProject + "/testdata/AddProduct.xlsx", "Sheet1");
    }

    @BeforeMethod
    public void reloadLoginPage() throws InterruptedException {
        driver.get(baseURL);
        objHomePage.clickLinkProject();
        objAddProductPage.loginPage();
        objAddProductPage.clickAddProductLink();
        Thread.sleep(1000);
    }

    @AfterMethod
    public void screenShot(ITestResult iTestResult){
        if (ITestResult.FAILURE == iTestResult.getStatus()){
            try {
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                File scr = screenshot.getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scr, new File((pathProject + "/img/") +iTestResult.getName() + ".png"));
                System.out.println("Successfully capture screen shot");
            }
            catch (Exception e){
                System.out.println("Error" +e.getMessage());
            }

        }
    }

    public void hashMap(String testCase) throws Exception {
        mapData = ReadDataFromExcel.getDataFromExcelUsingHashmap(testCase);
        objAddProductPage.inputDataFromExcel(mapData.get(productNameCol).toString(), mapData.get(introduceCol).toString(),
                mapData.get(detailsCol).toString(), mapData.get(priceCol).toString(), mapData.get(discountCol).toString());
    }

    public void hashMapForExistedTestCase(String testCase) throws Exception {
        mapData = ReadDataFromExcel.getDataFromExcelUsingHashmap(testCase);
        objAddProductPage.inputDataFromExcelForExistedTestCase(mapData.get(introduceCol).toString(), mapData.get(priceCol).toString());
    }

    @Test (retryAnalyzer = RerunFailTestCase.class)
    public void validateAddProduct() throws Exception {
        String addProduct = "Add Product Success";
        List<String> listTestCaseID = ReadDataFromExcel.getTestCaseIDByTypeOfTestCase(addProduct);
        for (int i = 0; i < listTestCaseID.size(); i++){
            objAddProductPage.inputRandomData(5,15,3); //Hashcode: ProductNameLength, IntroduceLength, PriceLength
            objAddProductPage.selectRandomCategory();
            mapData = ReadDataFromExcel.getDataFromExcelUsingHashmap(listTestCaseID.get(i));
            objAddProductPage.inputDataFromExcelForAddProduct(mapData.get(detailsCol).toString(), mapData.get(discountCol).toString());
            objAddProductPage.checkFeaturedProduct();
            objAddProductPage.uploadFile();
            Assert.assertEquals(objAddProductPage.getWrongMessage((String) mapData.get(xpathCol)), mapData.get(msgCol).toString());
        }
    }

    @Test (retryAnalyzer = RerunFailTestCase.class)
    public void validateEmptyCategory() throws Exception {
        String emptyCategoryTC = "Validate Category";
        List<String> listTestCaseID = ReadDataFromExcel.getTestCaseIDByTypeOfTestCase(emptyCategoryTC);
        for (int i = 0; i < listTestCaseID.size(); i++){
            hashMap(listTestCaseID.get(i));
            Assert.assertEquals(objAddProductPage.getWrongMessage((String) mapData.get(xpathCol)), mapData.get(msgCol).toString());
        }
    }

    @Test (retryAnalyzer = RerunFailTestCase.class)
    public void validateRequiredFields() throws Exception {
        String requiredFieldTC = "Validate * fields";
        List<String> listTestCaseID = ReadDataFromExcel.getTestCaseIDByTypeOfTestCase(requiredFieldTC);
        for (int i = 0; i < listTestCaseID.size(); i++){
            objAddProductPage.selectRandomCategory();
            hashMap(listTestCaseID.get(i));
            Assert.assertEquals(objAddProductPage.getWrongMessage((String) mapData.get(xpathCol)), mapData.get(msgCol).toString());
        }
    }

    @Test (retryAnalyzer = RerunFailTestCase.class)
    public void checkExistedProductName() throws Exception {
        String checkExist = "Check Existed Product Name";
        List<String> listTestCaseID = ReadDataFromExcel.getTestCaseIDByTypeOfTestCase(checkExist);
        String txtProductName = objAddProductPage.randomString(4);
        for (int i = 0; i < listTestCaseID.size(); i++){
            objAddProductPage.inputProductNameForExistedTestCase(txtProductName);
            objAddProductPage.selectRandomCategory();
            hashMapForExistedTestCase(listTestCaseID.get(i));
            Assert.assertEquals(objAddProductPage.getWrongMessage((String) mapData.get(xpathCol)), mapData.get(msgCol).toString());
            if (mapData.get(msgCol).toString().equalsIgnoreCase("Tên sản phẩm đã tồn tại")){
                System.out.println("Passed Test Case");
            }
            else {System.out.println("Failed Test Case");}
            driver.navigate().refresh();
        }
    }

    @Test (retryAnalyzer = RerunFailTestCase.class)
    public void validateOtherSuccessfulTC() throws Exception {
        String successfulTCs = "Successful Test Cases";
        List<String> listTestCaseID = ReadDataFromExcel.getTestCaseIDByTypeOfTestCase(successfulTCs);
        for (int i = 0; i < listTestCaseID.size(); i++){
            objAddProductPage.selectRandomCategory();
            hashMap(listTestCaseID.get(i));
            Assert.assertEquals(objAddProductPage.getWrongMessage((String) mapData.get(xpathCol)), mapData.get(msgCol).toString());
            driver.navigate().refresh();
        }
    }

    @Test (retryAnalyzer = RerunFailTestCase.class)
    public void validateProductName() throws Exception {
        String productNameTC = "Validate Product Name";
        List<String> listTestCaseID = ReadDataFromExcel.getTestCaseIDByTypeOfTestCase(productNameTC);
        for (int i = 0; i < listTestCaseID.size(); i++){
            objAddProductPage.selectRandomCategory();
            hashMap(listTestCaseID.get(i));
            Assert.assertEquals(objAddProductPage.getWrongMessage((String) mapData.get(xpathCol)), mapData.get(msgCol).toString());
            driver.navigate().refresh();
        }
    }

    @Test (retryAnalyzer = RerunFailTestCase.class)
    public void validateIntroduce() throws Exception {
        String introduceTC = "Validate Introduce";
        List<String> listTestCaseID = ReadDataFromExcel.getTestCaseIDByTypeOfTestCase(introduceTC);
        for (int i = 0; i < listTestCaseID.size(); i++){
            objAddProductPage.selectRandomCategory();
            hashMap(listTestCaseID.get(i));
            Assert.assertEquals(objAddProductPage.getWrongMessage((String) mapData.get(xpathCol)), mapData.get(msgCol).toString());
            driver.navigate().refresh();
        }
    }

    @Test (retryAnalyzer = RerunFailTestCase.class)
    public void validatePrice() throws Exception {
        String priceTC = "Validate Price";
        List<String> listTestCaseID = ReadDataFromExcel.getTestCaseIDByTypeOfTestCase(priceTC);
        for (int i = 0; i < listTestCaseID.size(); i++){
            objAddProductPage.selectRandomCategory();
            hashMap(listTestCaseID.get(i));
            Assert.assertEquals(objAddProductPage.getWrongMessage((String) mapData.get(xpathCol)), mapData.get(msgCol).toString());
            driver.navigate().refresh();
        }
    }

    @Test (retryAnalyzer = RerunFailTestCase.class)
    public void captureScreenShotFailTestCase() throws Exception {
        String requiredFieldTC = "Capture Screen Shot";
        List<String> listTestCaseID = ReadDataFromExcel.getTestCaseIDByTypeOfTestCase(requiredFieldTC);
        for (int i = 0; i < listTestCaseID.size(); i++){
            objAddProductPage.selectRandomCategory();
            hashMap(listTestCaseID.get(i));
            Assert.assertEquals(objAddProductPage.getWrongMessage((String) mapData.get(xpathCol)), mapData.get(msgCol).toString());
        }
    }
}