//Additional undocumented change notes:
//this class is now an extension of "Thread". It will now run in a seperate thread when called by a different proccess.

package enamel;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.io.FilenameUtils;

import authoringApp.FileParser;

import java.awt.Component;
import java.io.*; 
import java.util.*;

public class ToyAuthoring extends Thread {
	
	private static JFileChooser fileChooser = new JFileChooser();
	
	public static void main(String[] args){
		Component parent = null;    	
		File f = new File("FactoryScenarios/");

		fileChooser.setCurrentDirectory(f);
		int returnVal = fileChooser.showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) { 
			   String ext= FilenameUtils.getExtension(fileChooser.getSelectedFile().getName());
               if (!ext.equals("txt") )
            	   {
            	   final JPanel panel = new JPanel();
            	   	
            	    JOptionPane.showMessageDialog(panel, "Could not open file, Wrong file type", "Error", JOptionPane.ERROR_MESSAGE);

            	   }
               else
               {

            		ToyAuthoring ta = new ToyAuthoring(fileChooser.getSelectedFile().getAbsolutePath());
            		ta.start();

            	   
               }

		}
	}
	
	public String directory;
	
	public ToyAuthoring(String string) {
		this.directory = string;
	}

	@Override
	public void run(){
			ScenarioParser s = new ScenarioParser(true);
			s.setScenarioFile(this.directory);
	    }
	
}
