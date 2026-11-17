package model;

import java.util.ArrayList;
import java.util.List;

public class Song {

    private String id;
    private String title;
    private String artist;
    private int durationSeconds;
    private Mood mood;
    private String style;
    private boolean playable;
    private List<Note> notes;

    public Song() {
        this.notes = new ArrayList<>();
    }

    public Song(String id, String title, String artist, int durationSeconds,
                Mood mood, String style, boolean playable, List<Note> notes) {

        setId(id);
        setTitle(title);
        setArtist(artist);
        setDurationSeconds(durationSeconds);
        setMood(mood);
        setStyle(style);
        this.playable = playable;
        setNotes(notes);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        if (artist == null || artist.trim().isEmpty()) {
            throw new IllegalArgumentException("Artist cannot be empty");
        }
        this.artist = artist;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        if (durationSeconds <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero");
        }
        this.durationSeconds = durationSeconds;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        if (mood == null) {
            throw new IllegalArgumentException("Mood cannot be null");
        }
        this.mood = mood;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        if (style == null || style.trim().isEmpty()) {
            throw new IllegalArgumentException("Style cannot be empty");
        }
        this.style = style;
    }

    public boolean isPlayable() {
        if (playable == false) {
            return false;
        }

        if (notes == null || notes.isEmpty()) {
            return false;
        }

        return true;
    }

    public void setPlayable(boolean playable) {
        this.playable = playable;
    }

    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    public void setNotes(List<Note> notes) {
        if (notes != null) {
            this.notes = new ArrayList<>(notes);
        } else {
            this.notes = new ArrayList<>();
        }
    }
}
