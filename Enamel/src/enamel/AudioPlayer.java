package enamel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
/**
 * This class simulates the hardware for a device with physical Braille cells
 * and programmable buttons, but using audio feedback instead of a GUI to read out
 * the cells, and keyboard keys to simulate the buttons.
 * <p>
 * The constructor initializes the window and takes as arguments the number of
 * Braille Cells and the number of buttons the AudioPlayer should display. This
 * class also contains several methods that allow for the manipulation of the
 * AudioPlayer.
 * <p>
 * The individual Braille Cells can be accessed using the
 * <code> getCell(int)</code> method and the cell's index.
 * Similarly, the buttons can be accessed 
 * using the <code> getButton(int)</code> method, and they are indexed from left
 * to right. Both of these methods return the actual reference of the objects,
 *  meaning you can then call methods from each of the
 * object's respective classes to manipulate them. For example, you can call
 * <code> displayCharacter(char)</code> on any specific cell to set the audio to a
 * character on that cell. You can also, in a similar fashion, use the
 * <code> getButton(int) </code> method to get the reference of a specific
 * button. 
 * 
 * @author ENAMEL team: Sunjik Lee, Li Yin, Vassilios Tzerpos.
 *
 */
public class AudioPlayer extends Player {

    private VoiceManager vm = VoiceManager.getInstance();
    private Voice voice = vm.getVoice ("kevin");
    private Map<Integer, KeyListener> keyListenerList = new HashMap<Integer, KeyListener>();
    
    JFrame AFrame;

    public static final int ONE = 49;
    
    /**
     * Creates and displays a blank window, which later will be populated with
     * KeyListeners. The two parameters <code>brailleCellNumber</code> and <code>buttonNumber</code>
     * are used for auditory feedback and keyboard, and are not displayed graphically.
     * For the AudioPlayer class, it is assumed the number of <code>buttonNumber</code> is 
     * not greater than 10, as that is the available number of keys on a standard keyboard's number row. 
     * The two parameters must be positive integers.
     * 
     * @param brailleCellNumber
     *            the number of braille cells the AudioPlayer should have
     * @param buttonNumber
     *            the number of buttons the AudioPlayer should have
     * @throws IllegalArgumentException
     *             if one or both of the two parameters is negative or 0
     */
    public AudioPlayer(int brailleCellNumber, int buttonNumber) {
        super(brailleCellNumber, buttonNumber);
                
        AFrame = new JFrame();
        AFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AFrame.repaint();
        AFrame.setVisible(true);
        voice.allocate();
    }
    
    /**
     * Refreshes the "display" (audio) to match the current state of the 
     * instantiated BrailleCell object. For the AudioPlayer class,
     * this method forms a String that describes the current state of the Braille Cell object,
     * listing which of the 8 pins are currently up. If no pins are up, it will simply
     * state that no pins are up. The String is then spoken out loud to the user via
     * Voice.speak().
     */
    @Override
    public void refresh() {
        
        for (int j = 0; j < brailleList.size(); j++) {
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            
            int k = 0;
            for (int i = 0; i < brailleList.get(j).getNumberOfPins(); i++) {
                if (brailleList.get(j).getPinState(i)) {
                        k++;
                }
            }
            
            if (k == 0) {
                sb1.append("Braille Cell " + (j+1) + " cleared.");
            }
            
            else {
                sb1.append("For Braille Cell " + (j+1) + ", ");
                
                int l = 0;
                for (int i = 0; i < brailleList.get(j).getNumberOfPins(); i++) {
                    if (brailleList.get(j).getPinState(i)) {
                        l++;
                        if (l == 1 && l == k) {
                            sb2.append(i+1 + " ");
                        }
                        else if (l == k) {
                            sb2.append("and " + (i+1) + " ");
                        }
                        else {
                            sb2.append(i+1 + " ");
                        }
                    }
                }
                
                if (k == 0) {
                    sb1.append("no pins are up");
                }
                else if (k == 1) {
                    sb1.append("pin ");
                    sb1.append(sb2);
                    sb1.append("is up");
                }
                else {
                    sb1.append("pins ");
                    sb1.append(sb2);
                    sb1.append("are up");
                }
            }
            voice.speak(sb1.toString());
            System.out.println(sb1);
        }
    }
    
	/**
	* Adds a KeyListener to both keyListenerList at the specified index 
	* (for reference purposes) and to the JFrame (for  the key listening function). 
	* This index will be used to specify 
	* which keyboard KeyCode will need to be pressed to trigger the event.
	* The index must be between 0 and buttonNumber. Since it's assumed that
	* only the number row keys will be used, the index must also be between
	* 0 and 9.
	* <p>
	* The KeyCode for the keyboard key "1" is defined as the parameter <code>ONE</code>.
	* Since the rest of the number row keys are sequential in their KeyCode,
	* adding the KeyCode <code>ONE</code> by the index gives the correct key that matches the index.
	* <p>
	* The keyPressed method requires a parameter from the ScenarioParser class,
	* needed for it to call ScenarioParser.skip() method as well as have
	* access to the userInput field. Pressing this key will skip to the
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
    public void addSkipButtonListener(int index, String param, ScenarioParser sp) { 
        
        KeyListener button = new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == (index + ONE)) {
                    if (sp.userInput) {
                        sp.skip(param);
                        logger.log(Level.INFO, "Button {0} was pressed", index+1);
                        sp.userInput = false;
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e) {}
        };
        keyListenerList.put(index, button);
        AFrame.addKeyListener(button);
    }
    
    /**
     * Adds a KeyListener with the specified index passed as argument to
     * both keyListenerList (for reference purposes) and to the JFrame for 
     * the function. This index will be used to specify which keyboard KeyCode
     * will need to be pressed to trigger the event.
     * The index must be between 0 and buttonNumber. Since it's assumed that
     * only the number row keys will be used, the index must also be between
     * 0 and 9.
     * <p>
     * The KeyCode for the keyboard key "1" is defined as the parameter <code>ONE</code>.
     * Since the rest of the number row keys are sequential in their KeyCode,
     * adding the KeyCode <code>ONE</code> by the index gives the correct key that matches the index.
     * <p>
     * The KeyListener requires a parameter from the ScenarioParser class,
     * needed for it to call <code>ScenarioParser.repeatText()</code> method as well as have
     * access to the userInput field. Pressing this key will repeat the
     * speech of the text specified in the scenario file.
     * 
     * @param index
	 * 			the index of the button to add the KeyListener to
	 * @param sp
	 * 			the reference to the current ScenarioParser object
	 */
    @Override
    public void addRepeatButtonListener(int index, ScenarioParser sp) {
        KeyListener button = new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == index + '0') {
                    if (sp.userInput) {
                        repeat++;
						logger.log(Level.INFO, "Repeat Button was pressed.");
						logger.log(Level.INFO, "Repeat Button was pressed {0} times", repeat);
                        sp.repeatText();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e) {}
        };
        keyListenerList.put(index, button);
        AFrame.addKeyListener(button);
    }
    
    /**
     * Returns a reference to the key at the index passed as argument. The
     * main purpose of providing this method is so the client can add
     * KeyListeners to the frame. Buttons are numbered from left to right as
     * matching the physical keyboard's "1" to "0" keys on the number row, 
     * from 0 to (buttonNumber-1), buttonNumber
     * being the number of buttons initialized by the constructor (again, buttonNumber
     * is assumed to be smaller or equal to 10).
     * 
     * @param index
     *            the index of the button to be returned
     * @return reference to the KeyListener at the index passed as argument
     * @throws IllegalArgumentException
     *             if the index is negative or equal to or bigger than
     *             buttonNumber (the number of buttons initialized)
     */
    public KeyListener getButton(int index) {
        if (index >= this.buttonNumber || index < 0) {
            throw new IllegalArgumentException("Invalid button index.");
        }
        return this.keyListenerList.get(index);
    }
    
    /**
     * Removes the KeyListener at the specified index passed as argument. 
     * The index must be between 0 and buttonNumber.
     * 
     * @param index
     *            the index of the KeyListener to be removed.
     * @throws IllegalArgumentException
     *             if the index is negative or equal to or bigger than
     *             buttonNumber (the number of buttons initialized)
     */
    @Override
    public void removeButtonListener(int index) {
        if (index >= this.buttonNumber || index < 0) {
            throw new IllegalArgumentException("Invalid index.");
        }
        if (keyListenerList.size() > 0 && keyListenerList.containsKey(index)) {
            AFrame.removeKeyListener(getButton(index));
        }
    }
}
