package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * A StickView object is a JPanel that represents one stick on the board. It
 * knows at what position it is placed and can be removed or readded to the
 * board if the user decides to undo his move.
 */
public class StickView extends JPanel {
    private static final long serialVersionUID = -1407263098108227593L;
    private final float stickWidthPercentage = 0.05f;
    private final float yOffsetPercentage = 0.2f;
    private final float stickHeightPercentage = 0.7f;
    private final float stickRadiusPercentage = 0.1f;
    private int row;
    private int index;
    private boolean isOnBoard;
    private boolean isHighlighted;

    /**
     * Creates a new StickView at a destined row and specified index.
     * 
     * @param row       The index of the row of this stick
     * @param index     The index of this stick in its row
     * @param isOnBoard A boolean value whether this stick is on the board or
     *                  not involved in the game at all
     */
    public StickView(int row, int index, boolean isOnBoard) {
        this.row = row;
        this.index = index;
        this.isOnBoard = isOnBoard;
        this.isHighlighted = false;
        setOpaque(true);
        setBackground(Color.GREEN);
    }

    /**
     * Removes this stick from the current board.
     */
    public void remove() {
        isOnBoard = false;
        setOpaque(false);
        repaint();
    }

    /**
     * Recovers a stick that might previously been removed.
     */
    public void recover() {
        isOnBoard = true;
        setOpaque(true);
        repaint();
    }

    /**
     * Highlights this StickView according to the parameter isHighlighted.
     * 
     * @param isHighlighted A boolean value indicating whether this View should
     *                      be highlighted or not
     */
    public void setHighlight(boolean isHighlighted) {
        if (isHighlighted) {
            setBackground(Color.BLUE);
        } else {
            setBackground(Color.GREEN);
        }
        repaint();
    }

    /**
     * Gets the current state of highlighting
     * 
     * @return Return true if this StickView is highlighted and false otherwise.
     */
    public boolean isHighlighted() {
        return isHighlighted;
    }

    /**
     * Checks whether this Stick is still on the board.
     * 
     * @return Return true if this Stick is still on the board and false
     *         otherwise.
     */
    public boolean isOnField() {
        return isOnBoard;
    }

    /**
     * Gets the index of the row where this stick is in.
     * 
     * @return Return the index of the row of this stick.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the index of this stick in its row.
     * 
     * @return Return the index position of this stick
     */
    public int getIndex() {
        return index;
    }

    /**
     * Draws the Stick depending on whether it is currently on the board. More
     * specifically a centered rectangle and circle are being drawn
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (isOnBoard) {
            int middleX = getWidth() / 2;

            int x = middleX - ((int) (getHeight() * stickWidthPercentage)) / 2;
            int y = (int) (getHeight() * yOffsetPercentage);
            int height = (int) (getHeight() * stickHeightPercentage);

            g2.setColor(Color.YELLOW);
            g2.fillRect(x, y, ((int) (getHeight() * stickWidthPercentage)),
                    height);

            int r = (int) (getHeight() * stickRadiusPercentage);
            g2.setColor(Color.RED);
            g2.fillOval(middleX - r / 2, y - r, r, r);
        }
    }
}
