package dao;

import model.Song;
import java.util.ArrayList;

public interface SongsDAO {

    ArrayList<Song> getAllSongs();

    Song getSongById(String id);

    void addSong(Song song);

    void deleteSong(String id);
}
