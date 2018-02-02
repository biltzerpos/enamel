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
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;

public class StartScreen {
	public String cust_file;
	private JFrame frame;
	private JTextField txtNewtextfile;
	Voice voice;
	VoiceManager vm;

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
		
		JButton btnDanielsButton = new JButton("Daniel's Button");
		btnDanielsButton.setVisible(false);
		
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
			        btnDanielsButton.setVisible(true);
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
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
				chooser.setFileFilter(filter);
				int returnval = chooser.showOpenDialog(null);
				if (returnval == JFileChooser.APPROVE_OPTION) {
					file = "FactoryScenarios/" + chooser.getSelectedFile().getName();
				}
				
				s.setScenarioFile(file);
				
			}
			
		});
		btnChooseExistingFile.setBounds(10, 23, 188, 40);
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
				

				
			}
		});
		btnCreateNewFile.setBounds(237, 23, 177, 40);
		panel.add(btnCreateNewFile);
		
		
		txtNewtextfile.setText("NewTextFile");
		txtNewtextfile.setBounds(10, 74, 404, 125);
		panel.add(txtNewtextfile);
		txtNewtextfile.setColumns(10);
		

		btnDanielsButton.setBounds(10, 210, 188, 40);
		panel.add(btnDanielsButton);
	}
}
