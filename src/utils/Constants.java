package utils;

public class Constants {
	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int JUMP = 3;
		public static final int FALLING = 4;

		public static int getSpriteAmount(int playerAction) {
			switch(playerAction) {
				case RUNNING:
					return 8;
				case IDLE:
					return 5;
				case JUMP:
					return 7;
				case ATTACK:
					return 6;
				default:
					return 1;
			}
		}
	}
}
