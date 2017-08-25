package authoring;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

/**
 * AudioRecorder class
 *
 * This class is intended to be used as a dependency by a client GUI class to
 * record audio from a microphone and save it as a .wav file. This class
 * configures and stores the parameters of the input lines needed for audio
 * recording, such as sample rate, without need for user input. Before
 * recording, a user should set the name of the file they want the audio to be
 * saved to using the method setFileName(). Audio recording started by calling
 * the recordAudio() method, and stopped by calling the stopAudio() method.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-03-13
 */
public class AudioRecorder {
	// File for audio to be saved as, will be specified by user
	private File wavFile;
	// Audio file format, set as .wav
	private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	// Target line to capture audio data from
	private TargetDataLine line;

	/**
	 * Create a new audio recorder with a given data line as the source. This
	 * will allow the recorder to drain the source until it is empty, or the
	 * source is closed. The source is typically a microphone, but not required
	 * to be so.
	 *
	 * @param line
	 *            Data line that the class should attempt to drain
	 */
	public AudioRecorder(TargetDataLine line) {
		this.line = line;
	}

	/**
	 * Method to start recording audio Hook this up to play button in GUI
	 * wavFile must be set by setFileName beforehand info and line must be set
	 * by setAudioLine beforehand Recording is stopped by method stopRecording
	 *
	 * @return True if the recording occured OK, and false otherwise
	 */
	public boolean recordAudio() {
		try {
			// start capturing from line in
			line.start();
			// create audio input stream
			AudioInputStream ais = new AudioInputStream(line);
			// start recording, continue until line is drained completely
			AudioSystem.write(ais, fileType, wavFile);
		} catch (IOException e) {
			// A file I/O exception happened, bail
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Method to stop audio recording Stops and closes the line in Hook this up
	 * with stop button in GUI
	 */
	public void stopRecording() {
		line.stop();
		line.close();
	}

	/**
	 * Method to set the name of the audio file to be recorded Hook this up to
	 * GUI file to enter file name
	 *
	 * @param fileName
	 *            file path and name of the file
	 */
	public void setFileName(String fileName) {
		wavFile = new File(fileName);
	}

}
