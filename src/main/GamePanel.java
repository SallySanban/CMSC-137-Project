package main;

import java.awt.Graphics;

import javax.swing.JPanel;
import java.awt.Dimension;

import inputs.KeyboardInput;
import inputs.MouseInput;

public class GamePanel extends JPanel{
	private MouseInput mouseInput;
	private Game game;
	static final int WINDOW_HEIGHT = 700;
	static final int WINDOW_WIDTH = 1000;
	
	
	public GamePanel(Game game) {
		mouseInput = new MouseInput(this);
		this.game = game;
		
		setPanelSize();
		
		addKeyListener(new KeyboardInput(this));
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);
	}

	private void setPanelSize() {
		Dimension size = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}
	
	public void updateGame() {
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		game.render(g);
	}
	
	public Game getGame() {
		return game;
	}
	
}