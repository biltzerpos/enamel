package enamel;

public abstract class NodeButton {
	protected int buttonNumber;

	public NodeButton(int buttonNumber) {
		this.buttonNumber = buttonNumber;
	}

	public NodeButton(NodeButton other) {
		this.buttonNumber = other.buttonNumber;
	}
	
	public int getNumber() {
		return this.buttonNumber;
	}
	
	public void setNumber(int number) {
		this.buttonNumber = number;
	}
	

}
