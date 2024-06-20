package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private static final String LOGIN_URL = "https://accounts.spotify.com/en/login";
    private WebDriver driver;

    @FindBy(id = "login-username")
    private WebElement usernameInput;

    @FindBy(id = "login-password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    By usernameErrorText = By.xpath("//div[@id='username-error']/span/p");
    By passwordErrorText = By.xpath("//div[@id='password-error']/span");
    By incorrectUsernamePasswordMessage = By.xpath("//span[@class='Message-sc-15vkh7g-0 dHbxKh']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public LoginPage openPage(){
        driver.get(LOGIN_URL);
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("login-username")));
        return this;
    }

    public LoginPage fillUsername(String username){
        usernameInput.sendKeys(username);
        return this;
    }

    public LoginPage fillPassword(String password){
        passwordInput.sendKeys(password);
        return this;
    }

    public LoginPage clearFields() {

        String username = usernameInput.getAttribute("value");
        String password = passwordInput.getAttribute("value");
        for (int i = 0; i < username.length(); i++) {
            usernameInput.sendKeys(Keys.BACK_SPACE);
        }
        for (int i = 0; i < password.length(); i++) {
            passwordInput.sendKeys(Keys.BACK_SPACE);
        }
        return this;
    }

    public LoginStatusPage loginAs(String username, String password){
        fillUsername(username);
        fillPassword(password);
        loginButton.click();
        return new LoginStatusPage(driver);
    }

    public String loginExpectingFailure(){
        loginButton.click();
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.presenceOfElementLocated(incorrectUsernamePasswordMessage));
        String errorMessage = driver.findElement(incorrectUsernamePasswordMessage).getText();
        return errorMessage;
    }

    public String getUsernameErrorText(){
        return driver.findElement(usernameErrorText).getText();
    }

    public String getPasswordErrorText(){
        return driver.findElement(passwordErrorText).getText();
    }
}
