package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.Frame;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
		this.setTitle("Nim Game");
		selectedSticks = 0;
		
		nimLogic = new NimLogic(new int[] { 1, 2, 3, 5 }, false, true);
		
		gameContainer = new GameContainer(nimLogic.getBoardState());
		buttonContainer = new ButtonContainer(new NewButtonListener(), new UndoButtonListener(),
											  new QuitButtonListener());
		
		add(gameContainer, BorderLayout.CENTER);
		add(buttonContainer, BorderLayout.EAST);
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(900,600);
		setVisible(true);
	}
	
	private class NewButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Random random = new Random();
			int size = random.nextInt(9) + 1;
			int[] randomArr = new int[size];
			for(int i = 0; i < size; i++) {
				randomArr[i] = random.nextInt(9) + 1;
			}
			// delegate the repaint/revalidate action to the child class
			// or maybe to another method
			boolean isMisere = ((ButtonContainer) buttonContainer).isMisereBoxChecked();
			boolean isHumanFirst = !((ButtonContainer) buttonContainer).isFirstMoveBoxChecked();
			nimLogic = new NimLogic(randomArr, isMisere, isHumanFirst);
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
			((GameContainer) gameContainer).updatePlayingField(nimLogic.getBoardState());
			revalidate();
		}
	}
	
	private class QuitButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	
	public void updateSelectedSticks(int selectedSticks) {
		this.selectedSticks = selectedSticks;
		buttonContainer.setSelectedSticks(selectedSticks);
	}
	
	public void removeSticks(int row) {
		System.out.println("row: " + row + " sticks: " + selectedSticks);
		nimLogic.removeSticks(row, selectedSticks);
		
		((GameContainer) gameContainer).updatePlayingField(nimLogic.getBoardState());
		if(nimLogic.isGameOver()) {
			showWinningDialog(nimLogic.getWinningMessage());
		}
		revalidate();
	}
	
	private void showWinningDialog(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	
	public static void main(String[] args) {
		JFrame f = new NimGameWindow();
		
	}
}
