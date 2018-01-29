package authoringApp;

//edited by QASIM Ahmed
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.*;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.apache.commons.io.FilenameUtils;

import enamel.ScenarioParser;
import enamel.ToyAuthoring;

public class AuthoringApp {

	private static JFrame gui;
	private static ArrayList<Component> guiComponents;
	private static JFileChooser fc = new JFileChooser();
	private static File f, currentFile;
	private static String[] fileStr;
	private static String scenarioStr;
	private static int currentLine;
	private static JPanel errorPanel;
	private static boolean isSaved = true;
	private static HashMap<String, Component> compMap;
>>>>>>> branch 'david' of https://github.com/davidc25tlumby/EECS2311-Group-11

	public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                gui = new AuthoringAppGUI();
                gui.setVisible(true);
<<<<<<< HEAD
                compMap = ((AuthoringAppGUI) gui).getCompMap();
=======
                compMap = ((AuthoringAppGUI) gui).getComponentMap();
                /*guiComponents = getCompList(gui);
                for (int i = 0; i < guiComponents.size(); i++){
                	if (guiComponents.get(i).getName() != null){
                		System.out.println(i + " " + guiComponents.get(i).getName());
                    	addActionListener(guiComponents.get(i));
                	}
                }*/
>>>>>>> branch 'david' of https://github.com/davidc25tlumby/EECS2311-Group-11
                addActionListeners();
            }
            
        });
	}


	protected static void addActionListeners() {
		((JMenuItem) compMap.get("newMenuItem")).addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isSaved){
					//TODO: give unsaved warning then save file;
				}
				//open new file
				isSaved = false;
			}
<<<<<<< HEAD
		});
		((JMenuItem) compMap.get("loadScenarioMenuItem")).addActionListener(new ActionListener() {
=======
>>>>>>> branch 'david' of https://github.com/davidc25tlumby/EECS2311-Group-11
			
<<<<<<< HEAD
=======
		});
		((JMenuItem) compMap.get("loadScenarioMenuItem")).addActionListener(new ActionListener() {
			
>>>>>>> branch 'david' of https://github.com/davidc25tlumby/EECS2311-Group-11
			@Override
			public void actionPerformed(ActionEvent e) {

				if (!isSaved){
					//save current file
				}
				try {
					f = openFileChooser(new File("FactoryScenarios/"), "txt");
					if (f != null) {
						currentFile = f;
						gui.setTitle("Authoring App - " + currentFile.getName());
						FileParser fp = new FileParser(f);
						fileStr = fp.getArray();
					}
					updateScenarioPane(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
<<<<<<< HEAD
=======
				}
			}
		});
		((JMenuItem) compMap.get("saveMenuItem")).addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				SaveAsFile save = new SaveAsFile("txt", currentFile.getAbsolutePath());
				save.stringArrayToFile(fileStr);
			}
			
		});
		((JMenuItem) compMap.get("saveAsMenuItem")).addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				f = openFileChooser(new File("FactoryScenarios/"), "txt");
				if (f != null) {
					currentFile = f;
					gui.setTitle("Authoring App - " + currentFile.getName());
					SaveAsFile save = new SaveAsFile("txt", currentFile.getAbsolutePath());
					save.stringArrayToFile(fileStr);
				}
			}
			
		});
	}

	protected static ArrayList<Component> getCompList(final Container c) {
		Component[] compArray = c.getComponents();
		ArrayList<Component> compList = new ArrayList<Component>();
		for (Component comp: compArray) {
			compList.add(comp);
			if (comp instanceof JMenu){
				for (int i = 0; i < ((JMenu) comp).getMenuComponentCount(); i++){
					compList.add(((JMenu) comp).getMenuComponent(i));
>>>>>>> branch 'david' of https://github.com/davidc25tlumby/EECS2311-Group-11
				}
			}
		});
		((JMenuItem) compMap.get("saveMenuItem")).addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				SaveAsFile save = new SaveAsFile("txt", currentFile.getAbsolutePath());
				save.stringArrayToFile(fileStr);
			}
			
		});
		((JMenuItem) compMap.get("saveAsMenuItem")).addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				f = openFileChooser(new File("FactoryScenarios/"), "txt");
				if (f != null) {
					currentFile = f;
					gui.setTitle("Authoring App - " + currentFile.getName());
					SaveAsFile save = new SaveAsFile("txt", currentFile.getAbsolutePath());
					save.stringArrayToFile(fileStr);
				}
			}
			
		});
		((JMenuItem) compMap.get("loadAndRunMenuItem")).addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				f = openFileChooser(new File("FactoryScenarios/"), "txt");
				if (f != null) {
					currentFile = f;
					ToyAuthoring ta = new ToyAuthoring(f.getAbsolutePath());
					ta.start();
				}
			}
			
		});
	}

	private static void updateScenarioPane(boolean isNew) {
		if (isNew){
			scenarioStr = "";
			for (int i = 0; i < fileStr.length; i++){
				scenarioStr += i + ": " + fileStr[i] + "\n";
			}
			currentLine = fileStr.length + 1;
		}
		else{
			scenarioStr += currentLine + ": " + fileStr[currentLine] + "\n";
		}
		((JTextPane) compMap.get("scenarioPane")).setText(scenarioStr);
	}

	//Opens a file chooser @ the specified directory and expects the file selected
	//to be of the extension 'ext'. Returns the selected file. If extension is of
	//wrong type, return null.
	public static File openFileChooser(File currentDir, String ext) {
		fc.setCurrentDirectory(currentDir);
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) { 
			   String selectedExt = FilenameUtils.getExtension(fc.getSelectedFile().getName());
               if (!ext.equals(selectedExt)) {
            	   /*final JPanel */errorPanel = new JPanel();
            	   JOptionPane.showMessageDialog(errorPanel, "Could not open file, Wrong file type", "Error", JOptionPane.ERROR_MESSAGE);
            	   return null;
            }
               else
               {
            	   return fc.getSelectedFile();
               }
		}
		return null;
	}
}
