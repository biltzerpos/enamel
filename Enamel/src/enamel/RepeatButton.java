package enamel;

public class RepeatButton extends NodeButton {
	protected String repeatText;
	
	public RepeatButton(int buttonNumber) {
		super(buttonNumber);
		this.repeatText = "";
		// TODO Auto-generated constructor stub
	}
	
	public RepeatButton(int buttonNumber, String repeatText) {
		super(buttonNumber);
		this.repeatText = repeatText;
		// TODO Auto-generated constructor stub
	}
	
	public RepeatButton(NodeButton other) {
		super(other);
		// TODO Auto-generated constructor stub
	}
	
	public String getRepeatText() {
		return this.repeatText;
	}

	public void setRepeatText(String repeatText) {
		this.repeatText = repeatText;
	}
	
}
