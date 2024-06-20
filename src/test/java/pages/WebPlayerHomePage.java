package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.List;

public class WebPlayerHomePage {
    private WebDriver driver;
    private Wait<WebDriver> wait;

    @FindBy(xpath = "//button[@data-testid='user-widget-link']")
    private WebElement userButton;

    @FindBy(xpath = "//*[@id='Desktop_LeftSidebar_Id']/nav/div[2]/div[1]/div[1]/header/div/span/button")
    private WebElement addPlaylistButton;

    @FindBy(xpath = "//*[@id='Desktop_LeftSidebar_Id']/nav/div[1]/ul/li[2]/a")
    private WebElement searchButton;

    By profileOption = By.xpath("//*[@id='context-menu']/div/ul/li[2]/a");

    By mainAreaTitleText = By.xpath("//button[@class='wCkmVGEQh3je1hrbsFBY']/span/h1");
    By playlistDescriptionText = By.xpath("//div[@class='RP2rRchy4i8TIp1CTmb7']/span[3]/div");
    By firstTrackNameInPlaylist = By.xpath("//div[@class='main-view-container']//div[@data-testid='tracklist-row']/div[2]/div/a/div");
    By trackInPlaylistRow = By.xpath("//div[@class='main-view-container']//div[@data-testid='playlist-tracklist']//div[@data-testid='tracklist-row']");

    By removeFromPlaylistOption = By.xpath("//*[@id='context-menu']/ul/li[2]/button");
    By createPlaylistOption = By.xpath("//*[@id='context-menu']/ul/li[1]/button");

    By topPlaylistNameOnList = By.xpath("(//p[@data-encore-id='listRowTitle'])[1]/span");
    By playlistRows = By.xpath("//*[@id='Desktop_LeftSidebar_Id']//div[@data-encore-id='listRow']");
    By topPlaylist = By.xpath("(//*[@id='Desktop_LeftSidebar_Id']//div[@data-encore-id='listRow'])[1]/div");

    By moreOptionsPlaylistButton = By.xpath("//div[@class='main-view-container']//button[@data-encore-id='buttonTertiary']");

    By editPlaylistDetailOption = By.xpath("//*[@id='context-menu']/ul/li[3]/button/span");
    By editPlaylistNameInput = By.xpath("//input[@data-testid='playlist-edit-details-name-input']");
    By editPlaylistSaveButton = By.xpath("//button[@data-testid='playlist-edit-details-save-button']");

    By deletePlaylistOption = By.xpath("//*[@id='context-menu']/ul/li[4]/button");
    By deletePlaylistButton = By.xpath("/html/body/div[21]/div/div/div/div/button[2]");

    By searchInput = By.xpath("//input[@data-testid='search-input']");
    By songsButton = By.xpath("//div[@class='KjPUGV8uMbl_0bvk9ePv']/div/a[contains(@href, 'tracks')]");

    By addToPlaylistOption = By.xpath("//*[@id='context-menu']/ul/li[1]/button");
    By firstPlaylistOption = By.xpath("//div[contains(@style, 'context-menu-submenu')]/li[3]/button");


    public WebPlayerHomePage(WebDriver driver) {
        this.driver = driver;
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[@data-testid='user-widget-link']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("WxM1eb7qnneSkMiT4dvw")));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        PageFactory.initElements(driver, this);
    }

    public String getAccountName(){
        userButton.click();
        //click profile from dropdown menu
        wait.until(ExpectedConditions.elementToBeClickable(profileOption)).click();
        //get displayed username
        String username = wait.until(ExpectedConditions
                .presenceOfElementLocated(mainAreaTitleText)).getText();
        return username;
    }

    public WebPlayerHomePage createNewPlaylist(){
        wait.until(ExpectedConditions.visibilityOf(addPlaylistButton)).click();
        int count = getPlaylistCount();
        System.out.println("count" +count);
        wait.until(ExpectedConditions.elementToBeClickable(createPlaylistOption)).click();
        //wait until new playlist added to the list
        wait.until(ExpectedConditions.numberOfElementsToBe(playlistRows, count+1));
        getPlaylistNameMainArea();
        return this;
    }

    public void editPlaylistName(String editedName) {
        wait.until(ExpectedConditions.elementToBeClickable(moreOptionsPlaylistButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(editPlaylistDetailOption)).click();
        WebElement nameInput = wait.until(ExpectedConditions
                .presenceOfElementLocated(editPlaylistNameInput));
        nameInput.clear();
        nameInput.sendKeys(editedName);
        driver.findElement(editPlaylistSaveButton).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteLastPlaylist(){
        wait.until(ExpectedConditions.elementToBeClickable(moreOptionsPlaylistButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(deletePlaylistOption)).click();
        wait.until(ExpectedConditions.elementToBeClickable(deletePlaylistButton)).click();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTopPlaylistDescription(){
        clickTopPlaylist();
        String description = wait.until(ExpectedConditions.presenceOfElementLocated(playlistDescriptionText)).getText();
        return description;
    }

    public WebPlayerHomePage searchSong(String name){
        searchButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(searchInput))
                .sendKeys(name);
        wait.until(ExpectedConditions.elementToBeClickable(songsButton)).click();
        return this;
    }

    public WebPlayerHomePage addTrackToNewestPlaylist(String trackName){
        String xpath = String.format("((//*[@id='searchPage']//div[contains(text(), '%s')])/ancestor::div[@data-testid='tracklist-row'])[1]"
                ,trackName);
        WebElement track = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath(xpath)));
        Actions actions = new Actions(driver);
        actions.contextClick(track).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(addToPlaylistOption)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(firstPlaylistOption)).click();
        return this;
    }

    public String getFirstTrackNameInPlaylist(){
        clickTopPlaylist();
        String trackName;
        try {
           trackName = wait.until(ExpectedConditions
                    .presenceOfElementLocated(firstTrackNameInPlaylist)).getText();
        }catch (TimeoutException e){
            trackName = "no track in playlist";
        }
        return trackName;
    }

    public String removeTrackFromPlaylist(String trackName){
        clickTopPlaylist();
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String xpath = String.format("((//div[@class='main-view-container']//div[contains(text(), '%s')])/ancestor::div[@data-testid='tracklist-row'])[1]",
                trackName);
        WebElement trackRow;
        try {
            trackRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            Actions actions = new Actions(driver);
            actions.contextClick(trackRow).perform();
        }catch (TimeoutException e){
            return "no such track in playlist";
        }

        wait.until(ExpectedConditions.elementToBeClickable(removeFromPlaylistOption)).click();
        return trackName + " removed";
    }

    public int getTrackCountInTopPlaylist(){
        clickTopPlaylist();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<WebElement> tracks = driver.findElements(trackInPlaylistRow);
        System.out.println(tracks.size() + " tracks");
        return  tracks.size();
    }


    public String getPlaylistNameMainArea() {
        return wait.until(ExpectedConditions
                .presenceOfElementLocated(mainAreaTitleText)).getText();
    }

    public String getLastCreatedPlaylistName() {
        String playlistName;
        try {
            playlistName  = wait.until(ExpectedConditions
                    .presenceOfElementLocated(topPlaylistNameOnList)).getText();
        }catch (TimeoutException e){
            playlistName = "no playlist";
        }
        System.out.println("Top playlist name: " + playlistName);
        return playlistName;
    }

    public int getPlaylistCount(){
        //wait.until(ExpectedConditions.presenceOfElementLocated());
        List<WebElement> playlists = driver.findElements(playlistRows);
        return playlists.size();
    }

    private void clickTopPlaylist(){
        wait.until(ExpectedConditions.presenceOfElementLocated(topPlaylist)).click();
        getPlaylistNameMainArea();
    }


}
