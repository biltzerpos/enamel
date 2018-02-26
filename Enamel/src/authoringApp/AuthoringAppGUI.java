package authoringApp;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenu;

/**
 * The GUI of the authoring application.
 * 
 * @author Xiahan Chen, Huy Hoang Minh Cu, Qasim Mahir
 */
public class AuthoringAppGUI extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 103899240155095320L;
	private HashMap<String, Component> compMap = new HashMap<String, Component>();

	/**
	 * Initializes the JFrame. Calls methods to initialize components and create
	 * an Array of components within this JFrame.
	 */
	public AuthoringAppGUI() {
		initComponents();
		createCompMap();
	}

	/**
	 * Creates an HashMap of name component pairs for components within this
	 * JFrame.
	 */
	private void createCompMap() {
		ArrayList<Component> compList = getCompList(this);
		for (int i = 0; i < compList.size(); i++) {
			if (compList.get(i).getName() != null) {
				System.out.println(compList.get(i).getName());
				this.compMap.put(compList.get(i).getName(), compList.get(i));
			}
		}
	}

	/**
	 * Recursively adds components and their child components into an ArrayList.
	 * 
	 * @param c
	 *            The parent container.
	 * @return An ArrayList of the parent container and all it's child
	 *         components.
	 */
	public ArrayList<Component> getCompList(final Container c) {
		Component[] compArray = c.getComponents();
		ArrayList<Component> compList = new ArrayList<Component>();
		for (Component comp : compArray) {
			compList.add(comp);
			if (comp instanceof JMenu) {
				for (int i = 0; i < ((JMenu) comp).getMenuComponentCount(); i++) {
					compList.add(((JMenu) comp).getMenuComponent(i));
				}
			} else if (comp instanceof Container) {
				compList.addAll(getCompList((Container) comp));
			}
		}
		return compList;
	}

	/**
	 * @return The HashMap of components with the key being their component
	 *         name.
	 */
	public HashMap<String, Component> getCompMap() {
		return this.compMap;
	}

	/**
	 * Initializes all the components within this JFrame.
	 */
	private void initComponents() {

		scenarioScrollPane = new javax.swing.JScrollPane();
		scenarioPane = new javax.swing.JTextPane();
		inputTextField = new javax.swing.JTextField();
		editRemoveLine = new javax.swing.JButton();
		insertText = new javax.swing.JButton();
		insertSkipButton = new javax.swing.JButton();
		insertRepeatButton = new javax.swing.JButton();
		insertRepeat = new javax.swing.JButton();
		insertUserInput = new javax.swing.JButton();
		insertResetButtons = new javax.swing.JButton();
		insertEndRepeat = new javax.swing.JButton();
		insertSound = new javax.swing.JButton();
		insertLabel = new javax.swing.JLabel();
		insertSeperator = new javax.swing.JSeparator();
		insertKeyPhraseSeperator = new javax.swing.JSeparator();
		insertKeyPhraseLabel = new javax.swing.JLabel();
		removeLabel = new javax.swing.JLabel();
		removeSeperator = new javax.swing.JSeparator();
		insertPause = new javax.swing.JButton();
		consoleLabel = new javax.swing.JLabel();
		displayComboBox = new javax.swing.JComboBox<>();
		displayAddButton = new javax.swing.JButton();
		insertSkip = new javax.swing.JButton();
		authoringAppMenuBar = new javax.swing.JMenuBar();
		fileMenu = new javax.swing.JMenu();
		newMenuItem = new javax.swing.JMenuItem();
		loadScenarioMenuItem = new javax.swing.JMenuItem();
		fileMenuSeperator1 = new javax.swing.JPopupMenu.Separator();
		saveMenuItem = new javax.swing.JMenuItem();
		saveAsMenuItem = new javax.swing.JMenuItem();
		fileMenuSeperator2 = new javax.swing.JPopupMenu.Separator();
		exitMenuItem = new javax.swing.JMenuItem();
		editMenu = new javax.swing.JMenu();
		undoMenuItem = new javax.swing.JMenuItem();
		redoMenuItem = new javax.swing.JMenuItem();
		editMenuSeperator1 = new javax.swing.JPopupMenu.Separator();
		cutMenuItem = new javax.swing.JMenuItem();
		copyMenuItem = new javax.swing.JMenuItem();
		pasteMenuItem = new javax.swing.JMenuItem();
		editMenuSeperator2 = new javax.swing.JPopupMenu.Separator();
		textPaneEditingMenuItem = new javax.swing.JRadioButtonMenuItem();
		runMenu = new javax.swing.JMenu();
		runMenuItem = new javax.swing.JMenuItem();
		loadAndRunMenuItem = new javax.swing.JMenuItem();
		helpMenu = new javax.swing.JMenu();
		ttsMenuItem = new javax.swing.JRadioButtonMenuItem();
		helpContentsMenuItem = new javax.swing.JMenuItem();
		helpMenuSeperator1 = new javax.swing.JPopupMenu.Separator();
		aboutMenuItem = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Authoring App");
		setName("JFrame"); // NOI18N
		setResizable(false);
		setSize(new java.awt.Dimension(1500, 1000));

		scenarioScrollPane.setName("scenarioScrollPane"); // NOI18N


		scenarioPane.setEditable(false);
		scenarioPane.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		scenarioPane.setName("scenarioPane"); // NOI18N
		scenarioScrollPane.setViewportView(scenarioPane);


		inputTextField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
		inputTextField.setForeground(new java.awt.Color(204, 204, 204));
		inputTextField.setText("(Insert text/argument)");
		inputTextField.setName("inputTextField"); // NOI18N
		inputTextField.setToolTipText("this is your console, input your command here");

		editRemoveLine.setText("Remove Line");
		editRemoveLine.setToolTipText("");
		editRemoveLine.setEnabled(false);
		editRemoveLine.setName("editRemoveLine"); // NOI18N
		editRemoveLine.setToolTipText("click here to remove previous line");

		insertText.setText("Text");
		insertText.setEnabled(false);
		insertText.setName("insertText"); // NOI18N
		insertText.setToolTipText("click here to insert plain text");


		insertSkipButton.setText("Skip Button");
		insertSkipButton.setEnabled(false);
		insertSkipButton.setName("insertSkipButton"); // NOI18N
		insertSkipButton.setToolTipText("click here to insert the skip button");

		insertRepeatButton.setText("Repeat Button");
		insertRepeatButton.setEnabled(false);
		insertRepeatButton.setName("insertRepeatButton"); // NOI18N
		insertRepeatButton.setToolTipText("click here to insert the repeat button");

		insertRepeat.setText("Repeat");
		insertRepeat.setEnabled(false);
		insertRepeat.setName("insertRepeat"); // NOI18N
		insertRepeat.setToolTipText("click here to insert the beginning of the repeat loop");

		insertUserInput.setText("User Input");
		insertUserInput.setEnabled(false);
		insertUserInput.setName("insertUserInput"); // NOI18N
		insertUserInput.setToolTipText("click here to create choices");

		insertResetButtons.setText("Reset Buttons");
		insertResetButtons.setEnabled(false);
		insertResetButtons.setName("insertResetButtons"); // NOI18N
		insertResetButtons.setToolTipText("click here to insert the reset button");

		insertEndRepeat.setText("End Repeat");
		insertEndRepeat.setEnabled(false);
		insertEndRepeat.setName("insertEndRepeat"); // NOI18N
		insertEndRepeat.setToolTipText("click here to end the repeat loop");

		insertSound.setText("Sound");
		insertSound.setEnabled(false);
		insertSound.setName("insertSound"); // NOI18N
		insertSound.setToolTipText("click here to insert sound file");

		insertLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		insertLabel.setText("Insert:");
		insertLabel.setName("insertLabel"); // NOI18N

		insertSeperator.setName("insertSeperator"); // NOI18N

		insertKeyPhraseSeperator.setName("insertKeyPhraseSeperator"); // NOI18N

		insertKeyPhraseLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		insertKeyPhraseLabel.setText("Insert Key Phrase:");
		insertKeyPhraseLabel.setName("insertKeyPhraseLabel"); // NOI18N

		removeLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		removeLabel.setText("Edit:");
		removeLabel.setName("editLabel"); // NOI18N

		removeSeperator.setName("editSeperator"); // NOI18N

		insertPause.setText("Pause");
		insertPause.setEnabled(false);
		insertPause.setName("insertPause"); // NOI18N
		insertPause.setToolTipText("click here to insert pause function need to have a time after that");

		consoleLabel.setBackground(new java.awt.Color(255, 255, 255));
		consoleLabel.setText("   Console:");
		consoleLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		consoleLabel.setName("consoleLabel"); // NOI18N

		displayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "Display String", "Display Clear All", "Display Clear Cell", "Display Cell Pins",
						"Display Cell Char", "Display Cell Raise", "Display Cell Lower" }));
		displayComboBox.setToolTipText("");
		displayComboBox.setEnabled(false);
		displayComboBox.setLightWeightPopupEnabled(false);
		displayComboBox.setName("displayComboBox"); // NOI18N

		displayAddButton.setText("+");
		displayAddButton.setEnabled(false);
		displayAddButton.setName("displayAddButton"); // NOI18N

		insertSkip.setText("Skip");
		insertSkip.setEnabled(false);
		insertSkip.setName("insertSkip"); // NOI18N

		authoringAppMenuBar.setName("authoringAppMenuBar"); // NOI18N

		fileMenu.setText("File");
		fileMenu.setName("fileMenu"); // NOI18N
		fileMenu.setToolTipText("File");

		newMenuItem.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
		newMenuItem.setText("New");
		newMenuItem.setName("newMenuItem"); // NOI18N
		fileMenu.add(newMenuItem);

		loadScenarioMenuItem.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
		loadScenarioMenuItem.setText("Load scenario");
		loadScenarioMenuItem.setName("loadScenarioMenuItem"); // NOI18N
		fileMenu.add(loadScenarioMenuItem);

		fileMenuSeperator1.setName("fileMenuSeperator1"); // NOI18N
		fileMenu.add(fileMenuSeperator1);

		saveMenuItem.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
		saveMenuItem.setText("Save");
		saveMenuItem.setToolTipText("");
		saveMenuItem.setEnabled(false);
		saveMenuItem.setName("saveMenuItem"); // NOI18N
		fileMenu.add(saveMenuItem);

		saveAsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
				java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		saveAsMenuItem.setText("Save as..");
		saveAsMenuItem.setEnabled(false);
		saveAsMenuItem.setName("saveAsMenuItem"); // NOI18N
		fileMenu.add(saveAsMenuItem);

		fileMenuSeperator2.setName("fileMenuSeperator"); // NOI18N
		fileMenu.add(fileMenuSeperator2);

		exitMenuItem.setText("Exit");
		exitMenuItem.setName("exitMenuItem"); // NOI18N
		fileMenu.add(exitMenuItem);

		authoringAppMenuBar.add(fileMenu);

		editMenu.setText("Edit");
		editMenu.setName("editMenu"); // NOI18N

		undoMenuItem.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
		undoMenuItem.setText("Undo");
		undoMenuItem.setEnabled(false);
		undoMenuItem.setName("undoMenuItem"); // NOI18N
		editMenu.add(undoMenuItem);

		redoMenuItem.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
		redoMenuItem.setText("Redo");
		redoMenuItem.setEnabled(false);
		redoMenuItem.setName("redoMenuItem"); // NOI18N
		editMenu.add(redoMenuItem);

		editMenuSeperator1.setName("editMenuSeperator"); // NOI18N
		editMenu.add(editMenuSeperator1);

		cutMenuItem.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
		cutMenuItem.setText("Cut");
		cutMenuItem.setEnabled(false);
		cutMenuItem.setName("cutMenuItem"); // NOI18N
		editMenu.add(cutMenuItem);

		copyMenuItem.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
		copyMenuItem.setText("Copy");
		copyMenuItem.setEnabled(false);
		copyMenuItem.setName("copyMenuItem"); // NOI18N
		editMenu.add(copyMenuItem);

		pasteMenuItem.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
		pasteMenuItem.setText("Paste");
		pasteMenuItem.setEnabled(false);
		pasteMenuItem.setName("pasteMenuItem"); // NOI18N
		editMenu.add(pasteMenuItem);

		editMenuSeperator2.setName("editMenuSeperator2"); // NOI18N
		editMenu.add(editMenuSeperator2);

		textPaneEditingMenuItem.setText("Text pane editing");
		textPaneEditingMenuItem.setToolTipText("");
		textPaneEditingMenuItem.setName("textPaneEditingMenuItem"); // NOI18N
		editMenu.add(textPaneEditingMenuItem);
		textPaneEditingMenuItem.getAccessibleContext().setAccessibleName("");

		authoringAppMenuBar.add(editMenu);

		runMenu.setText("Run");
		runMenu.setName("runMenu"); // NOI18N

		runMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
		runMenuItem.setText("Run");
		runMenuItem.setEnabled(false);
		runMenuItem.setName("runMenuItem"); // NOI18N
		runMenu.add(runMenuItem);

		loadAndRunMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11,
				java.awt.event.InputEvent.CTRL_MASK));
		loadAndRunMenuItem.setText("Load & run");
		loadAndRunMenuItem.setToolTipText("");
		loadAndRunMenuItem.setName("loadAndRunMenuItem"); // NOI18N
		runMenu.add(loadAndRunMenuItem);

		authoringAppMenuBar.add(runMenu);

		helpMenu.setText("Help");
		helpMenu.setName("helpMenu"); // NOI18N

		ttsMenuItem.setSelected(true);
		ttsMenuItem.setText("Text-to-speech");
		ttsMenuItem.setActionCommand("Text-to-speech ");
		ttsMenuItem.setName("ttsMenuItem"); // NOI18N
		helpMenu.add(ttsMenuItem);

		helpContentsMenuItem.setText("Help contents");
		helpContentsMenuItem.setName("helpContentsMenuItem"); // NOI18N
		helpMenu.add(helpContentsMenuItem);

		helpMenuSeperator1.setName("helpMenuSeperator1"); // NOI18N
		helpMenu.add(helpMenuSeperator1);

		aboutMenuItem.setText("About");
		aboutMenuItem.setName("aboutMenuItem"); // NOI18N
		helpMenu.add(aboutMenuItem);

		authoringAppMenuBar.add(helpMenu);

		setJMenuBar(authoringAppMenuBar);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(inputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 802,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(consoleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 800,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(scenarioScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 800,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup()
												.addComponent(displayComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(displayAddButton))
								.addComponent(editRemoveLine, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(insertSkipButton, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(insertRepeatButton, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(insertRepeat, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(insertUserInput, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(insertResetButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(insertEndRepeat, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(insertSound, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(insertLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(insertKeyPhraseLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 347,
										Short.MAX_VALUE)
								.addComponent(insertText, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(insertSeperator).addComponent(insertKeyPhraseSeperator)
								.addComponent(removeSeperator)
								.addComponent(insertPause, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(insertSkip, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(removeLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap()));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addGroup(layout
										.createSequentialGroup()
										.addComponent(insertLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(insertSeperator, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(5, 5, 5)
										.addComponent(insertText, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(insertKeyPhraseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(insertKeyPhraseSeperator, javax.swing.GroupLayout.PREFERRED_SIZE,
												10, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(1, 1, 1)
										.addComponent(insertPause, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(insertSkipButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(insertSkip, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(insertUserInput, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(insertRepeatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(insertRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(insertEndRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(insertResetButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(insertSound, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(displayAddButton, javax.swing.GroupLayout.PREFERRED_SIZE,
														40, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(displayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE,
														40, javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addComponent(scenarioScrollPane))
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(6, 6, 6)
												.addComponent(removeLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(removeSeperator, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup()
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(consoleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
														javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(editRemoveLine, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(layout.createSequentialGroup()
												.addComponent(inputTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
														41, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(3, 3, 3)))
								.addContainerGap()));

		pack();
	}

	/**
	 * Sets the style of the GUI and calls the constructor.
	 * 
	 * @param args
	 *            unused
	 */
	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(AuthoringAppGUI.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(AuthoringAppGUI.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(AuthoringAppGUI.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(AuthoringAppGUI.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new AuthoringAppGUI().setVisible(true);
			}
		});
	}

	private javax.swing.JMenuItem aboutMenuItem;
	private javax.swing.JMenuBar authoringAppMenuBar;
	private javax.swing.JLabel consoleLabel;
	private javax.swing.JMenuItem copyMenuItem;
	private javax.swing.JMenuItem cutMenuItem;
	private javax.swing.JButton displayAddButton;
	private javax.swing.JComboBox<String> displayComboBox;
	private javax.swing.JMenu editMenu;
	private javax.swing.JPopupMenu.Separator editMenuSeperator1;
	private javax.swing.JPopupMenu.Separator editMenuSeperator2;
	private javax.swing.JButton editRemoveLine;
	private javax.swing.JMenuItem exitMenuItem;
	private javax.swing.JMenu fileMenu;
	private javax.swing.JPopupMenu.Separator fileMenuSeperator1;
	private javax.swing.JPopupMenu.Separator fileMenuSeperator2;
	private javax.swing.JMenuItem helpContentsMenuItem;
	private javax.swing.JMenu helpMenu;
	private javax.swing.JPopupMenu.Separator helpMenuSeperator1;
	private javax.swing.JTextField inputTextField;
	private javax.swing.JButton insertEndRepeat;
	private javax.swing.JLabel insertKeyPhraseLabel;
	private javax.swing.JSeparator insertKeyPhraseSeperator;
	private javax.swing.JLabel insertLabel;
	private javax.swing.JButton insertPause;
	private javax.swing.JButton insertRepeat;
	private javax.swing.JButton insertRepeatButton;
	private javax.swing.JButton insertResetButtons;
	private javax.swing.JSeparator insertSeperator;
	private javax.swing.JButton insertSkip;
	private javax.swing.JButton insertSkipButton;
	private javax.swing.JButton insertSound;
	private javax.swing.JButton insertText;
	private javax.swing.JButton insertUserInput;
	private javax.swing.JMenuItem loadAndRunMenuItem;
	private javax.swing.JMenuItem loadScenarioMenuItem;
	private javax.swing.JMenuItem newMenuItem;
	private javax.swing.JMenuItem pasteMenuItem;
	private javax.swing.JMenuItem redoMenuItem;
	private javax.swing.JLabel removeLabel;
	private javax.swing.JSeparator removeSeperator;
	private javax.swing.JMenu runMenu;
	private javax.swing.JMenuItem runMenuItem;
	private javax.swing.JMenuItem saveAsMenuItem;
	private javax.swing.JMenuItem saveMenuItem;
	private javax.swing.JTextPane scenarioPane;
	private javax.swing.JScrollPane scenarioScrollPane;
	private javax.swing.JRadioButtonMenuItem textPaneEditingMenuItem;
	private javax.swing.JRadioButtonMenuItem ttsMenuItem;
	private javax.swing.JMenuItem undoMenuItem;
}
