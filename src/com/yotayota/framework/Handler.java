package com.yotayota.framework;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.yotayota.entities.Block;
import com.yotayota.entities.Gate;
import com.yotayota.entities.Player;
import com.yotayota.entities.Star;
import com.yotayota.entities.Tree;

/**
 * Contains a LinkedList with each entity of the game, where each entity is a
 * subclass of the GameObject class. Ticking and rendering can thus be done by
 * doing so for each item in the list of the handler. Player and rest of game
 * entities are separated.
 */
public class Handler {

	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	public GameObject tempObject;
	public Player player;

	public void tick() {

		for (int i = 0; i < objects.size(); i++) {
			tempObject = objects.get(i);
			tempObject.tick();
		}

		player.tick();

		checkCollision();
	}

	public void render(Graphics g) {
		for (int i = 0; i < objects.size(); i++) {
			tempObject = objects.get(i);
			tempObject.render(g);
		}
		player.render(g);
	}

	/**
	 * The level is rendered by reading off a pixel-image. Different color in
	 * the image refers to different in-game objects as defined in the code
	 * below.
	 * 
	 * @param image
	 *            the image that the file is loaded from.
	 */
	public void loadLevelFromImage(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();

		// Looping through the pixels of the image
		for (int xx = 0; xx < h; xx++) {
			for (int yy = 0; yy < w; yy++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;

				// White => Block.
				if (red == 255 && green == 255 && blue == 255) {
					addObject(new Block(xx * 32, yy * 32, ObjectId.Block));
				}
				// 100,100,100 => Star.
				if (red == 100 && green == 100 && blue == 100) {
					addObject(new Star(xx * 32, yy * 32, ObjectId.Star));
				}
				// 0,255,0 => Tree.
				if (red == 0 && green == 255 && blue == 0) {
					addObject(new Tree(xx * 32, yy * 32, ObjectId.Tree));
				}
				// 100,0,0 => Gate.
				if (red == 100 && green == 0 && blue == 0) {
					addObject(new Gate(xx * 32, yy * 32, ObjectId.Gate));
				}
			}
		}
	}

	/**
	 * Adds an entity to the handler's list. If the entity's id is player, then
	 * it is added as the handler's player, otherwise it is added to the
	 * handler's list.
	 * 
	 * @param object
	 *            entity to be added to the handler's list. The object's tick()
	 *            and render() methods will then be called in the game loop.
	 */
	public void addObject(GameObject object) {
		if (object.getId() == ObjectId.Player)
			player = (Player) object;
		else
			objects.add(object);
	}

	/**
	 * Removes an entity from the handler's list.
	 * 
	 * @param object
	 *            entity to be removed.
	 */
	public void removeObject(GameObject object) {
		objects.remove(object);
	}

	public void removePlayer() {
		this.player = null;
	}

	public int getSize() {
		return objects.size();
	}

	/**
	 * Iterates through all entities in the ArrayList objects. Collision is
	 * determined if getBounds() of the two objects return intersecting
	 * rectangles. In case of collision the method hasCollided() is called for
	 * the objects and returns true if the object should be removed, false
	 * otherwise.
	 */
	private void checkCollision() {

		// Bullet collision
		iterObjOne: for (int i = 0; i < objects.size(); i++) {
			GameObject objOne = objects.get(i);

			// Only check collision for Bullet objects.
			if (objOne.getId() != ObjectId.Bullet)
				continue;
			Rectangle boundsOne = objOne.getBounds();

			for (GameObject objTwo : objects) {
				// Defines what Bullets does not collide with.
				if (objTwo.getId() == ObjectId.Block
						| objTwo.getId() == ObjectId.Star
						| objTwo.getId() == ObjectId.Bullet | objOne == objTwo)
					continue;
				// Collision => call hasCollided() of the two objects.
				if (boundsOne.intersects(objTwo.getBounds())) {
					if (objOne.hasCollided())
						removeObject(objOne);
					if (objTwo.hasCollided())
						removeObject(objTwo);
					continue iterObjOne;
				}
			}
		}

		// Player collision
		Rectangle playerBounds = player.getBounds();
		for (int i = 0; i < objects.size(); i++) {
			GameObject obj = objects.get(i);
			if (obj.getId() == ObjectId.Bullet)
				continue;
			if (playerBounds.intersects(obj.getBounds())) {

				float playerX = player.getX();
				float playerY = player.getY();
				int dX = (int) (playerX - obj.getX());
				int dY = (int) (playerY - obj.getY());

				player.setVelX(0);
				player.setVelY(0);
				player.setX(playerX + dX / 4);
				player.setY(playerY + dY / 4);

				player.hasCollided();
			}
		}
	}
}
