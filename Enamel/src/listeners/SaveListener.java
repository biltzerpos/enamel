package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;

import authoring.GUI;
import commands.PlayerCommand;

/**
 * This class is used as an action listener whenever the "Export" button is
 * clicked. It allows the user to output the settings and commands used by the
 * user.Allowing the user to save a copy of the scenario they are creating such
 * as the number of Cells and Buttons and can define what the title of the
 * scenario should be.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-04-02
 */
public class SaveListener implements ActionListener {

	private GUI gui;
	String newLine = System.getProperty("line.separator");

	/**
	 * Create an export listener with a reference to the parent GUI.
	 *
	 * @param gui
	 *            A reference to the parent GUI, needed in order to properly
	 *            access the command list
	 */
	public SaveListener(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StringBuilder sb = new StringBuilder();
		// Build the file header first
		sb.append("Cell " + gui.getSettingsPanel().getCellField() + newLine);
		sb.append("Button " + gui.getSettingsPanel().getButtonField() + newLine);
		sb.append(gui.getSettingsPanel().getTitleField() + newLine + newLine);

		// Get the list of commands for export
		List<PlayerCommand> list = gui.getLeftPanel().getList();
		sb.append(parseCommands(list));

		// sb now contains the export file contents
		JFileChooser save = new JFileChooser();

		FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("text files (*.txt)", "txt");
		save.addChoosableFileFilter(txtFilter);
		save.setFileFilter(txtFilter);

		save.showSaveDialog(null);

		// Check to see if any file was set
		File file = save.getSelectedFile();
		if (file == null) {
			return;
		}

		// Get the file and fix the extension if it's wrong
		if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("txt")) {
			file = new File(file.toString() + ".txt");
		}

		exportFile(file, sb.toString());
	}

	/**
	 * Parse a list of commands into serialized strings. Made private once
	 * testing is completed.
	 *
	 * @param list
	 *            Generic ordered list containing all the commands that should
	 *            be serialized
	 *
	 * @return String containing newline-separated serialized commands
	 */
	public String parseCommands(List<PlayerCommand> list) {
		StringBuilder sb = new StringBuilder();

		for (PlayerCommand pc : list) {
			sb.append(pc.serialize());
			sb.append(newLine);
		}

		return sb.toString();
	}

	private void exportFile(File file, String contents) {
		try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "8859_1"));) {
			out.append(contents);
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
