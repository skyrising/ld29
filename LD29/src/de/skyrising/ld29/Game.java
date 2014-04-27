package de.skyrising.ld29;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import de.skyrising.ld29.entity.Entity;
import de.skyrising.ld29.entity.EntityPlayer;
import de.skyrising.ld29.level.Level;
import de.skyrising.ld29.render.GameRenderer;
import de.skyrising.ld29.render.ResourceManager;
import de.skyrising.ld29.render.SpriteSheet;
import de.skyrising.ld29.render.gui.GuiGameOver;
import de.skyrising.ld29.render.gui.GuiMainMenu;
import de.skyrising.ld29.render.gui.GuiScreen;

public class Game extends Canvas implements Runnable, MouseListener, MouseMotionListener, KeyListener{
	public static final boolean DEBUG = true;
	public static final String TITLE = "Game";
	public static final String VERSION = "0.0.0";
	public static final boolean VSYNC = false;
	public static final float PLAYER_SPEED = 2;
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
	public List<Level> levels;
	public int levelNum;
	public Level level;
	public boolean paused = true;
	public EntityPlayer player;
	
	public boolean keyLeft = false;
	public boolean keyJump = false;
	public boolean keyRight = false;

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
		System.out.println("Loading...");
		random.setSeed(29);
		levels = Level.randomLevels(10);
		System.out.println("Loaded " + levels.size() + " levels");
		for(Level l : levels)
			System.out.println(l);
		mainSprites = resourceManager.loadSpriteSheet("sprites.png", 16, 16);
		setGuiScreen(new GuiMainMenu());
	}

	public void tick() {
		if(currentScreen != null)
			currentScreen.tick();
		
		if(!paused && currentScreen != null && currentScreen.doesPauseGame()) {
			paused = true;
			System.out.println("Paused");
		}else if(paused && (currentScreen == null || !currentScreen.doesPauseGame())) {
			paused = false;
			System.out.println("Unpaused");
			if(player == null)
				startGame(0);
		}
		if(paused)
			Entity.entities.clear();
		if(gameRenderer != null)
			gameRenderer.tick();
		if(!paused && player != null) {
			if(keyLeft && player.vX > -1)
				player.vX -= PLAYER_SPEED;
			if(keyRight && player.vX < 1)
				player.vX += PLAYER_SPEED;
			if(keyJump)
				player.jump = true;
			/*for(Entity e : Entity.entities)
				e.tick();*/
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
	
	public void startGame(int level) {
		gameRenderer = new GameRenderer();
		levelNum = level-1;
		nextLevel();
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		player = new EntityPlayer();
		player.posX = (float) (8 - player.getBounds().getCenterX());
		if(currentScreen != null)
			setGuiScreen(null);
	}
	
	public void stopGame() {
		player = null;
		setGuiScreen(new GuiGameOver(gameRenderer.time));
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		new Game(frame).run();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
			keyLeft = true;
		if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)
			keyRight = true;
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE)
			keyJump = true;
		if(e.getKeyCode() == KeyEvent.VK_S)
			player.posY = 0;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
			keyLeft = false;
		if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)
			keyRight = false;
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE)
			keyJump = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(paused)
			return;
		player.shooting = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(paused)
			return;
		player.shooting = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(paused)
			return;
		player.shooting = false;
	}

	public void nextLevel() {
		levelNum++;
		if(levelNum < levels.size()) {
			level = levels.get(levelNum);
			if(gameRenderer == null)
				gameRenderer = new GameRenderer();
			gameRenderer.levelDelay = 100;
		}
		else
			stopGame();
	}
}
