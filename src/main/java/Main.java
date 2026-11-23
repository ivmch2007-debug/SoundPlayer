import dao.JsonSongsDAO;
import dao.JsonPlaylistsDAO;
import model.Song;
import model.Playlist;
import model.Mood;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        JsonSongsDAO songsDAO = new JsonSongsDAO();
        JsonPlaylistsDAO playlistsDAO = new JsonPlaylistsDAO();

        // Crear una canción de prueba
        Song testSong = new Song(
                "TEST001",
                "Temporary Song",
                "Test Artist",
                120,
                Mood.HAPPY,
                "Pop",
                false,
                new ArrayList<>()
        );

        // Añadir la canción
        songsDAO.addSong(testSong);

        System.out.println("Song added: "
                + (songsDAO.getSongById("TEST001") != null));

        // Crear una playlist de prueba
        ArrayList<String> songIds = new ArrayList<>();
        songIds.add("TEST001");

        Playlist testPlaylist = new Playlist(
                "TESTP001",
                "Temporary Playlist",
                "Playlist for testing",
                Mood.HAPPY,
                songIds
        );

        // Añadir la playlist
        playlistsDAO.addPlaylist(testPlaylist);

        System.out.println("Playlist added: "
                + (playlistsDAO.getPlaylistById("TESTP001") != null));

        // Eliminar los datos de prueba
        playlistsDAO.deletePlaylist("TESTP001");
        songsDAO.deleteSong("TEST001");

        System.out.println("Song deleted: "
                + (songsDAO.getSongById("TEST001") == null));

        System.out.println("Playlist deleted: "
                + (playlistsDAO.getPlaylistById("TESTP001") == null));

        System.out.println("\nFinal songs: "
                + songsDAO.getAllSongs().size());

        System.out.println("Final playlists: "
                + playlistsDAO.getAllPlaylists().size());
    }
}
