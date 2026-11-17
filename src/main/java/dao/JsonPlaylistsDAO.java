package dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Playlist;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonPlaylistsDAO implements PlaylistsDAO {

    private final File file;
    private final Gson gson;

    public JsonPlaylistsDAO() {
        this.gson = new Gson();

        File dataFolder = new File("data");

        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        this.file = new File(dataFolder, "playlists.json");

        if (!file.exists()) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write("[]");
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException("Error creating playlists file", e);
            }
        }
    }

    @Override
    public ArrayList<Playlist> getAllPlaylists() {
        try {
            FileReader reader = new FileReader(file);

            Type type = new TypeToken<ArrayList<Playlist>>() {}.getType();

            ArrayList<Playlist> playlists = gson.fromJson(reader, type);

            reader.close();

            if (playlists == null) {
                return new ArrayList<>();
            }

            return playlists;

        } catch (IOException e) {
            throw new RuntimeException("Error reading playlists", e);
        }
    }

    @Override
    public Playlist getPlaylistById(String id) {
        if (id == null) {
            return null;
        }

        ArrayList<Playlist> playlists = getAllPlaylists();

        for (Playlist playlist : playlists) {
            if (id.equals(playlist.getId())) {
                return playlist;
            }
        }

        return null;
    }

    @Override
    public void addPlaylist(Playlist playlist) {
        if (playlist == null || playlist.getId() == null) {
            throw new IllegalArgumentException("Invalid playlist");
        }

        ArrayList<Playlist> playlists = getAllPlaylists();

        for (Playlist existingPlaylist : playlists) {
            if (playlist.getId().equals(existingPlaylist.getId())) {
                throw new IllegalArgumentException("Playlist ID already exists");
            }
        }

        playlists.add(playlist);

        savePlaylists(playlists);
    }

    @Override
    public void deletePlaylist(String id) {
        if (id == null) {
            return;
        }

        ArrayList<Playlist> playlists = getAllPlaylists();

        for (int i = playlists.size() - 1; i >= 0; i--) {
            if (id.equals(playlists.get(i).getId())) {
                playlists.remove(i);
            }
        }

        savePlaylists(playlists);
    }

    private void savePlaylists(ArrayList<Playlist> playlists) {
        try {
            FileWriter writer = new FileWriter(file);

            gson.toJson(playlists, writer);

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException("Error saving playlists", e);
        }
    }
}