package de.skyrising.ld29.render.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import de.skyrising.ld29.Game;

public abstract class GuiScreen extends Gui{
	public List<GuiControl> controlList = new ArrayList<GuiControl>();
	protected int width, height;
	
	@Override
	public void render(Game game, Graphics2D g2d, int width, int height, int scrollX,
			int scrollY) {
		this.width = width;
		this.height = height;
		for(GuiControl c : controlList)
			c.render(game, g2d, width, height, scrollX, scrollY);
	}
	
	public void tick() {
		
	}
	
	public void drawBackground(Game game, Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, width, height);
	}
	
	/**
	 * Invoked when the GuiScreen is added
	 */
	public void init() {
		
	}
	
	public void cleanUp() {
		
	}
}
