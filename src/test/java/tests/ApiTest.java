package tests;

import info.ApiInfo;
import models.Playlist;
import org.junit.jupiter.api.Test;
import services.CreatePlaylistService;
import services.PlaylistContentService;
import services.UpdatePlaylistService;

import static org.junit.jupiter.api.Assertions.*;

public class ApiTest {

    public Playlist createPlaylist(String name, String description) {
        Playlist playlist = new Playlist(name,
                description,
                false);

        CreatePlaylistService createPlaylistService = new CreatePlaylistService();
        Playlist createdPlaylist = createPlaylistService.createPlaylist(playlist);

        assertEquals(201, createPlaylistService.getStatusCode());
        return createdPlaylist;
    }

    @Test
    public void testCreatePlaylist() {
        Playlist playlist = new Playlist("New Playlist",
                "New playlist description",
                false);
        Playlist createdPlaylist = createPlaylist("New Playlist",
                "New playlist description");

        assertEquals(playlist, createdPlaylist);
    }

    @Test
    public void testUpdatePlaylist() {

        //create playlist
        Playlist createdPlaylist = createPlaylist("To be Updated Playlist",
                "To be updated playlist description");

        //update playlist
        createdPlaylist.setName("Updated Playlist !");
        createdPlaylist.setDescription("Updated playlist description !");

        UpdatePlaylistService updatePlaylistService = new UpdatePlaylistService();
        int statusCode = updatePlaylistService.updatePlaylist(createdPlaylist);
        assertEquals(200, statusCode);

    }

    @Test
    public void testAddTrackToPlaylist() {
        //create playlist
        Playlist createdPlaylist = createPlaylist("Add track playlist",
                "Add track playlist description");

        //add track to playlist
        PlaylistContentService playlistContentService = new PlaylistContentService();
        String snapshotId = playlistContentService
                .addTrackToPlaylist(ApiInfo.TRACK_URI,
                        createdPlaylist.getId());
        assertEquals(200, playlistContentService.getStatusCode());
        assertFalse(snapshotId.isBlank());
    }

    @Test
    public void testDeleteTrackFromPlaylist() {
        //create playlist
        Playlist createdPlaylist = createPlaylist("Delete track playlist",
                "Delete track playlist description");

        //add track to playlist
        PlaylistContentService playlistContentService = new PlaylistContentService();
        String snapshotId = playlistContentService
                .addTrackToPlaylist(ApiInfo.TRACK_URI,
                        createdPlaylist.getId());
        assertEquals(200, playlistContentService.getStatusCode());

        //delete track from playlist
        String resSnapshotId = playlistContentService
                .deleteTrackFromPlaylist(ApiInfo.TRACK_URI,
                        snapshotId,
                        createdPlaylist.getId()
                );
        assertEquals(200, playlistContentService.getStatusCode());
        assertFalse(resSnapshotId.isBlank());
    }


}
