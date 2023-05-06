package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;

public class KeyboardInput implements KeyListener{
	private GamePanel gamePanel;
	
	public KeyboardInput(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		
		// updated code by Yves: Make arrow keys also functional (omit switch case usage)
		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
			gamePanel.getGame().getPlayer().setUp(true);
		} else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
			gamePanel.getGame().getPlayer().setLeft(true);
		} else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
			gamePanel.getGame().getPlayer().setDown(true);
		} else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
			gamePanel.getGame().getPlayer().setRight(true);
			
			
		// Yves also added space for easier debugging attack functionality
		} else if (keyCode == KeyEvent.VK_SPACE) {
			gamePanel.getGame().getPlayer().setAttack(true, gamePanel);
		} 		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
int keyCode = e.getKeyCode();
		
		// updated code by Yves: Make arrow keys also functional (omit switch case usage)
		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
			gamePanel.getGame().getPlayer().setUp(false);
		} else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
			gamePanel.getGame().getPlayer().setLeft(false);
		} else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
			gamePanel.getGame().getPlayer().setDown(false);
		} else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
			gamePanel.getGame().getPlayer().setRight(false);
		}
	}
	
}