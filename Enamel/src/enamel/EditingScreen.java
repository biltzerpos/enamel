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
import javax.swing.JRadioButton;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class EditingScreen implements ActionListener{

	private JFrame frame;
	List<JButton> buttons = new ArrayList<JButton>();
	JPanel subPanel;
	JRadioButton radioButton;
	JRadioButton radioButton_1;
	JRadioButton radioButton_2;
	JRadioButton radioButton_3;
	JRadioButton radioButton_4;
	JRadioButton radioButton_5;
	int buttonCount = 5;
	int boxCount = 4;
	int currentButton = 0;
	private JTextField txtEnterCharHere;
	private JTextField txtEnterWordHere;
//	int[] indvCell = new int[boxCount];
	
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
		
		//Initialize Main JFrame
		frame = new JFrame();								
		frame.setBounds(100, 100, 975, 739);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Initialize Main JPanel
		JPanel panel = new JPanel();		
		panel.setBackground(Color.RED);
		panel.setBounds(0, 0, 957, 692);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		
		//Label to display the amount of buttons
		JLabel lblAvaliableButtons = new JLabel("Avaliable Buttons:");	
		lblAvaliableButtons.setForeground(Color.WHITE);
		lblAvaliableButtons.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAvaliableButtons.setBounds(50, 600, 183, 16);
		panel.add(lblAvaliableButtons);
		
		//Combo Box to display button options
		JComboBox buttonBox = new JComboBox();				
		buttonBox.setBounds(781, 67, 119, 22);	
		buttonBox.addItem("Skip");
		buttonBox.addItem("Repeat");
		panel.add(buttonBox);
		
		//Label to display button options
		JLabel lblButtonType = new JLabel("Button Type:");
		lblButtonType.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblButtonType.setForeground(Color.WHITE);
		lblButtonType.setBounds(781, 29, 123, 25);
		panel.add(lblButtonType);
		
		//Label to display options for cell modifier
		JLabel lblCellModifier = new JLabel("Cell Modifier:");
		lblCellModifier.setForeground(Color.WHITE);
		lblCellModifier.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCellModifier.setBounds(781, 118, 123, 25);
		panel.add(lblCellModifier);
		
		
		//Combo Box to display options for each button
		JComboBox cellBox = new JComboBox();
		cellBox.setBounds(781, 148, 119, 22);
		panel.add(cellBox);
		cellBox.addItem("");
		cellBox.addItem("Clear");
		cellBox.addItem("Pins");
		cellBox.addItem("Character");
		cellBox.addItem("Word");
		
		//Main Card for card layout
		JPanel card = new JPanel(new CardLayout());
		card.setBounds(781, 183, 119, 294);
		panel.add(card);
		
		//Initial card
		JPanel blankCard = new JPanel();
		blankCard.setBackground(Color.RED);
		card.add(blankCard, "");
		
		//Card for Clear Option
		JPanel clearCard = new JPanel();
		clearCard.setBackground(Color.GREEN);
		card.add(clearCard, "Clear");
		GridBagLayout gbl_clearCard = new GridBagLayout();
		gbl_clearCard.columnWidths = new int[]{0, 0};
		gbl_clearCard.rowHeights = new int[]{0, 0, 0, 0};
		gbl_clearCard.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_clearCard.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		clearCard.setLayout(gbl_clearCard);
		
		//Combo Box to choose cell for clear card
		JComboBox clearChoose = new JComboBox();
		GridBagConstraints gbc_clearChoose = new GridBagConstraints();
		gbc_clearChoose.insets = new Insets(0, 0, 5, 0);
		gbc_clearChoose.fill = GridBagConstraints.HORIZONTAL;
		gbc_clearChoose.gridx = 0;
		gbc_clearChoose.gridy = 0;
		for(int i = 0; i < boxCount; i++)
		{
			clearChoose.addItem(i);
		}
		clearCard.add(clearChoose, gbc_clearChoose);
		
		//Toggle to clear
		JRadioButton clearRadioButton = new JRadioButton("");
		GridBagConstraints gbc_clearRadioButton = new GridBagConstraints();
		gbc_clearRadioButton.insets = new Insets(0, 0, 5, 0);
		gbc_clearRadioButton.gridx = 0;
		gbc_clearRadioButton.gridy = 1;
		clearCard.add(clearRadioButton, gbc_clearRadioButton);
		JLabel lblSelectToClear = new JLabel("Select to clear cell");
		GridBagConstraints gbc_lblSelectToClear = new GridBagConstraints();
		gbc_lblSelectToClear.gridx = 0;
		gbc_lblSelectToClear.gridy = 2;
		clearCard.add(lblSelectToClear, gbc_lblSelectToClear);
		
		//Pins Card
		JPanel pinsCard = new JPanel();
		pinsCard.setBackground(Color.BLACK);
		card.add(pinsCard, "Pins");
		GridBagLayout gbl_pinsCard = new GridBagLayout();
		gbl_pinsCard.columnWidths = new int[]{0, 0, 0, 0};
		gbl_pinsCard.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_pinsCard.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pinsCard.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pinsCard.setLayout(gbl_pinsCard);
		
		//ComboBox to choose which cell to edit
		JComboBox blockChooser = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		for(int i = 0; i < boxCount; i++)
		{
			blockChooser.addItem(i);
		}
		pinsCard.add(blockChooser, gbc_comboBox);
		
		//Add all pins 
		JRadioButton pin1 = new JRadioButton("");
		GridBagConstraints gbc_radioButton_6 = new GridBagConstraints();
		gbc_radioButton_6.insets = new Insets(0, 0, 5, 5);
		gbc_radioButton_6.anchor = GridBagConstraints.WEST;
		gbc_radioButton_6.gridx = 1;
		gbc_radioButton_6.gridy = 1;
		pinsCard.add(pin1, gbc_radioButton_6);
		
		JRadioButton pin2 = new JRadioButton("");
		GridBagConstraints gbc_radioButton_7 = new GridBagConstraints();
		gbc_radioButton_7.insets = new Insets(0, 0, 5, 0);
		gbc_radioButton_7.gridx = 2;
		gbc_radioButton_7.gridy = 1;
		pinsCard.add(pin2, gbc_radioButton_7);
		
		JRadioButton pin3 = new JRadioButton("");
		GridBagConstraints gbc_radioButton_8 = new GridBagConstraints();
		gbc_radioButton_8.insets = new Insets(0, 0, 5, 5);
		gbc_radioButton_8.gridx = 1;
		gbc_radioButton_8.gridy = 2;
		pinsCard.add(pin3, gbc_radioButton_8);
		
		JRadioButton pin4 = new JRadioButton("");
		GridBagConstraints gbc_radioButton_9 = new GridBagConstraints();
		gbc_radioButton_9.insets = new Insets(0, 0, 5, 0);
		gbc_radioButton_9.gridx = 2;
		gbc_radioButton_9.gridy = 2;
		pinsCard.add(pin4, gbc_radioButton_9);
		
		JRadioButton pin5 = new JRadioButton("");
		GridBagConstraints gbc_radioButton_10 = new GridBagConstraints();
		gbc_radioButton_10.insets = new Insets(0, 0, 0, 5);
		gbc_radioButton_10.gridx = 1;
		gbc_radioButton_10.gridy = 3;
		pinsCard.add(pin5, gbc_radioButton_10);
		
		JRadioButton pin6 = new JRadioButton("");
		GridBagConstraints gbc_radioButton_11 = new GridBagConstraints();
		gbc_radioButton_11.gridx = 2;
		gbc_radioButton_11.gridy = 3;
		pinsCard.add(pin6, gbc_radioButton_11);
		
		//Character card
		JPanel charCard = new JPanel();
		charCard.setBackground(Color.BLUE);
		card.add(charCard, "Character");
		GridBagLayout gbl_charCard = new GridBagLayout();
		gbl_charCard.columnWidths = new int[]{0, 0};
		gbl_charCard.rowHeights = new int[]{0, 0, 0, 0};
		gbl_charCard.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_charCard.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		charCard.setLayout(gbl_charCard);
		
		//Cell chooser for character card
		JComboBox charChoose = new JComboBox();
		GridBagConstraints gbc_charChoose = new GridBagConstraints();
		gbc_charChoose.insets = new Insets(0, 0, 5, 0);
		gbc_charChoose.fill = GridBagConstraints.HORIZONTAL;
		gbc_charChoose.gridx = 0;
		gbc_charChoose.gridy = 0;
		for(int i = 0; i < boxCount; i++)
		{
			charChoose.addItem(i);
		}
		charCard.add(charChoose, gbc_charChoose);
		
		//Text field for choosing character
		txtEnterCharHere = new JTextField();
		txtEnterCharHere.setText("Enter Char Here");
		GridBagConstraints gbc_txtEnterCharHere = new GridBagConstraints();
		gbc_txtEnterCharHere.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEnterCharHere.gridx = 0;
		gbc_txtEnterCharHere.gridy = 2;
		charCard.add(txtEnterCharHere, gbc_txtEnterCharHere);
		txtEnterCharHere.setColumns(10);
		
		//Word card
		JPanel wordCard = new JPanel();
		wordCard.setBackground(Color.PINK);
		card.add(wordCard, "Word");
		wordCard.setLayout(new BorderLayout(0, 0));
		
		//Text field for adding word
		txtEnterWordHere = new JTextField();
		txtEnterWordHere.setText("Enter Word Here");
		wordCard.add(txtEnterWordHere, BorderLayout.NORTH);
		txtEnterWordHere.setColumns(10);
		
		
		// Apply button
		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnApply.setBounds(791, 490, 97, 25);
		panel.add(btnApply);
		
		
		
		cellBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(card.getLayout());
				cl.show(card, (String) cellBox.getSelectedItem());
			}});
		
		for (int i = 0; i < buttonCount; i++ ) //Display buttons based on the amount of buttons passed in through the text file
		{
			x1 = x1 + 50;
			buttons.add( new JButton("" + i) );
			buttons.get(i).setBounds(x1, 625 , 50, 25);
			buttons.get(i).addActionListener(this);
			panel.add(buttons.get(i));
		}	
	}

	public void initializeRadioButtons()
	{
		
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
