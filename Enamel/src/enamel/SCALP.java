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
	
    static private VoiceManager vm = VoiceManager.getInstance();
	static private Voice voice = vm.getVoice ("kevin16");
	
	//Right now, the directory to scan is hard-coded. We may eventually fix this with the current program directory.
	//Also, files AND directories are listed right now. Future implementation will list only files (using FilenameFilter)
	static File folder = new File("C:/Users/sunny/Documents/github/enamel/Enamel/SampleScenarios");	
	static List<String> listOfFiles = new LinkedList<String>(Arrays.asList(folder.list()));
	static List<File> listOfFilePaths = new LinkedList<File>(Arrays.asList(folder.listFiles()));
	static JFrame SFrame;
	static String current = "";
	static long debounce = 0;
	static ArrayList<HWButton> buttonList;
	
	public static void main(String[] args) {
		
		SFrame = new JFrame("SCALP");
		SFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SFrame.setVisible(true);
		
		voice.allocate();
		try { 
			Gpio.wiringPiSetup();
			HWButtonSelector();
			
		} catch (UnsatisfiedLinkError e) {
			System.out.println("The system does not support the hardware Braille cell. The program will ignore any code pertaining to the hardware Braille Cell.");
			keySelector();
		}
	}
	
	private static void HWButtonSelector() {
		//give some instructions.
		buttonList = new ArrayList<HWButton>();
		
		buttonList.add(new HWButton(4));
		buttonList.add(new HWButton(5));
		buttonList.add(new HWButton(6));
		buttonList.add(new HWButton(7));
		
		ListIterator<String> li = listOfFiles.listIterator();
			
		Gpio.wiringPiISR(buttonList.get(0).getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
			@Override
			public void callback(int pin) {
					if (System.currentTimeMillis() > debounce) {
						debounce = System.currentTimeMillis() + 1000L;
						current = li.previous();
			            voice.speak(current);
					}
				}	
		});
		Gpio.wiringPiISR(buttonList.get(1).getGPIOPinNumber(), Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {
			@Override
			public void callback(int pin) {
					if (System.currentTimeMillis() > debounce) {
						debounce = System.currentTimeMillis() + 1000L;
						current = li.next();
			            voice.speak(current);
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
						debounce = System.currentTimeMillis() + 1000L;
						current = li.previous();
			        	System.out.println("file " + current + " selected");
			        	removeHWButtons();
			        	startPlayer();
					}
				}	
		});
	}
	
	private static void removeHWButtons() {
		for (HWButton hwb : buttonList) {
			Gpio.wiringPiClearISR(hwb.getGPIOPinNumber());
		}
	}
	
	private static void keySelector() {
		//give some instructions.
		//Say name of file - "press spacebar to run this script"
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SFrame.addKeyListener(new KeyListener() {
					ListIterator<String> li = listOfFiles.listIterator();	
					@Override
					public void keyPressed(KeyEvent e) {
						switch (e.getKeyCode()){
				        case KeyEvent.VK_RIGHT :
				            current = li.next();
				            voice.speak(current);
				            System.out.println(current);
				            break;
				        case KeyEvent.VK_LEFT :
				        	current = li.previous();
				            voice.speak(current);
				            
				        	System.out.println(current);
				        	break;
				        case KeyEvent.VK_ENTER :
				        	System.out.println("exit without selecting");
				        	System.exit(0);
				        	break;
				        case KeyEvent.VK_SPACE :
				        	current = li.previous();
				        	System.out.println("file " + current + " selected");
				        	startPlayer();
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
	
	static void startPlayer() {
		SFrame.setVisible(false);
    	SFrame.dispose();
    	new Thread(new Runnable() {
    		public void run() {
    			ScenarioParser s = new ScenarioParser();
    			s.setScenarioFile("SampleScenarios/" + current);
    		}
    	}).start();
	}
}
