package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import background.BackgroundManager;
import entities.Player;
import main.Game;
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
		bgManager.update();
		player.update();
		checkCloseToBorder();

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
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		bgManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			player.setAttack(true);
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
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			player.setLeft(true);
			break;
		case KeyEvent.VK_D:
			player.setRight(true);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(true);
			break;

	}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			player.setLeft(false);
			break;
		case KeyEvent.VK_D:
			player.setRight(false);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(false);
			break;
		}
	}

	public void windowFocusLost() {
		player.resetDirectionBooleans();
	}

	public Player getPlayer() {
		return player;
	}
}
