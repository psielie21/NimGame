/**
 * A class representing a move of a player in the nim game
 * It only stores basic information, like who removed in
 * which row how many sticks.
 */
public final class Move {
    private int row;
    private int sticksRemoved;
    private Player player;

    /**
     * An empty constructor since a move object is only updated
     * through its methods
     */
    public Move() { }

    /**
     * Gets the player who made this move
     * @return a Player object
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Updates this move object to the new values
     * @param row the new row from which sticks were removed
     * @param sticksRemoved how many sticks were removed
     * @param player the Player who removed the sticks
     */
    public void setMove(int row, int sticksRemoved, Player player) {
        this.row = row;
        this.sticksRemoved = sticksRemoved;
        this.player = player;
    }

    /**
     * Returns a string representation of the currently stored move.
     * It outputs all stored information, like player, row and sticks.
     * @return a string representation of this move
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        if (this.player == Player.COMPUTER) {
            output.append("Player machine ");
        } else {
            output.append("Human player ");
        }
        output.append("removed " + this.sticksRemoved
                + " stick(s) from row " + (this.row + 1) + ".");
        return output.toString();
    }
}
