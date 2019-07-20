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
	boolean isHighlighted;
	
	public StickView(int row, int index, boolean isVisible) {
		this.row = row;
		this.index = index;
		this.isVisible = isVisible;
		this.isHighlighted = false;
	}
	
	public void remove() {
		isVisible = false;
		setOpaque(true);
		repaint();
	}
	
	public void recover() {
		isVisible = true;
		//setOpaque(false);
		//isHighlighted = false;
		repaint();
	}
	
	public void setHighlight(boolean state) {
		this.isHighlighted = state;
		
		if(isVisible) {
			setOpaque(!state);
			repaint();
		} 
	}
	
	public boolean isHighlighted() {
		return isHighlighted;
	}
	
	public boolean isOnField() {
		return isVisible;
	}
	
	public int getRow() {
		return row; 
	}
	
	public int getIndex() {
		return index;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if(isVisible) { 
			int middleX = getWidth() / 2;
			
			int x = middleX - STICK_WIDTH/2;
			int y = (int) (getHeight() * 0.30);
			int height = (int) (getHeight() * 0.90);
			
			g2.setColor(Color.ORANGE);
			g2.fillRect(x, y, STICK_WIDTH, height);
			
			int r = (int) (getHeight() * 0.10);
			g2.setColor(Color.BLUE);
			g2.fillOval(middleX - r/2, y - r, r, r);
			
			g2.setBackground(Color.BLUE);
		}
	}
}
