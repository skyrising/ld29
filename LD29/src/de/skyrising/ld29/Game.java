package de.skyrising.ld29;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;

import de.skyrising.ld29.render.GameRenderer;
import de.skyrising.ld29.render.ResourceManager;
import de.skyrising.ld29.render.SpriteSheet;
import de.skyrising.ld29.render.gui.GuiMainMenu;
import de.skyrising.ld29.render.gui.GuiScreen;

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
	public int width, height;
	
	public Random random = new Random();
	public ResourceManager resourceManager;
	public GuiScreen currentScreen;
	public SpriteSheet mainSprites;
	public GameRenderer gameRenderer;
	public boolean paused = true;

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
		
		init();

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
							+ "FPS" + (currentScreen != null ? " - " + currentScreen.getClass().getSimpleName() : ""));
				tps = ticks;
				fps = frames;
				ticks = 0;
				frames = 0;
			}
		}

		cleanUp();
	}

	public void init() {
		System.out.println("Fonts: " + new ArrayList<String>(Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames())));
		resourceManager = new ResourceManager();
		mainSprites = resourceManager.loadSpriteSheet("sprites.png", 16, 16);
		setGuiScreen(new GuiMainMenu());
	}

	public void tick() {
		if(currentScreen != null)
			currentScreen.tick();
		if(paused && (currentScreen == null || !currentScreen.doesPauseGame())) {
			paused = false;
			System.out.println("Unpaused");
		}else if(!paused && currentScreen != null && currentScreen.doesPauseGame()) {
			paused = true;
			System.out.println("Paused");
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		if(image == null || pixels == null || width != getWidth() || height != getHeight()) {
			resize();
		}
		
		Graphics2D g2d = (Graphics2D)image.getGraphics();
		g2d.setFont(Util.getFont(40));
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, width, height);
		
		if(gameRenderer != null)
			if(currentScreen == null || currentScreen.renderGame())
				gameRenderer.render(this, g2d, width, height);
		
		if(currentScreen != null)
			currentScreen.render(this, g2d, width, height);
		
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, image.getWidth(), image.getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	public void resize() {
		image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		width = getWidth();
		height = getHeight();
		System.out.println("Resized to " + width + "x" + height);
	}
	
	public void setGuiScreen(GuiScreen newScreen) {
		if(currentScreen != null) {
			currentScreen.cleanUp();
			this.removeMouseListener(currentScreen);
			this.removeMouseMotionListener(currentScreen);
			this.removeKeyListener(currentScreen);
		}
		currentScreen = newScreen;
		if(currentScreen != null) {
			this.addMouseListener(currentScreen);
			this.addMouseMotionListener(currentScreen);
			this.addKeyListener(currentScreen);
			currentScreen.init();
		}else {
			if(gameRenderer == null)
				gameRenderer = new GameRenderer();
		}
	}

	public void cleanUp() {

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		new Game(frame).run();
	}
}
