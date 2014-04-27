package de.skyrising.ld29.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import de.skyrising.ld29.Game;
import de.skyrising.ld29.render.SpriteSheet;

public class EntityPlayer extends Entity {

	public boolean shooting;
	
	public EntityPlayer() {
		super();
		//noClip = true;
	}

	@Override
	public Rectangle2D getBounds() {
		return new Rectangle2D.Float(posX, posY-1.6F, 0.8F, 1.6F);
	}

	@Override
	public void render(Game game, Graphics2D g2d, int width, int height) {
		g2d.setColor(Color.GREEN);
		SpriteSheet sprites = game.mainSprites;
		int phase = vX == 0 ? 0 : (int)((walkProgress*4)%4) + 1;
		g2d.translate(posX*16, posY*16-32);
		boolean right = vX > 0;
		BufferedImage img0 = sprites.getSprite(phase).getImage();
		BufferedImage img1 = sprites.getSprite(phase+16).getImage();
		if(right) {
			g2d.drawImage(img0, 0, 0, null);
			g2d.drawImage(img1, 0, 16, null);
		}else {
			g2d.drawImage(img0, 0, 0, 16, 16, 16, 0, 0, 16, null);
			g2d.drawImage(img1, 0, 16, 16, 32, 16, 0, 0, 16, null);
		}
		g2d.translate(-posX*16, -posY*16-32);
	}

}
