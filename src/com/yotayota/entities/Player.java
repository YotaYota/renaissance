package com.yotayota.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.yotayota.framework.Camera;
import com.yotayota.framework.GameObject;
import com.yotayota.framework.Handler;
import com.yotayota.framework.InputHandler;
import com.yotayota.framework.ObjectId;

public class Player extends GameObject {

	private InputHandler input;
	private Handler handler;
	private int speed = 5;
	private Color playerColor = Color.BLUE;
	/**
	 * @return facing of the entity. For facingX, 1 is defined as right and -1
	 *         is defined as left. For facingY, 1 is defined as up and -1 is
	 *         defined as down.
	 */
	private int facingX = 1;
	private int facingY = 0;

	public Player(float x, float y, InputHandler input, Handler handler,
			Camera cam, ObjectId id) {
		super(x, y, id);

		this.input = input;
		this.handler = handler;

		// TODO: might be altered at a later time:
		width = 32;
		height = 32;
	}

	public void tick() {
		int velX = 0;
		int velY = 0;
		facingX = facingY = 0;

		if (input.up.isPressed()) {
			velY--;
			facingY = -1;
		}
		if (input.down.isPressed()) {
			velY++;
			facingY = 1;
		}
		if (input.left.isPressed()) {
			velX--;
			facingX = -1;
		}
		if (input.right.isPressed()) {
			velX++;
			facingX = 1;
		}
		if (input.space.isPressed()) {
			if (facingX != 0 || facingY != 0)
				handler.addObject(new Bullet(x, y, ObjectId.Bullet,
						facingX * 10, facingY * 10));
		}

		x += speed * velX;
		y += speed * velY;
	}

	public void render(Graphics g) {
		g.setColor(playerColor);
		g.fillRect((int) x, (int) y, width, height);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	@Override
	public boolean hasCollided() {
		return false;
	}
}
