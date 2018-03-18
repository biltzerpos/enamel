package enamel;

import java.awt.BorderLayout;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
 * @author ENAMEL team: Sunjik Lee, Li Yin, Vassilios Tzerpos.
 */
public class VisualPlayer extends Player {

	
	private JFrame frame;
	List<BrailleCellPanel> brailleCellPanelList = new LinkedList<BrailleCellPanel>();
	LinkedList<JButton> buttonList = new LinkedList<JButton>();
	JPanel southPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	int[] pinIndex = {0, 2, 4, 1, 3, 5, 6, 7};

	
	/**
	 * Creates and displays a window with <code>brailleCellNumber</code> Braille
	 * cells and <code>jbuttonNumber</code> buttons. The two parameters must be
	 * positive integers.
	 * 
	 * @param brailleCellNumber
	 *            the number of braille cells the Simulator should have
	 * @param buttonNumber
	 *            the number of buttons the Simulator should have
	 * @throws IllegalArgumentException
	 *             if one or both of the two parameters is negative or 0
	 */
	public VisualPlayer(int brailleCellNumber, int buttonNumber)  {

		super(brailleCellNumber, buttonNumber);

		SwingUtilities.invokeLater(new Runnable() {
			//@Override
			public void run() {
				frame = new JFrame();
				frame.setTitle("Simulator");
				frame.setBounds(100, 100, 627, 459);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.getContentPane().setLayout(new BorderLayout());

				for (int i = 0; i < brailleCellNumber; i++) {

				    BrailleCellPanel bcp = new BrailleCellPanel();
				    brailleCellPanelList.add(bcp);
				    centerPanel.add(bcp);

					if (i == (brailleCellNumber - 1))
						frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
				}

				for (int i = 0; i < buttonNumber; i++) {
					JButton button = new JButton("" + (i + 1));
					buttonList.add(button);
					southPanel.add(button);
				}

				frame.getContentPane().add(southPanel, BorderLayout.SOUTH);
				frame.repaint();
				frame.setVisible(true);
				refresh();
			}
		});
	}

	/**
	 * Returns a reference to the button at the index passed as argument. The
	 * main purpose of providing this method is so the client can add
	 * actionListeners to the button. Buttons are numbered from left to right as
	 * they appear in the frame, from 0 to (bbuttonNumber-1), bbuttonNumber
	 * being the number of buttons initialized by the constructor.
	 * 
	 * @param index
	 *            the index of the button to be returned
	 * @return reference to the JButton object at the index passed as argument
	 * @throws IllegalArgumentException
	 *             if the index is negative or equal to or bigger than
	 *             buttonNumber (the number of buttons initialized)
	 */
	public JButton getButton(int index) {
		if (index >= this.buttonNumber || index < 0) {
			throw new IllegalArgumentException("Invalid button index.");
		}
		return this.buttonList.get(index);
	}

	/**
     * Refreshes the display to match the current state of the 
     * instantiated BrailleCell object. For the VisualPlayer class,
     * this method loops through the jRadioButton array and sets
     * the corresponding jRadioButton pins to <code>setSelected(true)</code>
     * or <code>setSelected(false)</code>, matching it with the 
     * brailleList's BrailleCell object's current state of boolean pins.
     */
	@Override
	public void refresh() {

	    for (int j = 0; j < brailleList.size(); j++) 
	    { 
	        BrailleCell cell = this.brailleList.get(j);
	        for (int i = 0; i < 8 ; i++) {
	            brailleCellPanelList.get(j).setRadioButtons(cell.listOfPins);
	        }
	        this.centerPanel.repaint();
	        frame.revalidate();
	        frame.repaint();
	    }
	}
	
	/**
     * Adds an ActionListener to the JButton at the specified index of buttonList.
     * The index must be between 0 and buttonNumber.
     * <p>
     * The actionPerformed method requires a reference to the ScenarioParser,
     * needed for it to call ScenarioParser.skip() method as well as have
     * access to the userInput field. Pressing this key will skip to the
     * specified area in the scenario file.
     * 
     * @param index
     *            the index of the KeyListener to be added.
     * @param param
	 * 			the String in ScenarioParser to skip to, needed for ScenarioParser's <code>skip(String indicator)</code>
	 * 			method
	 * @param sp
	 * 			the reference to the current ScenarioParser object          
     */
	@Override
	public void addSkipButtonListener(int index, String param, ScenarioParser sp) {

		buttonList.get(index).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (sp.userInput) {
					sp.skip(param);
					//logger.log(Level.INFO, "Button {0} was pressed", index+1);
					sp.userInput = false;
				}
			}
		});
	}
	
	/**
      * Adds an ActionListener to the JButton at the specified index of buttonList.
     * The index must be between 0 and buttonNumber.
     * The ActionListener requires a reference of the ScenarioParser,
     * needed for it to call <code>ScenarioParser.repeatText()</code> method as well as have
     * access to the userInput field. Pressing this key will repeat the
     * speech of the text specified in the scenario file.
     * 
    * @param index
	 * 			the index of the button to add the KeyListener to
	 * @param sp
	 * 			the reference to the current ScenarioParser object
	 */
	@Override
	public void addRepeatButtonListener(int index, ScenarioParser sp) {
		
		getButton(index).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// This if statement is used to allow the user to press the
				// button
				// after all the text has been read and that the program is
				// expecting
				// a user input.
				if (sp.userInput) {
					repeat++;
					logger.log(Level.INFO, "Repeat Button was pressed.");
					logger.log(Level.INFO, "Repeat Button was pressed {0} times", repeat);
					sp.repeatText();
				}
			}
		});
	}
	
	/**
     * Removes the ActionListener from the button at specified index of buttonList passed as argument. 
     * The index must be between 0 and buttonNumber.
     * 
     * @param index
     *            the index of the KeyListener to be removed.
     * @throws IllegalArgumentException
     *             if the index is negative or equal to or bigger than
     *             buttonNumber (the number of buttons initialized)
     */
	@Override
	public void removeButtonListener(int index) {
		if (index >= this.buttonNumber || index < 0) {
            throw new IllegalArgumentException("Invalid index.");
        }
		ActionListener[] aList = getButton(index).getActionListeners();
		if (aList.length > 0) {
			for (int x = 0; x < aList.length; x++) {
				getButton(index).removeActionListener(getButton(index).getActionListeners()[x]);
			}
		}
	}
}