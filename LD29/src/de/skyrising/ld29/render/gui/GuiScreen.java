package de.skyrising.ld29.render.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import de.skyrising.ld29.Game;
import de.skyrising.ld29.render.gui.GuiButton.ButtonState;

public abstract class GuiScreen extends Gui implements MouseListener, MouseMotionListener, KeyListener{
	public List<GuiControl> controlList = new ArrayList<GuiControl>();
	protected int width, height;
	
	@Override
	public void render(Game game, Graphics2D g2d, int width, int height) {
		this.width = width;
		this.height = height;
		for(GuiControl c : controlList)
			c.render(game, g2d, width, height);
	}
	
	public void tick() {
		
	}
	
	public void drawBackground(Game game, Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, width, height);
	}
	
	public void event(GuiControl control) {
		
	}
	
	/**
	 * Invoked when the GuiScreen is added
	 */
	public void init() {
		
	}
	
	public void cleanUp() {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println(e);
		for(GuiControl c : controlList)
			if(c instanceof GuiButton && e.getButton() == 1 && c.getX() < e.getX() && c.getY() < e.getX() && e.getX() < c.getX() + c.getWidth() && e.getY() < c.getY() + c.getHeight())
				((GuiButton)c).setState(ButtonState.ACTIVE);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println(e);
		for(GuiControl c : controlList)
			if(c instanceof GuiButton && e.getButton() == 1 && c.getX() < e.getX() && c.getY() < e.getX() && e.getX() < c.getX() + c.getWidth() && e.getY() < c.getY() + c.getHeight())
				((GuiButton)c).setState(ButtonState.HOVER);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println(e);
		for(GuiControl c : controlList)
			if(c instanceof GuiButton && c.getX() < e.getX() && c.getY() < e.getX() && e.getX() < c.getX() + c.getWidth() && e.getY() < c.getY() + c.getHeight())
				((GuiButton)c).setState(ButtonState.HOVER);
			else if(c instanceof GuiButton)
				((GuiButton)c).setState(ButtonState.NORMAL);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	public boolean doesPauseGame() {
		return true;
	}
	
	public boolean renderGame() {
		return false;
	}
}
