package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Currency;

import javax.swing.JPanel;

import entity.Player;
import tiles.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // Screen Setting
    final int originalTitleSize = 16; // 16x16 title
    final int scale = 3;

    public final int tileSize = originalTitleSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    public final int ScreenWidth = tileSize * maxScreenCol; // 768 pixel
    public final int ScreenHeight = tileSize * maxScreenRow;// 576 pixel
    //WorldSettings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);

    KeyHandler keyH = new KeyHandler();

    Thread gameThread;
    
    public CollisionChecker cChecker = new CollisionChecker(this);

    public Player player = new Player(this, keyH);

    public GamePanel() {

        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
//	public void run() {
//		
//		while(gameThread != null) {
//			
//		double drawInterval = 1000000000/FPS; // 0.01666 seconds
//		double nextDrawTime = System.nanoTime() + drawInterval;
//			
//			update();
//		
//			repaint();
//			
//			
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				remainingTime = remainingTime/1000000;
//				
//				if(remainingTime < 0) {
//					remainingTime = 0;
//				}
//				Thread.sleep((long) remainingTime);
//				
//				nextDrawTime += drawInterval;
//				
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
    public void run() {

        double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS :" + drawCount);
                timer = 0;
                drawCount = 0;
            }

        }
    }

    public void update() {
        player.update();

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2); //call tileM.draw(g2) before player for Show Player character if you call tileM.draw after player Player Charater you hide to behide Background

        player.draw(g2);

        g2.dispose();

    }
}
