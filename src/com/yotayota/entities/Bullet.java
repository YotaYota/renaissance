package com.yotayota.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.yotayota.framework.GameObject;
import com.yotayota.framework.ObjectId;

public class Bullet extends GameObject {

	int alterColor = 0;

	public Bullet(float x, float y, ObjectId id, int velX,
			int velY) {
		super(x, y, id);
		this.velX = velX;
		this.velY = velY;
	}

	public void tick() {
		x += velX;
		y += velY;
		alterColor++;
		alterColor %= 16;
		hasCollided();
	}

	public void render(Graphics g) {
		if (alterColor > 8) {
			g.setColor(Color.RED);
			g.fillRect((int) x, (int) y, 16, 16);
		} else {
			g.setColor(Color.ORANGE);
			g.fillRect((int) x, (int) y, 16, 16);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 16, 16);
	}

	public boolean hasCollided() {
		return true;
	}
}
