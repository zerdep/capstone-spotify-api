package models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Playlist {
    private String id;
    private String name;
    private String description;
    @SerializedName("public")
    private boolean isPublic;

    public Playlist(String name, String description, boolean isPublic) {
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Playlist playlist)) return false;
        return isPublic == playlist.isPublic && Objects.equals(name, playlist.name) && Objects.equals(description, playlist.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, isPublic);
    }
}
