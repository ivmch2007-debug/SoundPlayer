
package manager;

import model.Note;
import model.Playlist;
import model.Song;

import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class PlaybackManager {

    @FunctionalInterface
    public interface NotePlayer {
        void play(double frequency, int durationMs)
                throws LineUnavailableException;
    }

    private final LibraryManager library;
    private final NotePlayer notePlayer;
    private Consumer<Song> onSongStart = song -> {};

    // CONSTRUCTOR
    public PlaybackManager(LibraryManager library, NotePlayer notePlayer) {
        if (library == null || notePlayer == null) {
            throw new IllegalArgumentException(
                    "La biblioteca i el reproductor son obligatoris"
            );
        }

        this.library = library;
        this.notePlayer = notePlayer;
    }

    // AVISAR QUAN COMENCA UNA CANCO
    public void setOnSongStart(Consumer<Song> onSongStart) {
        if (onSongStart == null) {
            throw new IllegalArgumentException(
                    "El callback no pot ser null"
            );
        }

        this.onSongStart = onSongStart;
    }

    // OBTENIR CANCONS REPRODUIBLES
    public ArrayList<Song> getPlayableSongs() {
        ArrayList<Song> songs = new ArrayList<>();

        for (Song song : library.getAllSongs()) {
            if (LibraryManager.isReallyPlayable(song)) {
                songs.add(song);
            }
        }

        return songs;
    }

    // REPRODUIR UNA CANCO
    public void playSong(String songId) throws LineUnavailableException {
        Song song = library.getSongById(songId);

        if (!LibraryManager.isReallyPlayable(song)) {
            throw new IllegalArgumentException(
                    "La canco no existeix o no es reproduible"
            );
        }

        // Comprovem que totes les notes siguin valides
        for (Note note : song.getNotes()) {
            if (note == null ||
                    note.getFrequency() <= 0 ||
                    note.getDurationMs() <= 0) {
                throw new IllegalArgumentException(
                        "La canco conte una nota invalida"
                );
            }
        }

        // Avisem que comenca la reproduccio
        onSongStart.accept(song);

        // Reproduim les notes de la canco
        for (Note note : song.getNotes()) {
            notePlayer.play(
                    note.getFrequency(),
                    note.getDurationMs()
            );
        }
    }

    // REPRODUIR UNA PLAYLIST
    public void playPlaylist(String playlistId)
            throws LineUnavailableException {

        Playlist playlist = library.getPlaylistById(playlistId);

        if (playlist == null) {
            throw new IllegalArgumentException(
                    "La playlist no existeix"
            );
        }

        for (String songId : playlist.getSongIds()) {
            Song song = library.getSongById(songId);

            if (LibraryManager.isReallyPlayable(song)) {
                playSong(songId);
            }
        }
    }
}
