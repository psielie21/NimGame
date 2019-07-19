package model;

import java.util.Stack;

public class NimLogic {
	private Board game;
	private Stack<Board> history;
	
	public NimLogic(int[] sticks, boolean regularNimGame, boolean humanStarts) {
		Player first = humanStarts ? Player.HUMAN : Player.COMPUTER;
		history = new Stack<Board>();
		if(regularNimGame) {
			game = new Nim(sticks, first);
		} else {
			game = new Misere(sticks, first);
		}
		history.push(game.clone());
	}
	
	public void removeSticks(int row, int sticks) {
		for(int i = 0; i < game.getRowCount(); i++) {
			System.out.println("Row "+ i +": " + game.getSticks(i));
		}
		game.remove(row,  sticks);
		if(!game.isGameOver()) {
			game.machineRemove();
			history.push(game.clone());
		}
		
	}
	
	public void undo() {
		if(!history.empty()) {
			game = history.pop();
		} 
	}
	
	public int[] getBoardState() {
		int[] boardState = new int[game.getRowCount()];
		for(int i = 0; i < game.getRowCount(); i++) {
			boardState[i] = game.getSticks(i);
		}
		return boardState;
	}
}
