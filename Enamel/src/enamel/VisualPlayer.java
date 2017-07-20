package enamel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

/**
 * This class simulates the hardware for a device with physical Braille cells
 * and programmable buttons.
 * <p>
 * The constructor initializes the window and takes as arguments the number of
 * Braille Cells and the number of buttons the simulator should display. This
 * class also contains several methods that allow for the manipulation of the
 * Simulator.
 * <p>
 * The individual Braille Cells can be accessed using the
 * <code> getCell(int)</code> method and the cell's index. The cells are indexed
 * from left to right and top to bottom. Similarly, the buttons can be accessed
 * using the <code> getButton(int)</code> method, and they are indexed from left
 * to right. Both of these methods return the actual reference of the objects
 * displayed in the frame, meaning you can then call methods from each of the
 * object's respective classes to manipulate them. For example, you can call
 * <code> displayCharacter(char)</code> on any specific cell to display a
 * character on that cell. You can also, in a similar fashion, use the
 * <code> getButton(int) </code> method to get the reference of a specific
 * button. Then, you can call any methods specified in the JButton class on that
 * button, such as the <code> addActionListener() </code> method.
 * 
 * @author Team 4: Yassin Mohamed, Qassim Allauddin, Derek Li, Artem Solovey.
 *
 */
public class VisualPlayer extends Player {

	
	private JFrame frame;
	private GridLayout cellGrid = new GridLayout(4, 2);
	LinkedList<JPanel> panelList = new LinkedList<JPanel>();
	LinkedList<JButton> buttonList = new LinkedList<JButton>();
	JPanel southPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JRadioButton[] pins = new JRadioButton[8];
	int[] pinIndex = {0, 2, 4, 1, 3, 5, 6, 7};
	
	/**
	 * Creates and displays a window with <code>brailleCellNumber</code> Braille
	 * cells and <code>jButtonNumber</code> buttons. The two parameters must be
	 * positive integers.
	 * 
	 * @param brailleCellNumber
	 *            the number of braille cells the Simulator should have
	 * @param jButtonNumber
	 *            the number of buttons the Simulator should have
	 * @throws IllegalArgumentException
	 *             if one or both of the two parameters is negative or 0
	 */
	public VisualPlayer(int brailleCellNumber, int ButtonNumber) {

		super(brailleCellNumber, ButtonNumber);

		SwingUtilities.invokeLater(new Runnable() {
			//@Override
			public void run() {
				// TODO Auto-generated method stub
				frame = new JFrame();
				frame.setTitle("Simulator");
				frame.setBounds(100, 100, 627, 459);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setLayout(new BorderLayout());

				for (int i = 0; i < brailleCellNumber; i++) {

					JPanel panel = new JPanel(cellGrid);
					for (int j = 0; j < 8; j++) {
						JRadioButton radioButton = new JRadioButton();
						radioButton.setEnabled(false);
						radioButton.setSize(25, 25);
						radioButton.getAccessibleContext().setAccessibleName("Cell " + (j + 1));

						pins[j] = radioButton;

						panel.add(radioButton);
						panel.repaint();
					}
					
					panel.setVisible(true);

					panelList.add(panel);
					panel.setSize(50, 50);
					panel.setBorder(BorderFactory.createLineBorder(Color.black));
					centerPanel.add(panel);

					if (i == (brailleCellNumber - 1))
						frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
				}

				for (int i = 0; i < ButtonNumber; i++) {
					JButton button = new JButton("" + (i + 1));

					buttonList.add(button);
					southPanel.add(button);
				}

				frame.getContentPane().add(southPanel, BorderLayout.SOUTH);

				frame.repaint();
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Returns a reference to the button at the index passed as argument. The
	 * main purpose of providing this method is so the client can add
	 * actionListeners to the button. Buttons are numbered from left to right as
	 * they appear in the frame, from 0 to (jButtonNumber-1), jButtonNumber
	 * being the number of buttons initialized by the constructor.
	 * 
	 * @param index
	 *            the index of the button to be returned
	 * @return reference to the JButton object at the index passed as argument
	 * @throws IllegalArgumentException
	 *             if the index is negative or equal to or bigger than
	 *             jButtonNumber (the number of buttons initialized)
	 */
	public JButton getButton(int index) {
		if (index >= this.ButtonNumber || index < 0) {
			throw new IllegalArgumentException("Invalid button index.");
		}
		return this.buttonList.get(index);
	}

	@Override
	public void refresh() {
		for (BrailleCell s : brailleList) {
			for (int i = 0; i < s.getNumberOfPins(); i++) {
				pins[pinIndex[i]].setSelected(s.getPinState(i));
			}
		}
	}

	@Override
	public void addSkipButtonListener(int index, String param, ScenarioParser sc) {

		buttonList.get(index).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// This if statement is used to allow the user to press the
				// button
				// after all the text has been read and that the program is
				// expecting
				// a user input.
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
				//		if (sc.userInput) {
							sc.skip(param);
							sc.userInput = false;
						}
				//	}	
				}).start();
			}
		});
	}

	@Override
	public void removeButtonListener(int index) {

		ActionListener[] aList = getButton(index).getActionListeners();
		if (aList.length > 0) {
			for (int x = 0; x < aList.length; x++) {
				getButton(index).removeActionListener(getButton(index).getActionListeners()[x]);
			}
		}
	}

	@Override
	public void addRepeatButtonListener(int index, ScenarioParser sp) {
		
		//combine into skipbuttonlistener
		//if String param == null, sp.repeatText, else, sp.skip(param).
		//add sp.userInput = false; in repeatbuttonlistener
		//add userInpu = true at the end of repeatText();
		
		getButton(index).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// This if statement is used to allow the user to press the
				// button
				// after all the text has been read and that the program is
				// expecting
				// a user input.
				if (sp.userInput) {
					sp.repeatText();
				}
			}
		});
	}

}
