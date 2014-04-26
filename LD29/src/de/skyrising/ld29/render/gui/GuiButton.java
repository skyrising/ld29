package de.skyrising.ld29.render.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import de.skyrising.ld29.Game;
import de.skyrising.ld29.Util;
import de.skyrising.ld29.render.Texture;

public class GuiButton extends GuiControl {
	protected final int id;
	protected GuiScreen parent;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected String text;
	protected Texture texture;
	protected boolean drawBackground = true;
	protected int bgColor = 0x000000;
	protected int textColor = 0xFFFFFF;
	protected Font font = Util.getFont(22);
	protected ButtonState lastState = ButtonState.NORMAL;
	protected ButtonState currentState = ButtonState.NORMAL;
	protected boolean enabled = true;
	protected TextAlign textAlign = TextAlign.CENTER;

	public GuiButton(GuiScreen parent, int id, int x, int y, String text) {
		this(parent, id, x, y, 120, 25, text);
	}

	public GuiButton(GuiScreen parent, int id, int x, int y, int width,
			int height, String text) {
		this(parent, id, x, y, width, height, text, null);
	}

	public GuiButton(GuiScreen parent, int id, int x, int y, int width,
			int height, String text, Texture texture) {
		this.parent = parent;
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.texture = texture;
	}

	@Override
	public void render(Game game, Graphics2D g2d, int width, int height) {
		if (texture != null) {
			if (drawBackground)
				g2d.drawImage(texture.getImage(), x, y, x + this.width, y
						+ this.height, texture.getX(), texture.getY(),
						texture.getX() + texture.getWidth(), texture.getY()
								+ texture.getHeight(), new Color(bgColor),
						texture);
			else
				g2d.drawImage(texture.getImage(), x, y, x + this.width, y
						+ this.height, texture.getX(), texture.getY(),
						texture.getX() + texture.getWidth(), texture.getY()
								+ texture.getHeight(), texture);
		} else if (drawBackground) {
			g2d.setColor(new Color(bgColor));
			g2d.fillRect(x, y, this.width, this.height);
		}

		g2d.setFont(font);
		Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(text, g2d);
		g2d.setColor(new Color(textColor));
		if(textAlign == TextAlign.LEFT)
			g2d.drawString(text, x, (float) (y + (this.height + bounds.getHeight()) / 2));
		if(textAlign == TextAlign.CENTER)
			g2d.drawString(text, (float) (x + (this.width - bounds.getWidth()) / 2), (float) (y + (this.height + bounds.getHeight()) / 2));
		if(textAlign == TextAlign.RIGHT)
			g2d.drawString(text, (float) (x + (this.width - bounds.getWidth())), (float) (y + (this.height + bounds.getHeight()) / 2));
	}

	protected void stateChanged() {
		parent.event(this);
	}

	public int getId() {
		return id;
	}

	public GuiScreen getParent() {
		return parent;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getText() {
		return text;
	}

	public Texture getTexture() {
		return texture;
	}

	public boolean isDrawBackground() {
		return drawBackground;
	}

	public int getBackgroundColor() {
		return bgColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public Font getFont() {
		return font;
	}

	public ButtonState getState() {
		return currentState;
	}

	public GuiButton setParent(GuiScreen parent) {
		this.parent = parent;
		return this;
	}

	public GuiButton setX(int x) {
		this.x = x;
		return this;
	}

	public GuiButton setY(int y) {
		this.y = y;
		return this;
	}

	public GuiButton setWidth(int width) {
		this.width = width;
		return this;
	}

	public GuiButton setHeight(int height) {
		this.height = height;
		return this;
	}

	public GuiButton setText(String text) {
		this.text = text;
		return this;
	}

	public GuiButton setTexture(Texture texture) {
		this.texture = texture;
		return this;
	}

	public GuiButton setDrawBackground(boolean drawBackground) {
		this.drawBackground = drawBackground;
		return this;
	}

	public GuiButton setBackgroundColor(int bgColor) {
		this.bgColor = bgColor;
		return this;
	}

	public GuiButton setTextColor(int textColor) {
		this.textColor = textColor;
		return this;
	}

	public GuiButton setFont(Font font) {
		this.font = font;
		return this;
	}

	public GuiButton setState(ButtonState state) {
		this.lastState = currentState;
		this.currentState = state;
		//System.out.println("Button #" + id + " State: " + lastState + " -> " + currentState);
		if (lastState != currentState)
			stateChanged();
		return this;
	}

	@Override
	public TextAlign getTextAlign() {
		return textAlign;
	}

	@Override
	public GuiControl setTextAlign(TextAlign align) {
		this.textAlign = align;
		return this;
	}

	public static enum ButtonState {
		NORMAL, HOVER, ACTIVE;
	}
}
