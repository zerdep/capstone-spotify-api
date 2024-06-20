package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginStatusPage {
    private WebDriver driver;


    @FindBy(xpath = "//*[@id='root']/div/div/div/div/div/div/div[3]")
    private WebElement accountName;

    @FindBy(xpath = "//button[@data-testid='web-player-link']")
    private WebElement webPlayerButton;

    public LoginStatusPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-testid='user-info']")));
    }

    public String getAccountName(){
        return accountName.getText();
    }

    public WebPlayerHomePage openWebPlayer(){
        webPlayerButton.click();
        return new WebPlayerHomePage(driver);
    }
}
