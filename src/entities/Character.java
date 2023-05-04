package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Character {
	protected float x, y;
	protected int height, width;
	protected Rectangle hitbox;

	public Character(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		initHitbox();
	}

	protected void drawHitbox(Graphics g){
		g.setColor(Color.RED);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}

	private void initHitbox(){
		hitbox = new Rectangle((int)x, (int)y, width, height);
	}

	public void updateHitbox(){
		hitbox.x = (int)x;
		hitbox.y = (int)y;
	}

	public Rectangle getHitbox(){
		return hitbox;
	}
}
