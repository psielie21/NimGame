package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.NimLogic;

/**
 * This class represents the whole nim game window. It stores the playing field
 * and all the buttons with which the user interacts with.
 */
public class NimGameWindow extends JFrame {
    private static final long serialVersionUID = -7652321492236757596L;
    private JPanel gameContainer;
    private ButtonContainer buttonContainer;
    private NimLogic nimLogic;
    private int selectedSticks;
    private String previousInput;
    private boolean isFirstGame;

    /**
     * Creates a new nim game window. The inital configuration will be requested
     * from the user with a dialog modal.
     */
    public NimGameWindow() {
        super();

        setLayout(new BorderLayout());
        this.setTitle("Nim Game");
        selectedSticks = 0;

        previousInput = "";
        isFirstGame = true;
        setupButtonContainer();
        showNewGameDialog();

        add(buttonContainer, BorderLayout.EAST);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setVisible(true);

    }

    /**
     * Sets up the panel for the buttons. This includes the new, undo and quit
     * button as well as their corresponding keyboard shortcuts.
     * 
     * @return Returns a setup button container
     */
    private ButtonContainer setupButtonContainer() {
        Action newButtonAction = new NewButtonAction("New");
        Action undoButtonAction = new UndoButtonAction("Undo");
        Action quitButtonAction = new QuitButtonAction("Quit");
        buttonContainer = new ButtonContainer(newButtonAction, undoButtonAction,
                quitButtonAction);
        return buttonContainer;
    }

    /**
     * The new button action controls how the new button works. This includes
     * showing a specified new game dialog for the user.
     */
    private class NewButtonAction extends AbstractAction {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * Creates a new button action
         * 
         * @param text The string that gets displayed in the button
         */
        NewButtonAction(String text) {
            super(text);
        }

        /**
         * Shows the user a new game dialog where he can setup the inital state
         * for the next game.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            showNewGameDialog();
        }
    }

    /**
     * The undo button action controls how the undo button works. This means
     * allowing the user the take back his move.
     */
    private class UndoButtonAction extends AbstractAction {
        /**
         * 
         */
        private static final long serialVersionUID = -5949634297433843092L;

        /**
         * Creates an undo button action.
         * 
         * @param text The string that gets displayed in the button
         */
        UndoButtonAction(String text) {
            super(text);
        }

        /**
         * Undoes a move the user made and update the playing field.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            nimLogic.undo();
            ((GameContainer) gameContainer)
                    .updatePlayingField(nimLogic.getBoardState());
            revalidate();
        }
    }

    /**
     * The quit button action controls how the quit button works. This simply
     * lets the user quit the program.
     */
    private class QuitButtonAction extends AbstractAction {

        /**
         * 
         */
        private static final long serialVersionUID = -845755400975079853L;

        /**
         * Creates a quit button action.
         * 
         * @param text The string that gets displayed in the button
         */
        QuitButtonAction(String text) {
            super(text);
        }

        /**
         * Disposes the current frame and thereby closes the program.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    /**
     * Updates the counter for how many sticks are currently selected. Every
     * time the user or the program makes a change to this amount, this method
     * has to be called to update the current state of the game.
     * 
     * @param selectedSticks The amount of currently selected sticks.
     */
    public void updateSelectedSticks(int selectedSticks) {
        this.selectedSticks = selectedSticks;
        buttonContainer.setSelectedSticks(selectedSticks);
    }

    /**
     * Removes sticks in the specified row. The amount depends on current state
     * of selectedSticks. If the game is over after this move, an according
     * winning message gets displayed.
     * 
     * @param row The row from which to remove the sticks.
     */
    public void removeSticks(int row) {
        nimLogic.removeSticks(row, selectedSticks);

        ((GameContainer) gameContainer)
                .updatePlayingField(nimLogic.getBoardState());
        if (nimLogic.isGameOver()) {
            showWinningDialog(nimLogic.getWinningMessage());
        }
        updateSelectedSticks(0);
        revalidate();
    }

    /**
     * Shows a specified message in a winning dialog
     * 
     * @param msg The message to be displayed
     */
    private void showWinningDialog(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    /**
     * Shows a dialog the prompts the user to enter a new inital state for a new
     * game. He can either enter a sequence of positive numbers separated by
     * spaces or enter random for a random board configuration.
     */
    private void showNewGameDialog() {
        String userInput = JOptionPane.showInputDialog(null,
                "Enter the sticks for each row seperated by a space"
                        + " or random for a random configuration",
                previousInput);
        if (userInput != null) {
            if (userInput.equalsIgnoreCase("random")) {
                previousInput = "random";
                createNewGame(getRandomRows());
            } else {
                int[] rows = parseUserInput(userInput);
                if (rows != null) {
                    previousInput = userInput;
                    createNewGame(rows);
                } else {
                    showInvalidInputDialog();
                    showNewGameDialog();
                }
            }
        }
    }

    /**
     * Signals the user that his input was invalid
     */
    private void showInvalidInputDialog() {
        JOptionPane.showMessageDialog(this, "Invalid input. Please try again");
    }

    /**
     * Gets a int array with random size but at most size 11 filled with random
     * integers.
     * 
     * @return Returns a random chosen array of positive integers.
     */
    private int[] getRandomRows() {
        Random random = new Random();
        int size = random.nextInt(9) + 2;
        int[] randomArr = new int[size];
        for (int i = 0; i < size; i++) {
            randomArr[i] = random.nextInt(9) + 1;
        }
        return randomArr;
    }

    /**
     * Creates a new game with the specified sticks per row. Takes the other
     * parameters for the new game from the button container.
     * 
     * @param rows An array of positive integers representing the rows.
     */
    private void createNewGame(int[] rows) {
        boolean isMisere = ((ButtonContainer) buttonContainer)
                .isMisereBoxChecked();
        boolean isHumanFirst = !((ButtonContainer) buttonContainer)
                .isFirstMoveBoxChecked();
        nimLogic = new NimLogic(rows, isMisere, isHumanFirst);
        if (!isFirstGame) {
            remove(gameContainer);
        }
        isFirstGame = false;
        gameContainer = new GameContainer(nimLogic.getBoardState());
        add(gameContainer, BorderLayout.CENTER);
        revalidate();
    }

    /**
     * Parses a string and tries to return a board configuration.
     * 
     * @param input A string from the user input
     * @return Returns a valid integer array or null if the input was invalid.
     */
    private int[] parseUserInput(String input) {
        String[] tokens = input.split("\\s+");
        if (tokens.length == 1) {
            return null;
        }
        int[] rows = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].matches("\\d+")) {
                int parsedValue = Integer.parseInt(tokens[i]);
                if (parsedValue < 0) {
                    return null;
                }
                rows[i] = parsedValue;
            } else {
                return null;
            }
        }
        return rows;
    }

    /**
     * Starts the program by creating a new NimGameWindow
     * 
     * @param args The default args that have no meaning
     */
    public static void main(String[] args) {
        new NimGameWindow();
    }
}
