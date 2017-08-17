package enamel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioInterruptCallback;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class SCALP {
    
    static VoiceManager vm = VoiceManager.getInstance();
    static Voice voice = vm.getVoice ("kevin16");
    
    static boolean isSpeaking = false;
    
    static File factoryDirectory;
    static String[] factoryDirectoryFiles;
    static File USBBufferDirectory;
    static String[] USBBufferDirectoryFiles;

    static JFrame SFrame;   
    static long debounce = 0;
    static HWButton[] buttonList;
   
    static boolean USBBufferDirectoryChosen = false;
    static boolean factoryDirectoryChosen = false;
    
    static int index = 0;
    
    static String[] toggleRange;
    static String[] sConfigParameters = new String[]{"one file auto play", "multi file auto copy", "U S B buffer size", "smart clobber"};
    static String[] aConfigParameters = new String[]{"onefileplay", "multifilecopy", "usbbuffersize", "smartclobber"};
    //static Thread runnableSpeak;
    
    
    public static void main(String[] args) {
               
        Gpio.wiringPiSetup();
        
        buttonList = new HWButton[]{new HWButton(4), new HWButton(5), new HWButton(6)};
        SFrame = new JFrame("SCALP");
        SFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SFrame.setVisible(true);
        voice.setRate(150f);
        voice.allocate();
        
        try {
            String mode = args[0];
                    
            if (mode == "START_USB_WITH_FILE_1") {
                try {
                    String startFilePath = args[1];
                    startPlayer(startFilePath);
                } catch (UnsatisfiedLinkError e) {
                    System.out.println("The system does not support the hardware Braille cell. The program will ignore any code pertaining to the hardware Braille Cell.");
                    String startFilePath = args[1];
                    startPlayer(startFilePath);
                }
                
            }
            
            else if (mode == "START_FACTORY") {
                factoryDirectory = new File(args[1]);
                factoryDirectoryFiles = factoryDirectory.list();
                
                try { 
                    Gpio.wiringPiSetup();
                    HWButtonSelector(factoryDirectory, factoryDirectoryFiles);
                    
                } catch (UnsatisfiedLinkError e) {
                    System.out.println("The system does not support the hardware Braille cell. The program will ignore any code pertaining to the hardware Braille Cell.");
                    keySelector(factoryDirectory, factoryDirectoryFiles);
                }   
            }
            
            else if (mode == "START_HIGH_LEVEL_SELECTOR") {
                factoryDirectory = new File(args[1]);
                factoryDirectoryFiles = factoryDirectory.list();
                USBBufferDirectory = new File(args[2]);
                USBBufferDirectoryFiles = USBBufferDirectory.list();
                
               try { 
                   Gpio.wiringPiSetup();
                   HWHighLevelSelector();
                    
               } catch (UnsatisfiedLinkError e) {
                    System.out.println("The system does not support the hardware Braille cell. The program will ignore any code pertaining to the hardware Braille Cell.");
                    highLevelSelector();
               }
            }
            
            else {
                System.out.println("Error occured within SCALP.");
            }
        }
        
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You did not provide any directories (as command line arguments)");
            System.exit(1);
        }
    }
    
    private static void highLevelSelector() {
        SwingUtilities.invokeLater(new Runnable() {
            
            public void run() {
                SFrame.addKeyListener(new KeyListener() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                    stop();
                        switch (e.getKeyCode()){
                        case KeyEvent.VK_1 :
                            stop();
                            System.out.println("USB buffer directory selected");
                            SFrame.removeKeyListener(this);
                            USBBufferDirectoryChosen = true;
                            keySelector(USBBufferDirectory, USBBufferDirectoryFiles);
                            break;
                        case KeyEvent.VK_2 :
                            stop();
                            System.out.println("Factory directory selected");
                            SFrame.removeKeyListener(this);
                            factoryDirectoryChosen = true;
                            keySelector(factoryDirectory, factoryDirectoryFiles);
                            break;
                        case KeyEvent.VK_3 :
                            SFrame.removeKeyListener(this);
                            settingsSelector("highLevelSelector");
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
        speak("You are in high level selector. button 1 for U S B Buffer Directory, button 2 for factory directory, or button 3 for Settings.");
    }
    
    private static void keySelector(File directory, String[] directoryFiles) {
        index = 0;
        String speakDirectory;
        System.out.println(directory.getName());
        if (directory.getName().equals("USBBuffer")) {
           speakDirectory = "U S B";
        }
        else {
           speakDirectory = directory.getName();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                SFrame.addKeyListener(new KeyListener() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        stop();
                        switch (e.getKeyCode()){
                        case KeyEvent.VK_1 :
                            if (index < directoryFiles.length - 1) {
                                index++;
                            }
                            else {
                                index = 0;
                            }
                            speak(directoryFiles[index]);
                            System.out.println(directoryFiles[index]);
                            break;
                            
                        case KeyEvent.VK_2 :
                            speak("starting file " + directoryFiles[index]);
                            System.out.println("starting file " + directoryFiles[index]);
                            SFrame.removeKeyListener(this);
                            startPlayer(directory.getAbsolutePath() + File.separator + directoryFiles[index]);
                            break;
                            
                        case KeyEvent.VK_3 :
                            SFrame.removeKeyListener(this);
                            settingsSelector("keySelector");
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
        speak("You are in " + speakDirectory + " directory.  button 1 for next file, button 2 to select current file, or button 3 for settings.");
        speak(directoryFiles[index] + " currently selected");
    }   
    
    private static void settingsSelector(String previous) {
        index = 0;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                SFrame.addKeyListener(new KeyListener() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        stop();
                        switch (e.getKeyCode()){
                        case KeyEvent.VK_1 :
                            if (index < sConfigParameters.length - 1) {
                                index++;
                            }
                            else {
                                index = 0;
                            }
                            speak(sConfigParameters[index]);
                            System.out.println(sConfigParameters[index]);
                            break;
                        case KeyEvent.VK_2 :
                            speak(sConfigParameters[index] + " selected");
                            System.out.println(sConfigParameters[index] + " selected");
                            SFrame.removeKeyListener(this);
                            configureSelector(sConfigParameters[index], previous);
                            break;
                        case KeyEvent.VK_3 :
                            SFrame.removeKeyListener(this);
                            speak("Previous menu");
                            if (previous.equals("keySelector")) {
                                if (USBBufferDirectoryChosen == true) {
                                    keySelector(USBBufferDirectory, USBBufferDirectoryFiles);
                                }
                                else if (factoryDirectoryChosen == true) {
                                    keySelector(factoryDirectory, factoryDirectoryFiles);
                                }
                            }
                            else {
                                highLevelSelector(); 
                            }
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
        speak("You are in Settings. button 1 to cycle through configs, button 2 to select config, button 3 for previous menu.");
        speak(sConfigParameters[index] + " currently selected.");
    }
    
    private static void configureSelector(String config, String previous) {    
        index = 0;
        if (config.equals("U S B buffer size")) {
            toggleRange = new String[]{"1", "5", "10", "15", "20"};
        }
        else {
            toggleRange = new String[]{"true", "false"};
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SFrame.addKeyListener(new KeyListener() {
                    
                    @Override
                    public void keyPressed(KeyEvent e) {
                        stop();
                        switch (e.getKeyCode()){
                        case KeyEvent.VK_1 :
                            if (index < toggleRange.length - 1) {
                                index++;
                            }
                            else {
                                index = 0;
                            }
                            speak(toggleRange[index]);
                            System.out.println(toggleRange[index]);
                            break;
                        case KeyEvent.VK_2 :
                            try {
                                ProcessBuilder p = new ProcessBuilder("/home/pi/changeconfig.sh", aConfigParameters[index], toggleRange[index]);
                                p.start();
                                speak("Changed " + config + "to " + toggleRange[index]);
                                System.out.println("Changed " + config + " to " + toggleRange[index]);
                            } catch (Exception ex) {System.out.println(ex);}
                            SFrame.removeKeyListener(this);
                            settingsSelector(previous);
                            break;
                        case KeyEvent.VK_3 :
                            SFrame.removeKeyListener(this);
                            speak("exit without saving");
                            settingsSelector(previous);
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
 
        speak("Button 1 to toggle, button 2 to accept and return, button 3 to exit without saving. Any changes will take effect after a restart.");
        //find a way to find current values from config.txt and speak it.
    }
    
    static void startPlayer(String path) {
        SFrame.setVisible(false);
        SFrame.dispose();
        new Thread(new Runnable() {
            public void run() {
                ScenarioParser s = new ScenarioParser();
                voice.deallocate();
                s.setScenarioFile(path);
            }
        }).start();
    }
    
    
    private static void HWHighLevelSelector() {
        speak("You are in high level selector. button 1 for U S B Buffer Directory, button 2 for factory directory, or button 3 for Settings.");
        Gpio.wiringPiISR(buttonList[0].getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
            @Override
            public void callback(int pin) {
                    if (System.currentTimeMillis() > debounce) {
                        debounce = System.currentTimeMillis() + 1000L;
                        System.out.println("USB buffer directory selected");
                        USBBufferDirectoryChosen = true;
                        removeHWButtons();
                        HWButtonSelector(USBBufferDirectory, USBBufferDirectoryFiles);
                    }
                }   
        });
        Gpio.wiringPiISR(buttonList[1].getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
            @Override
            public void callback(int pin) {
                    if (System.currentTimeMillis() > debounce) {
                        debounce = System.currentTimeMillis() + 1000L;
                        System.out.println("Factory directory selected");
                        factoryDirectoryChosen = true;
                        removeHWButtons();
                        HWButtonSelector(factoryDirectory, factoryDirectoryFiles);
                    }
                }   
        });
        Gpio.wiringPiISR(buttonList[2].getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
            @Override
            public void callback(int pin) {
                    if (System.currentTimeMillis() > debounce) {
                        debounce = System.currentTimeMillis() + 1000L;
                        removeHWButtons();
                        HWSettingsSelector("highLevelSelector");
                    }
                }   
        }); 
    }
    
    private static void HWButtonSelector(File directory, String[] directoryFiles) {
        index = 0;
        String speakDirectory;
        System.out.println(directory.getName());
        if (directory.getName().equals("USBBuffer")) {
           speakDirectory = "U S B";
        }
        else {
           speakDirectory = directory.getName();
        }
        
        speak("You are in " + speakDirectory + " directory.  button 1 for next file, button 2 to select current file, or button 3 for settings.");
        speak(directoryFiles[index] + " currently selected");
        
        Gpio.wiringPiISR(buttonList[0].getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
            @Override
            public void callback(int pin) {
                    if (System.currentTimeMillis() > debounce) {
                        debounce = System.currentTimeMillis() + 1000L;
                        if (index < directoryFiles.length - 1) {
                                index++;
                            }
                            else {
                                index = 0;
                            }
                        speak(directoryFiles[index]);
                        System.out.println(directoryFiles[index]);
                    }
                }   
        });
        Gpio.wiringPiISR(buttonList[1].getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
            @Override
            public void callback(int pin) {
                    if (System.currentTimeMillis() > debounce) {
                        debounce = System.currentTimeMillis() + 1000L;
                        speak("starting file " + directoryFiles[index]);
                        System.out.println("starting file " + directoryFiles[index]);
                        removeHWButtons();
                        startPlayer(directory.getAbsolutePath() + File.separator + directoryFiles[index]);
                    }
                }   
        });
        Gpio.wiringPiISR(buttonList[2].getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
            @Override
            public void callback(int pin) {
                    if (System.currentTimeMillis() > debounce) {
                        debounce = System.currentTimeMillis() + 1000L;
                        removeHWButtons();
                        settingsSelector("keySelector");
                    }
                }   
        });
    }   
    
    private static void HWSettingsSelector(String previous) {
        index = 0;
        speak("You are in Settings. button 1 to cycle through configs, button 2 to select config, button 3 for previous menu.");
        speak(sConfigParameters[index] + " currently selected.");
        Gpio.wiringPiISR(buttonList[0].getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
            @Override
            public void callback(int pin) {
                    if (System.currentTimeMillis() > debounce) {
                        debounce = System.currentTimeMillis() + 1000L;
                        if (index < sConfigParameters.length - 1) {
                            index++;
                        }
                        else {
                            index = 0;
                        }
                        speak(sConfigParameters[index]);
                        System.out.println(sConfigParameters[index]);
                    }
                }   
        });
        Gpio.wiringPiISR(buttonList[1].getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
            @Override
            public void callback(int pin) {
                    if (System.currentTimeMillis() > debounce) {
                        debounce = System.currentTimeMillis() + 1000L;
                        speak(sConfigParameters[index] + " selected");
                        System.out.println(sConfigParameters[index] + " selected");
                        removeHWButtons();
                        HWConfigureSelector(sConfigParameters[index], previous);
                    }
                }   
        });
        Gpio.wiringPiISR(buttonList[2].getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
            @Override
            public void callback(int pin) {
                    if (System.currentTimeMillis() > debounce) {
                        debounce = System.currentTimeMillis() + 1000L;
                        speak("Previous menu");
                        removeHWButtons();
                        if (previous.equals("keySelector")) {
                            if (USBBufferDirectoryChosen == true) {
                                HWButtonSelector(USBBufferDirectory, USBBufferDirectoryFiles);
                            }
                            else if (factoryDirectoryChosen == true) {
                                HWButtonSelector(factoryDirectory, factoryDirectoryFiles);
                            }
                        }
                        else {
                            HWHighLevelSelector(); 
                        }
                    }
                }   
        });
    }
    
    private static void HWConfigureSelector(String config, String previous) {    
        index = 0;
        if (config.equals("U S B buffer size")) {
            toggleRange = new String[]{"1", "5", "10", "15", "20"};
        }
        else {
            toggleRange = new String[]{"true", "false"};
        }
        speak("Button 1 to toggle, button 2 to accept and return, button 3 to exit without saving. Any changes will take effect after a restart.");
        Gpio.wiringPiISR(buttonList[0].getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
            @Override
            public void callback(int pin) {
                    if (System.currentTimeMillis() > debounce) {
                        debounce = System.currentTimeMillis() + 1000L;
                        if (index < toggleRange.length - 1) {
                            index++;
                        }
                        else {
                            index = 0;
                        }
                        speak(toggleRange[index]);
                        System.out.println(toggleRange[index]);
                    }
                }   
        });
        Gpio.wiringPiISR(buttonList[1].getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
            @Override
            public void callback(int pin) {
                    if (System.currentTimeMillis() > debounce) {
                        debounce = System.currentTimeMillis() + 1000L;
                        try {
                            ProcessBuilder p = new ProcessBuilder("/home/pi/changeconfig.sh", aConfigParameters[index], toggleRange[index]);
                            p.start();
                            speak("Changed " + config + "to " + toggleRange[index]);
                            System.out.println("Changed " + config + " to " + toggleRange[index]);
                        } catch (Exception ex) {System.out.println(ex);}
                        removeHWButtons();
                        HWSettingsSelector(previous);
                    }
                }   
        });
        Gpio.wiringPiISR(buttonList[2].getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
            @Override
            public void callback(int pin) {
                    if (System.currentTimeMillis() > debounce) {
                        debounce = System.currentTimeMillis() + 1000L;
                        removeHWButtons();
                        speak("exit without saving");
                        HWSettingsSelector(previous);
                    }
                }   
        });
    }
    
    private static void removeHWButtons() {
        for (HWButton hwb : buttonList) {
            Gpio.wiringPiClearISR(hwb.getGPIOPinNumber());
        }
    }
    
    static void speak(String message) {
       // new Thread(new Runnable() {
       //     public void run() {
                voice.speak(message);
       //     }
       // }).start();
    }
    
    private static void stop () {
       //     voice.getAudioPlayer().cancel();
       //     voice.getAudioPlayer().drain();
       //     voice.getAudioPlayer().reset();
    }
}
