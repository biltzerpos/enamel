package authoringApp;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JMenu;

/**
 * Draws a GUI for creating a new scenario file.
 * @author Xiahan Chen, Huy Hoang Minh Cu, Qasim Mahir
 */
public class NewFileGUI extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7008263644028988740L;
	private HashMap<String, Component> compMap = new HashMap<String, Component>();

	/**
	 * Creates an HashMap of name component pairs for components within this JFrame.
	 */
    private void createCompMap() {
		ArrayList<Component> compList = getCompList(this);
		//System.out.println(compList);
		for (int i = 0; i < compList.size(); i++){
			if (compList.get(i).getName() != null){
				this.compMap.put(compList.get(i).getName(), compList.get(i));
			}
		}
	}

    /**
     * Recursively adds components and their child components into an ArrayList.
     * @param c The parent container.
     * @return An ArrayList of the parent container and all it's child components.
     */
	public ArrayList<Component> getCompList(final Container c) {
		Component[] compArray = c.getComponents();
		ArrayList<Component> compList = new ArrayList<Component>();
		for (Component comp: compArray) {
			compList.add(comp);
			if (comp instanceof JMenu){
				for (int i = 0; i < ((JMenu) comp).getMenuComponentCount(); i++){
					compList.add(((JMenu) comp).getMenuComponent(i));
				}
			}
			else if (comp instanceof Container){
				compList.addAll(getCompList((Container) comp));
			}
		}
		return compList;
	}
	
	/**
	 * @return The HashMap of components with the key being their component name.
	 */
	public HashMap<String, Component> getCompMap(){
		return this.compMap;
	}
	
	/**
	 * Initializes the JFrame. Calls methods to initialize components and create an Array of components within this JFrame.
	 */
    public NewFileGUI() {
        initComponents();
        createCompMap();
    }

	/**
	 * Initializes all the components within this JFrame.
	 */
    private void initComponents() {

        numCell = new javax.swing.JTextField();
        numCol = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        createButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        
        jLabel1.setText("Enter number of cell");

        jLabel2.setText("Enter number of button");
        numCell.setName("numCell");
        numCol.setName("numCol");
        createButton.setText("Create");
        createButton.setName("createButton");
        cancelButton.setText("Cancel");
        cancelButton.setName("cancelButton");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(numCell, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(numCol, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numCell, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numCol, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(createButton, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }                                       

    /**
     * Sets the style of the GUI and calls the constructor.
     * @param args unused
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
            java.util.logging.Logger.getLogger(NewFileGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewFileGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewFileGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewFileGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewFileGUI().setVisible(true);
            }
        });
    }
                
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton createButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField numCell;
    private javax.swing.JTextField numCol;
    
}