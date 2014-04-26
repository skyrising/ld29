package de.skyrising.ld29.render.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Rectangle2D;

import de.skyrising.ld29.Game;

public class GuiMainMenu extends GuiScreen {

	public void render(Game game, Graphics2D g2d, int width, int height, int scrollX, int scrollY) {
		super.render(game, g2d, width, height, scrollX, scrollY);
		drawBackground(game, g2d);
		
		g2d.setFont(Font.decode("Arial Bold "+game.height/2));
		Rectangle2D bounds2 = g2d.getFontMetrics().getStringBounds("29", g2d);
		g2d.setColor(new Color(0xFF75BC));
		g2d.setBackground(new Color(0x240248));
		drawString(g2d, "29", (int)(game.width-bounds2.getWidth())/2, game.height/12 + (int)(bounds2.getHeight()/2));
		
		g2d.setPaint(new RadialGradientPaint(game.width/2, game.height/5, 
				game.height/5, new float[]{0,1}, new Color[]{new Color(0xAA000000, true), new Color(0, true)}));
		g2d.fillOval(game.width/2-game.height/5, 0, game.height*2/5, game.height*2/5);
		
		g2d.setFont(Font.decode("Arial Bold "+game.height/8));
		g2d.setColor(new Color(0xFFFFBA));
		g2d.setBackground(new Color(0xFF9C61));
		Rectangle2D bounds = g2d.getFontMetrics().getStringBounds("GO DEEPER!", g2d);
		drawStringWithShadow(g2d, "GO DEEPER!", (int)(game.width-bounds.getWidth())/2, game.height/10 + (int)bounds.getHeight()/2);
		
		g2d.setFont(Font.decode("Arial Bold "+game.height/12));
		Rectangle2D bounds1 = g2d.getFontMetrics().getStringBounds("LUDUM DARE", g2d);
		drawStringWithShadow(g2d, "LUDUM DARE", (int)(game.width-bounds1.getWidth())/2, game.height/10 + (int)(bounds.getHeight()*1.2));
	}
	
	
	public void drawBackground(Game game, Graphics2D g2d) {
		g2d.setPaint(new GradientPaint(0, 0, new Color(0x8e0032), 0, game.height, new Color(0x000726)));
		g2d.fillRect(0, 0, game.width, game.height);
		g2d.setPaint(null);
	}
}
