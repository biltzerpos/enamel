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
import enamel.ToyAuthoring;

public class AuthoringApp {

	private JButton testButton = new JButton("test");
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenu runMenu = new JMenu("Run");
	private JMenuItem newFile, loadFile, saveFile, saveAsFile, exit, runFile, runSelectFile;
	private JFileChooser fileChooser = new JFileChooser();
	private File currentFile, f;
	private String[] fileStr;
	private JPanel panel, scenarioPanel = new JPanel(new BorderLayout());
	private JTextPane scenarioText = new JTextPane();
	private JFrame frame = new JFrame();
	private JScrollPane scrollBar = new JScrollPane();

	public static void main(String[] args) {
		new AuthoringApp();
	}

	public AuthoringApp() {
		drawComponents();
		addActionListeners();
		setAccessible();
	}

	// Sets accessibility features of objects.
	private void setAccessible() {
		// TODO Auto-generated method stub
		setAccessible(fileMenu, "File", "drop down menu");
		setAccessible(loadFile, "Load", "a scenario file");
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
		loadFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					loadFileClicked();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		runSelectFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				runSelectFileClicked();
			}

		});
		saveFile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//JPanel panel
				saveFileClicked();
			}
		});
		
		saveAsFile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				saveAsFileClicked();
			}
			
		});
	}

	protected void saveAsFileClicked() {
		f = openFileChooser(new File("FactoryScenarios/"), "txt");
		if (f != null) {
			currentFile = f;
			frame.setTitle("Authoring App - " + currentFile.getName());
			SaveAsFile save = new SaveAsFile("txt", currentFile.getAbsolutePath());
			save.stringArrayToFile(fileStr);
		}
	}

	protected void saveFileClicked() {
		SaveAsFile save = new SaveAsFile("txt", currentFile.getAbsolutePath());
		save.stringArrayToFile(fileStr);
	}

	protected void runSelectFileClicked() { 	
		f = openFileChooser(new File("FactoryScenarios/"), "txt");
		if (f != null) {
			currentFile = f;
			ToyAuthoring ta = new ToyAuthoring(f.getAbsolutePath());
			ta.start();
		}
	}

	private void updateJTextPane() {
		String str = "";
		//System.out.println(fileStr.length);
		for (int i = 0; i < fileStr.length; i++){
			//System.out.println(fileStr[i])
			str += i + ": " + fileStr[i] + "\n";
			scenarioText.setText(str);
		}
	}

	protected void loadFileClicked() throws IOException {
		f = openFileChooser(new File("FactoryScenarios/"), "txt");
		if (f != null) {
			currentFile = f;
			frame.setTitle("Authoring App - " + currentFile.getName());
			FileParser fp = new FileParser(f);
			fileStr = fp.getArray();
		}
		updateJTextPane();
	}
	
	//Opens a file chooser @ the specified directory and expects the file selected
	//to be of the extension 'ext'. Returns the selected file. If extension is of
	//wrong type, return null.
	public File openFileChooser(File currentDir, String ext) {
		fileChooser.setCurrentDirectory(currentDir);
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) { 
			   String selectedExt = FilenameUtils.getExtension(fileChooser.getSelectedFile().getName());
               if (!ext.equals(selectedExt)) {
            	   /*final JPanel */panel = new JPanel();
            	   JOptionPane.showMessageDialog(panel, "Could not open file, Wrong file type", "Error", JOptionPane.ERROR_MESSAGE);
            	   return null;
            }
               else
               {
            	   return fileChooser.getSelectedFile();
               }
		}
		return null;
	}

	private void drawComponents() {
		
		//JMenuItem
		newFile = fileMenu.add("New");
		loadFile = fileMenu.add("Open");
		fileMenu.addSeparator();
		saveFile = fileMenu.add("Save");
		saveAsFile = fileMenu.add("Save as..");
		fileMenu.addSeparator();
		exit = fileMenu.add("Exit");
		runFile = runMenu.add("Run");
		runSelectFile = runMenu.add("Run..");
		
		//JMenu
		menuBar.add(fileMenu);
		
		//JMenuBar
		menuBar.add(runMenu);
		
		//JTextPane
		//scenarioText.setSize(30, 30);
		//scenarioText.setBounds(5, 5, 30, 30);
		scenarioText.setEditable(false);
		scrollBar.add(scenarioText);
		//scenarioText.setText("test \n test");
		
		//JPanel
		//scenarioPanel.setBackground(Color.yellow);
		//scenarioPanel.setBounds(100, 100, 200, 200);
		//scenarioPanel.add(scenarioText);
		
		//JFrame
		frame.pack();
		frame.getContentPane().add(scrollBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(960, 540);
		frame.setTitle("Authoring App");
		frame.setVisible(true);
		frame.setJMenuBar(menuBar);
		frame.add(scenarioText);
		//frame.pack();
	
		//scenarioPanel.add(testButton);
		
		//JButton
		
	}

}
