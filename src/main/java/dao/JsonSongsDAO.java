package dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Song;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonSongsDAO implements SongsDAO {

    private final File file;
    private final Gson gson;

    public JsonSongsDAO() {
        this.gson = new Gson();

        File dataFolder = new File("data");

        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        this.file = new File(dataFolder, "songs.json");

        if (!file.exists()) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write("[]");
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException("Error creating songs file", e);
            }
        }
    }

    @Override
    public ArrayList<Song> getAllSongs() {
        try {
            FileReader reader = new FileReader(file);

            Type type = new TypeToken<ArrayList<Song>>() {}.getType();

            ArrayList<Song> songs = gson.fromJson(reader, type);

            reader.close();

            if (songs == null) {
                return new ArrayList<>();
            }

            return songs;

        } catch (IOException e) {
            throw new RuntimeException("Error reading songs", e);
        }
    }

    @Override
    public Song getSongById(String id) {
        if (id == null) {
            return null;
        }

        ArrayList<Song> songs = getAllSongs();

        for (Song song : songs) {
            if (id.equals(song.getId())) {
                return song;
            }
        }

        return null;
    }

    @Override
    public void addSong(Song song) {
        if (song == null || song.getId() == null) {
            throw new IllegalArgumentException("Invalid song");
        }

        ArrayList<Song> songs = getAllSongs();

        for (Song existingSong : songs) {
            if (song.getId().equals(existingSong.getId())) {
                throw new IllegalArgumentException("Song ID already exists");
            }
        }

        songs.add(song);

        saveSongs(songs);
    }

    @Override
    public void deleteSong(String id) {
        if (id == null) {
            return;
        }

        ArrayList<Song> songs = getAllSongs();

        for (int i = songs.size() - 1; i >= 0; i--) {
            if (id.equals(songs.get(i).getId())) {
                songs.remove(i);
            }
        }

        saveSongs(songs);
    }

    private void saveSongs(ArrayList<Song> songs) {
        try {
            FileWriter writer = new FileWriter(file);

            gson.toJson(songs, writer);

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException("Error saving songs", e);
        }
    }
}