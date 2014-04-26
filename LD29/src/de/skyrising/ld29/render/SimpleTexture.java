package de.skyrising.ld29.render;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class SimpleTexture implements Texture {
	
	protected BufferedImage image;
	protected int x, y;

	public SimpleTexture(BufferedImage image, int x, int y) {
		this.image = image;
		if(image.getType() > BufferedImage.TYPE_INT_ARGB) {
			BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			newImage.getGraphics().drawImage(image, 0, 0, null);
			this.image = newImage;
		}
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
