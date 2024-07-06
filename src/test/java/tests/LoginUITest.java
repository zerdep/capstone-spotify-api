package tests;

import info.UserInfo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import pages.LoginStatusPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginUITest {
    WebDriver driver;

    @BeforeEach
    public void browserSetup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    /**
     * Validates that the login form displays appropriate error messages
     * when submitted with empty credentials.
     */
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

    /**
     * Checks the error message when incorrect login credentials are provided.
     */
    @Test
    public void testIncorrectCredentials(){
        LoginPage loginPage = new LoginPage(driver);
        String message = loginPage.openPage()
                .fillUsername("test")
                .fillPassword("test")
                .loginExpectingFailure();

        assertEquals("Incorrect username or password.", message);
    }

    /**
     * Ensures that the user can successfully log in with valid credentials.
     */
    @Test
    public void testCorrectCredentials(){
        LoginPage loginPage = new LoginPage(driver);
        LoginStatusPage statusPage = loginPage.openPage()
                .loginAs(UserInfo.USERNAME,UserInfo.PASSWORD);

        String accountName = statusPage.openWebPlayer().getAccountName();

        assertEquals(UserInfo.ACCOUNT_NAME, accountName);
    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }
}
