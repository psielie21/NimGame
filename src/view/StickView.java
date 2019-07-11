package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class StickView extends JPanel{
	private final int STICK_WIDTH = 7;
	int row;
	int index;
	boolean isVisible;
	
	public StickView(int row, int index, boolean isVisible) {
		this.row = row;
		this.index = index;
		this.isVisible = isVisible;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(isVisible) {
			Graphics2D g2 = (Graphics2D) g;
			int middleX = getWidth() / 2;
			
			int x = middleX - STICK_WIDTH/2;
			int y = (int) (getHeight() * 0.30);
			int height = (int) (getHeight() * 0.90);
			
			g2.setColor(Color.ORANGE);
			g2.fillRect(x, y, STICK_WIDTH, height);
			
			int r = (int) (getHeight() * 0.10);
			g2.setColor(Color.BLUE);
			g2.fillOval(middleX - STICK_WIDTH, y - r, r, r);
		}
		
	}
}
