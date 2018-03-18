package authoringApp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;
import javax.swing.*;
import org.apache.commons.io.FilenameUtils;
import enamel.ToyAuthoring;

/**
 * An application to create scenario files that are compatible with a Treasure Braille Box simulation.
 * @author Xiahan Chen, Huy Hoang Minh Cu, Qasim Mahir
 */
public class AuthoringApp {

	private static JFrame gui;
	private static JFileChooser fc = new JFileChooser();
	private static File f, currentFile, error;
	private static LinkedList<String> fileStr;
	private static LinkedList<Integer> id;
	private static JPanel errorPanel;
	private static HashMap<String, Component> compMap;
	private static JTextPaneController controller;
	
	private static int currentLine = 0, cell = 0, col = 0;
	private static boolean isSaved = true, isOpened = false;
	private static String currentID;


	/**
	 * Initializes the application by drawing the GUI and initializing a controller for the JTextPane. LinkedLists are created for an id+string pair that represents elements on the JTextPane.
	 * @param args unused
	 */
	public static void main(String[] args) {
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				gui = new AuthoringAppGUI();
				gui.setVisible(true);
				compMap = ((AuthoringAppGUI) gui).getCompMap();
				fileStr = new LinkedList<String>();
				id = new LinkedList<Integer>();
				id.add(0);
				controller = new JTextPaneController((JTextPane) compMap.get("scenarioPane"), (JScrollPane) compMap.get("scenarioScrollPane"));
				addActionListeners();
				
			}
			
		});
		
	}

	/**
	 * Implements all the action listeners for various components within the GUI.
	 */
	protected static void addActionListeners() {
		gui.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println(true);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println(true);
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyChar() == KeyEvent.VK_DOWN){
					currentLine++;
					System.out.println("Line: " + currentLine + " ID: " + id.get(currentLine));
				}
				else if (e.getKeyChar() == KeyEvent.VK_UP){
					currentLine--;
					System.out.println("Line: " + currentLine + " ID: " + id.get(currentLine));
				}
			}
		});
		
		((JMenuItem) compMap.get("newMenuItem")).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (!isSaved) {
					JOptionPane.showMessageDialog(gui, "please save first");
				}

				isSaved = false;
				NewFileGUI temp = new NewFileGUI();
				temp.setVisible(true);
				HashMap<String, Component> tempMap = ((NewFileGUI) temp).getCompMap();
				JTextField numCell = (JTextField) tempMap.get("numCell");
				JTextField numCol = (JTextField) tempMap.get("numCol");
				fileStr = new LinkedList<String>();
				id = new LinkedList<Integer>();
				((JButton) tempMap.get("createButton")).addActionListener(new ActionListener() {

					public void actionPerformed(java.awt.event.ActionEvent evt) {

						cell = Integer.parseInt(numCell.getText());
						col = Integer.parseInt(numCol.getText());
						isOpened = true;
						stateChanged();
						
						
						fileStr.add("Cell " + cell );
						fileStr.add("Button " + col );
						((JTextField) compMap.get("inputTextField")).setText("");
						id = controller.newDocCreated(fileStr);

						id.add(1);
						id.add(2);
						temp.dispose();
					}
					
					
				});

				((JButton) tempMap.get("cancelButton")).addActionListener(new ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						cell = Integer.parseInt(numCell.getText());
						col = Integer.parseInt(numCol.getText());;
						isOpened = false;
						temp.dispose();
					}
				});

			}

		});
		((JMenuItem) compMap.get("loadScenarioMenuItem")).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals("loadScenarioMenuItem")) {
					isOpened = true;
					stateChanged();
				}

				if (!isSaved) {
				}
				try {
					f = openFileChooser(new File("FactoryScenarios/"), "txt");
					if (f != null) {
						currentFile = f;
						gui.setTitle("Authoring App - " + currentFile.getName());
						FileParser fp = new FileParser(f);
						fileStr = fp.getArray();
					}
					id=controller.newDocCreated(fileStr);
					isOpened = true;
					((JTextField) compMap.get("inputTextField")).setText("");
					stateChanged();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		((JMenuItem) compMap.get("saveMenuItem")).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SaveAsFile save = new SaveAsFile("txt", new File(currentFile.getAbsolutePath()));
				try {
					save.stringArrayToFile(fileStr);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		});
		
		((JMenuItem) compMap.get("saveAsMenuItem")).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				f = saveFileChooser(new File("FactoryScenarios/"), "txt");
				if (f != null) {
					currentFile = f;
					gui.setTitle("Authoring App - " + currentFile.getName());
					SaveAsFile save = new SaveAsFile("txt", new File(currentFile.getAbsolutePath()));
					try {
						save.stringArrayToFile(fileStr);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		});
		((JMenuItem) compMap.get("loadAndRunMenuItem")).addActionListener(new ActionListener() {

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

		((JButton) compMap.get("insertText")).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String temp = ((JTextField) compMap.get("inputTextField")).getText();
				id.add(id.getLast()+1);
				fileStr.add(temp);
				controller.addElement(temp, id.getLast());
			}

		});
		
		((JMenuItem) compMap.get("exitMenuItem")).addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		}); 
		
		
		((JButton) compMap.get("insertResetButtons")).addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
			
             				
				}
             
			
		}); 

		((JButton) compMap.get("insertPause")).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (((JTextField) compMap.get("inputTextField")).getText().isEmpty()) {
					JOptionPane.showMessageDialog(gui, "pause need to have a number");
					throw new IllegalArgumentException();
				}
				try {
					int temp = Integer.parseInt((((JTextField) compMap.get("inputTextField")).getText()));
					id.add(id.getLast()+1);
					fileStr.add("/~pause:" + temp);
					controller.addElement("/~pause:" + temp, id.getLast());

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(gui, "pause need to have a number");
				}

			}

		});

		((JButton) compMap.get("insertRepeat")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				id.add(id.getLast()+1);
				fileStr.add("/~repeat");
				controller.addElement("/~repeat", id.getLast());


			}

		});

		((JButton) compMap.get("insertEndRepeat")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				id.add(id.getLast()+1);
				fileStr.add("/~endrepeat");
				controller.addElement("/~endrepeat", id.getLast());


			}

		});
		
		((JButton) compMap.get("insertSound")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				f = openFileChooser(new File("FactoryScenarios/AudioFiles"), "wav");
				if (f!=null)
				{
					fileStr.add("/~sound:"+f.getName());
					id.add(id.getLast()+1);
					controller.addElement("/~sound:"+f.getName(), id.getLast());
				}
			}
		}
		);
		
		
		((JButton) compMap.get("displayAddButton")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("rawtypes")
				JComboBox temp = (JComboBox) compMap.get("displayComboBox");
				String com = (String) temp.getItemAt(temp.getSelectedIndex());

				if (com.equals("Display String")) {
					if (((JTextField) compMap.get("inputTextField")).getText().isEmpty()) {
						JOptionPane.showMessageDialog(gui, "need a string");
						throw new IllegalArgumentException();
					} else {
						String tmp = ((JTextField) compMap.get("inputTextField")).getText();
						fileStr.add("/~disp-string:" + tmp);
						id.add(id.getLast() + 1);
						controller.addElement("/~disp-string:" + tmp, id.getLast());

					}
				}

				if (com.equals("Display Clear All")) {

					fileStr.add("/~disp-clearAll:");
					id.add(id.getLast() + 1);
					controller.addElement("/~disp-clearAll", id.getLast());

				}
				
				if (com.equals("Display Clear Cell")) {
					if (((JTextField) compMap.get("inputTextField")).getText().isEmpty()) {
						JOptionPane.showMessageDialog(gui, "need a number");
						throw new IllegalArgumentException();
					} else {
						try {
						int tmp = Integer.parseInt(((JTextField) compMap.get("inputTextField")).getText());
						if (tmp>cell||tmp<0) 
						{
							JOptionPane.showMessageDialog(gui, "there are no cell "+tmp+" to clear");

						}
						fileStr.add("/~disp-clear-cell:" + tmp);
						id.add(id.getLast() + 1);
						controller.addElement("/~disp-clear-cell:" + tmp, id.getLast());
						}
						catch (Exception e1)
						{
							JOptionPane.showMessageDialog(gui, "Display Clear Cell need to have a number");

						}
					}
				}
				
				if (com.equals("Display Cell Pins")) {
					if (((JTextField) compMap.get("inputTextField")).getText().isEmpty()) {
						JOptionPane.showMessageDialog(gui, "need a binary string of length 8");
						throw new IllegalArgumentException();
					} else {
						
						String tmp = ((JTextField) compMap.get("inputTextField")).getText();
						if (tmp.length()!=8 || !tmp.matches("[01]+"))
						{
							JOptionPane.showMessageDialog(gui, "need a binary string of length 8");
							throw new IllegalArgumentException();
						}
						
						fileStr.add("/~disp-cell-pins:" + tmp);
						id.add(id.getLast() + 1);
						controller.addElement("/~disp-cell-pins:" + tmp, id.getLast());
						
						
					}
				}
				
				if (com.equals("Display Cell Char")) {
					if (((JTextField) compMap.get("inputTextField")).getText().isEmpty()) {
						JOptionPane.showMessageDialog(gui, "need a number and a character");
						throw new IllegalArgumentException();
					} else {
						
						String tmp[] = ((JTextField) compMap.get("inputTextField")).getText().split(" ");
						tmp[1]=tmp[1].toUpperCase();
						
						if (tmp.length!=2 || !tmp[1].matches("[A-Z]")||!tmp[0].matches("[0-9]+"))
						{
							JOptionPane.showMessageDialog(gui, "need a number and a character");
							throw new IllegalArgumentException();
						}
						
						fileStr.add("/~disp-cell-char:" + tmp[0]+" "+tmp[1]);
						id.add(id.getLast() + 1);
						controller.addElement("/~disp-cell-char:"+ tmp[0]+" "+tmp[1], id.getLast());
		
					
					}
				}
				if (com.equals("Display Cell Raise")) {
					if (((JTextField) compMap.get("inputTextField")).getText().isEmpty()) {
						JOptionPane.showMessageDialog(gui, "need a number and a character");
						throw new IllegalArgumentException();
					} else {
						
						String tmp[] = ((JTextField) compMap.get("inputTextField")).getText().split(" ");
						tmp[1]=tmp[1].toUpperCase();
						
						if (tmp.length!=2 || !tmp[1].matches("[0-9]+")||!tmp[0].matches("[0-9]+"))
						{
							JOptionPane.showMessageDialog(gui, "need a number and a character");
							throw new IllegalArgumentException();
						}
						if (Integer.parseInt(tmp[0])>cell||Integer.parseInt(tmp[0])<0||Integer.parseInt(tmp[1])>col||Integer.parseInt(tmp[1])<0)
						{
							JOptionPane.showMessageDialog(gui, "invalid number, out of range");
							throw new IllegalArgumentException();
						}
						
						fileStr.add("/~disp-cell-raise:" + tmp[0]+" "+tmp[1]);
						id.add(id.getLast() + 1);
						controller.addElement("/~disp-cell-raise:"+ tmp[0]+" "+tmp[1], id.getLast());
						
				
					}
				}
				
				if (com.equals("Display Cell Lower")) {
					if (((JTextField) compMap.get("inputTextField")).getText().isEmpty()) {
						JOptionPane.showMessageDialog(gui, "need a number and a character");
						throw new IllegalArgumentException();
					} else {
						
						String tmp[] = ((JTextField) compMap.get("inputTextField")).getText().split(" ");
						tmp[1]=tmp[1].toUpperCase();
						
						if (tmp.length!=2 || !tmp[1].matches("[0-9]+")||!tmp[0].matches("[0-9]+"))
						{
							JOptionPane.showMessageDialog(gui, "need a number and a character");
							throw new IllegalArgumentException();
						}
						if (Integer.parseInt(tmp[0])>cell||Integer.parseInt(tmp[0])<0||Integer.parseInt(tmp[1])>col||Integer.parseInt(tmp[1])<0)
						{
							JOptionPane.showMessageDialog(gui, "invalid number, out of range");
							throw new IllegalArgumentException();
						}
						
						fileStr.add("/~disp-cell-lower:" + tmp[0]+" "+tmp[1]);
						id.add(id.getLast() + 1);
						controller.addElement("/~disp-cell-lower:"+ tmp[0]+" "+tmp[1], id.getLast());

					
					}
				}


			}

		});

	}
	
	/**
	 * Enables the components when a scenario file is opened in the application.
	 */
	protected static void stateChanged() {
		if (isOpened) { 
			System.out.println(true);
			compMap.get("saveAsMenuItem").setEnabled(true);
			compMap.get("insertText").setEnabled(true);
			compMap.get("insertPause").setEnabled(true);
			compMap.get("insertSkipButton").setEnabled(true);
			compMap.get("insertSkip").setEnabled(true);
			compMap.get("insertUserInput").setEnabled(true);
			compMap.get("insertRepeatButton").setEnabled(true);
			compMap.get("insertRepeat").setEnabled(true);
			compMap.get("insertEndRepeat").setEnabled(true);
			compMap.get("insertResetButtons").setEnabled(true);
			compMap.get("insertSound").setEnabled(true);
			compMap.get("displayComboBox").setEnabled(true);
			compMap.get("displayAddButton").setEnabled(true);
			compMap.get("editRemoveLine").setEnabled(true);
		}
	}

	/**
	 * Opens a FileChooser interface for the user to select a file.
	 * 
	 * @param currentDir
	 *            The directory that will first appear on the FileChooser.
	 * @param ext
	 *            Expected file extension to be returned.
	 * @return The file selected by the user and is of the appropriate
	 *         extension, otherwise null.
	 */
	public static File openFileChooser(File currentDir, String ext) {
		fc.setCurrentDirectory(currentDir);
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String selectedExt = FilenameUtils.getExtension(fc.getSelectedFile().getName());
			if (!ext.equals(selectedExt)) {
				errorPanel = new JPanel();
				JOptionPane.showMessageDialog(errorPanel, "Could not open file, Wrong file type", "Error",
						JOptionPane.ERROR_MESSAGE);
				return null;
			} else {
				return fc.getSelectedFile();
			}
		}
		return null;
	}
	
	public static File saveFileChooser(File currentDir, String ext) {
		fc.setCurrentDirectory(currentDir);
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String selectedExt = FilenameUtils.getExtension(fc.getSelectedFile().getName());
			if (!ext.equals(selectedExt)) {
				/* final JPanel */errorPanel = new JPanel();
				JOptionPane.showMessageDialog(errorPanel, "Could not save file, Wrong file type", "Error",
						JOptionPane.ERROR_MESSAGE);
				return null;
			} else {
				return fc.getSelectedFile();
			}
		}
		return null;
	}
	
	
}
