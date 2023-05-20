package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;

public class LogoutPage {
    WebDriver driver;
    WebDriverWait wait;

    By txtUsername = By.xpath("(//input[@class='el-input__inner'])[1]");
    By txtPassword = By.xpath("(//input[@class='el-input__inner'])[2]");
    By btnLogin = By.xpath("//button[@class='el-button btn-update el-button--primary']");
    By lblLogoutTitle = By.xpath("//li[@class='logout']");
    By btnOk = By.xpath("(//button[@type='button'])[3]");
    By btnCancel = By.xpath("//button[@class='el-button el-button--default el-button--small']");
    By lblLogoutCommand = By.xpath("//p[contains(text(), 'Bạn muốn đăng xuất')]");
    By contentHomePage = By.xpath("//h2[contains(text(), 'Một số lệnh phổ biến')]");

    public LogoutPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isExistLabelLogout(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblLogoutCommand));
        return driver.findElement(lblLogoutCommand).isDisplayed();
    }

    public boolean isHomePage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(contentHomePage));
        return driver.findElement(contentHomePage).isDisplayed();
    }

    public void loginPage() throws InterruptedException {
        driver.findElement(txtUsername).sendKeys(utils.ReadProperties.getPropertyValue("CONFIG_USER_NAME"));
        driver.findElement(txtPassword).sendKeys(utils.ReadProperties.getPropertyValue("CONFIG_PASSWORD"));
        driver.findElement(btnLogin).click();
    }

    public void clickLogoutTitle(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblLogoutTitle));
        driver.findElement(lblLogoutTitle).click();
    }

    public void clickButtonOk() throws InterruptedException {
        driver.findElement(btnOk).click();
    }
}
