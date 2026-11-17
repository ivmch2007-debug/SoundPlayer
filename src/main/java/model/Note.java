package model;

public class Note {

    private double frequency;
    private int durationMs;

    public Note() {
    }

    public Note(double frequency, int durationMs) {
        setFrequency(frequency);
        setDurationMs(durationMs);
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        if (frequency <= 0) {
            throw new IllegalArgumentException("Frequency must be greater than zero");
        }

        this.frequency = frequency;
    }

    public int getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(int durationMs) {
        if (durationMs <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero");
        }

        this.durationMs = durationMs;
    }
}
