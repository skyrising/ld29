package de.skyrising.ld29.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import de.skyrising.ld29.Game;
import de.skyrising.ld29.Util;

public class GameRenderer implements Renderable {

	@Override
	public void render(Game game, Graphics2D g2d, int width, int height) {
		g2d.setColor(new Color(0x333333));
		g2d.fillRect(0, 0, width, height);
		String s = "Coming soon!";
		g2d.setFont(Util.getFont(120));
		g2d.setColor(new Color(0xDDDDDD));
		Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(s, g2d);
		g2d.drawString(s, (float)(width-bounds.getWidth())/2, (float)(height+bounds.getHeight())/2);
	}

}
