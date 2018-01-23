package enamel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TitleScreen extends JFrame implements ActionListener {
	
	JFrame frame;
	
	public TitleScreen() throws IOException
	{
		frame = new JFrame();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
		JPanel panel = new JPanel(new BorderLayout());
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel label = new JLabel("Welcome to our program!");
		JButton yes = new JButton("YES");
		JButton no = new JButton("NO");
		
		yes.setPreferredSize(new Dimension(100, 100));
		yes.addActionListener(this);
		no.addActionListener(this);
		no.setPreferredSize(new Dimension(100, 100));
		
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setFont(new Font("Verdana", 1, 18));
		
		bottom.add(yes);
		bottom.add(no);
		
		bottom.setBackground(Color.YELLOW);
		
		panel.add(bottom, BorderLayout.PAGE_END);
		panel.add(label, BorderLayout.CENTER);
		panel.setBackground(Color.YELLOW);
		
		frame.add(panel);
			ScenarioParser s = new ScenarioParser(true);
		s.setScenarioFile("FactoryScenarios/Scenario_1.txt");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		String hold = e.getActionCommand();
//		frame.dispose();
//		String file = "";
//		JFileChooser chooser = new JFileChooser(new File("FactoryScenarios/"));
//		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
//		chooser.setFileFilter(filter);
//		int returnVal = chooser.showOpenDialog(null);
//		if (returnVal == JFileChooser.APPROVE_OPTION) {
//			file = "FactoryScenarios/" + chooser.getSelectedFile().getName();
//
//		}
//		
//		if(hold.equals("YES"))
//		{
//			ScenarioParser s = new ScenarioParser(true);
//			s.setScenarioFile(file);
//		}
//		
//		else if (hold.equals("NO"))
//		{
//			ScenarioParser s = new ScenarioParser(false);
//			s.setScenarioFile(file);
//		}
	}



}
