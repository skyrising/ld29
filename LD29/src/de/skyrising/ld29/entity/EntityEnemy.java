package de.skyrising.ld29.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import de.skyrising.ld29.Game;

public class EntityEnemy extends Entity {

	public EntityEnemy() {
		super();
	}
	
	@Override
	public Rectangle2D getBounds() {
		return new Rectangle2D.Float(posX, posY, 0.9F, 1F);
	}

	@Override
	public void render(Game game, Graphics2D g2d, int width, int height) {
		g2d.setColor(Color.RED);
		g2d.fillRect((int)(posX*16), (int)(posY*16), (int)(0.9*16), 16);
	}
}
