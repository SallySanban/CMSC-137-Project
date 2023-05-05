package entities;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.PlayerConstants.getSpriteAmount;
import static utils.HelpMethods.CanMoveHere;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import utils.LoadSave;

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
	private int[][] bgData;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
	}

	public void update() {
		updateAnimationTick();
		setAnimation();
		updatePosition();
		updateHitbox();
	}

	public void render(Graphics g) {
		g.drawImage(animations[playerAction][animationIndex], (int) x, (int) y, null);
		drawHitbox(g);
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
		if(!left && !right && !up && !down)
			return;

		float xSpeed = 0, ySpeed = 0;

		if(left && !right)
			xSpeed = -playerSpeed;
		else if(right && !left)
			xSpeed = playerSpeed;

		if(up && !down)
			ySpeed = -playerSpeed;
		else if(down && !up)
			ySpeed = playerSpeed;

		if(CanMoveHere(x+xSpeed, y+ySpeed, width, height, bgData)){
			this.x += xSpeed;
			this.y += ySpeed;
			moving = true;
		}

	}

	private void loadAnimations() {

	BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

	animations = new BufferedImage[4][8];

	for(int j=0; j < animations.length; j++) {
		for(int i=0; i < animations[j].length; i++) {
			animations[j][i] = img.getSubimage(i*75, j*75, 75, 75);
		}
	}


	}

	public void loadBgData(int[][] bgData){
		this.bgData = bgData;
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
