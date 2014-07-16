package com.yotayota.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.yotayota.framework.GameObject;
import com.yotayota.framework.ObjectId;

public class Tree extends GameObject {

	private int alterColor = 0;

	public Tree(float x, float y, ObjectId id) {
		super(x, y, id);
		width = 32;
		height = 32;
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
			g.setColor(Color.GREEN);
			g.fillRect((int) x, (int) y, width, height);
		} else {
			g.setColor(Color.ORANGE);
			g.fillRect((int) x, (int) y, width, height);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	@Override
	public boolean hasCollided() {
		return true;
		
	}
}
