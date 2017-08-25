package enamel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;


import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
/**
 * This class allows the user to select a scenario file to be used for
 * ScenarioParser to parse and play the text and commands. This class
 * also allows the user to change "config.txt", a configuration file
 * that the SCALP.sh script file uses. The SCALP.sh file starts this program
 * passing on the arguments that the <code>main(String[])</code> method uses
 * to start the various control flows.
 * <p>
 * The main method creates an instance of SCALP, and creates an empty
 * JFrame to be used for KeyListeners. This allows the user to use the
 * keyboard to simulate hardware button input (for example, testing purposes,
 * or no access to a Raspberry Pi with hardware buttons). This class
 * also allows the use of hardware buttons for input, if the system is
 * a Raspberry Pi with the valid GPIO (general purpose in/out) libraries installed.
 * This check is done with the method call <code>Gpio.wiringPiSetup()</code>, which
 * will fail (throw an exception) if the GPIO pins are not available. Then,
 * the system will progress with KeyListeners instead of GPIO hardware buttons.
 * <p>
 * The <code>String state</code> field controls all the logic of the program. Depending on
 * the current state, a button press will do specific things. For example, if the 
 * state is currently "highLevelSelector", pressing button 1 will select USB Buffer Directory,
 * and change the state to "fileSelector". Then, pressing button 1 will cycle through the files
 * within the USB Buffer Directory. This allows for three methods that handle the logic behind
 * the 3 buttons, <code>buttonOne()</code>, <code>buttonTwo()</code>, and <code>buttonThree()</code>,
 * and any new method of button input can simply call these three methods. For example, 
 * <code>keySelector()</code> and <code>hardwareSelector()</code> contain the exact same method calls,
 * but of course different code to listen to events (KeyListener vs. GPIO interrupt). This 
 * allows for significant code reuse. the <code>speakState()</code> method similarly uses
 * <code>String state</code> to determine which speech to speak out loud to the user. 
 * <p>
 * The states "settingsSelector" and "configSelector" allow for changing of "config.txt". 
 * These states allow for the user to cancel at any time, immediately going back to whatever method
 * they were in when the settings button was pressed. Or, changing a setting will also
 * go into the previous menu, bringing the user back to the "highLevelSelector" or "fileSelector".
 * 
 * @author Sunjik Lee, Li Yin, Vassilios Tzerpos.
 *
 */
public class SCALP {
    //FreeTTS voices
    VoiceManager vm = VoiceManager.getInstance();
    Voice voice = vm.getVoice ("kevin16"); 
    
    JFrame frame;   
    
    //Attributes for directories. Due to the fact that the program needs both the absolute path, but
    //also need just the current file name, the String[] holds just the names of the files in a list.
    //This need comes from the fact that speaking using FreeTTS, speaking the absolute path every time
    //will be very time-consuming and annoying for the user.
    File factoryDirectory;
    String[] factoryDirectoryFiles;
    File USBBufferDirectory;
    String[] USBBufferDirectoryFiles;
    File currentDirectory;
    String[] currentDirectoryFiles;
    String currentFile;
    
    //Used for the various looping of files list, settings list, configurations option list
    int index = 0;
    
    //Settings configuration-specific fields. Again, need a String[] for the values, as well as one
    //for speaking it out loud. The array with whitespace between words is for speaking, the array
    //without whitespace is for the actual changing of the config.txt file. 
    String[] toggleRange;
    String[] sConfigParameters = new String[]{"one file auto play", "multi file auto copy", "U S B buffer size", "smart clobber"};
    String[] aConfigParameters = new String[]{"onefileplay", "multifilecopy", "usbbuffersize", "smartclobber"};    
    
    //The program uses state for control flow, i.e. what state the program is currently in.
    String state = "";

    //The current configuration parameter selected.
    String config; 
    
    //Needed to remember what the previous state was. 
    String previous;
    
    boolean hardwareAvailable;
    
    //Used to control speakState(), and when it's appropriate to speak the instructions or
    //not, due to the fact that not every time a user presses a button does the instructions
    //need to be spoken. 
    boolean speakInstructions = true;
    
    //Hardware button list.
    GpioPinDigitalInput[] buttonList;
    GpioController gpio;
    
    public static void main(String[] args) {

        //Create instance of this class and allocate the voice.
        SCALP main = new SCALP();
        main.voice.setRate(150f);
        main.voice.allocate();
        
        //Ensure the arguments are not empty. 
        if (args.length == 0) {
            System.out.println("Arguments are empty: please read the documentation on how to start this program with the proper arguments.");
            System.exit(1);
        }

        //Determine if Gpio is available, i.e. the program is being run on a Raspberry Pi. 
        //If not, run the system using KeyListeners as input.
        try {
            main.gpio = GpioFactory.getInstance();
            main.buttonList = new GpioPinDigitalInput[3];
           //Create 3 buttons. 
            for (int i = 0; i < 3; i++) {
                GpioPinDigitalInput button = main.gpio.provisionDigitalInputPin(RaspiPin.getPinByAddress(i+4), PinPullResistance.PULL_DOWN);
                button.setDebounce(1000);
                main.buttonList[i] = button;
            }
            main.hardwareAvailable = true;
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Hardware buttons not found. Use the keyboard number row keys.");
            main.frame = new JFrame("SCALP");
            main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            main.frame.setVisible(true);
            main.hardwareAvailable = false;
        }
        
        //Filters out any non-scenario files, such as the audio folders, or any other folders
        //that may be in the directory.
        //THIS ASSUMES THE SCENARIO FILES ARE IN .txt FORMAT. If this was to be changed later,
        //update this filter accordingly, or any String[] array of files will not contain
        //any scenarios!
        FilenameFilter scenariosOnly = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                return lowercaseName.endsWith(".txt");      
                }           
        };
        
        //Start the selector depending on the mode.
        //Three modes are available: starting with file 1, start factory, and start high level selector.
        //A mode is determined through the arguments passed when starting the program. 
        //If the argument is invalid, whether its the incorrect mode (the first argument), 
        //or incorrect number of paths (second and third arguments),
        //then print a message and close the program. 
        String mode = args[0];
       
        try {
            if (mode.equals("START_USB_WITH_FILE_1")) {
                main.state = "confirmStartPlayer";
                
                main.currentFile = args[1];
                
                if (main.hardwareAvailable) {
                    main.hardwareSelector();
                }
                else {
                    main.keySelector();
                }
            }
        
            else if (mode.equals("START_FACTORY")) {
                main.state = "fileSelector";
                main.currentDirectory = new File(args[1]);
                
                main.currentDirectoryFiles = main.currentDirectory.list(scenariosOnly);
            
                if (main.hardwareAvailable) {
                    main.hardwareSelector();
                }
                else {
                    main.keySelector();
                }
            }
        
            else if (mode.equals("START_HIGH_LEVEL_SELECTOR")) {
                main.state = "highLevelSelector";
                main.factoryDirectory = new File(args[1]);
                main.factoryDirectoryFiles = main.factoryDirectory.list(scenariosOnly);
                main.USBBufferDirectory = new File(args[2]);
                main.USBBufferDirectoryFiles = main.USBBufferDirectory.list(scenariosOnly);
                
                if (main.hardwareAvailable) {
                    main.hardwareSelector();
                }
                else {
                    main.keySelector();
                }
            }
            
            else {
                System.out.println("Error occured within SCALP: invalid mode entered.");
                System.exit(1);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Something went wrong in the program. The arguments were entered incorrectly: "
                    + "the first argument (the mode) may or may not be correct, but the arguments following"
                    + "the mode are incorrect. Please check the documentation and the arguments and try again");
        }
    }
    
    /**
     * This method adds a KeyListener to the dummy frame, and
     * this KeyListener responds to the number row's "1", "2", and "3" keys.
     * The functions of the keyPressed event have been delegated to <code>buttonOne()</code>,
     * <code>buttonTwo()</code>, and <code>buttonThree()</code> methods, as well as <code>speakState()</code>.
     * This was done so that the code for what happens when a particular button is pressed at a particular time
     * can be reused for any type of input, whether it be a KeyListener, a JButton ActionListener, or
     * the GPIO hardware buttons.
     */
    private  void keySelector() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                speakState();
                frame.addKeyListener(new KeyListener() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        switch (e.getKeyCode()){
                        case KeyEvent.VK_1 :
                            buttonOne();
                            speakState();
                            break;
                        case KeyEvent.VK_2 :
                            buttonTwo();
                            speakState();
                            break;
                        case KeyEvent.VK_3 :
                            buttonThree();
                            speakState();
                            break;
                        }
                    }
                    @Override
                    public void keyReleased(KeyEvent e) {}
                    @Override
                    public void keyTyped(KeyEvent e) {}
                });
            }
        });    
    }
    
    /**
     * This method creates an ISR, which listens for interrupts on the specified GPIO pin.
     * The functions of the callback event have been delegated to <code>buttonOne()</code>,
     * <code>buttonTwo()</code>, and <code>buttonThree()</code> methods, as well as <code>speakState()</code>.
     * This was done so that the code for what happens when a particular button is pressed at a particular time
     * can be reused for any type of input, whether it be a KeyListener, a JButton ActionListener, or
     * the GPIO hardware buttons.
     * <p>
     * A <code>debounce</code> field prevents the button from being pressed in rapid succession, ensuring
     * that only after a full second will a secondary press be registered.
     */
    private void hardwareSelector() {
        speakState();
        buttonList[0].addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent arg0) {
                if (arg0.getState() == PinState.HIGH) {
                    buttonOne();
                    speakState();
                }
            }   
        });
        buttonList[1].addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent arg0) {
                if (arg0.getState() == PinState.HIGH) {
                    buttonTwo();
                    speakState();
                }
            }   
        });
        buttonList[2].addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent arg0) {
                if (arg0.getState() == PinState.HIGH) {
                    buttonThree();
                    speakState();
                }
            }   
        });
    }
    
    /**
     * This method is responsible for speaking to the user the current state of the program:
     * where they are in the program (which selector they are in) and what options they have (what each
     * button does). A String <code>state</code> field is responsible for determining what
     * state the program is in, and is changed in the button action methods.
     * Depending on the state of <code>speakInstructions</code>, this method will either speak the 
     * instructions or simply return without doing anything. As <code>index</code> is reset to 0 when
     * instructions are spoken, and only when the user enters another state will <code>speakInstructions</code>
     * be set to true, this allows for the index to be reset at the correct time.
     */
    private void speakState() {
        if (speakInstructions) {
            index = 0;
            if (state.equals("highLevelSelector")) {
                speak("You are in high level selector. button 1 for U S B Buffer Directory, button 2 for factory directory, or button 3 for Settings.");
            }
            
            else if (state.equals("fileSelector")) {
                String speakDirectory;
                System.out.println(currentDirectory.getName());
                if (currentDirectory.getName().equals("USBBuffer")) {
                   speakDirectory = "U S B";
                }
                else {
                   speakDirectory = currentDirectory.getName();
                }
                speak("You are in " + speakDirectory + " directory.  button 1 for next file, button 2 to select current file, or button 3 for settings.");
                speak(currentDirectoryFiles[index] + " currently selected");
                System.out.println(currentDirectoryFiles[index] + " currently selected");
            }
            
            else if (state.equals("settingsSelector")) {
                speak("You are in Settings. button 1 to cycle through configs, button 2 to select config, button 3 for previous menu.");
                speak(sConfigParameters[index] + " currently selected.");
                System.out.println(sConfigParameters[index] + " currently selected.");

            }
            
            else if (state.equals("configSelector")) {
                if (config.equals("U S B buffer size")) {
                    toggleRange = new String[]{"1", "5", "10", "15", "20"};
                }
                else {
                    toggleRange = new String[]{"true", "false"};
                }
                speak("Button 1 to toggle, button 2 to accept and return, button 3 to exit without saving. Any changes will take effect after a restart.");
            }
            else if (state.equals("confirmStartPlayer")) {
                speak("The file " + currentFile + " is loaded. Press button 1 to start, or button 3 for settings.");
            }
        }
    }
    
    /**
     * This method handles the logic for the third button. 
     * For the states, the behaviour is as follows:
     * highLevelSelector - Selects the USB Buffer directory by setting currentDirectory and currentDirectoryFiles
     *                      to USBBufferDirectory and USBBufferDirectoryFiles. Changes state to fileSelector. 
     * fileSelector - Loops through currentDirectoryFiles list. If at the end of the list, start again from the beginning.
     *                  Speaks the current file as well.
     * settingsSelector - Loops through settings parameters, with the circular loop, and speaks the current settings parameter.
     * configSelector - Loops through configuration parameters ("true"/"false" or "5", "10", "15", "20" depending on which
     *                  settings parameter is selected), again looping and speaking out loud the current configuration parameter.
     * confirmStartPlayer - Starts the current file (only used for the START_USB_WITH_FILE_1 mode). This is needed so that
     *                  the user still has a chance to enter settings even with the START_USB_WITH_FILE_1 selected; allows
     *                  the user to confirm before starting the file. 
     */
    private void buttonOne() {
        speakInstructions = false;
        if (state.equals("highLevelSelector")) {
            speakInstructions = true;
            System.out.println("USB buffer directory selected");
            currentDirectory = USBBufferDirectory;
            currentDirectoryFiles = USBBufferDirectoryFiles;
            state = "fileSelector";
        }
        else if (state.equals("fileSelector")) {
            if (index < currentDirectoryFiles.length - 1) {
                index++;
            }
            else {
                index = 0;
            }
            speak(currentDirectoryFiles[index]);
            System.out.println(currentDirectoryFiles[index]);
        }
        else if (state.equals("settingsSelector")) {
            if (index < sConfigParameters.length - 1) {
                index++;
            }
            else {
                index = 0;
            }
            speak(sConfigParameters[index]);
            System.out.println(sConfigParameters[index]);
        }
        else if (state.equals("configSelector")) {
            if (index < toggleRange.length - 1) {
                index++;
            }
            else {
                index = 0;
            }
            speak(toggleRange[index]);
            System.out.println(toggleRange[index]);
        }
        else if (state.equals("confirmStartPlayer")) {
            speak("starting file");
            System.out.println("starting file");
            startPlayer(currentFile);
        }
    }
    
    /**
     * This method handles the logic for the third button. 
     * For the states, the behaviour is as follows:
     * highLevelSelector - Selects the factory directory by setting currentDirectory and currentDirectoryFiles
     *                      to factoryDirectory and factoryDirectoryFiles. Changes state to fileSelector.
     * fileSelector - Starts the currently selected file from currentDirectoryFiles; selection takes place using button 1.
     * settingsSelector - Selects the currently selected settings parameter by setting <code>config</code> 
     *                      to the current sConfigParameters[index]. Changes state to configSelector.
     * configSelector - Creates a new ProcessBuilder that starts the shell script "changeconfig.sh", which will change
     *                  "config.txt". Note, currently it is a hard-coded directory, and since it is a shell script, this
     *                  will not work with Windows systems. Meant to be used only on a properly set up Rasberry Pi. 
     *                  Speaks the change, and sets the state to settingsSelector.
     * confirmStartPlayer - Not present; button 2 does not have any effect in the state confirmStartPlayer.
     */
    private void buttonTwo() {
        speakInstructions = true;
        if (state.equals("highLevelSelector")) {
            System.out.println("Factory directory selected");
            currentDirectory = factoryDirectory;
            currentDirectoryFiles = factoryDirectoryFiles;
            state = "fileSelector";
        }
        else if (state.equals("fileSelector")) {
            speakInstructions = false;
            speak("starting file " + currentDirectoryFiles[index]);
            System.out.println("starting file " + currentDirectoryFiles[index]);
            startPlayer(currentDirectory.getAbsolutePath() + File.separator + currentDirectoryFiles[index]);
        }
        else if (state.equals("settingsSelector")) {
            speak(sConfigParameters[index] + " selected");
            System.out.println(sConfigParameters[index] + " selected");
            config = sConfigParameters[index];
            state = "configSelector";
        }
        else if (state.equals("configSelector")) {
            try {
                ProcessBuilder p = new ProcessBuilder("/home/pi/changeconfig.sh", aConfigParameters[index], toggleRange[index]);
                p.start();
                speak("Changed " + config + "to " + toggleRange[index]);
                System.out.println("Changed " + config + " to " + toggleRange[index]);
            } catch (Exception ex) {System.out.println(ex);}
            state = "settingsSelector";
        }
    }
    
    /**
     * This method handles the logic for the third button. 
     * For the states, the behaviour is as follows:
     * highLevelSelector - Sets the state to settingsSelector, while setting previous to highLevelSelector.
                            This allows the user to return to the previous menu through the <code>previous</code> field.
     * fileSelector - Same behaviour as highLevelSelector, but sets previous to fileSelector instead.
     * settingsSelector - Sets the state to previous, to allow the program to reenter the previous state.
     * configSelector - Sets the state ot settingsSelector; effectively, "exiting" the configuration selector state without saving.
     * confirmStartPlayer - Same behaviour as fileSelector and highLevelSelector, but sets previous to confirmStartPlayer.
     */
    private void buttonThree() {
        speakInstructions = true;
        if (state.equals("highLevelSelector")) {
            state = "settingsSelector";
            previous = "highLevelSelector";
        }
        else if (state.equals("fileSelector")) {
            state = "settingsSelector";
            previous = "fileSelector";
        }
        else if (state.equals("settingsSelector")) {
            speak("Previous menu");
            state = previous;
        }
        else if (state.equals("configSelector")) {
            speak("exit without saving");
            state = "settingsSelector";
        }
        else if (state.equals("confirmStartPlayer")) {
            state = "settingsSelector";
            previous = "confirmStartPlayer";
        }
    }      
    
    /**
     * This method clears any SCALP-related components (JFrame, hardware button ISRs)
     * before creating an instance of ScenarioParser and starting the player.
     * Starting the player is done in a new thread.
     * 
     * @param path
     *          the absolute path of the scenario file to start the ScenarioParser with 
     */
    void startPlayer(String path) {
        if (hardwareAvailable) {
            removeHWButtons();
        }
        else {
            frame.setVisible(false);
            frame.dispose();
        }
        new Thread(new Runnable() {
            public void run() {
                ScenarioParser s = new ScenarioParser();
                voice.deallocate();
                s.setScenarioFile(path);
            }
        }).start();
    }
    
    /**
     * This method removes the hardware buttons' listeners. 
     * Loops through and removes all the listeners from the buttons in 
     * <code>buttonList</code>.
     */
    private void removeHWButtons() {
        for (int i = 0; i < buttonList.length; i++) {
            buttonList[index].removeAllListeners();
            gpio.shutdown();
            //gpio.unprovisionPin(buttonList[index]);
        }
    }
    
    /**
     * A method for speaking out loud the String passed as argument,
     * using FreeTTS.
     * 
     * @param message
     *          the message to be spoken out loud to the user
     */
     void speak(String message) {
       voice.speak(message);
    }
}
