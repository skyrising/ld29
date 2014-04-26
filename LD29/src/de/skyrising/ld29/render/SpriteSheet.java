package de.skyrising.ld29.render;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SpriteSheet extends SimpleTexture {

	private int spriteWidth;
	private int spriteHeight;
	private Map<Integer, Texture> sprites = new HashMap<Integer, Texture>();
	
	public SpriteSheet(BufferedImage image, int spriteWidth, int spriteHeight) {
		super(image, 0, 0);
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}
	
	public Texture getSprite(int index) {
		if(sprites.containsKey(index))
			return sprites.get(index);
		int sx = image.getWidth() / spriteWidth;
		int ix = index % sx;
		int iy = index / sx;
		int x = ix*spriteWidth;
		int y = iy*spriteWidth;
		BufferedImage spriteImg = image.getSubimage(x, y, spriteWidth, spriteHeight);
		Texture sprite = new SimpleTexture(spriteImg, x, y);
		sprites.put(index, sprite);
		return sprite;
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		if(img instanceof BufferedImage) {
			image = (BufferedImage)img;
			sprites.clear();
			return false;
		}
		return true;
	}
}
