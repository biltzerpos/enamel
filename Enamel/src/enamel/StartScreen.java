//Instantiate the number of buttons and cells




package enamel;

import java.awt.EventQueue;


import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import javax.swing.JLabel;


public class StartScreen {
	public String cust_file;
	public String cust_filename;
	private JFrame frame;
	private JTextField txtNewtextfile;
	Voice voice;
	VoiceManager vm;
	private JTextField txtSetFileName;
	public int cellnumber;
	public int buttonnumber;
	public String buttontext;
	public String celltext;
	private JTextField buttontextfield;
	private JTextField celltextfield;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartScreen window = new StartScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 261);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnSaveNewFile = new JButton("Save New File");
		btnSaveNewFile.setVisible(false);
		
		JLabel lblSelectButtonNumber = new JLabel("Select Button Number");
		lblSelectButtonNumber .setVisible(false);
		lblSelectButtonNumber.setBounds(58, 125, 110, 14);
		panel.add(lblSelectButtonNumber);
		
		JLabel lblSelectCellNumber = new JLabel("Select Cell Number");
		lblSelectCellNumber .setVisible(false);
		lblSelectCellNumber.setBounds(281, 125, 110, 14);
		panel.add(lblSelectCellNumber);
		
		JLabel lblEnterFileText = new JLabel("Enter File Text:");
		lblEnterFileText.setVisible(false);
		lblEnterFileText.setBounds(6, 62, 188, 14);
		panel.add(lblEnterFileText);
		
		JLabel lblEnterFileName = new JLabel("Enter File Name Text:");
		lblEnterFileName.setVisible(false);
		lblEnterFileName.setBounds(16, 210, 178, 14);
		panel.add(lblEnterFileName);
		
		txtSetFileName = new JTextField();
		txtSetFileName.setVisible(false);
		
		txtNewtextfile = new JTextField();
		txtNewtextfile.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					cust_file = txtNewtextfile.getText();
			
					txtNewtextfile.setVisible(false);
					vm = VoiceManager.getInstance();
			        voice = vm.getVoice ("kevin16");
			        voice.allocate();
			        voice.speak(cust_file);
			       
			        btnSaveNewFile.setVisible(true);
			       
				}
			}
		});
		txtNewtextfile.setVisible(false);
		
		
		
		JButton btnChooseExistingFile = new JButton("Choose Existing File");
		btnChooseExistingFile.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent arg0) {
				//Choose Existing File Button
				String file = "";
				
				ScenarioParser s = new ScenarioParser(true);
				JFileChooser chooser = new JFileChooser(new File("FactoryScenarios/"));
				//Create textfield to allow user to name the file and save as string
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
				chooser.setFileFilter(filter);
				int returnval = chooser.showOpenDialog(null);
				if (returnval == JFileChooser.APPROVE_OPTION) {
					file = "FactoryScenarios/" + chooser.getSelectedFile().getName();
				}
				
				s.setScenarioFile(file);
				frame.dispose(); 
				//EditingScreen test = new EditingScreen(setScenarioFile(file));
			}
			
		});
		btnChooseExistingFile.setBounds(6, 11, 188, 40);
		panel.add(btnChooseExistingFile);
		
		JButton btnCreateNewFile = new JButton("Create New File");
		btnCreateNewFile.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				//Create New File Button
				
				//Close this JFrame
				//Open new frame to read new text
				
				btnCreateNewFile.setVisible(false);
				btnChooseExistingFile.setVisible(false);
				txtNewtextfile.setVisible(true);
				txtSetFileName.setVisible(true);
				lblSelectCellNumber .setVisible(true);
				lblSelectCellNumber .setVisible(true);
				lblEnterFileText.setVisible(true);
				lblEnterFileName.setVisible(true);
			}
		});
		btnCreateNewFile.setBounds(237, 11, 177, 40);
		panel.add(btnCreateNewFile);
		
		txtSetFileName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					cust_filename = txtSetFileName.getText();
			
					txtNewtextfile.setVisible(false);
					lblEnterFileText.setVisible(false);
					vm = VoiceManager.getInstance();
			        voice = vm.getVoice ("kevin16");
			        voice.allocate();
			        voice.speak(cust_filename);
			       
			        btnSaveNewFile.setVisible(true);
			}}});
		txtSetFileName.setBounds(10, 227, 188, 23);
		panel.add(txtSetFileName);
		txtSetFileName.setColumns(10);
		txtNewtextfile.setBounds(6, 87, 404, 31);
		panel.add(txtNewtextfile);
		txtNewtextfile.setColumns(10);
		

		btnSaveNewFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
		            PrintWriter out = new PrintWriter(new FileWriter(cust_filename + ".txt"));
		            
		            txtNewtextfile.getText();
		            buttontextfield.getText();
		            celltextfield.getText();
		            out.println("Button " + buttontext);
		            out.println("Cell "+ celltext);
		            out.println(cust_filename);
		            out.println();
		            out.println();
		            out.println(cust_file);
		            out.flush();
		            out.close();
		            

		        } catch (IOException e1) {
		            System.err.println("Error occurred");
		            e1.printStackTrace();
		        }
				frame.dispose(); 
				//EditingScreen test = new EditingScreen(setScenarioFile(cust_filename + ".txt"));
			}
		});
		btnSaveNewFile.setBounds(237, 210, 177, 40);
		panel.add(btnSaveNewFile);
		
		buttontextfield = new JTextField();
		buttontextfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					buttontext = buttontextfield.getText();
					buttonnumber = Integer.parseInt(buttontext);
					if (buttonnumber / 1 == buttonnumber) {
					buttontextfield.setVisible(false);
					lblSelectButtonNumber.setVisible(false);
					vm = VoiceManager.getInstance();
			        voice = vm.getVoice ("kevin16");
			        voice.allocate();
			        voice.speak(buttontext);
			       
			        btnSaveNewFile.setVisible(true);
					}
			}
			}
		});
		buttontextfield.setBounds(6, 150, 188, 20);
		panel.add(buttontextfield);
		buttontextfield.setColumns(10);
		
		celltextfield = new JTextField();
		celltextfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					celltext = celltextfield.getText();
					cellnumber = Integer.parseInt(celltext);
					
					if (cellnumber / 1 == cellnumber) {
					celltextfield.setVisible(false);
					lblSelectButtonNumber.setVisible(false);
					vm = VoiceManager.getInstance();
			        voice = vm.getVoice ("kevin16");
			        voice.allocate();
			        voice.speak(celltext);
			       
			        btnSaveNewFile.setVisible(true);
					}
			}
			}});
		
		celltextfield.setBounds(237, 150, 173, 20);
		panel.add(celltextfield);
		celltextfield.setColumns(10);

	}
}
