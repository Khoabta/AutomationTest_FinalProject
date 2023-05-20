package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

public class AddProductPage {
    WebDriver driver;
    WebDriverWait wait;

    By txtUsername = By.xpath("(//input[@class='el-input__inner'])[1]");
    By txtPassword = By.xpath("(//input[@class='el-input__inner'])[2]");
    By btnLogin = By.xpath("//button[@class='el-button btn-update el-button--primary']");

    By addProductLink = By.xpath("//a[contains(text(), 'Thêm sản phẩm')]");
    By addProductTitle = By.xpath("//h3[contains(text(), 'Thêm sản phẩm')]");
    By txtProductName = By.xpath("(//input[@class='el-input__inner'])[1]");
    By dropListCategory = By.xpath("(//input[@class='el-input__inner'])[2]");
    By txtPrice = By.xpath("(//input[@class='el-input__inner'])[3]");
    By txtDiscount = By.xpath("(//input[@class='el-input__inner'])[4]");
    By txtIntroduce = By.xpath("(//textarea[@class='el-textarea__inner'])[1]");
    By txtDetails = By.xpath("(//textarea[@class='el-textarea__inner'])[2]");
    By checkBoxFeaturedProduct = By.xpath("//input[@class='el-checkbox__original']");
    By choseFile = By.xpath("//input[@type='file']");
    By addProductButton = By.xpath("(//button[@type='button'])[1]");
    By resetButton = By.xpath("(//button[@type='button'])[2]");

    public AddProductPage (WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    public void clickAddProductLink(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(addProductLink));
        driver.findElement(addProductLink).click();
    }

    public boolean isAddProductPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(addProductTitle));
        return driver.findElement(addProductTitle).isDisplayed();
    }

    public void loginPage() throws InterruptedException {
        driver.findElement(txtUsername).sendKeys(utils.ReadProperties.getPropertyValue("CONFIG_USER_NAME"));
        driver.findElement(txtPassword).sendKeys(utils.ReadProperties.getPropertyValue("CONFIG_PASSWORD"));
        driver.findElement(btnLogin).click();
    }

    /**
     *
     * @param n : là giá trị truyền vào để xác định độ dài của chuỗi khi random
     * StringBuilder: tạo 1 Builder để chưa chuỗi được random
     * append(): dùng để nối chuỗi và đưa vào StringBuilder
     * @return
     */
    public String randomString(int n){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "1234567890"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++){
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public String randomPrice(int n){
        String AlphaNumericString = "1234567890";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++){
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    private void inputProductName(String productName){
        driver.findElement(txtProductName).sendKeys(productName);
    }

    private void inputRandomProductName(int n){
        String productName = randomString(n);
        driver.findElement(txtProductName).sendKeys(productName);
    }

    public void selectRandomCategory(){
        driver.findElement(dropListCategory).click();
        Random random = new Random();
        Actions actions = new Actions(driver);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        List<WebElement> categories = driver.findElements(By.xpath("//ul[@class='el-scrollbar__view el-select-dropdown__list']" +
                "/li[@class='el-select-dropdown__item selected hover' " +
                "or 'el-select-dropdown__item hover' " +
                "or 'el-select-dropdown__item selected']"));
        int index = random.nextInt(categories.size());
        actions.moveToElement(categories.get(index));
        executor.executeScript("arguments[0].click();", categories.get(index));
    }

    private void inputIntroduce(String introduce){
        driver.findElement(txtIntroduce).sendKeys(introduce);
    }

    private void inputRandomIntroduce(int m){
        String introduce = randomString(m);
        driver.findElement(txtIntroduce).sendKeys(introduce);
    }

    private void inputIntroduceForExistedTestCase(String introduce){
        driver.findElement(txtIntroduce).sendKeys(introduce);
    }

    private void inputDetails(String details){
        driver.findElement(txtDetails).sendKeys(details);
    }

    public void checkFeaturedProduct(){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        WebElement checkButton = driver.findElement(checkBoxFeaturedProduct);
        executor.executeScript("arguments[0].click();", checkButton);
    }

    private void inputPrice(String price){
        driver.findElement(txtPrice).sendKeys(price);
    }

    private void inputRandomPrice(int p){
        String price = randomPrice(p);
        driver.findElement(txtPrice).sendKeys(price);
    }

    private void inputPriceForExistTestCase(String price){
        driver.findElement(txtPrice).sendKeys(price);
    }

    private void inputDiscount(String discount){
        driver.findElement(txtDiscount).sendKeys(discount);
    }

    public void uploadFile(){
        String pathProject = System.getProperty("user.dir");
        driver.findElement(choseFile).sendKeys(pathProject + "/img/BMW.jpg");
    }

    private void clickAddProductButton(){
        driver.findElement(addProductButton).click();
    }

    /**
     *
     * @param productNameLength : độ dài của chuỗi Product Name khi được random
     * @param introduceLength : độ dài của chuỗi Introduce khi được random
     * @param priceLength : độ dài của chuỗi Price khi được random
     */
    public void inputRandomData(int productNameLength, int introduceLength, int priceLength) throws InterruptedException {
        this.inputRandomProductName(productNameLength);
        this.inputRandomIntroduce(introduceLength);
        this.inputRandomPrice(priceLength);
    }

    public void inputDataFromExcel(String strProductName, String strIntroduce, String strDetails, String strPrice, String strDiscount) throws InterruptedException {
        this.inputProductName(strProductName);
        this.inputIntroduce(strIntroduce);
        this.inputDetails(strDetails);
        this.inputPrice(strPrice);
        this.inputDiscount(strDiscount);
        this.clickAddProductButton();
    }

    public void inputDataFromExcelForAddProduct(String strDetails, String strDiscount) throws InterruptedException {
        this.inputDetails(strDetails);
        this.inputDiscount(strDiscount);
        this.clickAddProductButton();
    }

    public void inputProductNameForExistedTestCase(String strProductName){
        driver.findElement(txtProductName).sendKeys(strProductName);
    }

    public void inputDataFromExcelForExistedTestCase(String strIntroduce, String strPrice) throws InterruptedException {
        this.inputIntroduceForExistedTestCase(strIntroduce);
        this.inputPriceForExistTestCase(strPrice);
        this.clickAddProductButton();
    }

    public String getWrongMessage(String strXpath){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strXpath)));
        return driver.findElement(By.xpath(strXpath)).getText();
    }
}
