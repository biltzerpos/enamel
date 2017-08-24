package enamel;
import com.pi4j.wiringpi.Gpio;

public class HWButton
{
    private int DIN;
    
    public HWButton(int GPIOpinNumber)
    {
      	this.DIN = GPIOpinNumber;
      	try {
	      	Gpio.pullUpDnControl(DIN, Gpio.PUD_DOWN);
	      	Gpio.pinMode(DIN, Gpio.INPUT);
      	} catch (UnsatisfiedLinkError e) {
      		System.out.println("Hardware buttons not supported on this system.");
      	}
    }
    
    public int getGPIOPinNumber() {
    	return this.DIN;
    }
}
