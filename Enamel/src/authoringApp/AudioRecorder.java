package authoringApp;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class AudioRecorder {

	File path = new File("C:\\Users\\Qasim Ahmed\\git\\EECS2311-Group-11\\Enamel\\FactoryScenarios\\AudioFiles\\Recordaudio.wav");
	AudioFileFormat.Type type = AudioFileFormat.Type.WAVE;     //WAVE file type	
	
     
	TargetDataLine data; 
	
	public static AudioFormat getformat() {
		//audioformat
		AudioFormat format = new AudioFormat(50000, 15, 2, true, true);    //2 is stereo
		return format;
	}
	
	//captures sound and records in type WAV format
	public void startcapturingsound() {
		try {
            AudioFormat format = getformat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            data = (TargetDataLine) AudioSystem.getLine(info);
            data.open(format);
            data.start();   // start capturing
  
            AudioInputStream inputstream = new AudioInputStream(data);
 
            System.out.println("Start recording...");
 
            // starts recording
            AudioSystem.write(inputstream, type, path);
 
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }  
	public void finish() {               //closes data line to finish recording
        data.stop();
        data.close();
        System.out.println("Done");
    }
	
	public static void main(String[] args) {
		
		final AudioRecorder audiorecorder = new AudioRecorder();
		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
                    Thread.sleep(60000); //60 sec
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                audiorecorder.finish();
            }
        });
 
        thread.start();
 
        // start recording
        audiorecorder.startcapturingsound();
    }
	
	{
			
		
	}	
	}
	
	

	
