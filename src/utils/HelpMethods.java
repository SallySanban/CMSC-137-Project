package utils;

import main.Game;

public class HelpMethods {

	public static boolean CanMoveHere(float x, float y, int width, int height, int[][] bgData){
		if(!IsSolid(x, y, bgData))
			if(!IsSolid(x+width, y+height, bgData))
				if(!IsSolid(x+width, y, bgData))
					if(!IsSolid(x, y+height, bgData))
						return true;

		return false;
	}

	private static boolean IsSolid(float x, float y, int[][] bgData){
		if(x < 0 || x >= Game.GAME_WIDTH)
			return true;
		if(y < 0 || y >= Game.GAME_HEIGHT)
			return true;

		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		int value = bgData[(int)xIndex][(int)yIndex];

		if(value >= 60 || value < 0 || value != 58)
			return true;
		return false;
	}
}
