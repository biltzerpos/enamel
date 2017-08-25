package common;

import javax.swing.JFrame;

import authoring.GUI;

/**
 * Simple dummy thread to create the GUI and run it
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-03-02
 */
public class MainThread implements Runnable {

	@Override
	public void run() {
		GUI frame = new GUI();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
}
