package enamel;

import java.awt.EventQueue;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Recorder1 {

	private JFrame frame;
	public static String cust_file;
	private JTextField textField;
	Voice voice;
	VoiceManager vm;

	/**
	 * Launch the application.
	 */
    static final long RECORD_TIME = 3000;  // 30 seconds limit past user
    
    //Change recording time to allow user to choose when they want to stop

 
    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
 
    // the line from which audio data is captured
    TargetDataLine line;
 
	static JButton btnNewButton = new JButton("Start Recording");
	static Boolean recordstart = btnNewButton.isVisible();
    /**
     * Defines an audio format
     */
    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
 

 
    /**
     * Closes the target data line to finish capturing and recording
     */
    void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
    }
 
    /**
     * Entry to run the program
     */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Recorder1 window = new Recorder1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

        final Recorder1 recorder = new Recorder1();
        
        // creates a new thread that waits for a specified
        // of time before stopping
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(RECORD_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish();
            }
        });
 
        stopper.start();
        // start recording
        recorder.start();
	}

	/**
	 * Create the application.
	 */
	public Recorder1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEnterInFile = new JLabel("Enter in File Name:");
		lblEnterInFile.setBounds(10, 11, 90, 14);
		frame.getContentPane().add(lblEnterInFile);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cust_file = textField.getText();
					
					textField.setVisible(false);
					vm = VoiceManager.getInstance();
			        voice = vm.getVoice ("kevin16");
			        voice.allocate();
			        voice.speak(cust_file);
			        
			        
		            try {
		            
		            // path of the wav file
		            File wavFile = new File("FactoryScenarios\\AudioFiles\\"+cust_file+".wav");	
		            AudioFormat format = getAudioFormat();
		            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		     
		                // checks if system supports the data line
		            if (!AudioSystem.isLineSupported(info)) {
		                System.out.println("Line not supported");
		                System.exit(0);
		            }
		            line = (TargetDataLine) AudioSystem.getLine(info);
		            line.open(format);
		            line.start();   // start capturing

		 

			        
		            System.out.println("Start capturing...");
		            
		            AudioInputStream ais = new AudioInputStream(line);
		 
		            System.out.println("Start recording...");
		            
		            
		 
		            // start recording

						AudioSystem.write(ais, fileType, wavFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (LineUnavailableException ex) {
		            ex.printStackTrace();
					}
			        
				}
			}
		});
		textField.setBounds(10, 39, 173, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton.setVisible(false);
			}
		});
		

		btnNewButton.setBounds(10, 76, 173, 23);
		frame.getContentPane().add(btnNewButton);
	}
	

    /**
     * Captures the sound and record into a WAV file
     */
    
    void start() {
    	

    }

}
