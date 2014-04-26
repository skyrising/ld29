package de.skyrising.ld29.render;

import java.awt.Graphics2D;

import de.skyrising.ld29.Game;

public interface Renderable {
	void render(Game game, Graphics2D g2d, int width, int height);
}
