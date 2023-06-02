package gamestates;

import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.*;

import background.BackgroundManager;
import entities.Player;
import main.Game;
import main.GamePanel;
import utils.LoadSave;

public class Playing extends State implements Statemethods{
	private Player player;
	private BackgroundManager bgManager;
	private int xLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int lvlTilesWide = LoadSave.GetBgData()[0].length;
	private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
	private int maxlvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
	private BufferedImage backgroundImg;
	public boolean gamePaused = false;

	public Playing(Game game) {
		super(game);
		initialize();
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.GAME_BG_IMAGE);
		

	}
	private void initialize() {
		bgManager = new BackgroundManager(game);
		player = new Player(200, 150, 65, 70);
		player.loadBgData(bgManager.getCurrBg().getBgData());
	}

	@Override
	public void update() {
		if(!gamePaused) {
			bgManager.update();
			player.update();
			//checkCloseToBorder();
		}
		

	}

	private void checkCloseToBorder() {
		int playerX = (int) player.getHitbox().x;
		int diff = playerX - xLvlOffset;

		if(diff > rightBorder)
			xLvlOffset += diff - rightBorder;
		else if(diff< leftBorder)
			xLvlOffset += diff - leftBorder;

		if(xLvlOffset > maxlvlOffsetX)
			xLvlOffset = maxlvlOffsetX;
		else if(xLvlOffset < 0)
			xLvlOffset = 0;
	}

	@Override
	public void draw(Graphics g) {
		for(int i = 0; i<3; i++){
			g.drawImage(backgroundImg, (i*Game.GAME_WIDTH)-xLvlOffset, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		}

		//bgManager.draw(g, xLvlOffset);
		bgManager.draw(g);
		//player.render(g, xLvlOffset);
		player.render(g);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			player.setAttack(true, game.getGamePanel());
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
				
		// updated code by Yves: Make arrow keys also functional (omit switch case usage)
		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
			player.setJump(true);
		} else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
			player.setLeft(true);
		} else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
			player.setDown(true);
		} else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
			player.setRight(true);
			
			
		// Yves also added space for easier debugging attack functionality
		} else if (keyCode == KeyEvent.VK_SPACE) {
			player.setAttack(true, game.getGamePanel());
		}
		
		else if(keyCode == KeyEvent.VK_ESCAPE) {
			gamePaused = true;
			GameState.state = GameState.PAUSED;	
			game.getPaused().enterChatMode();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		
		// updated code by Yves: Make arrow keys also functional (omit switch case usage)
		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
			player.setJump(false);
		} else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
			player.setLeft(false);
		} else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
			player.setDown(false);
		} else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
			player.setRight(false);
			
			
		// Yves also added space for easier debugging attack functionality
		} else if (keyCode == KeyEvent.VK_SPACE) {
			player.setAttack(false, game.getGamePanel());
		} 	
	}

	public void windowFocusLost() {
		player.resetDirectionBooleans();
	}

	public Player getPlayer() {
		return player;
	}
	
	public boolean getPaused() {
		return gamePaused;
	}
}
