package services;


import info.ApiInfo;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class PlaylistContentService {
    private String endpoint = "https://api.spotify.com/v1/playlists/{playlist_id}/tracks";
    private int statusCode;

    public String addTrackToPlaylist(String uris, String playlistId){
        statusCode = 0;
        Response response = given()
                .auth().oauth2(ApiInfo.TOKEN)
                .queryParam("uris", uris)
                .pathParam("playlist_id", playlistId)
        .when()
                .post(endpoint);

        statusCode = response.getStatusCode();
        String snapshotId = response.jsonPath().getString("snapshot_id");
        System.out.println("Track added, snapshot_id:"+ snapshotId);
        return snapshotId;
    }

    public String deleteTrackFromPlaylist(String uri, String snapshotId, String playlistId){
        String body = String.format( """
                {
                    "tracks": [
                        {
                            "uri": "%s"
                        }
                    ],
                    "snapshot_id": "%s"
                }
                """,uri,snapshotId);
        statusCode = 0;
        Response response = given()
                .auth().oauth2(ApiInfo.TOKEN)
                .contentType("application/json")
                .body(body)
                .pathParam("playlist_id", playlistId)
                .when()
                .delete(endpoint);

        statusCode = response.getStatusCode();
        String resSnapshotId = response.jsonPath().getString("snapshot_id");
        System.out.println("Track deleted, snapshot_id:" + resSnapshotId);
        return resSnapshotId;
    }

    public int getStatusCode(){
        return statusCode;
    }
}

