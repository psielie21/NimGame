import java.util.Arrays;

/**
 * Misere represents an alternative to the regular game mode.
 * The rules of the game stay almost the same, the only difference
 * is that now the player who removes the last stick loses the game.
 */
public class Misere extends Nim {

    /**
     * Sets up a game of nim, with the given initial
     * board configuration, more specifically how many
     * rows there are in total on the board and how
     * many sticks there are per row.
     *
     * @param initialState an array describing the inital board state
     * @param human a Player object referencing the human player
     * @param computer a Player object referencing the computer
     */
    public Misere(int[] initialState, Player human, Player computer) {
        super(initialState, human, computer);
    }

    @Override
    public void machineRemove() {
        /* Use the same strategy as in the regular nim game
         * with an exception from this rule when there is
         * only one row with strictly more than one match
         * which is called the misere row
        */
        int misereRow = getMisereRowIndex();
        if (misereRow > -1) {
            int count = countRowsWithOneStick();
            int s = (count % 2 == 0)
                    ? sticks[misereRow] - 1 : sticks[misereRow];
            finalizeMachineMove(misereRow, s);
        } else {
            super.machineRemove();
        }
    }

    @Override
    public Board clone() {
        int[] copiedArray = Arrays.copyOf(sticks, sticks.length);
        Nim clonedGame = new Misere(copiedArray, human, computer);
        return clonedGame;
    }

    /**
     * Gets the winner of the misere mode.
     * The difference is that the winner in misere mode
     * is the player who did not take the last stick.
     * @return the Player who has won the game
 *              or null if no on has won the game yet
     */
    @Override
    public Player getWinner() {
        if (isGameOver()) {
            if (getLastMove().getPlayer() == computer) {
                return human;
            } else {
                return computer;
            }
        } else {
            return null;
        }
    }

    /**
     * Checks if the misere condition is satisfied
     * and if so which row is the one
     * The misere condition is true if and only if there is
     * exactly one row with strictly more than one stick left
     * @return -1 if the misere condition is not satisfied
     *      or the row index otherwise
     */
    private int getMisereRowIndex() {
        int misereRowIndex = -1;
        for (int i = 0; i < sticks.length; i++) {
            if (sticks[i] > 1) {
                if (misereRowIndex == -1) {
                    misereRowIndex = i;
                } else {
                    return -1;
                }
            }
        }
        return misereRowIndex;
    }

    /**
     * Counts how many rows have exactly one stick left
     * @return the number of rows with one stick left
     */
    private int countRowsWithOneStick() {
        int rowCt = 0;
        for (int i = 0; i < sticks.length; i++) {
            if (sticks[i] == 1) {
                rowCt += 1;
            }
        }
        return rowCt;
    }
}