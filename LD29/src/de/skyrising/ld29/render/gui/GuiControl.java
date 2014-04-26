package de.skyrising.ld29.render.gui;

public abstract class GuiControl extends Gui {
	public abstract int getId();
	public abstract int getX();
	public abstract int getY();
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract TextAlign getTextAlign();
	public abstract GuiControl setX(int x);
	public abstract GuiControl setY(int y);
	public abstract GuiControl setWidth(int width);
	public abstract GuiControl setHeight(int height);
	public abstract GuiControl setTextAlign(TextAlign align);
	
	public static enum TextAlign {
		LEFT, CENTER, RIGHT;
	}
}
