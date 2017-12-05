package listeners;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.accessibility.Accessible;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import authoring.ColourMapper;
import authoring.GUI;
import commands.GoHereCommand;
import commands.PauseCommand;
import commands.PlayerCommand;
import commands.ResetButtonCommand;
import commands.SetStringCommand;
import commands.SkipButtonCommand;
import commands.SkipCommand;
import commands.TTSCommand;
import commands.UserInputCommand;

/**
 * This class is used as an action listener whenever the "New Question" button
 * is clicked. It serves as a way to create question by asking user about
 * introduction text, braille text, repeating text, correct button, text for
 * incorrect.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 4/3/2017
 *
 */
public class NewQuestionListener extends JPanel implements ActionListener, Accessible {
	private static final long serialVersionUID = 7443038348707836054L;
	private GUI gui;
	private JComboBox<String> buttons;
	private JTextArea introField;
	private JTextField brailleField;
	private JTextArea repeatField;
	private JTextField buttonField;
	private ColourMapper mapper;

	/**
	 * Create the NewQuestionListener with a reference to the current GUI
	 * object. Access to this object is required in order to access the left
	 * panel
	 *
	 * @param gui
	 *            Instance of currently running GUI
	 * @param mapper
	 *            Reference to the common instance of the colourmapper
	 */
	public NewQuestionListener(GUI gui, ColourMapper mapper) {
		this.gui = gui;
		this.mapper = mapper;
		
		this.getAccessibleContext().setAccessibleDescription("");

		setLayout(new GridBagLayout());

		JLabel introLabel = new JLabel("Introduction Text:", SwingConstants.RIGHT);
		JLabel brailleLabel = new JLabel("Braille Text:", SwingConstants.RIGHT);
		JLabel correctLabel = new JLabel("Correct Button:", SwingConstants.RIGHT);
		JLabel incorrectLabel = new JLabel("Text For Incorrect:", SwingConstants.RIGHT);

		// Creates a text area that wraps properly and scrolls vertically only
		introField = new JTextArea(5, 18);
		introField.setLineWrap(true);
		JScrollPane introPane = new JScrollPane(introField);
		introField.getAccessibleContext().setAccessibleDescription("This is where you ask the question");
		patch(introField);
		
		
		//Set initial focus to introField
		introField.addAncestorListener(new AncestorListener(){

			@Override
			public void ancestorAdded(AncestorEvent event) {
				// TODO Auto-generated method stub
				introField.requestFocusInWindow();
				
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			
			
			
			
		});
		
		
		//Creates a text area that wraps properly and scrolls vertically only for incorrect text
		repeatField = new JTextArea(5, 18);
		repeatField.setLineWrap(true);
		JScrollPane repeatPane = new JScrollPane(repeatField);
		repeatField.getAccessibleContext().setAccessibleDescription("This is what is said everytime an incorrect answer is given");
		patch(repeatField);
		
		
		brailleField = new JTextField();
		brailleField.getAccessibleContext().setAccessibleDescription("This is what will be displayed on the braille cells");
		buttonField = new JTextField();
		this.buttons = new JComboBox<String>();
		this.buttons.getAccessibleContext().setAccessibleDescription("This is the correct answer button for this question");

		introField.setMinimumSize(new Dimension(200, 15));
		brailleField.setMinimumSize(new Dimension(200, 15));
		buttonField.setMinimumSize(new Dimension(200, 15));
		repeatField.setMinimumSize(new Dimension(200, 15));
		buttons.setMinimumSize(new Dimension(200, 15));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(introLabel, gbc);

		gbc.gridy++;
		add(brailleLabel, gbc);

		gbc.gridy++;
		add(correctLabel, gbc);

		gbc.gridy++;
		add(incorrectLabel, gbc);

		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx++;
		gbc.gridy = 0;
		add(introPane, gbc);

		gbc.gridy++;
		add(brailleField, gbc);

		gbc.gridy++;
		add(buttons, gbc);

		gbc.gridy++;
		add(repeatPane, gbc);
		
		
		/*gui.addWindowFocusListener(new WindowAdapter() {
		    public void windowGainedFocus(WindowEvent e) {
		        introField.requestFocusInWindow();
		    }
		});*/
	}

	
	
	public static void patch(Component c) {
		c.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
		c.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
    }
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Generate a psuedorandom identifier
		Random r = new Random();
		String randomLabel = "" + r.nextInt(500);
		String strNumOfButtons = gui.getSettingsPanel().getButtonField();
		if (strNumOfButtons == null || strNumOfButtons.isEmpty()) {
			return;
		}

		int numOfButtons = Integer.parseInt(strNumOfButtons);
		buttons.removeAllItems();
		for (int i = 0; i < numOfButtons; i++) {
			buttons.addItem("Button " + (i + 1));
		}

		int result = JOptionPane.showConfirmDialog(null, this, "Enter Question Details", JOptionPane.OK_CANCEL_OPTION);
		if (result != JOptionPane.OK_OPTION) {
			return;
		}


		// At this point we have enough information to create a basic question.
		// It is composed of TTS, Braille string, a repeat section, a repeat
		// button, some error
		ArrayList<PlayerCommand> questionCommands = new ArrayList<>();
		questionCommands.add(new ResetButtonCommand(""));
		questionCommands.add(new TTSCommand(introField.getText()));
		questionCommands.add(new PauseCommand("1"));

		// Set the Braille fields
		questionCommands.add(new SetStringCommand(brailleField.getText()));

		// Start of the repeat section
		questionCommands.add(new GoHereCommand(randomLabel + "-start"));

		// Loop through all the buttons defined
		PlayerCommand holder;
		for (int i = 0; i < numOfButtons; i++) {
			if (i != buttons.getSelectedIndex()) {
				// All buttons that are wrong will just repeat the question
				// (bad)
				holder = new SkipButtonCommand("" + i + " " + randomLabel + "-bad");
				questionCommands.add(holder);
			} else {
				// The correct button skips to the end
				holder = new SkipButtonCommand("" + i + " " + randomLabel + "-good");
				questionCommands.add(holder);
			}
		}
		// Adds UserInputCommand to wait for button presses
		questionCommands.add(new UserInputCommand());

		// Labels for bad
		questionCommands.add(new GoHereCommand("" + randomLabel + "-bad"));

		questionCommands.add(new TTSCommand(repeatField.getText()));
		questionCommands.add(new SkipCommand(randomLabel + "-start"));

		// Label for good
		holder = new GoHereCommand("" + randomLabel + "-good");
		questionCommands.add(holder);

		// Set the colors
		mapper.addColourMapping(questionCommands);

		for (PlayerCommand pc : questionCommands) {
			gui.getLeftPanel().addItem(pc);
		}
	}

}
