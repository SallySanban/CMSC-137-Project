package main;

import javax.swing.JFrame;

public class GameWindow {
	private JFrame jframe;
	
	public GameWindow(GamePanel gamePanel) {
		jframe = new JFrame();
		
		//sets the size of the window
		jframe.setSize(1000, 700);
		
		//will close when X is clicked
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//place panel on frame
		jframe.add(gamePanel);
		
		//shows the screen in the center
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);
	}
}