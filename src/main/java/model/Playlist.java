
package model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private String id;
    private String name;
    private List<String> songIds;

    public Playlist() {
        this.songIds = new ArrayList<>();
    }

    public Playlist(String id, String name, List<String> songIds) {
        this.id = id;
        this.name = name;

        if (songIds != null) {
            this.songIds = new ArrayList<>(songIds);
        } else {
            this.songIds = new ArrayList<>();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<String> songIds) {
        if (songIds != null) {
            this.songIds = new ArrayList<>(songIds);
        } else {
            this.songIds = new ArrayList<>();
        }
    }


    public boolean addSong(String songId) {
        if (songId == null || songIds.contains(songId)) {
            return false;
        }

        songIds.add(songId);
        return true;
    }

    public boolean removeSong(String songId) {
        return songIds.remove(songId);
    }
}
