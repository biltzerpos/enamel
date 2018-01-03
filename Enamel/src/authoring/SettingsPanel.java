package authoring;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * SettingsPanel is a Panel, added by the GUI class, which contains only the
 * file header information. The three components in the file header (cells,
 * buttons, and title) are required by the Player implementation in order to be
 * a valid file.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-03-03
 */
public class SettingsPanel extends JPanel {
	private static final long serialVersionUID = -3823080553310951237L;
	public JTextField cellField;
	private JTextField buttonField;
	private JTextField titleField;

	/**
	 * Create a new Settings Panel with all the fields and labels already set
	 * up. This method should only be called by the GUI and never used directly.
	 */
	public SettingsPanel() {
		setLayout(new GridBagLayout());

		// Create labels
		JLabel cellLabel = new JLabel("Cells:", SwingConstants.RIGHT);
		JLabel buttonLabel = new JLabel("Buttons:", SwingConstants.RIGHT);
		JLabel titleLabel = new JLabel("Title:", SwingConstants.RIGHT);

		// Create buttons
		cellField = new JTextField(10);
		buttonField = new JTextField();
		titleField = new JTextField();
		
		cellField.setEditable(false);
		buttonField.setEditable(false);
		titleField.setEditable(false);
		
		// Set the field sizes so that they don't take up much room
		cellField.setMinimumSize(new Dimension(200, 15));
		buttonField.setMinimumSize(new Dimension(200, 15));
		titleField.setMinimumSize(new Dimension(200, 15));

		// Create a grid starting at (0,0)
		GridBagConstraints gbc = new GridBagConstraints();

		// Place all the labels in the left column
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(cellLabel, gbc);

		gbc.gridy++;
		add(buttonLabel, gbc);

		gbc.gridy++;
		add(titleLabel, gbc);

		// Reset the anchor points and place all the fields in the right column
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx++;
		gbc.gridy = 0;
		add(cellField, gbc);

		gbc.gridy++;
		add(buttonField, gbc);

		gbc.gridy++;
		add(titleField, gbc);
	}

	/**
	 * Obtain the value of the Braille Cell field
	 *
	 * @return String containing the user input for the Cell field
	 */
	public String getCellField() {
		return cellField.getText();
	}

	/**
	 * Set the value of the Cell field. Should only be fired from the
	 * interactive dialog
	 *
	 * @param fieldText
	 *            New value of the input field
	 */
	public void setCellField(String fieldText) {
		this.cellField.setText(fieldText);
	}

	/**
	 * Obtain the value of the Button Count field
	 *
	 * @return String containing the user input for the Button field
	 */
	public String getButtonField() {
		return buttonField.getText();
	}

	/**
	 * Set the value of the Button field. Should only be fired from the
	 * interactive dialog
	 *
	 * @param fieldText
	 *            New value of the input field
	 */
	public void setButtonFieldText(String fieldText) {
		this.buttonField.setText(fieldText);
	}

	/**
	 * Obtain the value of the Title field
	 *
	 * @return String containing the user input for the Title field
	 */
	public String getTitleField() {
		return titleField.getText();
	}

	/**
	 * Set the value of the Title field. Should only be fired from the
	 * interactive dialog
	 *
	 * @param fieldText
	 *            New value of the input field
	 */
	public void setTitleField(String fieldText) {
		this.titleField.setText(fieldText);
	}

}
