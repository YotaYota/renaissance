package com.yotayota.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import com.yotayota.entities.Player;
import com.yotayota.framework.Camera;
import com.yotayota.framework.Handler;
import com.yotayota.framework.InputHandler;
import com.yotayota.framework.ObjectId;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int HEIGHT = 800;
	public static int WIDTH = 600;
	public static int levelHeight;
	public static int levelWidth;

	private boolean running = false;
	private Thread thread;

	private Handler handler;
	BufferedImageLoader loader;
	Camera cam;

	public synchronized void start() {

		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		init();
		this.requestFocus();

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;

		// Game loop
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames + ", Ticks: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	/**
	 * Called before the game loop. Any logic before the game loop is set up
	 * here.
	 */
	private void init() {

		HEIGHT = getHeight();
		WIDTH = getWidth();

		loader = new BufferedImageLoader();
		BufferedImage levelImg = loader.loadImage("/level.png");

		levelWidth = levelImg.getWidth() * 8;
		System.out.println(levelWidth);
		levelHeight = levelImg.getHeight() * 8;
		System.out.println(levelHeight);

		handler = new Handler();
		handler.loadLevelFromImage(levelImg);

		cam = new Camera(WIDTH / 2, HEIGHT / 2);
		Player playerOne = new Player(10, 10, new InputHandler(this), handler,
				cam, ObjectId.Player);

		handler.addObject(playerOne);
	}

	/**
	 * Called during game loop to increment the logic of each entity
	 */
	private void tick() {
		handler.tick();
		cam.tick(handler.player);
	}

	/**
	 * Called during game loop to paint each entity.
	 */
	private void render() {
		// if Computer has extra time it will create extra images behind the
		// first one, ready to be loaded
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;

		/**
		 * Beginning of game-drawing code.
		 */

		g.setColor(new Color(45, 25, 45));
		g.fillRect(0, 0, getWidth(), getHeight());

		g2d.translate(cam.getX(), cam.getY()); // begin of camera

		handler.render(g);

		g2d.translate(-cam.getX(), -cam.getY()); // end of camera

		/**
		 * End of game-drawing code.
		 */

		g.dispose();
		bs.show();

	}

	/**
	 * Starts the game.
	 */
	public static void main(String args[]) {
		new Window(HEIGHT, WIDTH, "Renaissance Prototype", new Game());
	}
}
