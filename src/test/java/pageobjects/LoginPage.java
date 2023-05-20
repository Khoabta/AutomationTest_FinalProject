package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    By txtUsername = By.xpath("(//input[@class='el-input__inner'])[1]");
    By txtPassword = By.xpath("(//input[@class='el-input__inner'])[2]");
    By btnLogin = By.xpath("//button[@class='el-button btn-update el-button--primary']");
    By btnReset = By.xpath("//button[@class='el-button btn-update el-button--danger']");
    By lblLogin = By.xpath("//h3[contains(text(), 'Login')]");
    By lblLogout = By.xpath("//li[contains(text(), 'Đăng xuất')]");
    By lblLogoutTitle = By.xpath("//li[@class='logout']");
    By btnOk = By.xpath("(//button[@type='button'])[3]");
    By contentHomePage = By.xpath("//h2[contains(text(), 'Một số lệnh phổ biến')]");

    public LoginPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isExistLabelLogin(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblLogin));
        return driver.findElement(lblLogin).isDisplayed();
    }

    private void inputUsername(String username){
        driver.findElement(txtUsername).sendKeys(username);
    }

    private void inputPassword(String password){
        driver.findElement(txtPassword).sendKeys(password);
    }

    private void clickButtonLogin(){
        driver.findElement(btnLogin).click();
    }

    /**
     *
     * @param strUsername : biến Username được truyền vào element Username trên trình duyệt thông qua hàm inputUsername
     * @param strPassword : biến Password được truyền vào element Password trên trình duyệt thông qua hàm inputPassword
     * @throws InterruptedException
     */
    public void inputDataLogin (String strUsername, String strPassword) throws InterruptedException {
        this.inputUsername(strUsername);
        this.inputPassword(strPassword);
        Thread.sleep(1000);
        this.clickButtonLogin();
    }

    public String getTitleLogout(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblLogout));
        return driver.findElement(lblLogout).getText();
    }

    /**
     *
     * @param strXpath : biến được truyền vào bằng cách đọc từ file excel
     * @return
     */
    public String getWrongMessage(String strXpath){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strXpath)));
        return driver.findElement(By.xpath(strXpath)).getText();
    }

    public void clickLogoutTitle(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblLogoutTitle));
        driver.findElement(lblLogoutTitle).click();
    }

    public void clickButtonOk() throws InterruptedException {
        driver.findElement(btnOk).click();
    }

    public boolean isHomePage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(contentHomePage));
        return driver.findElement(contentHomePage).isDisplayed();
    }
}
