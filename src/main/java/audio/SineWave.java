package audio;

public class SineWave extends Waveform {

    @Override
    public double generate(double phase) {
        return Math.sin(2 * Math.PI * phase);
    }
}