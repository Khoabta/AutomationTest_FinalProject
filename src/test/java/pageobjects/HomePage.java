package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    By linkProject = By.linkText("Project");
    By lblCommonCommands = By.className("title");

    public void clickLinkProject(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(linkProject));
        driver.findElement(linkProject).click();
    }

    public String getTitle(){
        return driver.findElement(lblCommonCommands).getText();
    }

    public HomePage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }
}
