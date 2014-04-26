package de.skyrising.ld29.render;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public interface Texture extends ImageObserver{
	int getX();
	int getY();
	int getWidth();
	int getHeight();
	BufferedImage getImage();
}
