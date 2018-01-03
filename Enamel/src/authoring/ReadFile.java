package authoring;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Utility class which allows arbitrary files to be played through the speakers.
 * This class is used via the Play command in the authoring UI to aid in
 * locating and inserting the correct audio files when using the authoring UI
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-03-15
 */
public class ReadFile {

	private static final int BUFFER_SIZE = 128000;
	private File soundFile;
	private AudioInputStream audioStream;
	private AudioFormat audioFormat;
	private SourceDataLine sourceLine;

	/**
	 * Play a file through the default audio sink (typically speakers)
	 *
	 * @param filename
	 *            the name of the file that is going to be played
	 */
	public void playSound(String filename) {

		String strFilename = filename;

		try {
			soundFile = new File(strFilename);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		try {
			audioStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		audioFormat = audioStream.getFormat();

		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		sourceLine.start();

		int nBytesRead = 0;
		byte[] abData = new byte[BUFFER_SIZE];
		while (nBytesRead != -1) {
			try {
				nBytesRead = audioStream.read(abData, 0, abData.length);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (nBytesRead >= 0) {
				sourceLine.write(abData, 0, nBytesRead);
			}
		}

		sourceLine.drain();
		sourceLine.close();
	}
}