package enamel;


import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class BrailleCellPanel extends JPanel {

    ArrayList<JRadioButton> pins = new ArrayList<JRadioButton>(8);
    int[] pinIndex = {0, 2, 4, 1, 3, 5, 6, 7};
    
    public BrailleCellPanel()
    {
        //GridLayout cellGrid = new GridLayout(4, 2);
        //JPanel panel = new JPanel(cellGrid);
        super(new GridLayout(4, 2));

        for (int j = 0; j < 8; j++) {
            JRadioButton radioButton = new JRadioButton();
            radioButton.setEnabled(false);
            radioButton.setSize(25, 25);
            radioButton.getAccessibleContext().setAccessibleName("Cell " + (j + 1));
            pins.add(radioButton);
            add(radioButton);
        }
        setSize(50, 50);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setVisible(true);
    }
    
    public void setRadioButtons(boolean[] b)
    {
        for (int i = 0; i< 8; i++)
        {
            pins.get(pinIndex[i]).setSelected(b[i]);
        }
    }
    
}
