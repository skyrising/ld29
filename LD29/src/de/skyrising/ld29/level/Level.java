package de.skyrising.ld29.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.skyrising.ld29.Game;
import de.skyrising.ld29.render.SimpleTexture;
import de.skyrising.ld29.render.Texture;

public class Level {
	public String name;
	public List<List<Integer>> barriers;
	
	public Texture render(int width) {
		int h = width/4;
		int w = width/16;
		BufferedImage img = new BufferedImage(w*16, barriers.size()*h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)img.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int y = 0;
		for(List<Integer> barrier : barriers) {
			boolean solid = true;
			int x = 0;
			for(int i : barrier) {
				if(solid && i > 0) {
					g2d.setColor(Color.GRAY);
					g2d.fillRect(x, y+h-w/4, i*w, w/4);
					g2d.setColor(Color.DARK_GRAY);
					g2d.fillRect(x-1, y+h-w/4, i*w+2, 4);
				}
				x+=i*w;
				solid = !solid;
			}
			if(x < 16*w && solid) {
				g2d.setColor(Color.GRAY);
				g2d.fillRect(x, y+h-w/4, 16*w-x, w/4);
				g2d.setColor(Color.DARK_GRAY);
				g2d.fillRect(x-1, y+h-w/4, 16*w-x+1, 4);
			}
			y+=h;
		}
		return new SimpleTexture(img, 0, 0);
	}
	
	@Override
	public String toString() {
		return "Level:"+name+barriers;
	}
	
	public static List<Level> randomLevels(int num) {
		List<Level> list = new ArrayList<Level>(num);
		Random random = Game.instance.random;
		for(int i = 0; i < num; i++) {
			Level level = new Level();
			level.name = i == 0 ? "Ground Level" : "U" + i;
			int barriers = Math.min(random.nextInt(3 + i) + 3, 6);
			level.barriers = new ArrayList<List<Integer>>();
			for(int b = 0; b < barriers; b++) {
				List<Integer> barrier = new ArrayList<Integer>();
				int x = 0;
				while(x < 16) {
					int w = random.nextInt((num - i)/(num/16+1) + 3) + 1;
					if((x+=w)<16)
						barrier.add(w);
					if(++x < 16)
						barrier.add(1);
				}
				level.barriers.add(barrier);
			}
			list.add(level);
		}
		return list;
	}
}
