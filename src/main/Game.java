package main;

import java.awt.Graphics;

import background.BackgroundManager;
import entities.Player;

public class Game implements Runnable {
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;

	private Player player;
	private BackgroundManager bgManager;

	public final static int TILE_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 20;
	public final static int TILES_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE *TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

	public Game() {
		initialize();
		gamePanel = new GamePanel(this);

		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();

		startGameLoop();
	}

	private void initialize() {
		bgManager = new BackgroundManager(this);
		player = new Player(200, 150, 65, 70);
		player.loadBgData(bgManager.getCurrBg().getBgData());
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();

	}

	public void update() {
		player.update();
		bgManager.update();
	}

	public void render(Graphics g) {
		bgManager.draw(g);
		player.render(g);

	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0/FPS_SET;
		double timePerUpdate = 1000000000.0/UPS_SET;

		long previousTime = System.nanoTime();

		double deltaU = 0;
		double deltaF = 0;

		while(true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if(deltaU >= 1) {
				update();
				deltaU--;
			}

			if(deltaF >= 1) {
				gamePanel.repaint();
				deltaF--;
			}
		}
	}

	public void windowFocusLost() {
		player.resetDirectionBooleans();
	}

	public Player getPlayer() {
		return player;
	}
}