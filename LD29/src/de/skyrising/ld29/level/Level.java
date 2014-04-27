package de.skyrising.ld29.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;

import de.skyrising.ld29.render.SimpleTexture;
import de.skyrising.ld29.render.Texture;

public class Level {
	private String name;
	private List<List<Integer>> barriers;
	
	public Texture render(int width) {
		BufferedImage img = new BufferedImage((width/16)*16, barriers.size()*96, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)img.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(0xCCCCCC));
		g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
		
		int w = width/16;
		int y = 0;
		for(List<Integer> barrier : barriers) {
			boolean solid = true;
			int x = 0;
			for(int i : barrier) {
				if(solid && i > 0) {
					g2d.setColor(Color.GRAY);
					g2d.fillRect(x, y+80, i*w, 16);
					g2d.setColor(Color.DARK_GRAY);
					g2d.fillRect(x-1, y+78, i*w+2, 4);
				}
				x+=i*w;
				solid = !solid;
			}
			if(x < 16*w && solid) {
				g2d.setColor(Color.GRAY);
				g2d.fillRect(x, y+80, 16*w-x, 16);
				g2d.setColor(Color.DARK_GRAY);
				g2d.fillRect(x-1, y+78, 16*w-x+1, 4);
			}
			y+=96;
		}
		return new SimpleTexture(img, 0, 0);
	}
	
	@Override
	public String toString() {
		return "Level:"+name+barriers;
	}
}
