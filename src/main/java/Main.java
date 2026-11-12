package main.java;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SoundSynth synth = new SoundSynth();

        try{
            synth.makeSound(WaveType.SINE, 440, 1000);
            synth.makeSound(WaveType.SINE, 440, 200);
            synth.makeSound(WaveType.SINE, 700, 500);
            synth.makeSound(WaveType.SINE, 440, 1000);

            synth.makeSound(WaveType.SQUARE, 440, 1000);
            synth.makeSound(WaveType.SQUARE, 440, 200);
            synth.makeSound(WaveType.SQUARE, 700, 500);
            synth.makeSound(WaveType.SQUARE, 440, 1000);

            synth.makeSound(WaveType.TRIANGLE, 440, 1000);
            synth.makeSound(WaveType.TRIANGLE, 440, 200);
            synth.makeSound(WaveType.TRIANGLE, 700, 500);
            synth.makeSound(WaveType.TRIANGLE, 440, 1000);

            synth.makeSound(WaveType.SAWTOOTH, 440, 1000);
            synth.makeSound(WaveType.SAWTOOTH, 440, 200);
            synth.makeSound(WaveType.SAWTOOTH, 700, 500);
            synth.makeSound(WaveType.SAWTOOTH, 440, 1000);
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
}