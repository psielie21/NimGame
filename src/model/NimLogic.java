package model;

import java.util.Stack;

public class NimLogic {
	private Board game;
	private Stack<Board> history;
	
	public NimLogic(int[] sticks, boolean isMisere, boolean humanStarts) {
		Player first = humanStarts ? Player.HUMAN : Player.COMPUTER;
		history = new Stack<Board>();
		if(isMisere) {
			game = new Misere(sticks, first);
		} else {
			game = new Nim(sticks, first);
		}
	}
	
	public void removeSticks(int row, int sticks) {
		history.push(game.clone());
		game.remove(row,  sticks);
		if(!game.isGameOver()) {
			game.machineRemove();
		}
		for(int i = 0; i < game.getRowCount(); i++) {
			System.out.println("Row "+ i +": " + game.getSticks(i));
		}	
	}
	
	public boolean isGameOver() {
		return game.isGameOver();
	}
	
	public String getWinningMessage() {
		if(game.isGameOver()) {
			if(game.getWinner() == Player.COMPUTER) {
				return "The computer has won";
			} else {
				return "Congratulations! You have won!";
			}
		} else {
			throw new Error("Game is not over yet");
		}
	}
	
	public void undo() {
		for(int i = 0; i < game.getRowCount(); i++) {
			System.out.println(game.getSticks(i));
		}
		System.out.println(history);
		
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
