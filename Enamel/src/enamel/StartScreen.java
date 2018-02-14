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
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		
		JRadioButton brailleB1 = new JRadioButton("1");
		brailleB1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buttonnumber = 1;
			}
		});
		brailleB1.setVisible(false);
		brailleB1.setBounds(6, 146, 37, 23);
		panel.add(brailleB1);
		
		JRadioButton brailleB2 = new JRadioButton("2");
		brailleB2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buttonnumber = 2;
			}
		});
		brailleB2.setVisible(false);
		brailleB2.setBounds(58, 146, 43, 23);
		panel.add(brailleB2);
		
		JRadioButton brailleB3 = new JRadioButton("3");
		brailleB3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buttonnumber = 3;
			}
		});
		brailleB3.setVisible(false);
		brailleB3.setBounds(6, 180, 37, 23);
		panel.add(brailleB3);
		
		JRadioButton brailleB4 = new JRadioButton("4");
		brailleB4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buttonnumber = 4;
			}
		});
		brailleB4.setVisible(false);
		brailleB4.setBounds(58, 180, 43, 23);
		panel.add(brailleB4);
		
		JRadioButton brailleC1 = new JRadioButton("1");
		brailleC1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cellnumber = 1;				
			}
		});
		brailleC1.setVisible(false);
		brailleC1.setBounds(237, 146, 43, 23);
		panel.add(brailleC1);
		
		JRadioButton brailleC2 = new JRadioButton("2");
		brailleC2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cellnumber = 2;
			}
		});
		brailleC2.setVisible(false);
		brailleC2.setBounds(305, 146, 37, 23);
		panel.add(brailleC2);
		
		JRadioButton brailleC3 = new JRadioButton("3");
		brailleC3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cellnumber = 3;
			}
		});
		brailleC3.setVisible(false);
		brailleC3.setBounds(372, 146, 37, 23);
		panel.add(brailleC3);
		
		JRadioButton brailleC4 = new JRadioButton("4");
		brailleC4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cellnumber = 4;
			}
		});
		brailleC4.setVisible(false);
		brailleC4.setBounds(237, 180, 43, 23);
		panel.add(brailleC4);
		
		JRadioButton brailleC5 = new JRadioButton("5");
		brailleC5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cellnumber = 5;
			}
		});
		brailleC5.setVisible(false);
		brailleC5.setBounds(305, 180, 37, 23);
		panel.add(brailleC5);
		
		JRadioButton brailleC6 = new JRadioButton("6");
		brailleC6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cellnumber = 6;
			}
		});
		brailleC6.setVisible(false);
		brailleC6.setBounds(372, 180, 42, 23);
		panel.add(brailleC6);
		
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
				brailleB1.setVisible(true);
				brailleB2.setVisible(true);
				brailleB3.setVisible(true);
				brailleB4.setVisible(true);
				brailleC1.setVisible(true);
				brailleC2.setVisible(true);
				brailleC3.setVisible(true);
				brailleC4.setVisible(true);
				brailleC5.setVisible(true);
				brailleC6.setVisible(true);	
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
		            txtNewtextfile.write(out);
		            out.println(txtNewtextfile);
		            out.flush();
		            out.close();
		            

		        } catch (IOException e1) {
		            System.err.println("Error occurred");
		            e1.printStackTrace();
		        }
				
			}
		});
		btnSaveNewFile.setBounds(237, 210, 177, 40);
		panel.add(btnSaveNewFile);

	}
}
