package model;

import java.util.Stack;

/**
 * The Nim logic class provides the model for the nim game. 
 * It can handle all the input from the user and provides the
 * necessary functionalities that need to be displayed.
 */
public class NimLogic {
	private Board game;
	private Stack<Board> history;
	
	/**
	 * Creates a nim logic with the specified parameters.
	 * 
	 * @param sticks An array of positive integers representing 
	 * 				 the sticks per row.
	 * @param isMisere A boolean value indicating which mode shall be played.
	 * @param humanStarts A boolean value signaling whether the human player
	 * 					  starts.
	 */
	public NimLogic(int[] sticks, boolean isMisere, boolean humanStarts) {
		Player first = humanStarts ? Player.HUMAN : Player.COMPUTER;
		history = new Stack<Board>();
		if(isMisere) {
			game = new Misere(sticks, first);
		} else {
			game = new Nim(sticks, first);
		}
	}
	
	/**
	 * Remove a certaina amount of sticks from the specified row.
	 * 
	 * @param row The index of the row from where to remove sticks.
	 * @param sticks The amount of sticks to be removed.
	 */
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
	
	/**
	 * Checks whether the current game is already over.
	 * 
	 * @return A boolean value that is true if the game is over
	 * 		   and false if the game is still going on.
	 */
	public boolean isGameOver() {
		return game.isGameOver();
	}
	
	/**
	 * Gets a winning message but only if the game is over.
	 * 
	 * @return Returns a message of the current winner.
	 */
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
	
	/**
	 * Undoes a previously made move.
	 */
	public void undo() {
		for(int i = 0; i < game.getRowCount(); i++) {
			System.out.println(game.getSticks(i));
		}
		System.out.println(history);
		
		if(!history.empty()) {
			game = history.pop();
		} 
	}
	
	/**
	 * Gets the current state of the board.
	 * 
	 * @return Returns the current state of the board.
	 */
	public int[] getBoardState() {
		int[] boardState = new int[game.getRowCount()];
		for(int i = 0; i < game.getRowCount(); i++) {
			boardState[i] = game.getSticks(i);
		}
		return boardState;
	}
}
