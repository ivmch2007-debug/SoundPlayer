
import controller.SoundPlayerController;
import dao.JsonSongsDAO;
import dao.JsonPlaylistsDAO;
import dao.SongsDAO;
import dao.PlaylistsDAO;
import manager.LibraryManager;
import manager.PlaybackManager;
import manager.AlbumGenerator;
import view.ConsoleView;

public class Main {

    public static void main(String[] args) {

        // Crear los DAO para guardar y cargar los datos JSON
        SongsDAO songsDAO = new JsonSongsDAO();
        PlaylistsDAO playlistsDAO = new JsonPlaylistsDAO();

        // Crear el manager de la biblioteca
        LibraryManager library = new LibraryManager(
                songsDAO, playlistsDAO
        );

        // Crear el sintetizador de sonido
        SoundSynth synth = new SoundSynth();

        // Crear el manager de reproduccion
        PlaybackManager playback = new PlaybackManager(
                library,
                (frequency, durationMs) -> synth.makeSound(
                        WaveType.SINE,
                        (int) Math.round(frequency),
                        durationMs
                )
        );

        // Crear el generador de albumes
        AlbumGenerator albumGenerator = new AlbumGenerator(library);

        // Crear la vista de consola
        ConsoleView view = new ConsoleView();

        // Crear el controlador
        SoundPlayerController controller = new SoundPlayerController(
                view,
                library,
                playback,
                albumGenerator
        );

        // Iniciar la aplicacion
        controller.run();
    }
}
