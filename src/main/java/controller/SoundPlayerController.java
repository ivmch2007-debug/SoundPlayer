package controller;

import view.ConsoleView;

/** Coordina los menus. La logica se conectara cuando existan los managers. */
public class SoundPlayerController {
    private final ConsoleView view;

    public SoundPlayerController(ConsoleView view) {
        this.view = view;
    }

    public void run() {
        String option;
        do {
            option = view.showMainMenu();
            switch (option) {
                case "1": manageSongs(); break;
                case "2": managePlaylists(); break;
                case "3": managePlayback(); break;
                case "4": generateRandomAlbum(); break;
                case "Q": view.showMessage("Hasta pronto!"); break;
            }
        } while (!option.equals("Q"));
    }

    private void manageSongs() {
        int option;
        do {
            option = view.showSongsMenu();
            switch (option) {
                case 1:
                    // TODO: view.showSongs(libraryManager.getAllSongs());
                    view.showWarning("Listado de canciones pendiente de conectar.");
                    break;
                case 2:
                    // TODO: pedir datos con la vista y crear cancion con LibraryManager.
                    view.showWarning("Alta de canciones pendiente de conectar.");
                    break;
                case 3:
                    // TODO: pedir ID y eliminar cancion mediante LibraryManager.
                    view.showWarning("Eliminar canciones pendiente de conectar.");
                    break;
            }
        } while (option != 0);
    }

    private void managePlaylists() {
        int option;
        do {
            option = view.showPlaylistsMenu();
            switch (option) {
                case 1:
                    // TODO: mostrar playlists y estadisticas desde LibraryManager.
                    view.showWarning("Listado de playlists pendiente de conectar.");
                    break;
                case 2:
                    // TODO: pedir nombre y crear playlist con LibraryManager.
                    view.showWarning("Crear playlist pendiente de conectar.");
                    break;
                case 3:
                    // TODO: pedir ID de playlist y de cancion; llamar a LibraryManager.
                    view.showWarning("Anadir cancion a playlist pendiente de conectar.");
                    break;
                case 4:
                    // TODO: pedir IDs y quitar cancion con LibraryManager.
                    view.showWarning("Quitar cancion de playlist pendiente de conectar.");
                    break;
                case 5:
                    // TODO: pedir ID y eliminar playlist con LibraryManager.
                    view.showWarning("Eliminar playlist pendiente de conectar.");
                    break;
            }
        } while (option != 0);
    }

    private void managePlayback() {
        int option;
        do {
            option = view.showPlaybackMenu();
            switch (option) {
                case 1:
                    // TODO: elegir cancion reproducible y usar PlaybackManager.
                    view.showWarning("Reproducir cancion pendiente de conectar.");
                    break;
                case 2:
                    // TODO: elegir playlist y usar PlaybackManager.
                    view.showWarning("Reproducir playlist pendiente de conectar.");
                    break;
            }
        } while (option != 0);
    }

    private void generateRandomAlbum() {
        // TODO: pedir mood y duracion y llamar a AlbumGenerator.
        view.showWarning("Generar album random pendiente de conectar.");
    }
}

