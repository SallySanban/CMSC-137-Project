package main;

import java.awt.Graphics;

import entities.Player;

public class Game implements Runnable {
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Player player;
	
	public Game() {
		initialize();
		gamePanel = new GamePanel(this);
		
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		
		startGameLoop();		
	}
	
	private void initialize() {
		player = new Player(200, 200);
		
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	public void update() {
		player.update();
	}
	
	public void render(Graphics g) {
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