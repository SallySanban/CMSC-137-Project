package entities;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.PlayerConstants.getSpriteAmount;
import static utils.HelpMethods.*;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;
import main.GamePanel;
import utils.LoadSave;

public class Enemy extends Character{
	private BufferedImage[][] animations;
	private int animationTick;
	private int animationIndex;
	private int animationSpeed = 50;
	private int playerAction = IDLE;
	private boolean moving = false;
	private boolean attacking = false;
	private boolean left, right, up, down;
	private boolean jump;
	float playerSpeed = 2.7f;
	private int[][] bgData;
	private float xDrawOffset = 9 * Game.SCALE;
	private float yDrawOffset = 2 * Game.SCALE;
	public int powerValue;


	//for jumping and gravity
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;

	public Enemy(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, 35*Game.SCALE, 50*Game.SCALE);
	}

	public void update() {
		updateAnimationTick();
		setAnimation();
		updatePosition();
//		updateHitbox();
	}

	public void setPower(int power){
		this.powerValue = power;
	}

	public void render(Graphics g) {
		if(left) {
			//g.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset) + 75 - lvlOffset, (int) (hitbox.y - yDrawOffset), -width, height, null);
			g.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset) + 75, (int) (hitbox.y - yDrawOffset), -width, height, null);
		}
		else {
			//g.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset, (int) (hitbox.y - yDrawOffset), width, height, null);
			g.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
		}
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

//		if(!inAir){
//			if(airSpeed > 0)
//				playerAction = JUMP;
//			else
//				playerAction = FALLING;
//		}

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
		if(!left && !right && !inAir)
			return;

		float xSpeed = 0;

		if(jump)
			jump();

		if(left)
			xSpeed -= playerSpeed;

		if(right)
			xSpeed += playerSpeed;

		if(!inAir){
			if(!IsEntityOnFloor(hitbox, bgData)){
				inAir = true;
			}
		}


		if(inAir){
			if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, bgData)){
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			}
			else{
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed) + 40; //
				if(airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
		}else
			updateXPos(xSpeed);
		moving = true;
	}

	private void jump() {
		if(inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;

	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;

	}

	private void updateXPos(float xSpeed) {
		if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, bgData)){
			hitbox.x += xSpeed;
		}else{
			hitbox.x = GetEntityPosNextToWall(hitbox, xSpeed);
		}

	}

	public float getEnemyX(){
		return hitbox.x;
	}

	public float getEnemyY(){
		return hitbox.y;
	}

	public void setEnemyX(float x){
		hitbox.x = x;
	}

	public void setEnemyY(float y){
		hitbox.y = y;
	}


	private void loadAnimations() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.ENEMY_ATLAS);
		animations = new BufferedImage[4][8];

		for(int j=0; j < animations.length; j++) {
			for(int i=0; i < animations[j].length; i++) {
				animations[j][i] = img.getSubimage(i*75, j*75, 75, 75);
			}
		}
	}

	public void loadBgData(int[][] bgData){
		this.bgData = bgData;
		if(!IsEntityOnFloor(hitbox, bgData))
			inAir = true;
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

	public void setJump(boolean jump){
		this.jump = jump;
	}


}
