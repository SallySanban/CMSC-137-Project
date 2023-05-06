package main;

import java.awt.Graphics;

import javax.swing.JPanel;
import java.awt.Dimension;

import inputs.KeyboardInput;
import inputs.MouseInput;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

import javax.swing.JLabel;

public class GamePanel extends JPanel{
	private MouseInput mouseInput;
	private Game game;
	public JLabel menuText;

	public GamePanel(Game game, JLabel menuText) {
		this.menuText = menuText;
		mouseInput = new MouseInput(this);
		this.game = game;

		setPanelSize();

		addKeyListener(new KeyboardInput(this));
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);
	}

	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
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