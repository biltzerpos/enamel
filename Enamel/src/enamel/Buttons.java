package enamel;
import com.pi4j.wiringpi.Gpio;

public class Buttons
{
    private int DIN;
    
    public Buttons(int pinNumber)
    {
      	this.DIN = pinNumber;
      	Gpio.pullUpDnControl(DIN, Gpio.PUD_DOWN);
      	Gpio.pinMode(DIN, Gpio.INPUT);
    }
    
    public int getPinNumber() {
    	return this.DIN;
    }
}
