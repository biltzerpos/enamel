package enamel;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

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
 * <code> displayCharacter(char)</code> on any specific cell to display a character
 * on that cell. You can also, in a similar fashion, use the
 * <code> getButton(int) </code> method to get the reference of a specific
 * button. Then, you can call any methods specified in the JButton class on that
 * button, such as the <code> addActionListener() </code> method.
 * 
 * @author Team 4: Yassin Mohamed, Qassim Allauddin, Derek Li, Artem Solovey.
 *
 */
public class Simulator {

	int brailleCellNumber;
	int jButtonNumber;
	private JFrame frame;
	private GridLayout cellGrid = new GridLayout(4, 2);
	LinkedList<JPanel> panelList = new LinkedList<JPanel>();
	ArrayList<JRadioButton> pins = new ArrayList<JRadioButton>(8);
	LinkedList<ArrayList<JRadioButton>> pinList = new LinkedList<ArrayList<JRadioButton>>();
	LinkedList<JButton> buttonList = new LinkedList<JButton>();
	LinkedList<BrailleCell> brailleList = new LinkedList<BrailleCell>();
	JPanel southPanel = new JPanel();
	JPanel centerPanel = new JPanel();

	/**
	 * Creates and displays a window with <code>brailleCellNumber</code>
	 * Braille cells and <code>jButtonNumber</code> buttons. The two parameters must be
	 * positive integers.
	 * 
	 * @param brailleCellNumber
	 *            the number of braille cells the Simulator should have
	 * @param jButtonNumber
	 *            the number of buttons the Simulator should have
	 * @throws IllegalArgumentException
	 *             if one or both of the two parameters is negative or 0
	 */
	public Simulator(int brailleCellNumber, int jButtonNumber) {

		if (brailleCellNumber <= 0 || jButtonNumber <= 0)
			throw new IllegalArgumentException("Non-positive integer entered.");

		this.brailleCellNumber = brailleCellNumber;
		this.jButtonNumber = jButtonNumber;
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
				pins.add(radioButton);
				panel.add(radioButton);
				panel.repaint();
			}

			panel.setVisible(true);
			pinList.add(pins);

			BrailleCell cell = new BrailleCell(pins.get(0), pins.get(1), pins.get(2), pins.get(3), pins.get(4),
					pins.get(5), pins.get(6), pins.get(7));
			panelList.add(panel);
			brailleList.add(cell);
			panel.setSize(50, 50);
			panel.setBorder(BorderFactory.createLineBorder(Color.black));
			centerPanel.add(panel);

			pins.clear();
			if (i == (brailleCellNumber - 1))
				frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

		}

		for (int i = 0; i < jButtonNumber; i++) {
			JButton button = new JButton("" + i);
			buttonList.add(button);

			southPanel.add(button);
		}
		frame.getContentPane().add(southPanel, BorderLayout.SOUTH);

		frame.repaint();
		frame.setVisible(true);

	}



	/**
	 * Returns a reference to the button at the index passed as argument.
	 * The main purpose of providing this method is so the client can add
	 * actionListeners to the button.
	 * Buttons are numbered from left to right as they appear in the frame, from
	 * 0 to (jButtonNumber-1), jButtonNumber being the number of buttons
	 * initialized by the constructor. 
	 * 
	 * @param index
	 *            the index of the button to be returned
	 * @return reference to the JButton object at the index passed as argument
	 * @throws IllegalArgumentException
	 *             if the index is negative or equal to or bigger than
	 *             jButtonNumber (the number of buttons initialized)
	 */
	public JButton getButton(int index) {
		if (index >= this.jButtonNumber || index < 0) {
			throw new IllegalArgumentException("Invalid button index.");
		}
		return this.buttonList.get(index);
	}

	/**
	 * Returns a reference to the BrailleCell object at the index passed as
	 * argument. Braille Cells are numbered left to right and top to bottom as
	 * they appear in the frame, from 0 to (brailleCellNumber - 1),
	 * brailleCellNumber being the number of BrailleCell objects initialized by
	 * the constructor.
	 * 
	 * 
	 * @param index
	 *            the index of the BrailleCell object whose reference is to be
	 *            returned
	 * @return reference to the BrailleCell object at the index passed as
	 *         argument
	 * @throws IllegalArgumentException
	 *             if the index is negative or equal to or bigger than
	 *             brailleCellNumber (the number of Braille Cells initialized)
	 */
	public BrailleCell getCell(int index) {
		if (index >= this.brailleCellNumber || index < 0) {
			throw new IllegalArgumentException("Invalid cell index.");
		}
		return this.brailleList.get(index);
	}

	/**
	 * Clears all the Braille Cells, i.e lowers all the pins for all of them,
	 * effectively making them display nothing.
	 */
	public void clearAllCells() {

		for (int i = 0; i < this.brailleCellNumber; i++) {
			this.brailleList.get(i).clear();

		}
	}

	/**
	 * Displays the string passed as argument on all the Braille Cells
	 * If the string is shorter than the total number of Braille Cells, the
	 * remaining cells are cleared. However, if the string is longer it only displays
	 * the part of it up to however many Braille Cells there are and ignores the
	 * rest.
	 * 
	 * @param aString
	 *            the String to be displayed on the Braille Cells
	 */
	public void displayString(String aString) {
		this.clearAllCells();
		for (int i = 0; i < this.brailleCellNumber && i < aString.length(); i++) {
			this.brailleList.get(i).displayCharacter(aString.charAt(i));
		}
	}

}
