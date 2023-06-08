package main;

// imports
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Dimension;
import inputs.KeyboardInput;
import inputs.MouseInput;
import javax.swing.JLabel;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

// class declaration
public class GamePanel extends JPanel {

	// static fields
	public static int MENU_TEXT_RESERVE_SPACE = 10;

	// main variable imports
	private MouseInput mouseInput;
	private Game game;
	public JLabel menuText;

	// constructor call
	public GamePanel(Game game, JLabel menuText) {
		mouseInput = new MouseInput(this);
		this.game = game;

		// this will be added to the panel later
		this.menuText = menuText;

		setPanelSize();

		addKeyListener(new KeyboardInput(this));
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);
	}

	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT - MENU_TEXT_RESERVE_SPACE);
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