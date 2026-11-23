package audio;

public class TriangleWave extends Waveform {

    @Override
    public double generate(double phase) {
        return 4 * Math.abs(phase - 0.5) - 1;
    }
}
