package de.skyrising.ld29.render.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import de.skyrising.ld29.render.Renderable;

public abstract class Gui implements Renderable{
	
	public void drawString(Graphics2D g2d, String string, int x, int y) {
		g2d.drawString(string, x, y);
	}
	
	public void drawStringWithShadow(Graphics2D g2d, String string, int x, int y) {
		Color c = g2d.getColor();
		g2d.setColor(g2d.getBackground());
		g2d.drawString(string, x+3, y+3);
		g2d.setColor(c);
		g2d.drawString(string, x, y);
	}
}
