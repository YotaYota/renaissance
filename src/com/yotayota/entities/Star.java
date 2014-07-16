package com.yotayota.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.yotayota.framework.GameObject;
import com.yotayota.framework.ObjectId;

/**
 * @author Johannes Jeppsson
 * 
 *         Object with no collision. Rendering color is gray (R, G, B) = (100,
 *         100, 100)
 */
public class Star extends GameObject {

	private int alterColor = 0;
	private Random rand;

	public Star(float x, float y, ObjectId id) {
		super(x, y, id);
		width = 8;
		height = 8;
		rand = new Random();
		alterColor = rand.nextInt(120);
	}

	/**
	 * alterColor is incremented by 1 each tick an then taken modulo 120.
	 */
	public void tick() {
		alterColor++;
		int a = alterColor;
		this.alterColor = a % 120;
	}

	/**
	 * If the integer alterColor is above 60, then it is rendered as green.
	 * Otherwise it is rendered as a darker green.
	 */
	public void render(Graphics g) {
		if (alterColor > 60) {
			g.setColor(Color.WHITE);
			g.fillRect((int) x, (int) y, width, height);
		} else {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect((int) x, (int) y, width, height);
		}
	}

	/**
	 * Returns empty rectangle to avoid collision.
	 */
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 0, 0);
	}

	@Override
	public boolean hasCollided() {
		return false;
		
	}
}