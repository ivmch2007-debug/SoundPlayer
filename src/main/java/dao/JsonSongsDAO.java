package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Song;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class JsonSongsDAO implements SongsDAO {

    private final Path filePath;
    private final Gson gson;

    // Constructor
    public JsonSongsDAO() {
        this.filePath = Path.of("data", "songs.json");
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            Files.createDirectories(filePath.getParent());

            if (!Files.exists(filePath)) {
                Files.writeString(filePath, "[]");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error creating songs file", e);
        }
    }

    // Read all songs from JSON
    @Override
    public ArrayList<Song> getAllSongs() {
        try {
            String json = Files.readString(filePath);

            Type listType = new TypeToken<ArrayList<Song>>() {}.getType();

            ArrayList<Song> songs = gson.fromJson(json, listType);

            if (songs == null) {
                return new ArrayList<>();
            }

            return songs;

        } catch (IOException e) {
            throw new RuntimeException("Error reading songs", e);
        }
    }

    // Search a song by ID
    @Override
    public Song getSongById(String id) {
        for (Song song : getAllSongs()) {
            if (song.getId().equals(id)) {
                return song;
            }
        }

        return null;
    }

    // Add a song
    @Override
    public void addSong(Song song) {
        if (song == null || song.getId() == null) {
            throw new IllegalArgumentException("Invalid song");
        }

        ArrayList<Song> songs = getAllSongs();

        if (getSongById(song.getId()) != null) {
            throw new IllegalArgumentException("Song ID already exists");
        }

        songs.add(song);
        saveSongs(songs);
    }

    // Delete a song by ID
    @Override
    public void deleteSong(String id) {
        ArrayList<Song> songs = getAllSongs();

        songs.removeIf(song -> song.getId().equals(id));

        saveSongs(songs);
    }

    // Save songs to JSON
    private void saveSongs(ArrayList<Song> songs) {
        try {
            String json = gson.toJson(songs);
            Files.writeString(filePath, json);

        } catch (IOException e) {
            throw new RuntimeException("Error saving songs", e);
        }
    }
}
