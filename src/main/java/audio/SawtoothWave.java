package audio;

public class SawtoothWave extends Waveform {

    @Override
    public double generate(double phase) {
        return 2 * phase - 1;
    }
}

