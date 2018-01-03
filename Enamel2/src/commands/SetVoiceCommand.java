package commands;

/**
 * A class to represent set voice commands. Contains a string for which numbered
 * text to speech voice to use
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-01
 */
public class SetVoiceCommand extends PlayerCommand {

	private String voiceNumber;

	/**
	 * Constructor to create a SetVoiceCommand object
	 *
	 * @param voice
	 *            String of either 1, 2, 3, 4 representing the text to speech
	 *            voice to use
	 */
	public SetVoiceCommand(String voice) {
		this.voiceNumber = voice;
	}

	@Override
	public String serialize() {
		return "/~set-voice:" + voiceNumber;
	}

	@Override
	public String toString() {
		String voice = "TTS Voice to use: ";
		if (Integer.parseInt(voiceNumber) == 1) {
			voice = voice + "male 1";
		} else if (Integer.parseInt(voiceNumber) == 2) {
			voice = voice + "female 2";
		} else if (Integer.parseInt(voiceNumber) == 3) {
			voice = voice + "male 3";
		} else if (Integer.parseInt(voiceNumber) == 4) {
			voice = voice + "male 4";
		}
		return voice;
	}

	@Override
	public String getEditLabel() {
		return "Enter a voice number";
	}

	@Override
	public String getCurrentValue() {
		return this.voiceNumber;
	}

	@Override
	public void setCurrentValue(String voice) {
		this.voiceNumber = voice;
	}

}
