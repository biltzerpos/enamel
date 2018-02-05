package enamel;

import java.util.*;

public class Node {
	
	public class NodeButton {
		private int buttonNumber;
		private String response;
		private int nextNode;

		public NodeButton(int buttonNumber) {
			this.buttonNumber = buttonNumber;
			this.response = "";
			this.nextNode = -1;
		}

		public NodeButton(int buttonNumber, String response) {
			this.buttonNumber = buttonNumber;
			this.response = response;
			this.nextNode = -1;
		}
		
		public NodeButton(int buttonNumber, String response, int next_node) {			 
			this.buttonNumber = buttonNumber;
			this.response = response;
			this.nextNode = next_node;
		}
		
		public NodeButton(int buttonNumber, int next_node) {			 
			this.buttonNumber = buttonNumber;
			this.response = "";
			this.nextNode = next_node;
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
		
		public void setNextNode(int next) {
			this.nextNode = next;
		}
		
		public int getNextNode() {
			return this.nextNode;
		}
	}
	
	private int id;
	private String name;
	private String response;
	private String repeatText;
	Map<Integer, NodeButton> buttonList;
	private int[] listOfPins = new int[8];
	
	public Node(int id) {
		this(id, String.valueOf(id), null, null);
	}
	
	public Node(int id, String name) {
		this(id, name, null, null);
	}
	
	public Node(int id, String name, String response) {
		this(id, name, null, response);
	}
	
	public Node(int id, String name, int[] listOfPins) {
		this(id, name, listOfPins, null);
	
	}
	
	public Node(int id, String name, int[] listOfPins, String response) {
		this.id = id;
		this.name = name;
		this.response = response;
		this.listOfPins = listOfPins;
		this.buttonList = new HashMap<>();
	}
	
	public Node(int id, int[] listOfPins) {
		this(id, String.valueOf(id), listOfPins, null);
	}
	
	public Node(int id, String name, int[] listOfPins, String response, Map<Integer, NodeButton> buttonList) {
		this(id, name, listOfPins, response, null, buttonList);
	}
	
	public Node(int id, String name, int[] listOfPins, String response, String repeatText) {
		this.id = id;
		this.name = name;
		this.listOfPins = listOfPins;
		this.response = response;
		this.repeatText = repeatText;
		this.buttonList = new HashMap<>(buttonList);
	}
	
	public Node(int id, String name, int[] listOfPins, String response, String repeatText, Map<Integer, NodeButton> buttonList) {
		this.id = id;
		this.name = name;
		this.listOfPins = listOfPins;
		this.response = response;
		this.repeatText = repeatText;
		this.buttonList = new HashMap<>(buttonList);
	}
	
	public Node(Node other) {
		this.id = other.id;
		this.name = other.name;
		this.listOfPins = other.listOfPins;
		this.response = other.response;
		this.repeatText = other.repeatText;
		this.buttonList = other.buttonList;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setResponse(String response) {
		this.response = response;
	}
	
	public String getResponse() {
		return this.response;
	}
	
	public void setRepeatedText(String repeatText) {
		this.repeatText = repeatText;
	}
	
	public String getRepeatedText() {
		return this.repeatText;
	}
	
	public int[] getPins() {
		return this.listOfPins;
	}
	
	public void setPins(int[] pins) {
		this.listOfPins = pins;
	}
	
	public void setPin(int pin, int value) {
		this.listOfPins[pin] = value;
	}
	
	public void addButton(int number) {
		this.buttonList.put(number, new NodeButton(number));
	}
	
	public void addButton(int number, String response) {
		this.buttonList.put(number, new NodeButton(number, response));
	}
	
	public void addButton(int number, String response, int nextNodeId) {
		this.buttonList.put(number, new NodeButton(number, response, nextNodeId));
	}
	
	public void addButton(int number, int nextNodeId) {
		this.buttonList.put(number, new NodeButton(number,  nextNodeId));
	}
	
	public NodeButton getButton(int buttonNumber) {
		return this.buttonList.get(buttonNumber);
	}
	
	public int getNext(int buttonNumber) { 
		return this.buttonList.get(buttonNumber).getNextNode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buttonList == null) ? 0 : buttonList.hashCode());
		result = prime * result + Arrays.hashCode(listOfPins);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((repeatText == null) ? 0 : repeatText.hashCode());
		result = prime * result + ((response == null) ? 0 : response.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Node other = (Node) obj;
		if (buttonList == null) {
			if (other.buttonList != null) {
				return false;
			}
		} else if (!buttonList.equals(other.buttonList)) {
			return false;
		}
		if (!Arrays.equals(listOfPins, other.listOfPins)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (repeatText == null) {
			if (other.repeatText != null) {
				return false;
			}
		} else if (!repeatText.equals(other.repeatText)) {
			return false;
		}
		if (response == null) {
			if (other.response != null) {
				return false;
			}
		} else if (!response.equals(other.response)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(id);
	}

}
