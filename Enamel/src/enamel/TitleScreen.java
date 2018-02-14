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

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;


public class TitleScreen extends JFrame implements ActionListener {
	
	JFrame frame;
	JPanel panel;
	JPanel bottom;
	JLabel label;
	JButton yes;
	JButton no;
	Voice voice;
	VoiceManager vm;
	
	ScenarioParser visual;
	ScenarioParser audial;
	Thread loop;
	boolean isVisual;
	

	public TitleScreen() throws IOException
	{
		loop = new Thread(
				  new Runnable() {
					
					@Override
				    public void run() {
				    	String file = ""; 
						JFileChooser chooser = new JFileChooser(new File("FactoryScenarios/"));
						FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
						chooser.setFileFilter(filter);
						int returnVal = chooser.showOpenDialog(null);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							file = "FactoryScenarios/" + chooser.getSelectedFile().getName();

						}
						ScenarioParser s = new ScenarioParser(TitleScreen.this.isVisual);
						s.setScenarioFile(file);
						
				    }
				  }
				);
		vm = VoiceManager.getInstance();
        voice = vm.getVoice ("kevin16");
        voice.allocate();
		
		frame = new JFrame();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
		panel = new JPanel(new BorderLayout());
		bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		label = new JLabel("Welcome to our program!");
		yes = new JButton("YES");
		no = new JButton("NO");
		
		yes.setPreferredSize(new Dimension(100, 100));
		yes.addActionListener(this);
		
		no.setPreferredSize(new Dimension(100, 100));
		no.addActionListener(this);
		
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setFont(new Font("Verdana", 1, 18));
		
		bottom.add(yes);
		bottom.add(no);
		
		bottom.setBackground(Color.YELLOW);
		

		panel.add(bottom, BorderLayout.PAGE_END);
		panel.add(label, BorderLayout.CENTER);
		panel.setBackground(Color.YELLOW);
		
		frame.add(panel);
		voice.speak("Hello! Welcome to our program! If you are not visually impared, please press Yes. If you are visually impared, please press No.");
		

	}

	@Override
	public  synchronized void actionPerformed(ActionEvent e) {
		String hold = e.getActionCommand();
		frame.dispose();
		
		if(hold.equals("YES")) {
			isVisual = true;
		}
		
		else if (hold.equals("NO")) {
			isVisual = false;
		}
		loop.start();
	}
	




}
