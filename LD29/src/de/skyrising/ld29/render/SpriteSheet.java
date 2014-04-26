package de.skyrising.ld29.render;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SpriteSheet implements Texture {

	private BufferedImage image;
	private int spriteWidth;
	private int spriteHeight;
	private Map<Integer, Texture> sprites = new HashMap<Integer, Texture>();
	
	public SpriteSheet(BufferedImage image, int spriteWidth, int spriteHeight) {
		this.image = image;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
	}

	@Override
	public int getWidth() {
		return image.getWidth();
	}

	@Override
	public int getHeight() {
		return image.getHeight();
	}

	@Override
	public BufferedImage getImage() {
		return image;
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
