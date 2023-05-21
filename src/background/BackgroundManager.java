package background;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

public class BackgroundManager {

	private Game game;
	private BufferedImage[] bgSprite;
	private Background background;

	public BackgroundManager(Game game){
		this.game = game;
//		bgSprite = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_ATLAS);
		importBgSprites();
		background = new Background(LoadSave.GetBgData());

	}

	private void importBgSprites(){
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_ATLAS);
		bgSprite = new BufferedImage[60];
		for(int j = 0; j< 6; j++){
			for(int i = 0; i<10; i++){
				int index = j*10 + i;
				bgSprite[index] = img.getSubimage(i*32,  j*32,  32,  32);
			}
		}
	}

//	public void draw(Graphics g, int lvlOffset){
//
//		for(int j = 0; j<Game.TILES_IN_HEIGHT; j++){
//			for(int i = 0; i<Game.TILES_IN_WIDTH; i++){
//				int index = background.getSpriteIndex(i, j);
//				g.drawImage(bgSprite[index], Game.TILES_SIZE*i - lvlOffset,  Game.TILES_SIZE*j,  Game.TILES_SIZE,  Game.TILES_SIZE, null);
//			}
//		}
//	}
	
	public void draw(Graphics g){
		for(int j = 0; j<Game.TILES_IN_HEIGHT; j++){
			for(int i = 0; i<Game.TILES_IN_WIDTH; i++){
				int index = background.getSpriteIndex(i, j);
				g.drawImage(bgSprite[index], Game.TILES_SIZE*i,  Game.TILES_SIZE*j,  Game.TILES_SIZE,  Game.TILES_SIZE, null);
			}
		}
	}

	public void update(){

	}

	public Background getCurrBg(){
		return background;
	}
}
