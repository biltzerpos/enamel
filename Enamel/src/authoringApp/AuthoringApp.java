package authoringApp;
//edited by QASIM Ahmed
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.*;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.apache.commons.io.FilenameUtils;

import enamel.ScenarioParser;

public class AuthoringApp extends JFrame {

	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem newFile, loadFile, saveFile, saveAsFile, exit;
	private JFileChooser fileChooser = new JFileChooser();      
	
	
	public static void main(String[] args) {
		AuthoringApp gui = new AuthoringApp();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		gui.setSize(960, 540);
		gui.setTitle("Authoring App");
	}
		
	public AuthoringApp()
	{
		drawMenuBar();
		addActionListeners();
		setAccessible();
	}
		// Sets accessibility features of objects.
	private void setAccessible() {
		// TODO Auto-generated method stub
		setAccessible(fileMenu, "File", "File drop down menu");
		setAccessible(loadFile, "Load", "Load a scenario file");
	}

	// Set name and description of a JMenuItem object.
	private void setAccessible(JMenuItem o, String s, String s2) {
		o.getAccessibleContext().setAccessibleName(s);
		o.getAccessibleContext().setAccessibleDescription(s2);
	}

	// Set name and description of a JMenu object.
	private void setAccessible(JMenu o, String s, String s2) {
		o.getAccessibleContext().setAccessibleName(s);
		o.getAccessibleContext().setAccessibleDescription(s2);
	}
	
	
	private void addActionListeners() {
		loadFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Component parent = null;
				if (e.getSource().equals(loadFile)) {
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
				            	   
				               }
					
						File file = fileChooser.getSelectedFile();
						System.out.println("Directory: " + file);
					}
				}
			}
		});
	}
	
		
	
	
	

	private void drawMenuBar() {
		setJMenuBar(menuBar);
		newFile = fileMenu.add("New");
		loadFile = fileMenu.add("Open");
		fileMenu.addSeparator();
		saveFile = fileMenu.add("Save");
		saveAsFile = fileMenu.add("Save as..");
		fileMenu.addSeparator();
		exit = fileMenu.add("Exit");
		menuBar.add(fileMenu);
	}

	
	

	
	
}
