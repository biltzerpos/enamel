package authoringApp;

import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;

/**
 * Extension of JTextPane implementing features to add HTML DOM format and CSS
 * styling.
 * 
 * @author Xiahan Chen, Huy Hoang Minh Cu, Qasim Mahir
 *
 */
public class JTextPaneController extends JTextPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4947922282601680749L;
	private JTextPane tp;
	private JScrollPane sp;
	HTMLDocument doc;
	Element e;

	/**
	 * Initializes the JTextPaneControllers and sets the JTextPane to display
	 * defaultHTML.html.
	 * 
	 * @param tp
	 *            The JTextPane from the GUI.
	 * @param sp
	 *            The JScrollPane from the GUI.
	 */
	public JTextPaneController(JTextPane tp, JScrollPane sp) {
		this.tp = tp;
		this.sp = sp;

		try {
			tp.setPage(getClass().getResource("defaultHTML.html"));

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Populates the lines of the JTextPane using the strings from a scenario
	 * file.
	 * 
	 * @param fileStr
	 *            A linked List with each element representing a line of the
	 *            scenario file.
	 * @return LinkedList n - A linked list of integer IDs within the HTML file.
	 */
	public LinkedList<Integer> newDocCreated(LinkedList<String> fileStr) {

		doc = (HTMLDocument) tp.getDocument();
		LinkedList<Integer> n = new LinkedList<Integer>();
		n.add(0);
		for (int i = 1; i <= fileStr.size(); i++) {
			e = doc.getElement(new Integer(i - 1).toString());
			try {
				doc.insertAfterEnd(e, "<p id=\"" + i + "\">" + fileStr.get(i - 1) + "</p>");
				n.add(i);
			} catch (BadLocationException | IOException e1) {
				e1.printStackTrace();
			}

		}
		return n;
	}

	/**
	 * Adds an Element with it's string after the specified element.
	 * 
	 * @param temp
	 *            The String to be inserted.
	 * @param i
	 *            The id to be inserted after.
	 */
	public void addElement(String temp, int i) {
		e = doc.getElement(new Integer(i - 1).toString());

		try {
			doc.insertAfterEnd(e, "<p id=\"" + i + "\">" + temp + "</p>");
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
