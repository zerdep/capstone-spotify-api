package tests;

import info.UserInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import pages.LoginStatusPage;
import pages.WebPlayerHomePage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PlaylistUITest {
    WebDriver driver;

    @BeforeEach
    public void browserSetup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    /*
    * logs in to account and returns LoginStatusPage
    * */
    public LoginStatusPage login(){
        LoginPage loginPage = new LoginPage(driver);
        LoginStatusPage statusPage = loginPage.openPage()
                .loginAs(UserInfo.USERNAME,UserInfo.PASSWORD);
        assertEquals(UserInfo.ACCOUNT_NAME, statusPage.getAccountName());
        return statusPage;
    }

    /**
     * Verifies the functionality of creating a new playlist.
     */
    @Test
    public void testCreatePlaylist(){
        WebPlayerHomePage homePage = login()
                .openWebPlayer()
                .createNewPlaylist();
        assertEquals(homePage.getLastCreatedPlaylistName(), homePage.getPlaylistNameMainArea());
    }

    /**
     * Confirms that the details of an existing playlist can be edited.
     */
    @Test
    public void testEditPlaylistDetail(){

        WebPlayerHomePage homePage = login()
                .openWebPlayer()
                .createNewPlaylist();

        homePage.editPlaylistName("Edited name");
        assertEquals("Edited name", homePage.getLastCreatedPlaylistName());
        assertEquals("Edited name", homePage.getPlaylistNameMainArea());
    }

    /**
     * Ensures that a playlist can be deleted successfully.
     */
    @Test
    public void testDeletePlaylist(){
        WebPlayerHomePage homePage = login()
                .openWebPlayer()
                .createNewPlaylist();

        String lastPlaylistName = homePage.getLastCreatedPlaylistName();
        int playlistCount = homePage.getPlaylistCount();

        homePage.deleteLastPlaylist();

        assertEquals(playlistCount-1, homePage.getPlaylistCount());
        assertNotEquals(lastPlaylistName, homePage.getLastCreatedPlaylistName());
    }

    /**
     * Tests the ability to search for songs and add them to a playlist.
     */
    @Test
    public void testAddSongToPlaylist(){
        WebPlayerHomePage homePage = login()
                .openWebPlayer()
                .createNewPlaylist();

        String trackName = homePage.searchSong("Whitney Houston I Will Always Love You")
                .addTrackToNewestPlaylist("I Will Always Love You")
                .getFirstTrackNameInPlaylist();
        System.out.println(trackName);
        assertEquals("I Will Always Love You", trackName);
    }

    /**
     * Validates the functionality to remove a song from a playlist.
     */
    @Test
    public void testRemoveSongFromPlaylist(){
        WebPlayerHomePage homePage = login()
                .openWebPlayer()
                .createNewPlaylist();

        homePage.searchSong("Whitney Houston I Will Always Love You")
                .addTrackToNewestPlaylist("I Will Always Love You");

        String response = homePage.removeTrackFromPlaylist("I Will Always Love You");
        System.out.println(response);
        assertEquals("I Will Always Love You removed", response);
        int trackCount = homePage.getTrackCountInTopPlaylist();
        assertEquals(0, trackCount);
    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }
}
