package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public abstract class Character {
	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;

	public Character(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	protected void drawHitbox(Graphics g){
		//to visualize hitbox
		g.setColor(Color.RED);
		g.drawRect((int) hitbox.x,(int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	protected void initHitbox(float x, float y, float width, float height){
		hitbox = new Rectangle2D.Float(x, y, width,  height);

	}

//	protected void updateHitbox(){
//		hitbox.x = (int) x;
//		hitbox.y = (int) y;
//	}

	public Rectangle2D.Float getHitbox(){
		return hitbox;
	}
}
