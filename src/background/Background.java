package background;

public class Background {
	private int[][] bgData;

	public Background(int[][] bgData){
		this.bgData = bgData;
	}

	public int getSpriteIndex(int x, int y){
		return bgData[y][x];
	}

	public int[][] getBgData(){
		return bgData;
	}

}
