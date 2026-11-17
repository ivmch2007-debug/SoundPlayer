package view;

import java.util.List;
import java.util.Scanner;
import model.Mood;
import model.Playlist;
import model.Song;

/**
 * Vista de consola de SoundPlayer.
 * Solo muestra informacion y recoge datos; no accede a los DAO.
 */
public class ConsoleView {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";

    private final Scanner scanner;

    public ConsoleView() {
        scanner = new Scanner(System.in);
    }

    public String showMainMenu() {
        System.out.println(CYAN + "\n===== SOUNDPLAYER =====" + RESET);
        System.out.println("1. Gestionar canciones");
        System.out.println("2. Gestionar playlists");
        System.out.println("3. Reproducir");
        System.out.println("4. Generar album random por mood");
        System.out.println("Q. Salir");

        String option;
        boolean valid;
        do {
            option = readString("Selecciona una opcion: ").toUpperCase();
            valid = option.equals("1") || option.equals("2") ||
                    option.equals("3") || option.equals("4") || option.equals("Q");
            if (!valid) {
                showError("Opcion incorrecta. Elige 1, 2, 3, 4 o Q.");
            }
        } while (!valid);
        return option;
    }

    public int showSongsMenu() {
        System.out.println(CYAN + "\n--- GESTION DE CANCIONES ---" + RESET);
        System.out.println("1. Listar canciones");
        System.out.println("2. Anadir cancion");
        System.out.println("3. Eliminar cancion");
        System.out.println("0. Volver al menu principal");
        return readIntInRange("Selecciona una opcion: ", 0, 3);
    }

    public int showPlaylistsMenu() {
        System.out.println(CYAN + "\n--- GESTION DE PLAYLISTS ---" + RESET);
        System.out.println("1. Listar playlists");
        System.out.println("2. Crear playlist");
        System.out.println("3. Anadir cancion a playlist");
        System.out.println("4. Eliminar cancion de playlist");
        System.out.println("5. Eliminar playlist");
        System.out.println("0. Volver al menu principal");
        return readIntInRange("Selecciona una opcion: ", 0, 5);
    }

    public int showPlaybackMenu() {
        System.out.println(CYAN + "\n--- REPRODUCCION ---" + RESET);
        System.out.println("1. Reproducir cancion");
        System.out.println("2. Reproducir playlist");
        System.out.println("0. Volver al menu principal");
        return readIntInRange("Selecciona una opcion: ", 0, 2);
    }

    public String readString(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public String readRequiredString(String message) {
        String value;
        do {
            value = readString(message);
            if (value.isEmpty()) {
                showError("Este campo no puede estar vacio.");
            }
        } while (value.isEmpty());
        return value;
    }

    public int readIntInRange(String message, int min, int max) {
        int value = min;
        boolean valid = false;
        do {
            String input = readString(message);
            try {
                value = Integer.parseInt(input);
                valid = value >= min && value <= max;
            } catch (NumberFormatException e) {
                valid = false;
            }
            if (!valid) {
                showError("Introduce un numero entre " + min + " y " + max + ".");
            }
        } while (!valid);
        return value;
    }

    public int readPositiveInt(String message) {
        int value = 0;
        boolean valid = false;
        do {
            String input = readString(message);
            try {
                value = Integer.parseInt(input);
                valid = value > 0;
            } catch (NumberFormatException e) {
                valid = false;
            }
            if (!valid) {
                showError("Introduce un numero entero mayor que cero.");
            }
        } while (!valid);
        return value;
    }

    public Mood readMood() {
        Mood[] moods = Mood.values();
        System.out.println("Selecciona un mood:");
        for (int i = 0; i < moods.length; i++) {
            System.out.println((i + 1) + ". " + moods[i]);
        }
        int option = readIntInRange("Mood: ", 1, moods.length);
        return moods[option - 1];
    }

    public boolean readYesNo(String message) {
        String answer;
        do {
            answer = readString(message + " (S/N): ").toUpperCase();
            if (!answer.equals("S") && !answer.equals("N")) {
                showError("Responde S o N.");
            }
        } while (!answer.equals("S") && !answer.equals("N"));
        return answer.equals("S");
    }

    public void showSongs(List<Song> songs) {
        System.out.println(CYAN + "\n--- BIBLIOTECA DE CANCIONES ---" + RESET);
        if (songs == null || songs.isEmpty()) {
            showMessage("No hay canciones guardadas.");
            return;
        }
        for (Song song : songs) {
            String status = song.isPlayable() && song.getNotes() != null &&
                    !song.getNotes().isEmpty() ? GREEN + "[PLAYABLE]" : RED + "[NOT PLAYABLE]";
            System.out.println("ID: " + song.getId() + " | " + song.getTitle() +
                    " - " + song.getArtist());
            System.out.println("  Duracion: " + formatDuration(song.getDurationSeconds()) +
                    " | Mood: " + song.getMood() + " | Estilo: " + song.getStyle() +
                    " " + status + RESET);
        }
    }

    public void showPlaylists(List<Playlist> playlists) {
        System.out.println(CYAN + "\n--- PLAYLISTS ---" + RESET);
        if (playlists == null || playlists.isEmpty()) {
            showMessage("No hay playlists guardadas.");
            return;
        }
        for (Playlist playlist : playlists) {
            System.out.println("ID: " + playlist.getId() + " | " + playlist.getName() +
                    " | Canciones: " + playlist.getSongIds().size());
        }
    }

    // El controlador obtiene estos calculos del manager y la vista solo los muestra.
    public void showPlaylistDetails(Playlist playlist, int durationSeconds,
                                    int playable, int notPlayable) {
        System.out.println("Playlist: " + playlist.getName() + " (" + playlist.getId() + ")");
        System.out.println("Duracion: " + formatDuration(durationSeconds) +
                " | Reproducibles: " + playable +
                " | No reproducibles: " + notPlayable);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showSuccess(String message) {
        System.out.println(GREEN + message + RESET);
    }

    public void showError(String message) {
        System.out.println(RED + "Error: " + message + RESET);
    }

    public void showWarning(String message) {
        System.out.println(YELLOW + message + RESET);
    }

    public String formatDuration(int seconds) {
        return (seconds / 60) + " min " + (seconds % 60) + " s";
    }
}

