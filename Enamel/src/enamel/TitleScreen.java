package enamel;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class TitleScreen {
	public static Clip clip;
	public static Mixer mixer;
	
	
	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(640, 480);
		JPanel panel = new JPanel();
		
		Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		
		mixer = AudioSystem.getMixer(mixInfos[0]);
		
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		try {
			clip = (Clip)mixer.getLine(dataInfo);
		}
		catch (LineUnavailableException lue) {
			lue.printStackTrace();
		}
				
		try
		{
			URL soundURL = TitleScreen.class.getResource("/enamel/titlescreen.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
			clip.open(audioStream);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch(LineUnavailableException lue)
		{
			lue.printStackTrace();
		}
		catch(UnsupportedAudioFileException uafe) {
			uafe.printStackTrace();
		}
		clip.start();
	}
	
	


}
