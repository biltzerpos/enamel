package enamel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GraphCanvas extends JFrame {
	public int startingX;
	public int startingY;
	public int width;
	public int height;
	public Node c;
	public Scenario s;
	
	public static void main(String[] args) {
		GraphCanvas frame = new GraphCanvas(null, null);
		frame.setVisible(true);
	}
	
	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public GraphCanvas(Scenario s, Node n) {
		super();
		this.c = n;
		this.s = s;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */ 
	private void initialize() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.startingX = 0;
		this.startingY = 23;
		this.width = 550;
		this.height = 484;
		setSize(600, 544);
	}
	
	public void update() {
		repaint();
	}
	

    /**
     * @wbp.parser.entryPoint
     */
    public void paint(Graphics g) {
        Graphics2D graphics2 = (Graphics2D) g;
        graphics2.setStroke(new BasicStroke(2));
        Rectangle Rectangle = new Rectangle (this.startingX, this.startingY, this.width, this.height);
        graphics2.draw(Rectangle);
        
        int width = 200;
        int height = 75;
        
        graphics2.drawString(c.getName(), this.width/2-width/2 + 5, this.height/2);
        graphics2.drawString(c.getResponse().substring(0, Math.min(c.getResponse().length(), 24)) + "...", this.width/2-width/2 + 20, this.height/2+30);
        int nodeX = this.width / 2 + this.startingX - width / 2;
        int nodeY = this.height / 2 + this.startingY- height / 2;
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(nodeX, nodeY, width, height, 10, 10);
        graphics2.draw(roundedRectangle);
        
        int lineEnd = this.height/2+this.startingY-height/2-100;
        graphics2.drawLine(this.width/2, this.height/2+this.startingY-height/2, this.width/2, lineEnd);
        
        lineEnd = this.height/2+this.startingY+height/2+100;
      
        roundedRectangle = new RoundRectangle2D.Float(this.width/2+this.startingX-width+10, lineEnd, width, height, 10, 10);
        
        roundedRectangle = new RoundRectangle2D.Float(this.width/2+this.startingX+10, lineEnd, width, height, 10, 10);
        this.createRectangles(graphics2, nodeX, nodeY + height, width, lineEnd, 10, s.getNextNodes(c).length, height);
    }
    
    
    public void createRectangles(Graphics2D g, int firstX, int bottom, int firstWidth, int y, int space, int num, int height) {
    	Node[] nextNodes = s.getNextNodes(c);
    	int spaces = (num + 1) * space;
    	int width = (this.width - spaces) / Math.max(num, 1);
    	int rect = space + width;
    	int x = 0;
    	int dline = firstWidth/(num+1);
    	for (int i = 0; i < num; i++) {
    		x = space + this.startingX + rect * i;
    		g.draw(new RoundRectangle2D.Float(x, y, width, height, 10, 10));
    		g.drawLine(firstX + dline * (i + 1), bottom, x + width / 2, y);
    	}
    }

}
