package de.skyrising.ld29.render.gui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import de.skyrising.ld29.Game;
import de.skyrising.ld29.Util;

public class GuiGameOver extends GuiScreen{

	private int time;
	
	public GuiGameOver(int time) {
		this.time = time;
	}
	@Override
	public void render(Game game, Graphics2D g2d, int width, int height) {
		drawBackground(game, g2d);
		double time = this.time/60D;
		String s = "Time: " + Math.round(time*100)/100D + "s";
		g2d.setColor(Color.WHITE);
		g2d.setBackground(Color.GRAY);
		g2d.setFont(Util.getFont(80));
		Rectangle2D bounds = g2d.getFontMetrics().getStringBounds("YOU WON!", g2d);
		drawStringWithShadow(g2d, "YOU WON!", (int)(game.width-bounds.getWidth())/2, (int)(game.height/4 - bounds.getCenterY()), 3);
		g2d.setFont(Util.getFont(42));
		Rectangle2D bounds1 = g2d.getFontMetrics().getStringBounds(s, g2d);
		drawStringWithShadow(g2d, s, (int)(game.width-bounds1.getWidth())/2, (int)(game.height/2 - bounds1.getCenterY()), 3);
		super.render(game, g2d, width, height);
	}
	
	@Override
	public void drawBackground(Game game, Graphics2D g2d) {
		g2d.setPaint(new GradientPaint(0, 0, new Color(0x8e0032), 0, game.height, new Color(0x000726)));
		g2d.fillRect(0, 0, game.width, game.height);
		g2d.setPaint(null);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		Game.instance.setGuiScreen(new GuiMainMenu());
	}
}
