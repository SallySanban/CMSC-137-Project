// package declaration
package entities.zombies.parent;

// general imports
import static utils.Constants.PlayerConstants.*;
import static utils.Constants.PlayerConstants.getSpriteAmount;
import static utils.HelpMethods.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

import entities.Character;
import entities.Player;
import main.Game;
import utils.LoadSave;


// "Zombie Man" class
public class Zombie extends Character {
	
	
	// static declarations
	static final int NORMAL_ANIMATION_SPEED = 50;
	
	
	// declarations
	protected BufferedImage[][] animations;
	protected int animationTick;
	protected int animationIndex;
	protected int animationSpeed = 50;
	protected int playerAction = IDLE;
	protected boolean moving = false;
	protected boolean attacking = false;
	protected boolean left, right, up, down;
	protected boolean jump;
	float playerSpeed = 2.7f;
	protected int[][] bgData;
	protected float xDrawOffset = 9 * Game.SCALE;
	protected float yDrawOffset = 2 * Game.SCALE;
	protected Player player;
	protected float xSpeed = 0;

	//for jumping and gravity
	protected float airSpeed = 0f;
	protected float gravity = 0.04f * Game.SCALE;
	protected float jumpSpeed = -2.25f * Game.SCALE;
	protected float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	protected boolean inAir = false;
	protected float movement_stabilization_constant = 40.994f;

	
	// constuctor
	public Zombie(float x, float y, int width, int height, Player player) {
		super(x, y, width, height);
		initHitbox(x, y, 35*Game.SCALE, 50*Game.SCALE);
		this.player = player;
	}
	
	
	private void followPlayer() {
		
		
		// PART 1: Update X positions
		
		// first index is textField location, and the second index is hitbox Position
		float[] playerXPosition = player.XPositions();
		
		// if on left side of player
		if (this.hitbox.x > playerXPosition[1]) {
			xSpeed = 0-(playerSpeed*0.3f);
			System.out.println("this.hitbox.x = " + this.hitbox.x);
			System.out.println("playerXPosition[1] = " + playerXPosition[1]);
			
		// if on right side of player
		} else if (this.hitbox.x < playerXPosition[1]) {
			xSpeed = 0+(playerSpeed*0.3f);
			
		} else {
			xSpeed = 0;
		}
		
		// if can move to next direction, move there
		if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, bgData)){
			hitbox.x += (xSpeed);
			textField.setLocation(textField.getLocation().x + (int) xSpeed, textField.getLocation().y); 		
		
		// else, if cannot move, try jumping
		} else {
			jump();
		}
		
		
		
		// PART 2: Update Y Positions
		
		// if not set in air, check if it is so.
		if(!inAir){
			if(!IsEntityOnFloor(hitbox, bgData)){
				inAir = true;
			}
		}

		// now set data if it is so.
		if (inAir) {
			
			// case of when such entity can move in next direction
			if (CanMoveHere(hitbox.x, hitbox.y+airSpeed, hitbox.width, hitbox.height, bgData)){
				hitbox.y += airSpeed;
				airSpeed += gravity;
			
			
			// case of when such entity cannot move in next direction
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox,airSpeed) + movement_stabilization_constant; 
				if(airSpeed > 0) {
					resetInAir();
				} else {
					airSpeed = fallSpeedAfterCollision;
				}
			}
		} 
	}
	
	// update function
	public void update() {
		updateAnimationTick();
		setAnimation();
		followPlayer();
	}

	
	// function that renders given graphics 
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
//		drawHitbox(g);
	}

	
	// function that updates the animation tick
	protected void updateAnimationTick() {
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

	// function that sets the animation of a given enemy
	protected void setAnimation() {
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

	// resets the animation tich
	protected void resetAnimationTick() {
		animationTick = 0;
		animationIndex = 0;

	}

	// function that makes an enemy jump
	protected void jump() {
		if(inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;

	}

	
	// function that resets the inAir variable
	protected void resetInAir() {
		inAir = false;
		airSpeed = 0;

	}

	
	// function that updates the X position of the enemy
	protected void updateXPos(float xSpeed) {
		if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, bgData)){
			hitbox.x += xSpeed;
		}else{
			hitbox.x = GetEntityPosNextToWall(hitbox, xSpeed);
		}

	}
	
	
	// function that loads the background data given an int[][] array
	public void loadBgData(int[][] bgData){
		this.bgData = bgData;
		if(!IsEntityOnFloor(hitbox, bgData))
			inAir = true;
	}

	
	// resets direction booleans
	public void resetDirectionBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	
	// sets this enemy to attacking mode
	public void setAttack(boolean attacking) {
		this.attacking = attacking;
	}

	
	// getter for if this entity is going towards the left
	public boolean isLeft() {
		return left;
	}

	
	// sets this enemy to the left direction
	public void setLeft(boolean left) {
		this.left = left;
	}

	
	// getter for if this entity is going towards the right
	public boolean isRight() {
		return right;
	}

	
	// sets this enemy to the right direction
	public void setRight(boolean right) {
		this.right = right;
	}

	
	// getter for if this entity is going towards the up direction
	public boolean isUp() {
		return up;
	}

	
	// sets this enemy to the up direction
	public void setUp(boolean up) {
		this.up = up;
	}

	
	// getter for if this entity is going towards the down direction
	public boolean isDown() {
		return down;
	}

	
	// sets this enemy to the down direction
	public void setDown(boolean down) {
		this.down = down;
	}

	
	// sets this enemy to the jumping action
	public void setJump(boolean jump){
		this.jump = jump;
	}


}
