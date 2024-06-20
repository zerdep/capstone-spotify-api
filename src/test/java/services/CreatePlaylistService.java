package services;

import info.ApiInfo;
import io.restassured.response.Response;
import models.Playlist;


import static io.restassured.RestAssured.given;

public class CreatePlaylistService {
    private String endpoint = "https://api.spotify.com/v1/users/{user_id}/playlists";
    private int statusCode;

    public Playlist createPlaylist(Playlist playlist){

        Response response = given()
                .auth().oauth2(ApiInfo.TOKEN)
                .contentType("application/json")
                .body(playlist)
                .pathParam("user_id", ApiInfo.USER_ID)
                .when()
                .post(endpoint);

        statusCode = response.getStatusCode();
        Playlist createdPlaylist = response.as(Playlist.class);

        System.out.println("Playlist created: "+createdPlaylist.getName() + "|" + createdPlaylist.getDescription());

        return createdPlaylist;
    }

    public int getStatusCode(){
        return statusCode;
    }
}
