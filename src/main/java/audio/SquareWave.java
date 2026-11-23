package audio;

public class SquareWave extends Waveform {

    @Override
    public double generate(double phase) {
        return (phase < 0.5) ? 1.0 : -1.0;
    }
}
