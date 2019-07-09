import java.util.Arrays;

/**
 * The nim class provides an implementation of the classic nim game
 * where a computer plays against a human player. The moves made by
 * the computer are always optimal in the sense that once a winning
 * position is reached, the computer eventually wins the whole game.
 */
public class Nim implements Board {
    protected int[] sticks;
    private static boolean verbose = false;
    private Move lastMove;
    protected Player human;
    protected Player computer;

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
    public Nim(int[] initialState, Player human, Player computer) {
        this.sticks = initialState;
        this.lastMove = new Move();
        this.human = human;
        this.computer = computer;
    }

    /**
     * Returns the number of rows.
     *
     * @return the number of rows of the current game
     */
    @Override
    public int getRowCount() {
        return sticks.length;
    }

    /**
     * Returns the number of sticks in a specified row.
     *
     * @param row the row of which the sticks are needed
     * @return number of sticks
     */
    @Override
    public int getSticks(int row) {
        return sticks[row];
    }

    /**
     * Performs a move of the human player.
     *
     * @param row the row where the player wants to remove the sticks
     * @param s the amount of sticks that should be removed
     * @throws IllegalArgumentException The provided move is illegal
     */
    @Override
    public void remove(int row, int s) {
        if (isLegalMove(row, s)) {
            sticks[row] -= s;
            lastMove.setMove(row, s, human);
        } else {
            throw new IllegalArgumentException("The provided move is illegal");
        }
    }

    /**
     * Performs a move of the machine.
     * If it is on a winning position it keeps its advantage
     * or otherwise tries to get to a winning position
     */
    @Override
    public void machineRemove() {
        int nimSum = calculateNimSum();
        //check if we are in a winning state
        if (nimSum != 0) {
            handleWinningState(nimSum);
        } else {
            handleLosingState();
        }
    }

    /**
     * Returns the last method made in the game.
     *
     * @return the last move in a Move object
     */
    @Override
    public Move getLastMove() {
        return lastMove;
    }

    /**
     * Checks if all rows of sticks are empty.
     *
     * @return true if no move can be played or false otherwise
     */
    @Override
    public boolean isGameOver() {
        for (int i = 0; i < sticks.length; i++) {
            if (sticks[i] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Getter method for the winner
     *
     * @return the winner or null if there is none
     */
    @Override
    public Player getWinner() {
        if (isGameOver()) {
            return lastMove.getPlayer();
        } else {
            return null;
        }
    }

    /**
     * Deep clones this object
     *
     * @return an identical Board object of the current game
     */
    @Override
    public Board clone() {
        int[] copiedArray = Arrays.copyOf(sticks, sticks.length);
        Nim clonedGame = new Nim(copiedArray, human, computer);
        return clonedGame;
    }

    /**
     * Setter method for the global verbose attribute
     * This attribute controls how much text is being
     * outputted in the toString method
     *
     * @param v the new value for the verbose attribute
     */
    public static void setVerbose(boolean v) {
        verbose = v;
    }

    private static boolean getVerbose() {
        return verbose;
    }

    /**
     * Gives the sticks on the board in a string representation
     * If the verbose flag is set to true it appends also
     * the binary representation of the amount of each row of sticks.
     * Notice that the zero-indexed representation is outputted in
     * one-indexed fashion
     *
     * @return a string representation of the current board state
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < sticks.length; i++) {
            String line = (i + 1) + ": " + sticks[i];
            if (Nim.getVerbose()) {
                line += " (" + Integer.toBinaryString(sticks[i]) + ")";
            }
            output.append(line + "\n");
        }

        if (verbose) {
            int nimSum = calculateNimSum();
            output.append("Nim sum: " + nimSum + " ("
                    + Integer.toBinaryString(nimSum) + ")\n");
        }

        return output.toString();
    }

    /**
     * Checks if a potential move is legal.
     *
     * @param row the row of the move
     * @param s the amount of sticks to be removed
     * @return whether it is legal to make this make
     */
    private boolean isLegalMove(int row, int s) {
        return ((row >= 0 && row < sticks.length) && s <= sticks[row]);
    }

    /**
     * Takes the XOR of all the amount of sticks on the board
     * which corresponds to the nim sum of the current state
     *
     * @return the nim state of the current board state
     */
    private int calculateNimSum() {
        int nimSum = 0;
        for (int i = 0; i < sticks.length; i++) {
            nimSum ^= sticks[i];
        }
        return nimSum;
    }

    /**
     * When we are in a winning state we can win the whole
     * game by playing optimal. This is by reducing the nim sum
     * to zero, so that the opponent is forced to leave a nonzero
     * nim sum which is equivalent to a winning state.
     *
     * @param nimSum the current nim sum of the board
     */
    private void handleWinningState(int nimSum) {
        int row = chooseRow(nimSum);
        int s = sticks[row] - (sticks[row] ^ nimSum);

        finalizeMachineMove(row, s);
    }

    /**
     * Since there is no correct strategy to follow
     * when we are in a losing state, we remove
     * half of the sticks in the topmost row.
     */
    private void handleLosingState() {
        int row = getTopRow();
        int s;
        if (sticks[row] % 2 == 0) {
            s = sticks[row] / 2;
        } else {
            s = (sticks[row] + 1) / 2;
        }

        finalizeMachineMove(row, s);
    }

    /**
     * After all the logic is done and the machine has decided
     * on its move, this method handles the actual change of
     * the state of the board
     *
     * @param row the row where the sticks should be removed
     * @param s the amount of sticks to be removed
     */
    protected void finalizeMachineMove(int row, int s) {
        sticks[row] -= s;
        lastMove.setMove(row, s, computer);
    }

    /**
     * Chooses the row with from which the computer removes the sticks.
     * The row is per specification the minimal, valid row,
     * where valid is explained in the function {@link #isValidRow(int, int)}
     *
     * @param nimSum the nimSum of the current board
     * @return the index of the row that satisfies the described property
     */
    private int chooseRow(int nimSum) {

        int minVal = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < sticks.length; i++) {
            if (sticks[i] < minVal && isValidRow(sticks[i], nimSum)) {
                minVal = sticks[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    /**
     * Checks if a row is a valid choice for the computer move.
     * A row is a valid choice if its binary representation
     * has a one bit at the index of the highest bit
     * of the binary representation of the current nim sum.
     *
     * @param sticks the number of sticks in the row
     * @param nimSum the nim sum of the board
     * @return whether the row is a valid row
     */
    private boolean isValidRow(int sticks, int nimSum) {
        /*
        In order to be independent on the binary representation of the numbers
        we make sure that the highest bit of the binary representation of
        sticks in the row is at least that of the highest bit in the nim sum.
        Paired with the condition that the XOR of sticks and
        the nim sum is less than the amount of current sticks, we get
        that sticks has to have a one bit at the desired position
         */
        int highestBit = Integer.highestOneBit(nimSum);
        return (sticks ^ nimSum) <= sticks
                && Integer.highestOneBit(sticks) >= highestBit;
    }

    /**
     * Finds the topmost row that has at least one element.
     *
     * @return the index of the row
     */
    private int getTopRow() {
        for (int i = 0; i < sticks.length; i++) {
            if (sticks[i] != 0) {
                return i;
            }
        }
        return -1;
    }
}