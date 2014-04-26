package de.skyrising.ld29.render;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class SimpleTexture implements Texture {
	
	private BufferedImage image;
	private int x, y;

	public SimpleTexture(BufferedImage image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
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
	
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		if(img instanceof BufferedImage) {
			image = (BufferedImage)img;
			return false;
		}
		return true;
	}

}
