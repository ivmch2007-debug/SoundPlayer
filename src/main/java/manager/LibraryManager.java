package manager;

import dao.PlaylistsDAO;
import dao.SongsDAO;
import model.Playlist;
import model.Song;

import java.util.ArrayList;
import java.util.UUID;

public class LibraryManager {

    private final SongsDAO songsDAO;
    private final PlaylistsDAO playlistsDAO;

    public LibraryManager(SongsDAO songsDAO, PlaylistsDAO playlistsDAO) {
        if (songsDAO == null || playlistsDAO == null) {
            throw new IllegalArgumentException("Els DAO no poden ser null");
        }

        this.songsDAO = songsDAO;
        this.playlistsDAO = playlistsDAO;
    }

    public ArrayList<Song> getAllSongs() {
        return songsDAO.getAllSongs();
    }

    public Song getSongById(String id) {
        return songsDAO.getSongById(id);
    }

    public void addSong(Song song) {
        if (song == null || song.getId() == null || song.getId().isBlank()) {
            throw new IllegalArgumentException("La cançó necessita un ID valid");
        }

        songsDAO.addSong(song);
    }

    public void deleteSong(String id) {
        if (getSongById(id) == null) {
            throw new IllegalArgumentException("La cançó no existeix");
        }

        for (Playlist playlist : getAllPlaylists()) {
            if (playlist.getSongIds() != null &&
                    playlist.getSongIds().contains(id)) {
                throw new IllegalStateException("La cançó forma part de la playlist: " + playlist.getName());
            }
        }

        songsDAO.deleteSong(id);
    }

    public ArrayList<Playlist> getAllPlaylists() {
        return playlistsDAO.getAllPlaylists();
    }

    public Playlist getPlaylistById(String id) {
        return playlistsDAO.getPlaylistById(id);
    }

    public Playlist createPlaylist(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nom no pot estar buit");
        }

        Playlist playlist = new Playlist(
                UUID.randomUUID().toString(), name, "", null, new ArrayList<>());

        playlistsDAO.addPlaylist(playlist);
        return playlist;
    }

    public void addPlaylist(Playlist playlist) {
        if (playlist == null || playlist.getId() == null ||
                playlist.getId().isBlank()) {
            throw new IllegalArgumentException("La playlist necessita un ID valid");
        }

        if (playlist.getSongIds() != null) {
            for (String songId : playlist.getSongIds()) {
                if (getSongById(songId) == null) {
                    throw new IllegalArgumentException("Canco inexistent: " + songId);
                }
            }
        }

        playlistsDAO.addPlaylist(playlist);
    }

    public void deletePlaylist(String id) {
        if (getPlaylistById(id) == null) {
            throw new IllegalArgumentException("La playlist no existeix");
        }

        playlistsDAO.deletePlaylist(id);
    }

    public void addSongToPlaylist(String playlistId, String songId) {
        if (getSongById(songId) == null) {
            throw new IllegalArgumentException("La canco no existeix");
        }

        Playlist playlist = requirePlaylist(playlistId);

        if (!playlist.addSong(songId)) {
            throw new IllegalArgumentException("La canco ja es a la playlist");
        }

        replacePlaylist(playlist);
    }

    public void removeSongFromPlaylist(String playlistId, String songId) {
        Playlist playlist = requirePlaylist(playlistId);

        if (!playlist.removeSong(songId)) {
            throw new IllegalArgumentException("La canco no es a la playlist");
        }

        replacePlaylist(playlist);
    }

    public int getPlaylistDurationSeconds(String playlistId) {
        Playlist playlist = requirePlaylist(playlistId);
        int duration = 0;

        for (String songId : playlist.getSongIds()) {
            Song song = getSongById(songId);

            if (song != null) {
                duration += song.getDurationSeconds();
            }
        }

        return duration;
    }

    public int getPlayableSongCount(String playlistId) {
        Playlist playlist = requirePlaylist(playlistId);
        int count = 0;

        for (String songId : playlist.getSongIds()) {
            Song song = getSongById(songId);

            if (isReallyPlayable(song)) {
                count++;
            }
        }

        return count;
    }

    public int getNonPlayableSongCount(String playlistId) {
        Playlist playlist = requirePlaylist(playlistId);

        return playlist.getSongIds().size() -
                getPlayableSongCount(playlistId);
    }

    public static boolean isReallyPlayable(Song song) {
        return song != null && song.isPlayable() &&
                song.getNotes() != null && !song.getNotes().isEmpty();
    }

    private Playlist requirePlaylist(String id) {
        Playlist playlist = getPlaylistById(id);

        if (playlist == null) {
            throw new IllegalArgumentException("La playlist no existeix");
        }

        return playlist;
    }

    private void replacePlaylist(Playlist playlist) {
        playlistsDAO.deletePlaylist(playlist.getId());
        playlistsDAO.addPlaylist(playlist);
    }
}
