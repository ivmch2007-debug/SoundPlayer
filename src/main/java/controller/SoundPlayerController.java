
package controller;

import manager.LibraryManager;
import manager.PlaybackManager;
import manager.AlbumGenerator;

import model.Song;
import model.Playlist;
import model.Note;
import model.Mood;

import view.ConsoleView;

import java.util.ArrayList;
import java.util.UUID;
import javax.sound.sampled.LineUnavailableException;

public class SoundPlayerController {

    private final ConsoleView view;
    private final LibraryManager library;
    private final PlaybackManager playback;
    private final AlbumGenerator albumGenerator;

    public SoundPlayerController(ConsoleView view,
                                 LibraryManager library,
                                 PlaybackManager playback,
                                 AlbumGenerator albumGenerator) {

        this.view = view;
        this.library = library;
        this.playback = playback;
        this.albumGenerator = albumGenerator;
    }

    // MENU PRINCIPAL
    public void run() {

        String option;

        do {
            option = view.showMainMenu();

            switch (option) {
                case "1":
                    manageSongs();
                    break;

                case "2":
                    managePlaylists();
                    break;

                case "3":
                    managePlayback();
                    break;

                case "4":
                    generateRandomAlbum();
                    break;

                case "Q":
                    view.showMessage("Hasta pronto!");
                    break;
            }

        } while (!option.equals("Q"));
    }

    // GESTION DE CANCIONES
    private void manageSongs() {

        int option;

        do {
            option = view.showSongsMenu();

            try {
                switch (option) {
                    case 1:
                        view.showSongs(library.getAllSongs());
                        break;

                    case 2:
                        addSong();
                        break;

                    case 3:
                        view.showSongs(library.getAllSongs());

                        String songId = view.readRequiredString(
                                "ID de la cancion a eliminar: "
                        );

                        library.deleteSong(songId);
                        view.showSuccess("Cancion eliminada.");
                        break;
                }

            } catch (IllegalArgumentException | IllegalStateException e) {
                view.showError(e.getMessage());
            }

        } while (option != 0);
    }

    // ANADIR CANCION
    private void addSong() {

        String title = view.readRequiredString("Titulo: ");
        String artist = view.readRequiredString("Artista: ");

        int duration = view.readPositiveInt(
                "Duracion en segundos: "
        );

        Mood mood = view.readMood();

        String style = view.readRequiredString("Estilo: ");

        boolean playable = view.readYesNo(
                "Quieres introducir notas para reproducirla?"
        );

        ArrayList<Note> notes = new ArrayList<>();

        if (playable) {

            int quantity = view.readPositiveInt(
                    "Cuantas notas quieres introducir? "
            );

            for (int i = 0; i < quantity; i++) {

                view.showMessage("Nota " + (i + 1));

                int frequency = view.readPositiveInt(
                        "Frecuencia en Hz: "
                );

                int durationMs = view.readPositiveInt(
                        "Duracion en milisegundos: "
                );

                Note note = new Note(frequency, durationMs);
                notes.add(note);
            }
        }

        Song song = new Song(
                UUID.randomUUID().toString(),
                title,
                artist,
                duration,
                mood,
                style,
                playable,
                notes
        );

        library.addSong(song);

        view.showSuccess(
                "Cancion guardada correctamente. ID: " + song.getId()
        );
    }

    // GESTION DE PLAYLISTS
    private void managePlaylists() {

        int option;

        do {
            option = view.showPlaylistsMenu();

            try {
                switch (option) {

                    case 1:
                        showPlaylistsWithDetails();
                        break;

                    case 2:
                        createPlaylist();
                        break;

                    case 3:
                        showPlaylistsWithDetails();

                        String playlistId = view.readRequiredString(
                                "ID de la playlist: "
                        );

                        view.showSongs(library.getAllSongs());

                        String songId = view.readRequiredString(
                                "ID de la cancion: "
                        );

                        library.addSongToPlaylist(playlistId, songId);

                        view.showSuccess(
                                "Cancion anadida a la playlist."
                        );
                        break;

                    case 4:
                        showPlaylistsWithDetails();

                        String removePlaylistId = view.readRequiredString(
                                "ID de la playlist: "
                        );

                        String removeSongId = view.readRequiredString(
                                "ID de la cancion: "
                        );

                        library.removeSongFromPlaylist(
                                removePlaylistId, removeSongId
                        );

                        view.showSuccess(
                                "Cancion eliminada de la playlist."
                        );
                        break;

                    case 5:
                        showPlaylistsWithDetails();

                        String deleteId = view.readRequiredString(
                                "ID de la playlist a eliminar: "
                        );

                        library.deletePlaylist(deleteId);

                        view.showSuccess("Playlist eliminada.");
                        break;
                }

            } catch (IllegalArgumentException | IllegalStateException e) {
                view.showError(e.getMessage());
            }

        } while (option != 0);
    }

    // CREAR PLAYLIST
    private void createPlaylist() {

        String name = view.readRequiredString(
                "Nombre de la playlist: "
        );

        String description = view.readString(
                "Descripcion (opcional): "
        );

        Mood mood = null;

        if (view.readYesNo("Quieres asignar un mood?")) {
            mood = view.readMood();
        }

        Playlist playlist = new Playlist(
                UUID.randomUUID().toString(),
                name,
                description,
                mood,
                new ArrayList<>()
        );

        library.addPlaylist(playlist);

        view.showSuccess(
                "Playlist creada. ID: " + playlist.getId()
        );
    }

    // MOSTRAR PLAYLISTS Y SUS ESTADISTICAS
    private void showPlaylistsWithDetails() {

        ArrayList<Playlist> playlists = library.getAllPlaylists();

        view.showPlaylists(playlists);

        for (Playlist playlist : playlists) {

            String id = playlist.getId();

            int duration = library.getPlaylistDurationSeconds(id);
            int playable = library.getPlayableSongCount(id);
            int notPlayable = library.getNonPlayableSongCount(id);

            view.showPlaylistDetails(
                    playlist,
                    duration,
                    playable,
                    notPlayable
            );
        }
    }

    // REPRODUCCION
    private void managePlayback() {

        int option;

        do {
            option = view.showPlaybackMenu();

            try {
                switch (option) {

                    case 1:
                        ArrayList<Song> songs =
                                playback.getPlayableSongs();

                        view.showSongs(songs);

                        if (!songs.isEmpty()) {

                            String songId = view.readRequiredString(
                                    "ID de la cancion: "
                            );

                            view.showMessage("Reproduciendo cancion...");

                            playback.playSong(songId);
                        }
                        break;

                    case 2:
                        showPlaylistsWithDetails();

                        if (!library.getAllPlaylists().isEmpty()) {

                            String playlistId = view.readRequiredString(
                                    "ID de la playlist: "
                            );

                            Playlist playlist =
                                    library.getPlaylistById(playlistId);

                            if (playlist == null) {
                                view.showError("La playlist no existe.");
                            } else {
                                for (String id : playlist.getSongIds()) {

                                    Song song = library.getSongById(id);

                                    if (LibraryManager.isReallyPlayable(song)) {
                                        view.showMessage(
                                                "Reproduciendo: " + song.getTitle()
                                        );

                                        playback.playSong(id);
                                    }
                                }
                            }
                        }
                        break;
                }

            } catch (IllegalArgumentException | IllegalStateException e) {
                view.showError(e.getMessage());

            } catch (LineUnavailableException e) {
                view.showError(
                        "No se puede utilizar el dispositivo de audio."
                );
            }

        } while (option != 0);
    }

    // GENERAR ALBUM RANDOM
    private void generateRandomAlbum() {

        try {
            Mood mood = view.readMood();

            int minutes = view.readPositiveInt(
                    "Duracion objetivo en minutos: "
            );

            Playlist album = albumGenerator.generateAlbum(
                    mood, minutes
            );

            view.showSuccess(
                    "Album generado: " + album.getName()
            );

            view.showPlaylistDetails(
                    album,
                    library.getPlaylistDurationSeconds(album.getId()),
                    library.getPlayableSongCount(album.getId()),
                    library.getNonPlayableSongCount(album.getId())
            );

        } catch (IllegalArgumentException | IllegalStateException e) {
            view.showError(e.getMessage());
        }
    }
}
