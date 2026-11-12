package model;

public class Note {

    private double frequency;
    private int durationMs;

    public Note() {
    }

    public Note(double frequency, int durationMs) {
        this.frequency = frequency;
        this.durationMs = durationMs;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public int getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(int durationMs) {
        this.durationMs = durationMs;
    }
}

