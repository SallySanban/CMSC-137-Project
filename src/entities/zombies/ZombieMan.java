// package declaration
package entities.zombies;

// general imports
import static utils.Constants.PlayerConstants.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import main.Game;
import utils.LoadSave;
import entities.zombies.parent.*;


// "Zombie Man" class
public class ZombieMan extends Zombie {
	
	// Constructor call
	public ZombieMan(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, 35*Game.SCALE, 50*Game.SCALE);
	}
	
	
	// function that loads the animation of a given enemy
	private void loadAnimations() {
		
		// load individual image files
		BufferedImage idle_image = LoadSave.GetSpriteAtlas(LoadSave.ZOMBIE_MAN_IDLE_ATLAS);
		BufferedImage run_image = LoadSave.GetSpriteAtlas(LoadSave.ZOMBIE_MAN_RUNNING_ATLAS);
		BufferedImage atk_image = LoadSave.GetSpriteAtlas(LoadSave.ZOMBIE_MAN_ATTACKING_ATLAS);
		BufferedImage jump_image = LoadSave.GetSpriteAtlas(LoadSave.ZOMBIE_MAN_JUMPING_ATLAS);
		BufferedImage fall_image = LoadSave.GetSpriteAtlas(LoadSave.ZOMBIE_MAN_FALLING_ATLAS);
		
		// image selector variable
		BufferedImage selected_image = idle_image;
		
		// create animation array
		animations = new BufferedImage[5][10];

		// load such images
		for(int j=0; j < animations.length; j++) {
			
			// select image according current action of enemy
			switch (j) {
				case IDLE: 	  selected_image = idle_image;  break;	/* case 0 or idle */	
				case RUNNING: selected_image = run_image; 	break;	/* case 1 or running*/			
				case ATTACK:  selected_image = atk_image; 	break;	/* case 2 or attacking */
				case JUMP: 	  selected_image = jump_image;  break;	/* case 3 or jumping */
				case FALLING: selected_image = fall_image;  break;	/* case 4 or falling */
			}		
			
			// load selected image
			for(int i=0; i < animations[j].length; i++) {
				try {
					animations[j][i] = selected_image.getSubimage(i*75, j*75, 75, 75);
					
				// stop for-loop if no such sub-image exists already
				} catch (RasterFormatException e) {
					break;
				} 
			}
		}
	}

}
