package main;

import java.awt.Graphics;
import javax.swing.JPanel;

import inputs.KeyboardInput;
import inputs.MouseInput;

public class GamePanel extends JPanel{
	private MouseInput mouseInput;
	private int xDelta = 100;
	private int yDelta = 100;
	
	public GamePanel() {
		mouseInput = new MouseInput(this);
		
		addKeyListener(new KeyboardInput(this));
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);
	}
	
	public void changeXDelta(int value) {
		this.xDelta += value;
		repaint();
	}
	
	public void changeYDelta(int value) {
		this.yDelta += value;
		repaint();
	}
	
	public void setRectPos(int x, int y) {
		this.xDelta = x;
		this.yDelta = y;
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.fillRect(xDelta, yDelta, 200, 50);
	}
}