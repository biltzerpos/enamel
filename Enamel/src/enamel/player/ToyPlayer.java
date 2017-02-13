package enamel.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import enamel.simulator.Simulator;

/**
 * This class is included as an example of how to use the classes in the simulator package.
 * 
 */

public class ToyPlayer {

	public static void main(String[] args) {
		Simulator sim = new Simulator(5, 3);

		sim.displayString("dog");
		
		sim.getCell(3).displayCharacter('z');
		sim.getCell(4).raiseOnePin(3);

		sim.getButton(0).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Button 0 got pressed.");

			}

		});

	}

}
