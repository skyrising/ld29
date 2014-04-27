package de.skyrising.ld29.entity;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import de.skyrising.ld29.Game;
import de.skyrising.ld29.render.Renderable;

public abstract class Entity implements Renderable {
	public static List<Entity> entities = new ArrayList<Entity>();
	private static int nextId = 0;
	public static final float G = 9.81F;

	public boolean jump = false;
	public int id;
	public int health = 20;
	public float posX, posY;
	public float lastPosX, lastPosY;
	public float vX, vY;
	public float walkProgress = 0;
	public boolean onGround = true;
	public boolean noClip = false;
	public int layer;

	public Entity() {
		id = nextId++;
		entities.add(this);
	}

	public void tick() {
		Rectangle2D bounds = getBounds();
		lastPosX = posX;
		lastPosY = posY;
		vY += G / 60;
		layer = ((int) posY) / 4;
		if(layer>=Game.instance.level.barriers.size()) {
			Game.instance.nextLevel();
			posY = 0;
		}
		onGround = posY >= layer * 4 + 3.75F;
		if (jump && onGround) {
			vY -= 4F;
			jump = false;
		} else if (jump)
			jump = false;
		if(vY > 5)
			vY = 5;
		posY += vY / 60;
		posX += vX / 60;
		if (posX < 0)
			posX = 0;
		if (posX + bounds.getWidth() > 15.75)
			posX = (float) (15.75 - bounds.getWidth());
		layer = ((int) posY) / 4;
		onGround = posY >= layer * 4 + 3.75F;
		if(layer >= Game.instance.level.barriers.size())
			return;
		boolean leftSolid = true;
		boolean rightSolid = true;
		int x = (int) Math.ceil(posX + (1 - bounds.getWidth())/2);
		int x1 = 0;
		boolean solidGround = true;
		boolean solid = true;
		for (int i : Game.instance.level.barriers.get(layer)) {
			if ((x1 += i) >= x)
				break;
			solid = !solid;
		}
		if (!solid)
			leftSolid = false;
		x = (int) Math.floor(posX + bounds.getWidth() - (1 - bounds.getWidth())/2);
		x1 = 0;
		for (int i : Game.instance.level.barriers.get(layer)) {
			if ((x1 += i) >= x)
				break;
			solid = !solid;
		}
		if (!solid)
			rightSolid = false;
		solidGround = leftSolid || rightSolid;
		vX = onGround ? vX * 0.7F : vX;
		if(vX < 0.1 && vX > -0.1)
			vX = 0;
		if (posY > layer * 4 + 3.75F && onGround && solidGround && !noClip) {
			posY = layer * 4 + 3.75F;
			vY = 0;
		}
		walkProgress += Math.abs(posX - lastPosX);
		// posY = 0;
	}

	public void render() {

	}

	public abstract Rectangle2D getBounds();
}
