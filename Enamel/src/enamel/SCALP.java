package enamel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
//import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.LinkedList;
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
	
	static File factoryDirectory;
	static List<String> factoryDirectoryFiles;
	static File USBBufferDirectory;
	static List<String> USBBufferDirectoryFiles;

	static JFrame SFrame;	
	static long debounce = 0;
	static ArrayList<HWButton> buttonList;	
	
	public static void main(String[] args) {
		
		SFrame = new JFrame("SCALP");
		SFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SFrame.setVisible(true);
		
		String mode = args[0];
		
		voice.allocate();
				
		if (mode == "START_USB_WITH_FILE_1") {
			String startFilePath = args[1];
			startPlayer(startFilePath);
		}
		
		else if (mode == "START_FACTORY") {
			factoryDirectory = new File(args[1]);
			factoryDirectoryFiles = new LinkedList<String>(Arrays.asList(factoryDirectory.list()));
			
			try { 
				Gpio.wiringPiSetup();
				HWButtonSelector();
				
			} catch (UnsatisfiedLinkError e) {
				System.out.println("The system does not support the hardware Braille cell. The program will ignore any code pertaining to the hardware Braille Cell.");
				keySelector(factoryDirectory, factoryDirectoryFiles);
			}	
		}
		
		else if (mode == "START_HIGH_LEVEL_SELECTOR") {
			factoryDirectory = new File(args[1]);
			factoryDirectoryFiles = new LinkedList<String>(Arrays.asList(factoryDirectory.list()));
			USBBufferDirectory = new File(args[2]);
			USBBufferDirectoryFiles = new LinkedList<String>(Arrays.asList(USBBufferDirectory.list()));
			
			try { 
				Gpio.wiringPiSetup();
				HWButtonSelector();
				
			} catch (UnsatisfiedLinkError e) {
				System.out.println("The system does not support the hardware Braille cell. The program will ignore any code pertaining to the hardware Braille Cell.");
				highLevelSelector();
			}
		}
		
		else {
			System.out.println("Error occured within SCALP.");
		}
	}
	
	private static void highLevelSelector() {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SFrame.addKeyListener(new KeyListener() {
					@Override
					public void keyPressed(KeyEvent e) {
						switch (e.getKeyCode()){
				        case KeyEvent.VK_RIGHT :
				        	voice.speak("USB buffer directory selected");
				            System.out.println("USB buffer directory selected");
				            SFrame.removeKeyListener(this);
				            keySelector(USBBufferDirectory, USBBufferDirectoryFiles);
				            break;
				        case KeyEvent.VK_LEFT :
				        	voice.speak("Factory directory selected");
				            System.out.println("Factory directory selected");
				            SFrame.removeKeyListener(this);
				            keySelector(factoryDirectory, factoryDirectoryFiles);
				        	break;
				        case KeyEvent.VK_SPACE :
				        	System.out.println("exit without selecting");
				        	System.exit(0);
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
	
	private static void keySelector(File directory, List<String> directoryFiles) {
		//give some instructions.
		//Say name of file - "press spacebar to run this script"
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SFrame.addKeyListener(new KeyListener() {
					@Override
					public void keyPressed(KeyEvent e) {
						ListIterator<String> li = directoryFiles.listIterator();
						
						String currentFile;
						switch (e.getKeyCode()){
				        case KeyEvent.VK_LEFT :
				        	currentFile = li.previous();
				            voice.speak(currentFile);
				        	System.out.println(currentFile);
				            break;
				        case KeyEvent.VK_RIGHT :
				        	currentFile = li.next();
				            voice.speak(currentFile);
				            System.out.println(currentFile);
				        	break;
				        case KeyEvent.VK_ENTER :
				        	System.out.println("exit without selecting");
				        	System.exit(0);
				        	break;
				        case KeyEvent.VK_SPACE :
				        	currentFile = li.previous();
				        	System.out.println("file " + currentFile + " selected");
				            SFrame.removeKeyListener(this);
				        	startPlayer(directory.getAbsolutePath() + File.separator + currentFile);
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
	
	
	
	private static void HWButtonSelector() {
		//give some instructions.
		buttonList = new ArrayList<HWButton>();
		
		buttonList.add(new HWButton(4));
		buttonList.add(new HWButton(5));
		buttonList.add(new HWButton(6));
		buttonList.add(new HWButton(7));
		
		ListIterator<String> li = factoryDirectoryFiles.listIterator();
				
		Gpio.wiringPiISR(buttonList.get(0).getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
			@Override
			public void callback(int pin) {
					if (System.currentTimeMillis() > debounce) {
						debounce = System.currentTimeMillis() + 1000L;
			            voice.speak(li.previous());
					}
				}	
		});
		Gpio.wiringPiISR(buttonList.get(1).getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
			@Override
			public void callback(int pin) {
					if (System.currentTimeMillis() > debounce) {
						debounce = System.currentTimeMillis() + 1000L;
			            voice.speak(li.next());
					}
				}	
		});
		Gpio.wiringPiISR(buttonList.get(2).getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
			@Override
			public void callback(int pin) {
					if (System.currentTimeMillis() > debounce) {
						debounce = System.currentTimeMillis() + 1000L;
						System.out.println("exit without selecting");
			        	System.exit(0);
					}
				}	
		});
		Gpio.wiringPiISR(buttonList.get(3).getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
			@Override
			public void callback(int pin) {
					if (System.currentTimeMillis() > debounce) {
						String curFile = li.previous();
						debounce = System.currentTimeMillis() + 1000L;
						
			        	System.out.println("file " + curFile + " selected");
			        	removeHWButtons();
			        	startPlayer(curFile);
					}
				}	
		});
	}
	
	private static void removeHWButtons() {
		for (HWButton hwb : buttonList) {
			Gpio.wiringPiClearISR(hwb.getGPIOPinNumber());
		}
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
}
