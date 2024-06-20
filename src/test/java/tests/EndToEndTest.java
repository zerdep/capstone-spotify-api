package tests;

import info.UserInfo;
import models.Playlist;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import pages.LoginStatusPage;
import pages.WebPlayerHomePage;
import services.CreatePlaylistService;
import services.UpdatePlaylistService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EndToEndTest {

    WebDriver driver;

    @Before
    public void browserSetup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testEditDetailPlaylist() {

        //create playlist
        Playlist playlist = new Playlist("To be Updated Playlist",
                "To be updated playlist description",
                false);
        CreatePlaylistService createPlaylistService = new CreatePlaylistService();
        Playlist createdPlaylist = createPlaylistService.createPlaylist(playlist);
        assertEquals(201, createPlaylistService.getStatusCode());


        //update playlist
        createdPlaylist.setName("Updated Playlist!");
        createdPlaylist.setDescription("Updated playlist description!");

        UpdatePlaylistService updatePlaylistService = new UpdatePlaylistService();
        int statusCode = updatePlaylistService.updatePlaylist(createdPlaylist);
        assertEquals(200, statusCode);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //UI part
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

    @Test
    public void testAddSongToPlaylist(){
        new ApiTest().createPlaylist("New Playlist E2E", "New Playlist Description E2E");

        LoginStatusPage statusPage = new LoginPage(driver)
                .openPage()
                .loginAs(UserInfo.USERNAME,UserInfo.PASSWORD);
        assertEquals(UserInfo.ACCOUNT_NAME, statusPage.getAccountName());
        WebPlayerHomePage homePage = statusPage.openWebPlayer();
        String trackName = homePage.searchSong("Whitney Elizabeth Houston")
                .addTrackToNewestPlaylist("I Will Always Love You")
                .getFirstTrackNameInPlaylist();
        System.out.println(trackName);
        assertEquals("I Will Always Love You", trackName);
    }
}
