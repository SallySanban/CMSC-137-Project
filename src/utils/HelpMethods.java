package utils;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import main.Game;

public class HelpMethods {

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] bgData){
		if(!IsSolid(x, y, bgData))
			if(!IsSolid(x+width, y+height, bgData))
				if(!IsSolid(x+width, y, bgData))
					if(!IsSolid(x, y+height, bgData)) {
						return true;}
		return false;
	}

	public static boolean IsSolid(float x, float y, int[][] bgData){
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		if(x < 0 || x >= Game.GAME_WIDTH)
			return true;
		if(y < 0 || y >= Game.GAME_HEIGHT)
			return true;

		int value = bgData[(int) yIndex][(int) xIndex];

		if(value >= 60 || value < 0 || value != 58)
			return true;
		if(x < 0 || x >= Game.GAME_WIDTH)
			return true;
		return false;
	}

	public static float GetEntityPosNextToWall(Rectangle2D.Float hitbox, float xSpeed){
		int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
		if(xSpeed > 0){
			//Right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset + 1;
		}else{
			return currentTile * Game.TILES_SIZE;
			//Left
		}
	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed){
		int currentTile = (int)(hitbox.y / Game.TILES_SIZE);
		if(airSpeed > 0){
			//Falling
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;
		}else{
			//Jumping
			return currentTile * Game.TILES_SIZE;
		}
	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] bgData){
		//check pixel below bottomleft and bottom right
		if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, bgData))
			if(!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, bgData))
				return false;
		return true;

	}
}
