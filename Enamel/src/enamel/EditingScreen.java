package enamel;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;

public class EditingScreen implements ActionListener{

	private JFrame frame;
	List<JButton> buttons = new ArrayList<JButton>();
	int buttonCount = 5;
	int currentButton = 0;
//	Node currentNode;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditingScreen window = new EditingScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public EditingScreen() {
		initialize();
		
	}

	private void initialize() { //Initialize GUI
		int x1 = 0;
		frame = new JFrame();
		frame.setBounds(100, 100, 975, 739);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		panel.setBounds(0, 0, 957, 692);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblAvaliableButtons = new JLabel("Avaliable Buttons:");
		lblAvaliableButtons.setForeground(Color.WHITE);
		lblAvaliableButtons.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAvaliableButtons.setBounds(50, 600, 183, 16);
		panel.add(lblAvaliableButtons);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(781, 67, 119, 22);
		comboBox.addItem("Skip");
		comboBox.addItem("Repeat");
		panel.add(comboBox);
		
		JLabel lblButtonType = new JLabel("Button Type:");
		lblButtonType.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblButtonType.setForeground(Color.WHITE);
		lblButtonType.setBounds(781, 29, 123, 25);
		panel.add(lblButtonType);
		
		for (int i = 0; i < buttonCount; i++ ) //Display buttons based on the amount of buttons passed in through the text file
		{
			x1 = x1 + 50;
			buttons.add( new JButton("" + i) );
			buttons.get(i).setBounds(x1, 625 , 50, 25);
			buttons.get(i).addActionListener(this);
			panel.add(buttons.get(i));
		}	
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		for(int i = 0; i < buttonCount; i++) //Sets the current button to edit
		{
			if(e.getSource().equals(buttons.get(i)))
			{
				currentButton = i;
			}
		}
		
	}
}
