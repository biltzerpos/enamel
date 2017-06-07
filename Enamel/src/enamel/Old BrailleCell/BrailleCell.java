package src.enamel;
import com.pi4j.wiringpi.Gpio;
import java.util.HashMap;

import javax.swing.JRadioButton;
import java.util.*;
/**
 * This class implements a Braille Cell with 8 pins. The class contains methods
 * for displaying letters on the cell
 * as well as raising and/or lowering individual pins.
 * <p>
 * This class supports the standard Braille encoding of English characters plus the space character using
 * the <code> displayCharacter()</code> method. For any non-standard characters or ones
 * that aren't included by default, use the <code> setPins()</code> method,
 * which takes a string of 1s and 0s as argument and sets the pins accordingly.
 * 1 meaning raised and 0 meaning lowered.
 * 
 * The constructor of this class is not public. Objects are created when a 
 * <code> Simulator</code> object is created. Use the <code> getCell</code> method of class 
 * <code> Simulator</code> to obtain references to individual <code> BrailleCell</code> objects.
 * 
 * @author Team 4: Yassin Mohamed, Qassim Allauddin, Derek Li, Artem Solovey.
 *
 */
public class BrailleCell {

    JRadioButton radio1x1;
    JRadioButton radio1x2;
    JRadioButton radio2x1;
    JRadioButton radio2x2;
    JRadioButton radio3x1;
    JRadioButton radio3x2;
    JRadioButton radio4x1;
    JRadioButton radio4x2;
    
    private static HashMap<Character, String> alphabet = new HashMap<Character, String>();

    int ON = 0;
    int DOUT = 1;
    int STROBE = 2;
    int CLOCK = 3;
    
    // Capital X Y Z missing pin 7 raise. Fix.
    byte brailleMap[] = {
            (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    //   0 to 7
            (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    //   8 to 15
            (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    //  16 to 23
            (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    //  24 to 31
            (byte)255,  (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    //  32 to 39 *** Added 255 to ASCII 32, raise all pins on a ' ' blank character
            (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    //  40 to 47
            (byte)60,   (byte)132,  (byte)196,  (byte)148,  (byte)132,  (byte)212,  (byte)220,  (byte)204,  //  48 to 55
            (byte)84,   (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    //  56 to 63
            (byte)0,    (byte)130,  (byte)194,  (byte)146,  (byte)154,  (byte)138,  (byte)210,  (byte)218,  //  64 to 71
            (byte)202,  (byte)82,   (byte)90,   (byte)162,  (byte)226,  (byte)178,  (byte)186,  (byte)170,  //  72 to 79
            (byte)242,  (byte)250,  (byte)234,  (byte)114,  (byte)122,  (byte)166,  (byte)230,  (byte)94,   //  80 to 87
            (byte)182,  (byte)190,  (byte)174,  (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0,    //  88 to 95
            (byte)0,    (byte)128,  (byte)192,  (byte)144,  (byte)152,  (byte)136,  (byte)208,  (byte)216,  //  96 to 103
            (byte)200,  (byte)80,   (byte)88,   (byte)160,  (byte)224,  (byte)176,  (byte)184,  (byte)168,  // 104 to 111
            (byte)240,  (byte)248,  (byte)232,  (byte)112,  (byte)120,  (byte)164,  (byte)228,  (byte)92,   // 112 to 119
            (byte)180,  (byte)188,  (byte)172,  (byte)0,    (byte)0,    (byte)0,    (byte)0,    (byte)0     // 120 to 127
        };

    private void initializeAlphabet() {
            	// new alphabet - matches the hardware B11 braille cell layout
        alphabet.put('a', "10000000");
        alphabet.put('b', "11000000");
        alphabet.put('c', "10100000");
        alphabet.put('d', "10011000");
        alphabet.put('e', "10001000");
        alphabet.put('f', "11010000");
        alphabet.put('g', "11011000");
        alphabet.put('h', "11001000");
        alphabet.put('i', "01010000");
        alphabet.put('j', "01011000");
        alphabet.put('k', "10100000");
        alphabet.put('l', "11100000");
        alphabet.put('m', "10110000");
        alphabet.put('n', "10111000");
        alphabet.put('o', "10101000");
        alphabet.put('p', "11110000");
        alphabet.put('q', "11111000");
        alphabet.put('r', "11101000");
        alphabet.put('s', "01110000");
        alphabet.put('t', "01111000");
        alphabet.put('u', "10100100");
        alphabet.put('v', "11100100");
        alphabet.put('w', "01011100");
        alphabet.put('x', "10110100");
        alphabet.put('y', "10111100");
        alphabet.put('z', "10101100");
        alphabet.put(' ', "11111111");
    
        /*        alphabet.put('a', "10000000");
        alphabet.put('b', "10100000");
        alphabet.put('c', "11000000");
        alphabet.put('d', "11010000");
        alphabet.put('e', "10010000");
        alphabet.put('f', "11100000");
        alphabet.put('g', "11110000");
        alphabet.put('h', "10110000");
        alphabet.put('i', "01100000");
        alphabet.put('j', "01110000");
        alphabet.put('k', "10001000");
        alphabet.put('l', "10101000");
        alphabet.put('m', "11001000");
        alphabet.put('n', "11011000");
        alphabet.put('o', "10011000");
        alphabet.put('p', "11101000");
        alphabet.put('q', "11111000");
        alphabet.put('r', "10111000");
        alphabet.put('s', "01101000");
        alphabet.put('t', "01111000");
        alphabet.put('u', "10001100");
        alphabet.put('v', "10101100");
        alphabet.put('w', "01110100");
        alphabet.put('x', "11001100");
        alphabet.put('y', "11011100");
        alphabet.put('z', "10011100");
        alphabet.put(' ', "11111111");
*/    
        //Metec Braille Cell setup

        Gpio.pinMode(ON, Gpio.OUTPUT);
        Gpio.pinMode(DOUT, Gpio.OUTPUT);
        Gpio.pinMode(STROBE, Gpio.OUTPUT);
        Gpio.pinMode(CLOCK, Gpio.OUTPUT);
        Gpio.digitalWrite(ON, 1);
        Gpio.delay(3000);
        Gpio.digitalWrite(ON, 0);
        clearBrailleCell();
    }

    byte mapToBraille(byte in)
    {
        return brailleMap[in];
    }

    BrailleCell(JRadioButton radio1x1, JRadioButton radio1x2, JRadioButton radio2x1, JRadioButton radio2x2,
    JRadioButton radio3x1, JRadioButton radio3x2, JRadioButton radio4x1, JRadioButton radio4x2) {
        
        this.initializeAlphabet();
        this.radio1x1 = radio1x1;
        this.radio1x2 = radio1x2;
        this.radio2x1 = radio2x1;
        this.radio2x2 = radio2x2;
        this.radio3x1 = radio3x1;
        this.radio3x2 = radio3x2;
        this.radio4x1 = radio4x1;
        this.radio4x2 = radio4x2;
    }

    public void displayCharacter(char a) throws InterruptedException {

        a = Character.toLowerCase(a);
        if (!alphabet.containsKey(a)) {
            throw new IllegalArgumentException("Non standard character");
        }
        this.setPins(alphabet.get(a)); //GUI's set pins. Once we don't need GUI anymore, remove it.
        this.cellWrite((byte) a); //B11 Braille Cell
    }
    
    /* The next 4 methods = GUI stuff. 
     * Remove once we don't need it anymore. */
    public void setPins(String pins) {
        //checking for correct index
        if (pins.length() != 8) {
            throw new IllegalArgumentException("Illegal string passed, length > or < 8.");
        }
        for (int i = 0; i <= 7; i++) {
            if (pins.charAt(i) != '0' && pins.charAt(i) != '1') {
                throw new IllegalArgumentException(
                    "Invalid string passed, non-binary character detected at index:" + i + ".");
            }
        }

        this.clear();
        for (int i = 0; i <= 7; i++) {
            if (pins.charAt(i) == '1') {
                raiseOnePin(i + 1);
            }
        }
    }
    
        public void raiseOnePin(int pin) {
        if (pin < 1 || pin > 8) {
            throw new IllegalArgumentException("Invalid index");
        }
        switch (pin) {
            case 1:
            radio1x1.setSelected(true);
            break;
            case 2:
            radio2x1.setSelected(true);
            break;
            case 3:
            radio3x1.setSelected(true);
            break;
            case 4:
            radio1x2.setSelected(true);
            break;
            case 5:
            radio2x2.setSelected(true);            
            break;
            case 6:
            radio3x2.setSelected(true);
            break;
            case 7:
            radio4x1.setSelected(true);
            break;
            case 8:
            radio4x2.setSelected(true);
            break;
            default:
            break;

        }
    }
    
    //GUI
    public void lowerOnePin(int pin) {
        if (pin < 1 || pin > 8) {
            throw new IllegalArgumentException("Invalid index");
        }
        switch (pin) {
            case 1:
            radio1x1.setSelected(false);
            break;
            case 2:
            radio2x1.setSelected(false);
            break;
            case 3:
            radio3x1.setSelected(false);
            break;
            case 4:
            radio1x2.setSelected(false);
            break;
            case 5:
            radio2x2.setSelected(false);            
            break;
            case 6:
            radio3x2.setSelected(false);
            break;
            case 7:
            radio4x1.setSelected(false);
            break;
            case 8:
            radio4x2.setSelected(false);
            break;
            default:
            break;
        }
    }
    //GUI
    /*
    public void raiseOnePin(int pin) {
        if (pin < 1 || pin > 8) {
            throw new IllegalArgumentException("Invalid index");
        }
        switch (pin) {
            case 1:
            radio1x1.setSelected(true);
            break;
            case 2:
            radio1x2.setSelected(true);
            break;
            case 3:
            radio2x1.setSelected(true);
            break;
            case 4:
            radio2x2.setSelected(true);
            break;
            case 5:
            radio3x1.setSelected(true);
            break;
            case 6:
            radio3x2.setSelected(true);
            break;
            case 7:
            radio4x1.setSelected(true);
            break;
            case 8:
            radio4x2.setSelected(true);
            break;
            default:
            break;

        }
    }
    
    //GUI
    public void lowerOnePin(int pin) {
        if (pin < 1 || pin > 8) {
            throw new IllegalArgumentException("Invalid index");
        }
        switch (pin) {
            case 1:
            radio1x1.setSelected(false);
            break;
            case 2:
            radio1x2.setSelected(false);
            break;
            case 3:
            radio2x1.setSelected(false);
            break;
            case 4:
            radio2x2.setSelected(false);
            break;
            case 5:
            radio3x1.setSelected(false);
            break;
            case 6:
            radio3x2.setSelected(false);
            break;
            case 7:
            radio4x1.setSelected(false);
            break;
            case 8:
            radio4x2.setSelected(false);
            break;
            default:
            break;
        }
    }
*/
    //GUI
    public void clear() {
        radio1x1.setSelected(false);
        radio1x2.setSelected(false);
        radio2x1.setSelected(false);
        radio2x2.setSelected(false);
        radio3x1.setSelected(false);
        radio3x2.setSelected(false);
        radio4x1.setSelected(false);
        radio4x2.setSelected(false);
        clearBrailleCell();
    }
    
    /* Next 2 methods are for the Metec B11 Braille cell.
     * Should be the only methods needed.
     */
    void cellWrite(byte pins) // send and read from cell
    {
        byte converted;
        if (pins == 0) {
            converted = (byte)255;
        }
        else {
            converted = mapToBraille(pins);
        }      
        Gpio.digitalWrite(STROBE, 0);
        for (int i = 0; i < 8; i++) { 
            int currBit = ((converted >> i) & 1);
            Gpio.digitalWrite(CLOCK, 0);
            Gpio.digitalWrite(DOUT, currBit);
            Gpio.delay(1);
            Gpio.digitalWrite(CLOCK, 1);
        }
        Gpio.digitalWrite(STROBE, 1);
        Gpio.delay(200);
    }
    
   void raisePins(List<Integer> pins) {
        boolean currPin = false;
        Gpio.digitalWrite(STROBE, 0);
        
        for (int i = 8; i >= 1; i--) {
            if (pins.contains(i) == true) {
                currPin = true;
            }
            else {
                currPin = false;
            }   
            Gpio.digitalWrite(CLOCK, 0);
            if (currPin == true) {
                Gpio.digitalWrite(DOUT, 1);
            }
            else {
                Gpio.digitalWrite(DOUT, 0);
            }
            Gpio.delay(1);
            Gpio.digitalWrite(CLOCK, 1);
        }
        Gpio.digitalWrite(STROBE, 1);
        Gpio.delay(200);
    }
    
    public void clearBrailleCell() {
        this.cellWrite((byte)0);
    }
}
