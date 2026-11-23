package audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundSynth {

    public void makeSound(WaveType type, int numberOfTimesFullFuncPerSec,
                          int durationMs) throws LineUnavailableException {

        System.out.println(String.format(
                "Starting to make sound %s %d at %d ms",
                type, numberOfTimesFullFuncPerSec, durationMs
        ));

        byte[] buf = new byte[2];
        int frequency = 44100;

        AudioFormat af = new AudioFormat(
                (float) frequency, 16, 1, true, false
        );

        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl.open();
        sdl.start();

        // Seleccionamos el tipo de onda mediante polimorfismo.
        Waveform waveform = getWaveform(type);

        for (int i = 0; i < durationMs * (float) frequency / 1000; i++) {

            float numberOfSamplesToRepresentFullFunc =
                    (float) frequency / numberOfTimesFullFuncPerSec;

            double phase = (i % numberOfSamplesToRepresentFullFunc)
                    / numberOfSamplesToRepresentFullFunc;

            // Cada subclase calcula su propia forma de onda.
            double value = waveform.generate(phase);

            short a = (short) (value * 32767);

            buf[0] = (byte) (a & 0xFF);
            buf[1] = (byte) (a >> 8);

            sdl.write(buf, 0, 2);
        }

        sdl.drain();
        sdl.stop();
    }

    private Waveform getWaveform(WaveType type) {
        switch (type) {
            case SINE:
                return new SineWave();
            case SQUARE:
                return new SquareWave();
            case TRIANGLE:
                return new TriangleWave();
            case SAWTOOTH:
                return new SawtoothWave();
            default:
                throw new IllegalArgumentException("Unknown wave type");
        }
    }
}
