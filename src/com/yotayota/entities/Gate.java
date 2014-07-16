package com.yotayota.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.yotayota.framework.GameObject;
import com.yotayota.framework.ObjectId;

/**
 * 
 * @author Johannes Jeppsson
 * 
 *         Render color (R, G, B) = (100, 0, 0)
 * 
 */
public class Gate extends GameObject {

	private boolean open = true;
	private int timer = 0;

	public Gate(float x, float y, ObjectId id) {
		super(x, y, id);
		width = 32*8;
		height = 8;
	}

	public void tick() {
		if (open) {
			timer++;
			if (timer == 60) {
				open = false;
				timer = 0;
			}
		}
	}

	public void render(Graphics g) {
		if (open) {
			g.setColor(Color.WHITE);
			g.fillRect((int) x - width / 2, (int) y, width / 3, height);
			g.fillRect((int) x + width / 2, (int) y, width / 3, height);
		} else {
			g.setColor(Color.WHITE);
			g.fillRect((int) x, (int) y, width, height);
		}
	}

	public Rectangle getBounds() {
		if (!open)
			return new Rectangle((int) x, (int) y, width, height);
		else {
			return new Rectangle((int) x, (int) y, 0, 0);
		}
	}

	@Override
	public boolean hasCollided() {
		open = true;
		return false;
	}
}
