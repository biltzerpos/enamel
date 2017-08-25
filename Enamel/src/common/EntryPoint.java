package common;

/**
 * Entry point of the program. Launches the GUI and spins off threads as needed.
 *
 * @author Dilshad Khatri, Alvis Koshy, Drew Noel, Jonathan Tung
 * @version 1.0
 * @since 2017-01-22
 */
public class EntryPoint {
	/**
	 * Main method, standard entry point to a program
	 *
	 * @param args
	 *            Command-line parameters (ignored)
	 */
	public static void main(String[] args) {
		// Create a new thread to do things
		Thread t = new Thread(new MainThread());
		t.start();
	}
}
