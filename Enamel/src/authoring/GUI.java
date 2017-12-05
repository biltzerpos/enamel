package authoring;


import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.AbstractAction;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.KeyEventDispatcher;
import javax.swing.SwingUtilities;

import commands.PauseCommand;
import listeners.NewButtonListener;


/**
 * This class represents the user interface for the authoring program. It is
 * responsible for creating all the user interface panels. In order to create a
 * new authoring session, simply create a (single) instance of this class.
 * 
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-03-15
 */
public class GUI extends JFrame {
	private static final long serialVersionUID = -1291725446662111704L;
	private transient ThreadRunnable audioThread;
	private LeftPanel leftPanel;
	private RightPanel rightPanel;
	private SettingsPanel settingsPanel;
	private NewButtonListener newItem = new NewButtonListener(this);
	
	
	private HashMap<KeyStroke, Action> actionMap = new HashMap<KeyStroke, Action>();
	private HashMap<KeyStroke, String> newItemMap = new HashMap<KeyStroke, String>();


	/**
	 * Create a new default authoring GUI. Accepts no arguments, and runs until
	 * the UI is closed.
	 */
	public GUI() {
		this.setTitle("Authoring App");
		this.getAccessibleContext().setAccessibleName("Authoring App");
		this.getAccessibleContext().setAccessibleDescription(
				"Welcome to the Treasure Box Braille Authoring App. To scroll through the options, use the tab key. Press the space bar to select an option.");

		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		// Create the colour mapper
		ColourMapper mapper = new ColourMapper();

		// Create the root panel
		JPanel rootContainer = new JPanel();
		rootContainer.setLayout(new BoxLayout(rootContainer, BoxLayout.Y_AXIS));

		// Add the top settings panel
		settingsPanel = new SettingsPanel();
		settingsPanel.setMaximumSize(settingsPanel.getMinimumSize());
		//rootContainer.add(settingsPanel);

		JPanel bottomContainer = new JPanel();
		bottomContainer.setLayout(new BoxLayout(bottomContainer, BoxLayout.X_AXIS));

		rootContainer.add(bottomContainer);

		// Create the command list pane
		leftPanel = new LeftPanel(this, mapper);
		bottomContainer.add(leftPanel);

		// Create the buttons pane
		rightPanel = new RightPanel(this, mapper);
		bottomContainer.add(rightPanel);

		// Add the root container to the JFrame
		add(rootContainer);

		// Recalculate the button statuses
		leftPanel.recalculateButtonStatus();
		
		
		setup();
	}
	
	
	private void setup() {
	  
		KeyStroke key1 = KeyStroke.getKeyStroke(KeyEvent.VK_1, 0);
		actionMap.put(key1, new AbstractAction("action1") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent e) {
				leftPanel.commandList.requestFocus();
			}
		});
	  
		KeyStroke key2 = KeyStroke.getKeyStroke(KeyEvent.VK_2, 0);
		actionMap.put(key2, new AbstractAction("action2") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent e) {
				if (rightPanel.btnNew.isEnabled()) {
					rightPanel.btnNew.requestFocus();
				}
			}
		});
		
		KeyStroke key5 = KeyStroke.getKeyStroke(KeyEvent.VK_3, 0);
		actionMap.put(key5, new AbstractAction("action5") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent e) {
				settingsPanel.cellField.requestFocus();
			}
		});
	 
		KeyStroke key3 = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK);
		actionMap.put(key3, new AbstractAction("New Scenario") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent e) {
				rightPanel.btnNewScenario.doClick();
			}
		});
		
		KeyStroke key4 = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
		actionMap.put(key4, new AbstractAction("New Item") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent e) {
				if (rightPanel.btnNew.isEnabled()) {
					rightPanel.btnNew.doClick();
				}
			}
		});
		
		KeyStroke key6 = KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK);
		actionMap.put(key6, new AbstractAction("New Question") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent e) {
				if (rightPanel.btnNewQuestion.isEnabled()) {
					rightPanel.btnNewQuestion.doClick();
				}
			}
		});
		
		KeyStroke key8 = KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK);
		actionMap.put(key8, new AbstractAction("Move Up") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent e) {
				if (rightPanel.btnMoveUp.isEnabled()) {
					rightPanel.btnMoveUp.doClick();
				}
			}
		});
		
		KeyStroke key9 = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		actionMap.put(key9, new AbstractAction("Move Down") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent e) {
				if (rightPanel.btnMoveDown.isEnabled()) {
					rightPanel.btnMoveDown.doClick();
				}
			}
		});
		
		KeyStroke key10 = KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK);
		actionMap.put(key10, new AbstractAction("Delete") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent e) {
				if (rightPanel.btnDelete.isEnabled()) {
					rightPanel.btnDelete.doClick();
				}
			}
		});
		
		KeyStroke key11 = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
		actionMap.put(key11, new AbstractAction("Export") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent e) {
				if (rightPanel.btnSave.isEnabled()) {
					rightPanel.btnSave.doClick();
				}
			}
		});
		
		KeyStroke key12 = KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK);
		actionMap.put(key12, new AbstractAction("Import") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent e) {
				if (rightPanel.btnLoad.isEnabled()) {
					rightPanel.btnLoad.doClick();
				}
			}
		});
		
		KeyStroke key13 = KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key13,  "Pause");
		
		KeyStroke key14 = KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key14,  "Text-to-speech");
		
		KeyStroke key15 = KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key15,  "Display String");
		
		KeyStroke key16 = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key16, "Repeat");
		
		KeyStroke key17 = KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key17, "Button Repeat");
		
		KeyStroke key18 = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key18, "Button Location");
		
		KeyStroke key19 = KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key19,  "User Input");
		
		KeyStroke key20 = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key20,  "Sound");
		
		KeyStroke key21 = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key21,  "Reset Buttons");
		
		KeyStroke key22 = KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key22,  "Go To Location");
		
		KeyStroke key23 = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key23,  "Clear All");
		
		KeyStroke key24 = KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key24,  "Clear Cell");
		
		KeyStroke key25 = KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key25,  "Set Pins");
		
		KeyStroke key26 = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key26,  "Set Character");
		
		KeyStroke key27 = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key27,  "Raise Pin");
		
		KeyStroke key28 = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key28,  "Lower Pin");
		
		KeyStroke key29 = KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key29,  "Set Voice");
		
		KeyStroke key30 = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.ALT_DOWN_MASK);
		newItemMap.put(key30,  "Location Tag");


		
		
		
		
		
		
		
		
		
		// add more actions..

	  KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	  kfm.addKeyEventDispatcher( new KeyEventDispatcher() {

	    @Override
	    public boolean dispatchKeyEvent(KeyEvent e) {
	      KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(e);
	      if ( actionMap.containsKey(keyStroke) ) {
	        final Action a = actionMap.get(keyStroke);
	        final ActionEvent ae = new ActionEvent(e.getSource(), e.getID(), null );
	        SwingUtilities.invokeLater( new Runnable() {
	          @Override
	          public void run() {
	            a.actionPerformed(ae);
	          }
	        } ); 
	        return true;
	      } else if (newItemMap.containsKey(keyStroke) && rightPanel.btnNew.isEnabled())
	      {
	    	  String value = newItemMap.get(keyStroke);
	    	  newItem.processAnswer(value);
	    	  return true;
	      }
	      return false;
	    }
	  });
	}
	
	
	
	
	

	/**
	 * Set the value of the audio thread in order for the main thread to access
	 * it and run various methods on it.
	 * 
	 * @param audioThread
	 *            The instance of the audio thread that the GUI should attempt
	 *            to control
	 */
	public void setAudioThread(ThreadRunnable audioThread) {
		this.audioThread = audioThread;
	}

	/**
	 * Retrieve the last known instance of the audio thread. In the even that
	 * there was never a known instance, this will return null.
	 * 
	 * @return Either the last known instance (if it exists) or null
	 *         (otherwise).
	 */
	public ThreadRunnable getAudioThread() {
		return this.audioThread;
	}

	/**
	 * Obtain the shared reference to the Left Panel. The Left Panel contains
	 * the backend list which is both shown to the authoring user (as a text
	 * list) and used for generating the final output file (via serialization)
	 * 
	 * @return The single instance of the left panel
	 */
	public LeftPanel getLeftPanel() {
		return this.leftPanel;
	}

	/**
	 * Obtain the shared reference to the Right Panel. The Right Panel contains
	 * the buttons for the authoring user to control the application
	 * 
	 * @return The single instance of the right panel
	 */
	public RightPanel getRightPanel() {
		return this.rightPanel;
	}

	/**
	 * Obtain the shared reference to the Settings Panel. The Settings Panel
	 * contains the values which are used to generate the output file header
	 * information
	 * 
	 * @return The single instance of the settings panel
	 */
	public SettingsPanel getSettingsPanel() {
		return this.settingsPanel;
	}
}