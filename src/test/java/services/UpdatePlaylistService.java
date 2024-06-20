package services;

import info.ApiInfo;
import io.restassured.response.Response;
import models.Playlist;

import static io.restassured.RestAssured.given;

public class UpdatePlaylistService {
    private String endpoint = "https://api.spotify.com/v1/playlists/{playlist_id}";

    public int updatePlaylist(Playlist playlist){
        System.out.println("Updated: "+playlist.getName() + "|" + playlist.getDescription());

        Response response = given()
                .auth().oauth2(ApiInfo.TOKEN)
                .contentType("application/json")
                .body(playlist)
                .pathParam("playlist_id", playlist.getId())
                .when()
                .put(endpoint);

        return response.getStatusCode();
    }
}
