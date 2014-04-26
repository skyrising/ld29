package de.skyrising.ld29.render.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import de.skyrising.ld29.Game;
import de.skyrising.ld29.Util;
import de.skyrising.ld29.render.gui.GuiControl.TextAlign;

public class GuiMainMenu extends GuiScreen {
	
	public GuiMainMenu() {
		Font f = Util.getFont(26);
		controlList.add(new GuiButton(this, 0, 0, 0, 150, 30, "NEW GAME")
		.setDrawBackground(false).setFont(f).setTextAlign(TextAlign.LEFT));
		controlList.add(new GuiButton(this, 1, 0, 0, 150, 30, "LOAD GAME").
				setDrawBackground(false).setFont(f).setTextAlign(TextAlign.LEFT));
		controlList.add(new GuiButton(this, 2, 0, 0, 150, 30, "OPTIONS")
		.setDrawBackground(false).setFont(f).setTextAlign(TextAlign.LEFT));
		controlList.add(new GuiButton(this, 3, 0, 0, 150, 30, "QUIT")
		.setDrawBackground(false).setFont(f).setTextAlign(TextAlign.LEFT));
	}

	@Override
	public void render(Game game, Graphics2D g2d, int width, int height) {
		drawBackground(game, g2d);
		
		g2d.setFont(Util.getFont((int)(game.height/2.4)).deriveFont(Font.BOLD));
		Rectangle2D bounds2 = g2d.getFontMetrics().getStringBounds("29", g2d);
		g2d.setColor(new Color(0xFF75BC));
		g2d.setBackground(new Color(0x240248));
		drawString(g2d, "29", (int)(game.width-bounds2.getWidth())/2, game.height/10 + (int)(bounds2.getHeight()/2));
		
		g2d.setPaint(new RadialGradientPaint(game.width/2, game.height/5, 
				game.height/5, new float[]{0,1}, new Color[]{new Color(0xAA000000, true), new Color(0, true)}));
		g2d.fillOval(game.width/2-game.height/5, 0, game.height*2/5, game.height*2/5);
		
		g2d.setFont(Util.getFont(game.height/8).deriveFont(Font.BOLD));
		g2d.setColor(new Color(0xFFFFBA));
		g2d.setBackground(new Color(0xFF9C61));
		Rectangle2D bounds = g2d.getFontMetrics().getStringBounds("GO DEEPER!", g2d);
		drawStringWithShadow(g2d, "GO DEEPER!", (int)(game.width-bounds.getWidth())/2, game.height/10 + (int)bounds.getHeight()/2);
		
		g2d.setFont(Util.getFont(game.height/12).deriveFont(Font.BOLD));
		Rectangle2D bounds1 = g2d.getFontMetrics().getStringBounds("LUDUM DARE", g2d);
		drawStringWithShadow(g2d, "LUDUM DARE", (int)(game.width-bounds1.getWidth())/2, game.height/10 + (int)(bounds.getHeight()*1.2));

		for(GuiControl c : controlList)
			c.setX((int)(game.width*0.03)).setY((int)(game.height-5*(c.getHeight()+5)+c.getId()*(c.getHeight()+5)-game.height*0.03));
		super.render(game, g2d, width, height);
	}
	
	@Override
	public void drawBackground(Game game, Graphics2D g2d) {
		g2d.setPaint(new GradientPaint(0, 0, new Color(0x8e0032), 0, game.height, new Color(0x000726)));
		g2d.fillRect(0, 0, game.width, game.height);
		g2d.setPaint(null);
	}
	
	@Override
	public void event(GuiControl control) {
		System.out.println(control);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		Game.instance.setGuiScreen(null);
	}
}
