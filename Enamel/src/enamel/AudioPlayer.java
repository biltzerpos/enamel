package enamel;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer extends Player {
	
	
	public AudioPlayer(int cellNum, int buttonNum)
	{
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSkipButtonListener(int index, String param, ScenarioParser sp) {
		//Need Skip Button to implement index
		String scenario = sp.toString();
		if (scenario.contains(param)) {
			sp.skip(param);;
		}
		else {
			
		}
		
		try 
		   {
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(scenario).getAbsoluteFile( ));
		    Clip clip = AudioSystem.getClip( );
		    clip.open(audioInputStream);
		    clip.start( );
		   }
		   catch(Exception ex)
		   {
		     System.out.println("Cannot Skip to Word");
		     ex.printStackTrace( );
		   }
		 }


	@Override
	public void removeButtonListener(int index) {
		// TODO Auto-generated method stub

		
	}

	@Override
	public void addRepeatButtonListener(int index, ScenarioParser sp) {
		// TODO Auto-generated method stub
		//Look at how to determine when the clip stops reading
		//Initialize the button
		//initialize click event handler
		
		String scenariorepeat = sp.toString();
		
		
		try 
		   {
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(scenariorepeat).getAbsoluteFile( ));
		    Clip clip = AudioSystem.getClip( );
		    clip.open(audioInputStream);
		    clip.start( );
		   }
		   catch(Exception ex)
		   {
		     System.out.println("Cannot Repeat Sentences");
		     ex.printStackTrace( );
		   }
		 }
	}

