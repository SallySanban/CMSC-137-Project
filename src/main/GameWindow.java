package main;

// imports
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import entities.Player;
import javax.swing.*;
import java.awt.*;    
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

// class declaration
public class GameWindow {
	
	// main variable declarations
	private Player player;
	private JLabel menuText;
	private Frame frame = new Frame();
	
	// constructor call
	public GameWindow(GamePanel gamePanel, Player player) {
		
		// in case importing is needed here
		this.player = player;
		
		// The top border that shows text
		menuText = gamePanel.menuText;
		
		// add objects to the frame
		frame.add(menuText);
		frame.add(gamePanel);
		
		// set the layout using a BoxLayout
		frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));
		
		// set up the screen
		frame.setSize(GAME_WIDTH, GAME_HEIGHT);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		
		
		// add window event listeners
		frame.addWindowListener(new WindowAdapter() {
			
			// event upon closing window
			public void windowClosing(WindowEvent e) {
                System.exit(0);
			}

//			// both for maximizing window
//			@Override
//			public void windowGainedFocus(WindowEvent e) {}
//			@Override
//			public void windowLostFocus(WindowEvent e) {
//				gamePanel.getGame().windowFocusLost();				
//			}
			
		});
	}
}