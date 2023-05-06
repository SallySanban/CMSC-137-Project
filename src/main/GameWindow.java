package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import entities.Player;
import javax.swing.*;

public class GameWindow {
	private JFrame jframe;
	private Player player;
	private JLabel menuText;
	private JPanel container = new JPanel();
	
	public GameWindow(GamePanel gamePanel, Player player) {
		jframe = new JFrame();
		this.player = player;
		
		//will close when X is clicked
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// The top border that shows text
		menuText = gamePanel.menuText;
		
		// add objects to the JPanel container
		container.add(gamePanel);
		container.add(menuText);
		
		//place the container to the jframe itself
		jframe.add(container);
		
//		jframe.add(gamePanel);
		
		//shows the screen
		jframe.setResizable(false);
		jframe.pack();
		jframe.setVisible(true);
		jframe.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
				
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				gamePanel.getGame().windowFocusLost();
				
			}
			
		});
	}
}