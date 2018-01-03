package authoring;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import commands.SoundCommand;
import listeners.SaveListener;
import listeners.LoadListener;
import listeners.NewButtonListener;
import listeners.NewQuestionListener;
import listeners.NewScenarioListener;
import listeners.TestListener;

/**
 * RightPanel is the class responsible for creating the control buttons as well
 * as implementing the simple listeners. It adds the relevant hooks to the more
 * complex listeners (located in the listeners package). It is instantiated by
 * the GUI during runtime.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-03-17
 *
 */
public class RightPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 4894342125883442327L;
	public JButton btnStart = new JButton("Start Recording");
	public JButton btnStop = new JButton("Stop Recording");
	public JButton readFile = new JButton("Read Audio File");
	public JButton btnMoveUp = new JButton("Move Item Up (Control W)");
	public JButton btnMoveDown = new JButton("Move Item Down (Control S)");
	public JButton btnDelete = new JButton("Delete Item (Control D)");
	public JButton btnNew = new JButton("New Item (Control N)");
	public JButton btnSave = new JButton("Save (Control E)");
	public JButton btnLoad = new JButton("Load (Control I)");
	public JButton btnNewScenario = new JButton("New Scenario (Control Shift N)");
	public JButton btnNewQuestion = new JButton("New Question (Control M)");
	public JButton btnTestScenario = new JButton ("Test Scenario (Control T)");
	private GUI gui;

	private static final String FONT_FACE = "Arial";
	private static final int FONT_SIZE = 12;

	JButton openButton, saveButton;
	JTextArea log;
	JFileChooser fc = new JFileChooser();

	/**
	 * Create a new right panel of the GUI
	 *
	 * @param gui
	 *            Reference to the overall GUI object
	 * @param mapper
	 *            Reference to the shared colour mapper object
	 */
	public RightPanel(GUI gui, ColourMapper mapper) {
		super(new GridLayout(15, 1));
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Commands"));
		//this.accessibleContext.setAccessibleName("lol");

		this.getAccessibleContext().getAccessibleRole(); 
		// Set font sizes
		btnNewScenario.setFont(new Font(FONT_FACE, Font.PLAIN, FONT_SIZE));
		btnLoad.setFont(new Font(FONT_FACE, Font.PLAIN, FONT_SIZE));
		btnSave.setFont(new Font(FONT_FACE, Font.PLAIN, FONT_SIZE));
		btnStart.setFont(new Font(FONT_FACE, Font.PLAIN, FONT_SIZE));
		btnStop.setFont(new Font(FONT_FACE, Font.PLAIN, FONT_SIZE));
		readFile.setFont(new Font(FONT_FACE, Font.PLAIN, FONT_SIZE));
		btnMoveUp.setFont(new Font(FONT_FACE, Font.PLAIN, FONT_SIZE));
		btnMoveDown.setFont(new Font(FONT_FACE, Font.PLAIN, FONT_SIZE));
		btnDelete.setFont(new Font(FONT_FACE, Font.PLAIN, FONT_SIZE));
		btnNew.setFont(new Font(FONT_FACE, Font.PLAIN, FONT_SIZE));
		btnNewQuestion.setFont(new Font(FONT_FACE, Font.PLAIN, FONT_SIZE));
		btnTestScenario.setFont(new Font(FONT_FACE, Font.PLAIN, FONT_SIZE));

		// Add the buttons
		add(btnNew);
		add(btnNewQuestion);
		//add(btnStart);
		//add(btnStop);
		//add(readFile);
		add(btnMoveUp);
		add(btnMoveDown);
		add(btnDelete);
		add(btnLoad);
		add(btnSave);
		add(btnNewScenario);
		add(btnTestScenario);

		// Hook in the button listeners for the buttons
		btnStart.addActionListener(this);
		btnStop.addActionListener(this);
		btnMoveUp.addActionListener(this);
		btnMoveDown.addActionListener(this);
		btnNew.addActionListener(new NewButtonListener(gui));
		btnDelete.addActionListener(this);
		readFile.addActionListener(this);
		btnSave.addActionListener(new SaveListener(gui));
		btnLoad.addActionListener(new LoadListener(gui, mapper));
		btnNewScenario.addActionListener(new NewScenarioListener(gui));
		btnNewQuestion.addActionListener(new NewQuestionListener(gui, mapper));
		btnTestScenario.addActionListener(new TestListener(gui));
		
		

		// Do not allow this component to enlarge ever
		this.setMaximumSize(this.getPreferredSize());

		// Enabling to be false so that user initializes with a New Scenario
		// first
		btnStop.setEnabled(false);
		btnNew.setEnabled(false);
		btnSave.setEnabled(false);
		readFile.setEnabled(false);
		btnStart.setEnabled(false);
		btnNewQuestion.setEnabled(false);

		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Cases for button events
		if (e.getSource() == btnStart) {
			gui.setAudioThread(new ThreadRunnable());
			gui.getAudioThread().start();
			btnStop.setEnabled(true);
			btnStart.setEnabled(false);
		} else if (e.getSource() == btnStop) {
			gui.getAudioThread().stopRecording();
			btnStart.setEnabled(true);
			btnStop.setEnabled(false);
		} else if (e.getSource() == btnMoveUp) {
			gui.getLeftPanel().moveUp();
		} else if (e.getSource() == btnMoveDown) {
			gui.getLeftPanel().moveDown();
		} else if (e.getSource() == btnDelete) {
			gui.getLeftPanel().deleteItem();
		} else if (e.getSource() == readFile) {
			this.fileChooser();
		}

		// A call to a method that programmatically checks button status
		gui.getLeftPanel().recalculateButtonStatus();
	}

	/**
	 * A method that incorporates the ability to choose and open a file in the
	 * computer's directory
	 */
	private void fileChooser() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("wav files (*.wav)", "wav");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile());
			String name = chooser.getSelectedFile().toString();
			ReadFile read = new ReadFile();
			gui.getLeftPanel().addItem(new SoundCommand(name));
			read.playSound(name);
		}
	}

	/**
	 * A method that changes the availability of a btnMoveUp button
	 *
	 * @param status
	 *            parameter that is either true or false
	 */
	public void setUp(boolean status) {
		btnMoveUp.setEnabled(status);
	}

	/**
	 * A method that changes the availability of a btnMoveDown button
	 *
	 * @param status
	 *            parameter that is either true or false
	 */
	public void setDown(boolean status) {
		btnMoveDown.setEnabled(status);
	}

	/**
	 * A method that changes the availability of a btnDelete button
	 *
	 * @param status
	 *            parameter that is either true or false
	 */
	public void setDelete(boolean status) {
		btnDelete.setEnabled(status);
	}

	/**
	 * A method that changes the availability of a btnStart button
	 *
	 * @param status
	 *            parameter that is either true or false
	 */
	public void setStart(boolean status) {
		btnStart.setEnabled(status);
	}

	/**
	 * A method that changes the availability of a readFile button
	 *
	 * @param status
	 *            parameter that is either true or false
	 */
	public void setReadFile(boolean status) {
		readFile.setEnabled(status);
	}

	/**
	 * A method that changes the availability of a btnExport button
	 *
	 * @param status
	 *            parameter that is either true or false
	 */
	public void setExport(boolean status) {
		btnSave.setEnabled(status);
	}

	/**
	 * A method that changes the availability of a btnNew button
	 *
	 * @param status
	 *            parameter that is either true or false
	 */
	public void setNew(boolean status) {
		btnNew.setEnabled(status);
	}

	/**
	 * A method that changes the availability of a btnNewQuestion button
	 *
	 * @param status
	 *            parameter that is either true or false
	 */
	public void setNewQuestion(boolean status) {
		btnNewQuestion.setEnabled(status);
	}

}
