package entities;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.PlayerConstants.getSpriteAmount;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Player extends Character{
	private BufferedImage[][] animations;
	private int animationTick;
	private int animationIndex;
	private int animationSpeed = 50;
	private int playerAction = IDLE;
	private boolean moving = false;
	private boolean attacking = false;
	private boolean left, right, up, down;
	private float playerSpeed = 2.7f;
	
	public Player(float x, float y) {
		super(x, y);
		loadAnimations();
	}
	
	public void update() {
		updateAnimationTick();
		setAnimation();
		updatePosition();
	}
	
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][animationIndex], (int) x, (int) y, null);
	}
	
	private void updateAnimationTick() {
		animationTick++;
		
		if(animationTick >= animationSpeed) {
			animationTick = 0;
			animationIndex++;
			
			if(animationIndex >= getSpriteAmount(playerAction)) {
				animationIndex = 0;
				attacking = false;
			}
		}
		
	}
	
	private void setAnimation() {
		int startAnimation = playerAction;
		
		if(moving) {
			playerAction = RUNNING;
		}
		else {
			playerAction = IDLE;
		}
		
		if(attacking) {
			playerAction = ATTACK;
		}
		
		if(startAnimation != playerAction) {
			resetAnimationTick();
		}
		
	}
	
	private void resetAnimationTick() {
		animationTick = 0;
		animationIndex = 0;
		
	}

	private void updatePosition() {
		moving = false;
		
		if(left && !right) {
			x -= playerSpeed;
			moving = true;
		} else if(right && !left) {
			x += playerSpeed;
			moving = true;
		}
		
		if(up && !down) {
			y -= playerSpeed;
			moving = true;
		} else if(down && !up) {
			y += playerSpeed;
			moving = true;
		}
	}
	
	private void loadAnimations() {
		InputStream is = getClass().getResourceAsStream("/Enchantress.png");
		
		try {
			BufferedImage img = ImageIO.read(is);
			
			animations = new BufferedImage[4][8];
			
			for(int j=0; j < animations.length; j++) {
				for(int i=0; i < animations[j].length; i++) {
					animations[j][i] = img.getSubimage(i*75, j*75, 75, 75);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void resetDirectionBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}
	
	public void setAttack(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	
}
