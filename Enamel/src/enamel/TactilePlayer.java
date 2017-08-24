package enamel;

import java.util.ArrayList;
import java.util.List;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioInterruptCallback;
/**
 * This class controls the hardware for a Raspberry Pi with physical Braille cells
 * (Using "Metec B11 Braille Cell") and programmable buttons, using the Pi4J library.
 * <p>
 * The constructor initializes the GPIO (general purpose in/out) pins
 * and takes as arguments the number of Braille Cells and the number of 
 * buttons the system should use. This class also contains several methods 
 * that allow for the manipulation of the physical braille cell.
 * <b>Currently, due to the way the GPIO pins are numbered, the buttons must be 
 * specifically placed on the GPIO pins. See the Raspberry Pi GPIO pin diagram, found online, 
 * for more information on how the pins are numbered. Currently, the GPIO pins 4, 5, 6, 7
 * are connected to physical push buttons. New buttons could be added, but either
 * they must be placed in GPIO pins 8, 9... etc. (in sequence) or the constructor in
 * which the HWButton objects are created must be changed. 
 * <p>
 * The individual Braille Cells can be accessed using the
 * <code> getCell(int)</code> method and the cell's index.
 * Similarly, the buttons can be accessed using the <code> getButton(int)</code> method, 
 * and they are indexed from left to right. Both of these methods return the 
 * actual reference of the objects, meaning you can then call methods from each of the
 * object's respective classes to manipulate them. You can use the
 * <code> getButton(int) </code> method to get the reference of a specific
 * button. 
 * 
 * @author ENAMEL team: Sunjik Lee, Li Yin, Vassilios Tzerpos.
 *
 */
public class TactilePlayer extends Player {

	List<HWButton> HWButtonList;

	int ON = 0;
	int DOUT = 1;
	int STROBE = 2;
	int CLOCK = 3;
	private long debounce = 0;

	/**
     * Initializes the GPIO pins for the single Metec B11 braille cell, whose pins are
     * specified in the fields <code>ON, DOUT, STROBE, and CLOCK</code>.
     * The two parameters <code>brailleCellNumber</code> and <code>buttonNumber</code>
     * should not exceed 1 and 4, respectively, until future modifications to
     * the code and the hardware are completed. Due to the limitations of the
     * Raspberry Pi (specifically, that it has limited GPIO pins), this number
     * will be limited. 
     * 
     * @param brailleCellNumber
     *            the number of braille cells the TactilePlayer should have
     * @param buttonNumber
     *            the number of buttons the TactilePlayer should have
     * @throws IllegalArgumentException
     *             if one or both of the two parameters is negative or 0,
     *             or if brailleCellNumber exceeds 1, or if buttonNumber exceeds
     *             4.
     */
	public TactilePlayer(int brailleCellNumber, int buttonNumber) {

		super(brailleCellNumber, buttonNumber);
		if (brailleCellNumber > 1 || buttonNumber > 4)
			throw new IllegalArgumentException("The current system for TactilePlayer does not support more than 1 braille cell or more than 4 buttons");
		try {
			Gpio.pinMode(ON, Gpio.OUTPUT);
			Gpio.pinMode(DOUT, Gpio.OUTPUT);
			Gpio.pinMode(STROBE, Gpio.OUTPUT);
			Gpio.pinMode(CLOCK, Gpio.OUTPUT);
			Gpio.digitalWrite(ON, 1);
			Gpio.delay(2000);
			Gpio.digitalWrite(ON, 0);
		} catch (UnsatisfiedLinkError e) {}

		try {
			HWButtonList = new ArrayList<HWButton>(buttonNumber);
			for (int i = 0; i < buttonNumber; i++) {
				this.HWButtonList.add(new HWButton(i + 4));
			}
		} catch (UnsatisfiedLinkError e) {}
	}

	public HWButton getButton(int index) {
		return this.HWButtonList.get(index);
	}
	/**
     * Refreshes the Metec B11 braille cell to match the current state of the 
     * instantiated BrailleCell object. For the TactilePlayer class,
     * this method calls a method of the GPIO library. 
     * GPIO.digitalWrite turns off (0) or on (1) the specified pin.
     * To access all the pins on the braille cell, first, the STROBE must be
     * turned off. Then, for each pin (for one braille cell, 8 pins, two braille cells, 16), 
     * this method turns off the CLOCK, turns on/off the DOUT to match the BrailleCell
     * object's current pin<b>(matching in reverse order)</b>, then turns on the CLOCK again. 
     * At the end of the loop, the STROBE must be turned back on, at which point the 
     * braille cell will display the pins. 
     */
	@Override
	public void refresh() {
		try {
			Gpio.digitalWrite(STROBE, 0);
			for (int i = 0; i < brailleList.size(); i++) {
				for (int j = brailleList.get(i).getNumberOfPins() - 1; j >= 0; j--) {
					Gpio.digitalWrite(CLOCK, 0);
					if (brailleList.get(i).getPinState(j) == true) {
						Gpio.digitalWrite(DOUT, 1);
					} else {
						Gpio.digitalWrite(DOUT, 0);
					}
					Gpio.delay(1);
					Gpio.digitalWrite(CLOCK, 1);
				}
			}
			Gpio.digitalWrite(STROBE, 1);
		} catch (UnsatisfiedLinkError e) {
		}
	}
	/**
     * Adds a GPIO ISR (which listens for GPIO pin interrupts) with the specified index passed as argument.
     * The index must be between 0 and buttonNumber.
     * <p>
     * The GPIO listener requires a parameter from the ScenarioParser class,
     * needed for it to call <code>ScenarioParser.skip()</code> method as well as have
     * access to the userInput field. Pressing this button will skip to the
     * specified area in the scenario file. 
     * 
     * @param index
     *            the index of the KeyListener to be added.
     * @param param
	 * 			the String in ScenarioParser to skip to, needed for ScenarioParser's <code>skip(String indicator)</code>
	 * 			method
	 * @param sp
	 * 			the reference to the current ScenarioParser object          
     */
	@Override
	public void addSkipButtonListener(int index, String param, ScenarioParser sp) {
		try {
			Gpio.wiringPiISR(getButton(index).getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
				@Override
				public void callback(int pin) {
					if (sp.userInput) {
						sp.userInput = false;
						if (System.currentTimeMillis() > debounce) {
							debounce = System.currentTimeMillis() + 1000L;
							sp.skip(param);
						}
					}
				}
			});
		} catch (UnsatisfiedLinkError e) {
		} catch (IndexOutOfBoundsException e) {
		}
	}

	/**
     * Adds a GPIO ISR (which listens for GPIO pin interrupts) with the specified index passed as argument.
     * The index must be between 0 and buttonNumber.
     * <p>
     * The GPIO listener requires a parameter from the ScenarioParser class,
     * needed for it to call <code>ScenarioParser.repeatText()</code> method. 
     * Pressing this button will repeat the specified text in the scenario file. 
     * 
     * @param index
     *            the index of the KeyListener to be added.
	 * @param sp
	 * 			the reference to the current ScenarioParser object          
     */
	@Override
	public void addRepeatButtonListener(int index, ScenarioParser sp) {
		try {
			Gpio.wiringPiISR(getButton(index).getGPIOPinNumber(), Gpio.INT_EDGE_RISING, new GpioInterruptCallback() {
				@Override
				public void callback(int pin) {
					if (sp.userInput) {
						if (System.currentTimeMillis() > debounce) {
							debounce = System.currentTimeMillis() + 1000L;
							sp.repeatText();
						}
					}
				}
			});
		} catch (UnsatisfiedLinkError e) {
		} catch (IndexOutOfBoundsException e) {
		}
	}
	
	/**
     * Removes the ISR from the index of HWButtonList passed as argument. 
     * The index must be between 0 and buttonNumber.
     * 
     * @param index
     *            the index of the GPIO ISR to be removed.
     * @throws IllegalArgumentException
     *             if the index is negative or equal to or bigger than
     *             buttonNumber (the number of buttons initialized)
     */
	@Override
	public void removeButtonListener(int index) {
		if (index >= this.buttonNumber || index < 0) {
            throw new IllegalArgumentException("Invalid index.");
        }
		try {
			Gpio.wiringPiClearISR(getButton(index).getGPIOPinNumber());
		} catch (UnsatisfiedLinkError e) {
		}
	}

}
