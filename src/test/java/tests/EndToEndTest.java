package tests;

import info.UserInfo;
import models.Playlist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import pages.LoginStatusPage;
import pages.WebPlayerHomePage;
import services.UpdatePlaylistService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EndToEndTest {

    WebDriver driver;

    @BeforeEach
    public void browserSetup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    /**
     * Creates and edits playlist details with API operations, then verifies using UI operations.
     */
    @Test
    public void testEditDetailPlaylist() {

        //create playlist
        Playlist createdPlaylist = new ApiTest()
                .createPlaylist("New Playlist E2E",
                        "New Playlist Description E2E");


        //update playlist
        createdPlaylist.setName("Updated Playlist!");
        createdPlaylist.setDescription("Updated playlist description!");

        UpdatePlaylistService updatePlaylistService = new UpdatePlaylistService();
        int statusCode = updatePlaylistService.updatePlaylist(createdPlaylist);
        assertEquals(200, statusCode);

        //wait for update to show in profile
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //login and verify updated name and description
        LoginStatusPage statusPage = new LoginPage(driver)
                .openPage()
                .loginAs(UserInfo.USERNAME,UserInfo.PASSWORD);
        assertEquals(UserInfo.ACCOUNT_NAME, statusPage.getAccountName());
        WebPlayerHomePage homePage = statusPage.openWebPlayer();

        String name = homePage.getLastCreatedPlaylistName();
        String description = homePage.getTopPlaylistDescription();

        assertEquals("Updated Playlist!", name);
        assertEquals("Updated playlist description!", description);
    }

    /**
     * Creates playlist using API then adds track to playlist using UI operations.
     */
    @Test
    public void testAddSongToPlaylist(){

        //create playlist
        new ApiTest()
                .createPlaylist("New Playlist E2E",
                        "New Playlist Description E2E");

        //add track to playlist
        LoginStatusPage statusPage = new LoginPage(driver)
                .openPage()
                .loginAs(UserInfo.USERNAME,UserInfo.PASSWORD);
        assertEquals(UserInfo.ACCOUNT_NAME, statusPage.getAccountName());
        WebPlayerHomePage homePage = statusPage.openWebPlayer();
        String trackName = homePage.searchSong("Whitney Houston I Will Always Love You")
                .addTrackToNewestPlaylist("I Will Always Love You")
                .getFirstTrackNameInPlaylist();
        System.out.println(trackName);
        assertEquals("I Will Always Love You", trackName);
    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }
}
