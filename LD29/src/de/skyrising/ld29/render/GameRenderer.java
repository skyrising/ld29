package de.skyrising.ld29.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import de.skyrising.ld29.Game;
import de.skyrising.ld29.Util;
import de.skyrising.ld29.entity.Entity;

public class GameRenderer implements Renderable {
	
	public int levelDelay;
	public int time = 0;
	
	public void tick() {
		if(levelDelay > 0) {
			levelDelay--;
		}else {
			for(Entity e : Entity.entities)
				e.tick();
			time++;
		}
	}

	@Override
	public void render(Game game, Graphics2D g2d, int width, int height) {
		g2d.setColor(new Color(0xD0D0D0));
		g2d.fillRect(0, 0, width, height);
		Texture level = game.level.render(width*6/8);
		int x1 = width/2 - level.getWidth()/2;
		int x2 = width/2 + level.getWidth()/2;
		int scrollY = height-(height+(int)(Game.instance.player.posY*32+48))%(height+level.getHeight());
		g2d.drawImage(level.getImage(), width/2 - level.getWidth()/2, scrollY, level);
		
		g2d.setColor(Color.DARK_GRAY);
		AffineTransform at = g2d.getTransform();
		g2d.translate(x1, scrollY);
		double scale = level.getWidth()/256D;
		g2d.scale(scale, scale);
		for(Entity e : Entity.entities)
			e.render(game, g2d, width, height);
		g2d.setTransform(at);
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(x1-12, 0, 16, height);
		g2d.fillRect(x2-4, 0, 16, height);
		
		if(levelDelay > 0) {
			Color bgC = Color.DARK_GRAY;
			bgC = new Color(bgC.getRed(), bgC.getGreen(), bgC.getBlue(), (int)(2.55D*levelDelay));
			g2d.setColor(bgC);
			g2d.fillRect(0, 0, width, height);
			String s = game.level.name;
			g2d.setFont(Util.getFont(120));
			g2d.setColor(new Color(0xDDDDDD));
			Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(s, g2d);
			g2d.drawString(s, (float)(width-bounds.getWidth())/2, (float)(height+bounds.getHeight())/2);
		}
	}

}
