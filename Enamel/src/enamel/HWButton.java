package enamel;
import com.pi4j.wiringpi.Gpio;

public class HWButton
{
    private int DIN;
    
    public HWButton(int GPIOpinNumber)
    {
      	this.DIN = GPIOpinNumber;
      	Gpio.pullUpDnControl(DIN, Gpio.PUD_DOWN);
      	Gpio.pinMode(DIN, Gpio.INPUT);
    }
    
    public int getGPIOPinNumber() {
    	return this.DIN;
    }
}
