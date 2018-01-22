package enamel;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.io.FilenameUtils;

import authoringApp.FileParser;

import java.awt.Component;
import java.io.*; 
import java.util.*;

public class ToyAuthoring {
	private static JFileChooser fileChooser = new JFileChooser();
    public static void main(String[] args) {
    		
    		Component parent = null;    	
			File f = new File("FactoryScenarios/");

    		fileChooser.setCurrentDirectory(f);
			int returnVal = fileChooser.showOpenDialog(parent);
			if (returnVal == JFileChooser.APPROVE_OPTION) { 
				   String ext= FilenameUtils.getExtension(fileChooser.getSelectedFile().getName());
	               //System.out.println(ext);
	               if (!ext.equals("txt") )
	            	   {
	            	   final JPanel panel = new JPanel();
	            	   	
	            	    JOptionPane.showMessageDialog(panel, "Could not open file, Wrong file type", "Error", JOptionPane.ERROR_MESSAGE);

	            	   }
	               else
	               {
	            	   final JPanel panel = new JPanel();

	            	    JOptionPane.showMessageDialog(panel, "File accepted", "File recieved", JOptionPane.INFORMATION_MESSAGE);
	            	    ScenarioParser s = new ScenarioParser(true);
	            	    s.setScenarioFile(fileChooser.getSelectedFile().getAbsolutePath());
	            	  

	            	   
	               }
		
			File file = fileChooser.getSelectedFile();
			System.out.println("Directory: " + file);
			FileParser scenarioArray = new FileParser(file);
			}
    }
}