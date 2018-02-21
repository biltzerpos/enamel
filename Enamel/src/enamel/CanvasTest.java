package enamel;

import org.jgrapht.Graph;
import org.jgrapht.graph.*;

public class CanvasTest {
	
	private static int counter;
	private static Graph<Node, DefaultEdge> graph;
	private static Scenario s;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		graph = new SimpleDirectedGraph<>(DefaultEdge.class);
		s = new Scenario();
		counter = 0;
		addNodes();
		System.out.println(s);
		GraphCanvas c = new GraphCanvas(s, s.getHead());
		c.setVisible(true);
	}
	
	public static void addNodes() {
		int[] arr1 = {1,1,1,0,0,0,0,0};
		String name = "ONEE";
		String response = "These are pins 1, 2 and 3, the 3 pins on the left side.\nPress button 1 to continue.";
		Node node1 = new Node(counter, name, response);
		node1.setPins(arr1, 0);
		node1.addButton(0);
		graph.addVertex(node1);
		counter++;
		
		
		
		int[] arr2 = {0,0,0,1,1,1,0,0};
		response = "These are pins 4, 5 and 6, the 3 pins on the right side.\nPress button 1 to continue.";
		Node node2 = new Node(counter, name, response);
		node2.setPins(arr2, 0);
		node2.addButton(0);
		graph.addVertex(node2);
		counter++;
		
		s.setEdge(node1, node2, 0);
		
		
		arr1[2] = 0;
		response = "These are pins 1 and 2, the top two pins on the left side.\nPress button 1 to continue.";
		node1 = new Node(counter, name, response);
		node1.setPins(arr1, 0);
		node1.addButton(0);
		graph.addVertex(node1);
		counter++;
		
		s.setEdge(node2, node1, 0);
		
		arr2[5] = 0;
		response = "These are pins 4 and 5, the top two pins on the right side. Press button 1 to continue.";
		node2 = new Node(counter, name, response);
		node2.setPins(arr2, 0);
		node2.addButton(0);
		graph.addVertex(node2);
		counter++;
		
		s.setEdge(node1, node2, 0);
		
		arr1[1] = 0;
		arr1[3] = 1;
		response = "These are pins 1 and 4, the two pins on the top. Press button 1 to continue.";
		node1 = new Node(counter, name, response);
		node1.setPins(arr1, 0);
		node1.addButton(0);
		graph.addVertex(node1);
		counter++;

		s.setEdge(node2, node1, 0);
		
		arr2[2] = 1;
		arr2[3] = 0;
		arr2[4] = 0;
		arr2[5] = 1;
		response = "These are pins 3 and 6, the two pins on the bottom. Press button 1 to continue.";
		node2 = new Node(counter, name, response);
		node2.setPins(arr2, 0);
		node2.addButton(0);
		graph.addVertex(node2);
		counter++;
		
		s.setEdge(node1, node2, 0);
		

		response = "That's the end of directional orientation!";
		node1 = new Node(counter, name ,response);
		graph.addVertex(node1);
		counter++;
		
		s.setEdge(node2, node1, 0);
		
		
	}

}