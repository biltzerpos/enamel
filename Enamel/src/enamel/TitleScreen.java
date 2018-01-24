package enamel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TitleScreen extends JFrame implements ActionListener {
	
	JFrame frame;
	ScenarioParser visual;
	ScenarioParser audial;
	Thread loop;
	boolean isVisual;
	
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
		
		loop = new Thread(
				  new Runnable() {

					
					@Override
				    public void run() {
				    	String file = ""; 
						JFileChooser chooser = new JFileChooser(new File("FactoryScenarios/"));
						FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
						chooser.setFileFilter(filter);
						int returnVal = chooser.showOpenDialog(null);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							file = "FactoryScenarios/" + chooser.getSelectedFile().getName();

						}
						ScenarioParser s = new ScenarioParser(TitleScreen.this.isVisual);
						s.setScenarioFile(file);
						
				    }
				  }
				);
	}

	@Override
	public  synchronized void actionPerformed(ActionEvent e) {
		String hold = e.getActionCommand();
		frame.dispose();
		
		if(hold.equals("YES")) {
			isVisual = true;
		}
		
		else if (hold.equals("NO")) {
			isVisual = false;
		}
		loop.start();
	}
	
	



}
