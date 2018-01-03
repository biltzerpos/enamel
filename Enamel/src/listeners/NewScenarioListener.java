package listeners;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import authoring.GUI;

/**
 * NewScenarioListener class
 *
 * This class is used as an action listener whenever the "New Scenario" button
 * is clicked. It serves as a way to initialize the application. Allowing the
 * user to set the bounds of the scenario they are creating such as the number
 * of Cells and Buttons and can define what the title of the scenario should be.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 4/3/2017
 *
 */
public class NewScenarioListener implements ActionListener {

	// A GUI object to utilize the panels
	private GUI gui;

	// Labels and Textfields to be used to set their respective fields
	private JLabel cellLabel = new JLabel("Cell: ");
	private JLabel btnLabel = new JLabel("Button: ");
	private JLabel titleLabel = new JLabel("Title: ");
	private JTextField celltxt = new JTextField(5);
	private JTextField btntxt = new JTextField(5);
	private JTextField titletxt = new JTextField(5);
	private JPanel myPanel = new JPanel(new GridLayout(0, 1, 5, 5));

	/**
	 * Create the NewScenarioListener with a reference to the base GUI object
	 * (required to access the button panel)
	 *
	 * @param gui
	 *            Instance of currently running GUI
	 */
	public NewScenarioListener(GUI gui) {
		this.gui = gui;
		celltxt.getAccessibleContext().setAccessibleName("Type the number of Braille Cells you want in your Scenario.");
		btntxt.getAccessibleContext().setAccessibleName("Type the number of buttons you want in your Scenario.");
		titletxt.getAccessibleContext().setAccessibleName("Type the title of your Scenario.");
	}

	/**
	 * This method builds a pane that contains 3 fields that take input from a
	 * user to set parameters in the Settings Panel fields
	 */
	private void scenarioBuilder() {
		// call to the clearItem() method that clears out all events in the
		// scenario
		gui.getLeftPanel().clearItem();

		// Adding the labels and textfields to create a layout for pane
		myPanel.add(cellLabel);
		myPanel.add(celltxt);
		myPanel.add(btnLabel);
		myPanel.add(btntxt);
		myPanel.add(titleLabel);
		myPanel.add(titletxt);
		
		// Sets the default focus on the Cells text field.
		celltxt.addAncestorListener(new AncestorListener(){

			@Override
			public void ancestorAdded(AncestorEvent arg0) {
				// TODO Auto-generated method stub
				celltxt.requestFocusInWindow();
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
				// TODO Auto-generated method stub
				
			}

			
			
		});;
		

		// Creates a Confirmation Pane with an "OK" and "CANCEL" option
		int result = JOptionPane.showConfirmDialog(null, myPanel, "Enter the Scenario parameters",
				JOptionPane.OK_CANCEL_OPTION);
		

		if (result == JOptionPane.OK_OPTION) {
			// Checks to see if the fields are empty if user hits "OK"
			if (celltxt.getText().isEmpty() || !celltxt.getText().matches("\\d") || btntxt.getText().isEmpty() || !btntxt.getText().matches("\\d") || titletxt.getText().isEmpty()) {
				// Shows an error pane if the user does hit "OK" with empty
				// fields
				JOptionPane.showMessageDialog(null, "One or more fields are empty!", "Missing fields!",
						JOptionPane.ERROR_MESSAGE);
			} else {
				// Enables buttons to true only after the user initializes
				gui.getRightPanel().setStart(true);
				gui.getRightPanel().setNew(true);
				gui.getRightPanel().setExport(true);
				gui.getRightPanel().setReadFile(true);
				gui.getRightPanel().setNewQuestion(true);
			}

			// Takes the text that was input by the user in the fields and sets
			// them in the SettingsPanel fields
			gui.getSettingsPanel().setCellField(celltxt.getText());
			gui.getSettingsPanel().setButtonFieldText(btntxt.getText());
			gui.getSettingsPanel().setTitleField(titletxt.getText());

		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// A check to see if the Scenario pane is empty
		if (gui.getLeftPanel().elementCheck()) {

			// Clears the text fields in the setting panel
			gui.getSettingsPanel().setCellField("");
			gui.getSettingsPanel().setButtonFieldText("");
			gui.getSettingsPanel().setTitleField("");

			// Clears the text in the fields when "New Scenario" is pressed
			celltxt.setText("");
			btntxt.setText("");
			titletxt.setText("");

			// Calls the scenarioBuilder() method
			scenarioBuilder();

		} else {

			// If the scenario isn't empty, a warning message is displayed
			int result = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to start a new Scenario?" + " All current changes will be lost", "Warning!",
					JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				gui.getSettingsPanel().setCellField("");
				gui.getSettingsPanel().setButtonFieldText("");
				gui.getSettingsPanel().setTitleField("");
				celltxt.setText("");
				btntxt.setText("");
				titletxt.setText("");
				scenarioBuilder();
			}
		}
	}
}
