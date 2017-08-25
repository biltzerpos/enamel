package enamel;

import java.util.logging.Level;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
/**
 * This class controls the hardware for a Raspberry Pi with physical Braille cells
 * (Using "Metec B11 Braille Cell") and programmable buttons, using the Pi4J library.
 * <p>
 * The constructor initializes the GPIO (general purpose in/out) pins
 * and takes as arguments the number of Braille Cells and the number of 
 * buttons the system should use. This class also contains several methods 
 * that allow for the manipulation of the physical braille cell.
 * Currently, due to the way the GPIO pins are numbered, the buttons must be 
 * specifically placed on the GPIO pins. See the Raspberry Pi GPIO pin diagram, found online, 
 * for more information on how the pins are numbered. Currently, the GPIO pins 4, 5, 6, 7
 * are connected to physical push buttons. New buttons could be added, but either
 * they must be placed in GPIO pins 8, 9... etc. (in sequence) or the way the
 * buttonList is created must be changed. 
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
    
    GpioPinDigitalInput[] buttonList;
    GpioPinDigitalOutput ON;
    GpioPinDigitalOutput DOUT;
    GpioPinDigitalOutput STROBE;
    GpioPinDigitalOutput CLOCK;
    
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
            GpioController gpio = GpioFactory.getInstance();
            ON = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "ON", PinState.LOW);
            DOUT = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "DOUT", PinState.LOW);
            STROBE = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "STROBE", PinState.LOW);
            CLOCK = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "CLOCK", PinState.LOW);
            ON.high();
            Thread.sleep(2000);
            ON.low();
            
            buttonList = new GpioPinDigitalInput[buttonNumber];
            for (int i = 0; i < buttonNumber; i++) {
                GpioPinDigitalInput button = gpio.provisionDigitalInputPin(RaspiPin.getPinByAddress(i+4), PinPullResistance.PULL_DOWN);
                button.setDebounce(1000);
                buttonList[i] = button;
            }
        } catch (UnsatisfiedLinkError e) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns a reference to the button at the index passed as argument. The
     * main purpose of providing this method is so the client can add
     * actionListeners to the button. Buttons are numbered from left to right, 
     * from 0 to (buttonNumber-1), buttonNumber
     * being the number of buttons initialized by the constructor.
     * 
     * @param index
     *            the index of the button to be returned
     * @return reference to the GpioPinDigitalInput object at the index passed as argument
     * @throws IllegalArgumentException
     *             if the index is negative or equal to or bigger than
     *             buttonNumber (the number of buttons initialized)
     */
    public GpioPinDigitalInput getButton(int index) {
        return this.buttonList[index];
    }
    
    /**
     * Refreshes the Metec B11 braille cell to match the current state of the 
     * instantiated BrailleCell object. For the TactilePlayer class,
     * this method calls a method of the GPIO library. 
     * low() turns off the GPIO pin, high() turns on the GPIO pin.
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
            STROBE.low();
            for (int i = 0; i < brailleList.size(); i++) {
                for (int j = brailleList.get(i).getNumberOfPins() - 1; j >= 0; j--) {
                    CLOCK.low();
                    if (brailleList.get(i).getPinState(j) == true) {
                        DOUT.high();
                    } else {
                        DOUT.low();
                    }
                    Thread.sleep(2);
                    CLOCK.high();
                }
            }
            STROBE.high();
        } catch (UnsatisfiedLinkError e) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Adds a Listener (which listens for GPIO pin interrupts) with the specified index passed as argument.
     * The index must be between 0 and buttonNumber.
     * <p>
     * The GPIO listener requires a parameter from the ScenarioParser class,
     * needed for it to call <code>ScenarioParser.skip()</code> method as well as have
     * access to the userInput field. Pressing this button will skip to the
     * specified area in the scenario file. 
     * 
     * @param index
     *            the index of the Listener to be added
     * @param param
     *          the String in ScenarioParser to skip to, needed for ScenarioParser's <code>skip(String indicator)</code>
     *          method
     * @param sp
     *          the reference to the current ScenarioParser object          
     */
    @Override
    public void addSkipButtonListener(int index, String param, ScenarioParser sp) {
        try {
            buttonList[index].addListener(new GpioPinListenerDigital() {

                @Override
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent arg0) {
                    if (arg0.getState() == PinState.HIGH) {
                        if (sp.userInput) {
                            sp.skip(param);
                            logger.log(Level.INFO, "Button {0} was pressed", index+1);
                            sp.userInput = false;
                        }   
                    }
                    
                }   
            });
        } catch (UnsatisfiedLinkError e) {
        } catch (IndexOutOfBoundsException e) {
        }
    }

    /**
     * Adds a Listener (which listens for GPIO pin interrupts) with the specified index passed as argument.
     * The index must be between 0 and buttonNumber.
     * <p>  
     * The GPIO listener requires a parameter from the ScenarioParser class,
     * needed for it to call <code>ScenarioParser.repeatText()</code> method. 
     * Pressing this button will repeat the specified text in the scenario file. 
     * 
     * @param index
     *            the index of the Listener to be added
     * @param sp
     *          the reference to the current ScenarioParser object          
     */
    @Override
    public void addRepeatButtonListener(int index, ScenarioParser sp) {
        try {
            buttonList[index].addListener(new GpioPinListenerDigital() {

                @Override
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent arg0) {
                    if (arg0.getState() == PinState.HIGH) {
                        if (sp.userInput) {
    						repeat++;
    						logger.log(Level.INFO, "Repeat Button was pressed.");
    						logger.log(Level.INFO, "Repeat Button was pressed {0} times", repeat);
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
     * Removes the Listener from the index of buttonList passed as argument. 
     * The index must be between 0 and buttonNumber.
     * 
     * @param index
     *            the index of the Listener to be removed.
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
            buttonList[index].removeAllListeners();
        } catch (UnsatisfiedLinkError e) {
        }
    }

}
