package enamel;


public class NodeButton {
	private int buttonNumber;
	private String response;
	private Node nextNode;
	private int[] pins = new int[8];

	public NodeButton(int buttonNumber) {
		this.buttonNumber = buttonNumber;
		this.response = "";
		this.nextNode = null;
	}

	public NodeButton(int buttonNumber, String response) {
		this.buttonNumber = buttonNumber;
		this.response = response;
		this.nextNode = null;
	}
	
	public NodeButton(int buttonNumber, String response, Node nextNode) {			 
		this.buttonNumber = buttonNumber;
		this.response = response;
		this.nextNode = nextNode;
	}
	
	public NodeButton(int buttonNumber, String response, Node nextNode, int[] pins) {			 
		this.buttonNumber = buttonNumber;
		this.response = response;
		this.nextNode = nextNode;
		this.setPins(pins);
	}
	
	public NodeButton(int buttonNumber, Node nextNode) {			 
		this.buttonNumber = buttonNumber;
		this.response = "";
		this.nextNode = nextNode;
	}
	
	public NodeButton(NodeButton other) {
		this.buttonNumber = other.buttonNumber;
		this.response = other.response;
		this.nextNode = other.nextNode;
	}
	
	public int getNumber() {
		return this.buttonNumber;
	}
	
	public void setNumber(int number) {
		this.buttonNumber = number;
	}
	
	public String getResponse() {
		return this.response;
	}
			
	public void setResponse(String response) {
		this.response = response;
	}
	
	public void setNextNode(Node next) {
		this.nextNode = next;
	}
	
	public Node getNextNode() {
		return this.nextNode;
	}

	/**
	 * @return the listOfPins
	 */
	public int[] getPins() {
		return pins;
	}

	/**
	 * @param pins the listOfPins to set
	 */
	public void setPins(int[] pins) {
		this.pins = pins;
	}
}
