package tests;

import info.UserInfo;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import pages.LoginStatusPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginUITest {
    WebDriver driver;

    @Before
    public void browserSetup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println("hello");
    }
    @Test
    public void testEmptyCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openPage()
                .fillUsername("aa")
                .fillPassword("123")
                .clearFields();

        assertEquals("Please enter your Spotify username or email address.",
                loginPage.getUsernameErrorText());
        assertEquals("Please enter your password.",
                loginPage.getPasswordErrorText());
    }

    @Test
    public void testIncorrectCredentials(){
        LoginPage loginPage = new LoginPage(driver);
        String message = loginPage.openPage()
                .fillUsername("test")
                .fillPassword("test")
                .loginExpectingFailure();
        assertEquals("Incorrect username or password.", message);
    }

    @Test
    public void testCorrectCredentials(){
        LoginPage loginPage = new LoginPage(driver);
        LoginStatusPage statusPage = loginPage.openPage()
                .loginAs(UserInfo.USERNAME,UserInfo.PASSWORD);
        String accountName = statusPage.openWebPlayer().getAccountName();
        assertEquals(UserInfo.ACCOUNT_NAME, accountName);
    }
}
