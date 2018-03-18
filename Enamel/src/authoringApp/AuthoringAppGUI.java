package authoringApp;

import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;

/**
 * The GUI of the authoring application.
 * 
 * @author Xiahan Chen, Huy Hoang Minh Cu, Qasim Mahir
 */
public class AuthoringAppGUI extends javax.swing.JFrame {
    private JLabel recordButton; 
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
        recordButton = new javax.swing.JLabel();
        try {
        	Image img = ImageIO.read(getClass().getResource("resources/mic14.png"));
        	recordButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
        	System.out.println(ex);
       	}
        authoringAppMenuBar.add(recordButton);
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
        insertPause = new javax.swing.JButton();
        displayComboBox = new javax.swing.JComboBox<>();
        displayAddButton = new javax.swing.JButton();
        insertSkip = new javax.swing.JButton();
        consoleScrollPane = new javax.swing.JScrollPane();
        consoleTextPane = new javax.swing.JTextPane();
        editLabel = new javax.swing.JLabel();
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

        editRemoveLine.setText("Remove Line");
        editRemoveLine.setToolTipText("");
        editRemoveLine.setEnabled(false);
        editRemoveLine.setName("editRemoveLine"); // NOI18N

        insertText.setText("Text");
        insertText.setEnabled(false);
        insertText.setName("insertText"); // NOI18N

        insertSkipButton.setText("/~skip-button:int string");
        insertSkipButton.setEnabled(false);
        insertSkipButton.setName("insertSkipButton"); // NOI18N

        insertRepeatButton.setText("/~repeat-button:int");
        insertRepeatButton.setEnabled(false);
        insertRepeatButton.setName("insertRepeatButton"); // NOI18N

        insertRepeat.setText("/~repeat");
        insertRepeat.setEnabled(false);
        insertRepeat.setName("insertRepeat"); // NOI18N

        insertUserInput.setText("/~user-input");
        insertUserInput.setEnabled(false);
        insertUserInput.setName("insertUserInput"); // NOI18N

        insertResetButtons.setText("/~reset-buttons");
        insertResetButtons.setEnabled(false);
        insertResetButtons.setName("insertResetButtons"); // NOI18N

        insertEndRepeat.setText("/~endrepeat");
        insertEndRepeat.setEnabled(false);
        insertEndRepeat.setName("insertEndRepeat"); // NOI18N

        insertSound.setText("/~sound:filename.ext");
        insertSound.setEnabled(false);
        insertSound.setName("insertSound"); // NOI18N

        insertLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        insertLabel.setText("Insert:");
        insertLabel.setName("insertLabel"); // NOI18N

        insertPause.setText("/~pause:int");
        insertPause.setEnabled(false);
        insertPause.setName("insertPause"); // NOI18N

        displayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "/~disp-string:string", "/~disp-clearAll", "/~disp-clear-cell:int", "/~disp-cell-pins:int string", "/~disp-cell-char:int char", "/~disp-cell-raise:int1 int2", "/~disp-cell-lower:int1 int2" }));
        displayComboBox.setToolTipText("");
        displayComboBox.setEnabled(false);
        displayComboBox.setLightWeightPopupEnabled(false);
        displayComboBox.setName("displayComboBox"); // NOI18N

        displayAddButton.setText("+");
        displayAddButton.setEnabled(false);
        displayAddButton.setName("displayAddButton"); // NOI18N

        insertSkip.setText("/~skip:string");
        insertSkip.setEnabled(false);
        insertSkip.setName("insertSkip"); // NOI18N

        consoleTextPane.setEditable(false);
        consoleScrollPane.setViewportView(consoleTextPane);

        editLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        editLabel.setText("Edit:");
        editLabel.setName("insertLabel"); // NOI18N

        authoringAppMenuBar.setName("authoringAppMenuBar"); // NOI18N

        fileMenu.setText("File");
        fileMenu.setName("fileMenu"); // NOI18N

        newMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newMenuItem.setText("New");
        newMenuItem.setName("newMenuItem"); // NOI18N
        fileMenu.add(newMenuItem);

        loadScenarioMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        loadScenarioMenuItem.setText("Load scenario");
        loadScenarioMenuItem.setName("loadScenarioMenuItem"); // NOI18N
        fileMenu.add(loadScenarioMenuItem);

        fileMenuSeperator1.setName("fileMenuSeperator1"); // NOI18N
        fileMenu.add(fileMenuSeperator1);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setText("Save");
        saveMenuItem.setToolTipText("");
        saveMenuItem.setEnabled(false);
        saveMenuItem.setName("saveMenuItem"); // NOI18N
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
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

        undoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undoMenuItem.setText("Undo");
        undoMenuItem.setEnabled(false);
        undoMenuItem.setName("undoMenuItem"); // NOI18N
        editMenu.add(undoMenuItem);

        redoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        redoMenuItem.setText("Redo");
        redoMenuItem.setEnabled(false);
        redoMenuItem.setName("redoMenuItem"); // NOI18N
        editMenu.add(redoMenuItem);

        editMenuSeperator1.setName("editMenuSeperator"); // NOI18N
        editMenu.add(editMenuSeperator1);

        cutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cutMenuItem.setText("Cut");
        cutMenuItem.setEnabled(false);
        cutMenuItem.setName("cutMenuItem"); // NOI18N
        editMenu.add(cutMenuItem);

        copyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyMenuItem.setText("Copy");
        copyMenuItem.setEnabled(false);
        copyMenuItem.setName("copyMenuItem"); // NOI18N
        editMenu.add(copyMenuItem);

        pasteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        pasteMenuItem.setText("Paste");
        pasteMenuItem.setEnabled(false);
        pasteMenuItem.setName("pasteMenuItem"); // NOI18N
        editMenu.add(pasteMenuItem);

        authoringAppMenuBar.add(editMenu);

        runMenu.setText("Run");
        runMenu.setName("runMenu"); // NOI18N

        runMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        runMenuItem.setText("Run");
        runMenuItem.setEnabled(false);
        runMenuItem.setName("runMenuItem"); // NOI18N
        runMenu.add(runMenuItem);

        loadAndRunMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, java.awt.event.InputEvent.CTRL_MASK));
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
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(consoleScrollPane, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inputTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 899, Short.MAX_VALUE)
                    .addComponent(scenarioScrollPane))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(displayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(displayAddButton))
                    .addComponent(insertRepeat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(insertSkipButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(insertEndRepeat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(insertSkip, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(insertUserInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(insertText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(insertPause, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(insertRepeatButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(insertResetButtons, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(insertSound, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(insertLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editRemoveLine, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(insertLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(insertText, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(insertPause, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(insertSkipButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(insertSkip, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(insertUserInput, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(insertRepeatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(insertRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(insertEndRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(insertResetButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(insertSound, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(displayAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(displayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editRemoveLine, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 67, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scenarioScrollPane)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(consoleScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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
    private javax.swing.JScrollPane consoleScrollPane;
    private javax.swing.JTextPane consoleTextPane;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JButton displayAddButton;
    private javax.swing.JComboBox<String> displayComboBox;
    private javax.swing.JLabel editLabel;
    private javax.swing.JMenu editMenu;
    private javax.swing.JPopupMenu.Separator editMenuSeperator1;
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
    private javax.swing.JLabel insertLabel;
    private javax.swing.JButton insertPause;
    private javax.swing.JButton insertRepeat;
    private javax.swing.JButton insertRepeatButton;
    private javax.swing.JButton insertResetButtons;
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
    private javax.swing.JMenu runMenu;
    private javax.swing.JMenuItem runMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JTextPane scenarioPane;
    private javax.swing.JScrollPane scenarioScrollPane;
    private javax.swing.JRadioButtonMenuItem ttsMenuItem;
    private javax.swing.JMenuItem undoMenuItem;
}
