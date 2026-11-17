package manager;

import model.Note;
import model.Playlist;
import model.Song;

import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;

public class PlaybackManager {

    @FunctionalInterface
    public interface NotePlayer {
        void play(double frequency, int durationMs)
                throws LineUnavailableException;
    }

    private final LibraryManager library;
    private final NotePlayer notePlayer;

    public PlaybackManager(LibraryManager library, NotePlayer notePlayer) {
        if (library == null || notePlayer == null) {
            throw new IllegalArgumentException("La biblioteca i el reproductor son obligatoris");
        }

        this.library = library;
        this.notePlayer = notePlayer;
    }

    public ArrayList<Song> getPlayableSongs() {
        ArrayList<Song> songs = new ArrayList<>();

        for (Song song : library.getAllSongs()) {
            if (LibraryManager.isReallyPlayable(song)) {
                songs.add(song);
            }
        }

        return songs;
    }

    public void playSong(String songId) throws LineUnavailableException {
        Song song = library.getSongById(songId);

        if (!LibraryManager.isReallyPlayable(song)) {
            throw new IllegalArgumentException("La canco no existeix o no es reproduible");
        }

        for (Note note : song.getNotes()) {
            if (note == null || note.getFrequency() <= 0 || note.getDurationMs() <= 0) {
                throw new IllegalArgumentException("La canco conte una nota invalida");
            }
        }

        for (Note note : song.getNotes()) {
            notePlayer.play(note.getFrequency(), note.getDurationMs());
        }
    }

    public void playPlaylist(String playlistId)
            throws LineUnavailableException {

        Playlist playlist = library.getPlaylistById(playlistId);

        if (playlist == null) {
            throw new IllegalArgumentException("La playlist no existeix");
        }

        for (String songId : playlist.getSongIds()) {
            Song song = library.getSongById(songId);

            if (LibraryManager.isReallyPlayable(song)) {
                playSong(songId);
            }
        }
    }
}
