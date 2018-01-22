package enamel;

import java.io.*;
import java.io.FileFilter;

import javax.swing.*;
import javax.swing.filechooser.*;

public class ToyAuthoring {
	// hello
	public static void main(String[] args) {
		String file = "";

		ScenarioParser s = new ScenarioParser(true);
		JFileChooser chooser = new JFileChooser(new File("FactoryScenarios/"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = "FactoryScenarios/" + chooser.getSelectedFile().getName();

		}

		s.setScenarioFile(file);
		
		JButton skipBtn = new JButton("Skip");
	}
}