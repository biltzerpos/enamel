package enamel;

import java.util.*;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.*;
import org.jgrapht.graph.SimpleDirectedGraph;

public class Scenario {
	private static int scenarioCounter;
	private int scenarioNumber;
	private String name;
	private int idCounter; 
	static {
		scenarioCounter = 1;
	};
	private Graph<Node, DefaultEdge> graph;
	private String fileName;
	private Node head;
	
	public Scenario() {
		this.graph = new SimpleDirectedGraph<>(DefaultEdge.class);
		this.fileName = "Scenario_" + Scenario.scenarioCounter + ".txt";
		this.head = null;
		this.scenarioNumber = Scenario.scenarioCounter;
		Scenario.scenarioCounter++;
		
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public void setFileName(String fileName) {
		if (fileName.endsWith(".txt")) {
			this.fileName = fileName;
		} else {
			throw new IllegalArgumentException("The new file name has to end in '.txt'");
		}
	}
	
	public Node createNode() {
		idCounter++;
		return new Node(idCounter-1);
		
	}
	
	public Node createNode(String name) {
		idCounter++;
		return new Node(idCounter-1, name);
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getNumber() {
		return this.scenarioNumber;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Node getHead() {
		return this.head;
	}
	
	public Set<DefaultEdge> getEdgeSet() {
		return this.graph.edgeSet();
	}
	
	public Set<Node> getNodeSet() {
		return this.graph.vertexSet();
	}
	
	public boolean isEmpty() {
		return this.head == null;
	}
	
	public Node[] getPrevNodes(Node currNode) {
		if (currNode.equals(this.head)) {
			return null;
		}
		Set<DefaultEdge> edgeSet = this.graph.incomingEdgesOf(currNode);
		Node[] nodeArr = new Node[edgeSet.size()];
		int counter = 0;
		for (DefaultEdge edge : edgeSet) {
			nodeArr[counter] = (Node) edge.getSource();
		}
		return nodeArr;
		
	}
	
	public Node getNextNode(Node currNode, String name) {
		Node[] nodes = this.getNextNodes(currNode);
		for (Node node : nodes) {
			if (node.getName().equals(name)){
				return node;
			}
		}
		return null;
	}
	
	public Node[] getNextNodes(Node currNode) {
		Set<DefaultEdge> edgeSet = this.graph.outgoingEdgesOf(currNode);
		Node[] nodeArr = new Node[edgeSet.size()];
		int counter = 0;
		for (DefaultEdge edge : edgeSet) {
			nodeArr[counter] = (Node) edge.getSource();
		}
		return nodeArr;
	}
	
	public void setEdge(Node from, Node to, int buttonNumber) {
		this.graph.addEdge(from, to);
		from.getButton(buttonNumber).setNextNode(to);
	}
	
	
}
