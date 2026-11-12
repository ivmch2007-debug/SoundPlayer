package dao;

import model.Playlist;
import java.util.ArrayList;

public interface PlaylistsDAO {

    ArrayList<Playlist> getAllPlaylists();

    Playlist getPlaylistById(String id);

    void addPlaylist(Playlist playlist);

    void deletePlaylist(String id);
}
