package enamel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ToyAuthoring {

    //this this the origin version

    public static void main(String[] args) {

        // Initialising objects for file explorer and the ScenarioParser
        JButton open = new JButton();
        JFileChooser fileChooser = new JFileChooser();
        ScenarioParser s;

        // Starts JFileChooser at project directory
        fileChooser.setCurrentDirectory(new java.io.File("."));

        // Shows only text files in the file chooser
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));

        if (fileChooser.showOpenDialog(open) == JFileChooser.APPROVE_OPTION)
        {
            s = new ScenarioParser(true);

            String fileName = fileChooser.getSelectedFile().getName();

            // Using regex checks if the file name has the following: ["Scenario_" + (a positive integer) + ".txt"]
            if (fileName.matches("^Scenario_[1-9]*.txt$"))
            {
                s.setScenarioFile(fileChooser.getSelectedFile().getAbsolutePath());
            }
            else
            {
                // exit
                System.out.println("NO MATCH");

            }

        }

    }
}