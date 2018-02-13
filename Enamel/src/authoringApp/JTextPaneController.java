package authoringApp;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import javax.xml.soap.Node;

public class JTextPaneController extends JTextPane{
	
	private JTextPane tp;
	private JScrollPane sp;
	HTMLDocument doc;
	Element e;
	
	//Constructor
	public JTextPaneController(JTextPane tp, JScrollPane sp) {
		// TODO Auto-generated constructor stub
		this.tp = tp;
		this.sp = sp;
		
		try {
			tp.setPage(getClass().getResource("defaultHTML.html"));
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	public LinkedList<Integer> newDocCreated(LinkedList<String> fileStr) {
		// TODO Auto-generated method stub
		// System.out.println(tp.getText());
		doc = (HTMLDocument) tp.getDocument();
		LinkedList<Integer> n=new LinkedList<Integer>();
		n.add(0);
		for (int i = 1; i <= fileStr.size(); i++) {
			e = doc.getElement(new Integer(i - 1).toString() );
			try {
				System.out.println(fileStr.get(i-1));
				doc.insertAfterEnd(e,  "<p id=\"" + i + "\">" + fileStr.get(i - 1) + "</p>");
				n.add(i);
			} catch (BadLocationException | IOException e1) {
				// TODO Auto -generated catch block
				e1.printStackTrace();
			}
			
		}
		System.out.println(tp.getText());
		System.out.println("****************************************");
		return n;
	}

	

	public void addElement(String temp, int i) {
		// TODO Auto-generated method stub
		//doc = (HTMLDocument) tp.getDocument();
		//doc = (HTMLDocument) tp.getDocument();
		e = doc.getElement(new Integer(i-1).toString());
		
		try {
			doc.insertAfterEnd(e, "<p id=\"" + i + "\">" + temp + "</p>");
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(tp.getText());
		System.out.println("****************************************");
	}


	
}
