package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.NimLogic;

public class NimGameWindow extends JFrame{
	JPanel gameContainer;
	ButtonContainer buttonContainer;
	NimLogic nimLogic;
	private int selectedSticks;
	
	public NimGameWindow() {
		super();
		
		setLayout(new BorderLayout());
		selectedSticks = 0;
		
		nimLogic = new NimLogic(new int[] { 1, 2, 3, 5 }, true, true);
		
		gameContainer = new GameContainer(nimLogic.getBoardState());
		buttonContainer = new ButtonContainer(new NewButtonListener());
		
		add(gameContainer, BorderLayout.CENTER);
		add(buttonContainer, BorderLayout.EAST);
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(900,600);
		setVisible(true);
	}
	
	private class NewButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("DO STH NEW");
			Random random = new Random();
			int size = random.nextInt(9) + 1;
			int[] randomArr = new int[size];
			for(int i = 0; i < size; i++) {
				randomArr[i] = random.nextInt(9) + 1;
			}
			// delegate the repaint/revalidate action to the child class
			// or maybe to another method
			nimLogic = new NimLogic(randomArr, false, true);
			remove(gameContainer);
			gameContainer = new GameContainer(nimLogic.getBoardState());
			add(gameContainer);
			revalidate();
		}
	}
	
	private class UndoButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			nimLogic.undo();
			remove(gameContainer);
			gameContainer = new GameContainer(nimLogic.getBoardState());
			add(gameContainer);
			revalidate();
		}
	}
	
	private class QuitButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//dispatchEvent(new WindowEvent(super, WindowEvent.WINDOW_CLOSING));
		}
	}
	
	public void updateSelectedSticks(int selectedSticks) {
		this.selectedSticks = selectedSticks;
		buttonContainer.setSelectedSticks(selectedSticks);
	}
	
	public void removeSticks(int row) {
		nimLogic.removeSticks(row, selectedSticks);
		remove(gameContainer);
		gameContainer = new GameContainer(nimLogic.getBoardState());
		add(gameContainer);
		revalidate();
	}
	
	public static void main(String[] args) {
		JFrame f = new NimGameWindow();
		
	}
}
