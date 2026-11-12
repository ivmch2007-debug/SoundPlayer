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

    public Song(String id, String title, String artist, int durationSeconds, Mood mood, String style, boolean playable, List<Note> notes) {

        this.id = id;
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
        this.mood = mood;
        this.style = style;
        this.playable = playable;

        if (notes != null) {
            this.notes = new ArrayList<>(notes);
        } else {
            this.notes = new ArrayList<>();
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public boolean isPlayable() {
        return playable;
    }

    public void setPlayable(boolean playable) {
        this.playable = playable;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        if (notes != null) {
            this.notes = new ArrayList<>(notes);
        } else {
            this.notes = new ArrayList<>();
        }
    }
}
