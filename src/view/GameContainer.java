package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JPanel;

public class GameContainer extends JPanel{
	JPanel rowNumbers;
	JPanel playingField;
	StickView[][] sticksOnBoard;
	int[] sticks;
	
	public GameContainer(int[] sticks) {
		super();
		
		setLayout(new BorderLayout());
		
		this.sticks = sticks;
		
		rowNumbers = new JPanel();
		rowNumbers.setLayout(new GridLayout(sticks.length, 1));
		rowNumbers.setBackground(Color.GREEN);
		
		playingField = new JPanel();
		int maxSticks = Arrays.stream(sticks).max().getAsInt();
		playingField.setLayout(new GridLayout(sticks.length, maxSticks));
		playingField.setBackground(Color.BLUE);
		
		initializePlayingField(sticks, maxSticks);
		
		add(rowNumbers, BorderLayout.WEST);
		add(playingField, BorderLayout.CENTER);
		this.setBackground(Color.RED);
	}
	
	private void initializePlayingField(int[] sticks, int maxSticks) {
		sticksOnBoard = new StickView[sticks.length][maxSticks];
		
		for(int i = 0; i < sticks.length; i++) {
			rowNumbers.add(new RowNumber(i+1));
			for(int j = 0; j < maxSticks; j++) {
				sticksOnBoard[i][j] = new StickView(i, j, j < sticks[i]);
				MouseListener listener = new MatchViewListener();
				sticksOnBoard[i][j].addMouseListener(listener);
				playingField.add(sticksOnBoard[i][j]);
			}
		}
	}
	
	public void updatePlayingField(int[] sticks) {
		for(int i = 0; i < sticksOnBoard.length; i++) {
			for(int j = 0; j < sticksOnBoard[i].length; j++) {
				//TODO write one method for activating/deactivating StickView
				if(sticks[i] <= j) {
					sticksOnBoard[i][j].remove();
				} else {
					sticksOnBoard[i][j].recover();
				}
			}
		}
	}
	
	private class MatchViewListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			StickView stick = (StickView) e.getSource();
			if(stick.isOnField()) {
				((NimGameWindow) getTopLevelAncestor()).removeSticks(stick.getRow());
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			StickView stick = (StickView) e.getSource();
			highlightMatches(stick.getRow(), stick.getIndex(), true);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			StickView stick = (StickView) e.getSource();
			highlightMatches(stick.getRow(), stick.getIndex(), false);
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}
	}
	
	private void highlightMatches(int row, int startingIndex, boolean turnOn) {
		int selectedSticks = 0;
		for(int i = startingIndex; i < sticks[row]; i++) {
			sticksOnBoard[row][i].setHighlight(turnOn);
			if(sticksOnBoard[row][i].isOnField()) {
				selectedSticks += 1;
			}
		}
		if(!turnOn) {
			selectedSticks = 0;
		}
		NimGameWindow parent = (NimGameWindow) getTopLevelAncestor();
		parent.updateSelectedSticks(selectedSticks);
	}
}
