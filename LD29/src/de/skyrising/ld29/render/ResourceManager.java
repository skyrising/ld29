package de.skyrising.ld29.render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ResourceManager {
	
	private Map<String, Texture> textures = new HashMap<String, Texture>();
	private Map<String, SpriteSheet> spriteSheets = new HashMap<String, SpriteSheet>();
	
	public ResourceManager() {
		
	}
	
	public SpriteSheet loadSpriteSheet(String url, int sw, int sh) {
		if(spriteSheets.containsKey(url))
			return spriteSheets.get(url);
		try {
			BufferedImage img = ImageIO.read(ResourceManager.class.getResourceAsStream("/res/" + url));
			SpriteSheet s = new SpriteSheet(img, sw, sh);
			spriteSheets.put(url, s);
			return s;
		} catch (IOException e) {
			return null;
		}
	}
	
	public Texture loadTexture(String url) {
		if(textures.containsKey(url))
			return textures.get(url);
		if(spriteSheets.containsKey(url))
			return spriteSheets.get(url);
		try {
			BufferedImage img = ImageIO.read(ResourceManager.class.getResourceAsStream("/res/" + url));
			Texture tex = new SimpleTexture(img, 0, 0);
			textures.put(url, tex);
			return tex;
		} catch (IOException e) {
			return null;
		}
	}
}
