package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JPanel;

/**
 * A GameContainer object represents the board of the current game. More
 * specifically it displays the sticks and the rows and is also responsible to
 * act when sticks are selected/removed.
 */
public class GameContainer extends JPanel {
    private static final long serialVersionUID = -6216108142740882565L;
    private JPanel rowNumbers;
    private JPanel playingField;
    private StickView[][] sticksOnBoard;
    private int[] sticks;

    /**
     * Creates a new GameContainer with the specified initial configuration of
     * sticks.
     * 
     * @param sticks The inital state of the board
     */
    public GameContainer(int[] sticks) {
        super();

        setLayout(new BorderLayout());

        this.sticks = sticks;

        rowNumbers = new JPanel();
        rowNumbers.setLayout(new GridLayout(sticks.length, 1));
        rowNumbers.setBackground(Color.GREEN);

        initializePlayingField(sticks);

        add(rowNumbers, BorderLayout.WEST);
        add(playingField, BorderLayout.CENTER);

    }

    /**
     * Initializes the playing field. This includes constructing the JPanel,
     * setting its attributes and creating a 2D array {@code sticksOnBoard} that
     * represents each stick as a StickView.
     * 
     * @param sticks The initial state of the board
     */
    private void initializePlayingField(int[] sticks) {
        playingField = new JPanel();
        int maxSticks = Arrays.stream(sticks).max().getAsInt();

        playingField.setLayout(new GridLayout(sticks.length, maxSticks));
        playingField.setBackground(Color.GREEN);
        playingField.setOpaque(true);

        sticksOnBoard = new StickView[sticks.length][maxSticks];

        for (int i = 0; i < sticks.length; i++) {
            rowNumbers.add(new RowNumber(i + 1));
            for (int j = 0; j < maxSticks; j++) {
                sticksOnBoard[i][j] = new StickView(i, j, j < sticks[i]);
                MouseListener listener = new MatchViewListener();
                sticksOnBoard[i][j].addMouseListener(listener);
                playingField.add(sticksOnBoard[i][j]);
            }
        }
    }

    /**
     * Updates the playing field so that the new state that is being passed as
     * an argument becomes the new playing field.
     * 
     * @param sticks The new state of the game
     */
    public void updatePlayingField(int[] sticks) {
        for (int i = 0; i < sticksOnBoard.length; i++) {
            for (int j = 0; j < sticksOnBoard[i].length; j++) {
                if (sticks[i] <= j) {
                    sticksOnBoard[i][j].remove();
                } else {
                    sticksOnBoard[i][j].recover();
                }
            }
        }
    }

    /**
     * A Listener that responds to several mouse events. This works with a
     * StickView object that gets highlighted when the mouse hovers over it and
     * removed when it is selected or a StickView in the same row further left.
     */
    private class MatchViewListener extends MouseAdapter {

        /**
         * On a mouse click, remove this StickView and all StickViews to the
         * right.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            StickView stick = (StickView) e.getSource();
            if (stick.isOnField()) {
                ((NimGameWindow) getTopLevelAncestor())
                        .removeSticks(stick.getRow());
            }
        }

        /**
         * On a mouse enter, highlight all StickViews left to this and this
         * StickView.
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            StickView stick = (StickView) e.getSource();
            highlightMatches(stick.getRow(), stick.getIndex(), true);
        }

        /**
         * On a mouse exit, remove all highlights of this StickView row.
         */
        @Override
        public void mouseExited(MouseEvent e) {
            StickView stick = (StickView) e.getSource();
            highlightMatches(stick.getRow(), stick.getIndex(), false);
        }
    }

    /**
     * Highlights or removes a highlight from all StickViews left to a certain
     * index in a certain row. Also count how many StickViews were highlighted
     * to update the selected sticks counter.
     * 
     * @param row           The row where to highlight the sticks
     * @param startingIndex The index from where to highlight the sticks
     * @param isHighlighted A boolean value indicating whether the sticks should
     *                      be highlighted or not
     */
    private void highlightMatches(int row, int startingIndex,
            boolean isHighlighted) {
        int selectedSticks = 0;
        for (int i = startingIndex; i < sticks[row]; i++) {
            sticksOnBoard[row][i].setHighlight(isHighlighted);
            if (sticksOnBoard[row][i].isOnField()) {
                selectedSticks += 1;
            }
        }
        if (!isHighlighted) {
            selectedSticks = 0;
        }
        NimGameWindow parent = (NimGameWindow) getTopLevelAncestor();
        parent.updateSelectedSticks(selectedSticks);
    }
}
