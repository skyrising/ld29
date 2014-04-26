package de.skyrising.ld29;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	public static final boolean DEBUG = true;
	public static final String TITLE = "Game";
	public static final String VERSION = "0.0.0";
	public static final boolean VSYNC = true;
	public static Game instance;

	private static final long serialVersionUID = 1;

	public boolean running = false;
	public BufferedImage image = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_RGB);
	public int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	public JFrame frame;
	public int ticksRunning;
	public int tps = 0;
	public int fps = 0;

	public Game(JFrame frame) {
		if (instance != null)
			throw new RuntimeException("Only one instance allowed");
		instance = this;
		frame.setTitle(TITLE);
		frame.setSize(1280, 720);
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setPreferredSize(frame.getSize());
		frame.setLocationRelativeTo(null);
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		this.frame = frame;
	}

	@Override
	public void run() {
		System.out.println("Started");
		running = true;
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1e9 / 60;
		int frames = 0;
		int ticks = 0;
		long lastSec = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = !VSYNC;
			while (unprocessed >= 1) {
				ticks++;
				ticksRunning++;
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastSec >= 1000) {
				lastSec = System.currentTimeMillis();
				if (DEBUG && frame != null)
					frame.setTitle(TITLE + " - " + ticks + "TPS " + frames
							+ "FPS");
				tps = ticks;
				fps = frames;
				ticks = 0;
				frames = 0;
			}
		}

		cleanUp();
	}

	public void init() {
		
	}

	public void tick() {

	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		if(image == null || pixels == null || image.getWidth() != getWidth() || image.getHeight() != getHeight()) {
			resize();
		}
		
		for(int i = 0; i < pixels.length; i++)
			pixels[i] = 0;
		
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, image.getWidth(), image.getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	public void resize() {
		image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		System.out.println("Resized to " + getWidth() + "x" + getHeight());
	}

	public void cleanUp() {

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		new Game(frame).run();
	}
}
