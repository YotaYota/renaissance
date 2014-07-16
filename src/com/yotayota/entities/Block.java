package com.yotayota.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.yotayota.framework.GameObject;
import com.yotayota.framework.ObjectId;

public class Block extends GameObject {

	public Block(float x, float y, ObjectId id) {
		super(x, y, id);
		width = 32;
		height = 32;
	}

	@Override
	public void tick() {
	}

	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect((int) x, (int) y, 32, 32);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	@Override
	public boolean hasCollided() {
		return false;
	}
}
