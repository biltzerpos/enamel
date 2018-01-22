package authoringApp;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import enamel.ScenarioParser;

public class AuthoringApp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	public AuthoringApp() {
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


	// Adds action listeners to objects within this JFrame.
	private void addActionListeners() {
		loadFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Component parent = null;
				if (e.getSource().equals(loadFile)) {
					int returnVal = fileChooser.showOpenDialog(parent);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						System.out.println("Directory: " + file);
					}
				}
			}
		});
	}

	// Adds a menu bar consisting of drop down options.
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
