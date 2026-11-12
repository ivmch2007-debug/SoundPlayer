package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Playlist;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class JsonPlaylistsDAO implements PlaylistsDAO {

    private final Path filePath;
    private final Gson gson;

    // Constructor
    public JsonPlaylistsDAO() {
        this.filePath = Path.of("data", "playlists.json");
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            Files.createDirectories(filePath.getParent());

            if (!Files.exists(filePath)) {
                Files.writeString(filePath, "[]");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error creating playlists file", e);
        }
    }

    // Read all playlists from JSON
    @Override
    public ArrayList<Playlist> getAllPlaylists() {
        try {
            String json = Files.readString(filePath);

            Type listType =
                    new TypeToken<ArrayList<Playlist>>() {}.getType();

            ArrayList<Playlist> playlists =
                    gson.fromJson(json, listType);

            if (playlists == null) {
                return new ArrayList<>();
            }

            return playlists;

        } catch (IOException e) {
            throw new RuntimeException("Error reading playlists", e);
        }
    }

    // Search a playlist by ID
    @Override
    public Playlist getPlaylistById(String id) {
        for (Playlist playlist : getAllPlaylists()) {
            if (playlist.getId().equals(id)) {
                return playlist;
            }
        }

        return null;
    }

    // Add a playlist
    @Override
    public void addPlaylist(Playlist playlist) {
        if (playlist == null || playlist.getId() == null) {
            throw new IllegalArgumentException("Invalid playlist");
        }

        ArrayList<Playlist> playlists = getAllPlaylists();

        if (getPlaylistById(playlist.getId()) != null) {
            throw new IllegalArgumentException(
                    "Playlist ID already exists");
        }

        playlists.add(playlist);
        savePlaylists(playlists);
    }

    // Delete a playlist by ID
    @Override
    public void deletePlaylist(String id) {
        ArrayList<Playlist> playlists = getAllPlaylists();

        playlists.removeIf(playlist ->
                playlist.getId().equals(id));

        savePlaylists(playlists);
    }

    // Save playlists to JSON
    private void savePlaylists(ArrayList<Playlist> playlists) {
        try {
            String json = gson.toJson(playlists);
            Files.writeString(filePath, json);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Error saving playlists", e);
        }
    }
}
