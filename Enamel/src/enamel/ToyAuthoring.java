package enamel;

import com.pi4j.wiringpi.Gpio;
 

public class ToyAuthoring {

    public static void main(String[] args) {
        Gpio.wiringPiSetup();
        SoundPlayer s = new SoundPlayer();
        s.setScenarioFile("SampleScenarios/Scenario_" + args + ".txt"); 
    }
}
