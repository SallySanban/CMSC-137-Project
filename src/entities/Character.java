package entities;

public abstract class Character {
	protected float x, y;
	protected int hp;
	
	public Character(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
