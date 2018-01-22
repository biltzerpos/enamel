package authoringApp;
//edited by QASIM Ahmed
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.apache.commons.io.FilenameUtils;

import enamel.ScenarioParser;

public class AuthoringApp extends JFrame implements MenuListener, ActionListener{

	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem newFile, loadFile, saveFile, saveAsFile, exit;
	private JFileChooser chooser = new JFileChooser();      
	
	
	public static void main(String[] args) {
		AuthoringApp gui = new AuthoringApp();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		gui.setSize(960, 540);
		gui.setTitle("Authoring App");
	}
		
	public AuthoringApp(){
		drawMenuBar();
		loadFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Component parent = null;
				if (e.getSource().equals(loadFile)) {
					 int returnVal = chooser.showOpenDialog(parent);
				        if(returnVal == JFileChooser.APPROVE_OPTION) {
				               System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
				               String ext= FilenameUtils.getExtension(chooser.getSelectedFile().getName());
				               //System.out.println(ext);
				               if (!ext.equals("txt") )
				            	   {
				            	   final JPanel panel = new JPanel();

				            	    JOptionPane.showMessageDialog(panel, "Could not open file, Wrong file type", "Error", JOptionPane.ERROR_MESSAGE);


				            	   }
				               
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuCanceled(MenuEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuDeselected(MenuEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuSelected(MenuEvent me) {
		
		
	}

	
	
}
