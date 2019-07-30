package model;

/**
 * Enum describing a player
 */
public enum Player {
    /** Value corresponding to the computer player */
    COMPUTER {
        @Override
        public Player other() {
            return HUMAN;
        }

        @Override
        public String toString() {
            return "machine";
        }
    },
    /** Value corresponding to the human player */
    HUMAN {
        @Override
        public Player other() {
            return COMPUTER;
        }

        @Override
        public String toString() {
            return "human";
        }
    };

    /**
     * Returns the player object who is not at turn
     * 
     * @return A Player object who does not move now
     */
    public abstract Player other();
}
