package de.skyrising.ld29.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import de.skyrising.ld29.Game;
import de.skyrising.ld29.Util;

public class GameRenderer implements Renderable {

	@Override
	public void render(Game game, Graphics2D g2d, int width, int height) {
		g2d.setColor(new Color(0xD0D0D0));
		g2d.fillRect(0, 0, width, height);
		/*String s = "Coming soon!";
		g2d.setFont(Util.getFont(120));
		g2d.setColor(new Color(0xDDDDDD));
		Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(s, g2d);
		g2d.drawString(s, (float)(width-bounds.getWidth())/2, (float)(height+bounds.getHeight())/2);*/
		
		Texture level = Game.instance.levels.get(0).render(width*6/8);
		int x1 = width/2 - level.getWidth()/2;
		int x2 = width/2 + level.getWidth()/2;
		int scrollY = height-(Game.instance.ticksRunning*4)%(height+level.getHeight());
		g2d.drawImage(level.getImage(), width/2 - level.getWidth()/2, scrollY, level);
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(x1-12, 0, 16, height);
		g2d.fillRect(x2-4, 0, 16, height);
	}

}
