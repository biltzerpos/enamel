package enamel;

import java.util.*;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.*;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.SimpleDirectedGraph;

public class Scenario {
	private static int scenarioCounter;
	private int scenarioNumber;
	private String name;
	private int idCounter;
	private int numButtons;
	private int numCells;
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
	
	
	
	@SuppressWarnings("unchecked")
	public Scenario(Scenario other) {
		this.scenarioNumber = other.scenarioNumber;
		this.name = other.name;
		this.idCounter = other.idCounter;
		this.numButtons = other.numButtons;
		this.numCells = other.numCells;
		this.graph = (SimpleDirectedGraph<Node, DefaultEdge>) ((AbstractBaseGraph<Node, DefaultEdge>)other.graph).clone();
		this.fileName = other.fileName;
		this.head = other.head;
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
	
	public void addNode(Node n) {
		if (this.head == null) {
			this.head = n;
		}
		this.graph.addVertex(n);
	}
	
	public void setEdge(Node from, Node to, int buttonNumber) {
		if (!graph.containsVertex(from)) {
			if (this.head == null) {
				this.head = from;
			}
			this.graph.addVertex(from);
		}
		
		if (!graph.containsVertex(to)) {
			this.graph.addVertex(to);
		}
	
		
		this.graph.addEdge(from, to);
		try {
			NodeButton button = from.getButton(buttonNumber);
			if (button.getClass() == SkipButton.class) {
				((SkipButton) button).setNextNode(to);
			} else {
				throw new IllegalArgumentException("This button is not designed to point to any node");
			}
		}
		catch (IllegalArgumentException e) {
			from.addButton(buttonNumber, to);
		}
	}
	
	public void setNumButtons(int num) {
		this.numButtons = num;
	}
	
	public int getNumButtons() {
		return this.numButtons;
	}
	
	public void setNumCells(int cells) {
		this.numCells = cells;
	}
	
	public int getNumCells() {
		return this.numCells;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return graph.toString();
	}
	
	
	
}
