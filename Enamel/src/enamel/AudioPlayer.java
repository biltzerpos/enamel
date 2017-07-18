package enamel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ListIterator;

import javax.swing.JFrame;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class AudioPlayer extends Player {

//	private VoiceManager vm = VoiceManager.getInstance();
//	private Voice voice = vm.getVoice ("kevin16");
	JFrame AFrame;
	
	public static final int UP = 38;
	public static final int DOWN = 40;
	public static final int LEFT = 37;
	public static final int RIGHT = 39;
	public static final int ENTER = 10;
	public static final int SPACE = 32;
	
	public static final int ONE = 49;
	public static final int TWO = 50;
	public static final int THREE = 51;
	public static final int FOUR = 52;
	
	public AudioPlayer(int brailleCellNumber, int ButtonNumber) {
		super(brailleCellNumber, ButtonNumber);
		AFrame = new JFrame();
		AFrame.repaint();
		AFrame.setVisible(true);
//		voice.allocate();
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSkipButtonListener(int index, String param, ScenarioParser sp) {
		
		AFrame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == (index + ONE)) {
					if (sp.userInput) {
						sp.skip(param);
						sp.userInput = false;
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	@Override
	public void removeButtonListener(int index) {
		
		AFrame.removeKeyListener(AFrame.getKeyListeners()[0]);		
	}

	@Override
	public void addRepeatButtonListener(int index, ScenarioParser sp) {
		AFrame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == index + '0') {
					if (sp.userInput) {
						sp.repeatText();
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
		});
	}


}
