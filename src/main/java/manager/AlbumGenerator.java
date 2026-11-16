
package manager;

import model.Mood;
import model.Playlist;
import model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class AlbumGenerator {

    private final LibraryManager library;

    public AlbumGenerator(LibraryManager library) {
        if (library == null) {
            throw new IllegalArgumentException("La biblioteca es obligatoria");
        }

        this.library = library;
    }

    // GENERAR ALBUM ALEATORI

    public Playlist generateAlbum(Mood mood, int targetMinutes) {

        if (mood == null || targetMinutes <= 0) {
            throw new IllegalArgumentException("Cal indicar un mood i minuts positius");
        }

        ArrayList<Song> candidates = new ArrayList<>();

        // Busquem cancons del mood seleccionat
        for (Song song : library.getAllSongs()) {
            if (song.getMood() == mood && LibraryManager.isReallyPlayable(song) && song.getDurationSeconds() > 0) {
                candidates.add(song);
            }
        }

        if (candidates.isEmpty()) {
            throw new IllegalStateException("No hi ha cancons reproduibles per a aquest mood");
        }

        // Barregem les cancons aleatoriament
        Collections.shuffle(candidates);

        long targetSeconds = (long) targetMinutes * 60;
        int totalSeconds = 0;

        ArrayList<String> songIds = new ArrayList<>();

        // Afegim cancons fins arribar a la durada objectiu
        for (Song song : candidates) {

            if (totalSeconds >= targetSeconds) {
                break;
            }

            songIds.add(song.getId());
            totalSeconds += song.getDurationSeconds();
        }

        // Creem la nova playlist
        Playlist album = new Playlist(UUID.randomUUID().toString(), "Random " + mood + " (" + targetMinutes + " min)", songIds);

        // Guardem l'album utilitzant LibraryManager
        library.addPlaylist(album);

        return album;
    }
}

