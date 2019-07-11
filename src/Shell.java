import java.util.Arrays;
import java.util.Scanner;

import model.Board;
import model.Misere;
import model.Nim;
import model.Player;

/**
 * The shell provides the user interface to the whole nim program.
 * You can interact with the shell on the standard command line
 * via a range of commands that can be seen via the 'help' command
 * Its purpose is to provide a bridge between the user and the game of
 * nim.
 */
public final class Shell {
    private static Board game;
    private static Player first = Player.HUMAN;
    private static boolean isRunning = true;
    private static boolean isActiveGame = true;
    private final static String HUMAN_WIN_MSG =
                "Congratulations! You won.";
    private final static String COMPUTER_WIN_MSG =
                "Sorry! Machine wins.";
    private final static String NEW_CMD_HELP_MSG =
                "NEW <s_1> <s_2> ... <s_n> \n"
                + "Creates a new game with n rows and s_i matches in row i.\n"
                + "If this is the first game the human starts moving.\n";
    private final static String MISERE_CMD_HELP_MSG =
                "MISERE <s_1> <s_2> ... <s_n>\n"
                + "Creates analogous to the NEW command a new game but\n"
                + "in Misere mode.\n";
    private final static String REMOVE_CMD_HELP_MSG =
                "REMOVE <r> <s> \n"
                + "Carries out a human move.\n"
                + "The parameter r corresponds to the r-th row\n"
                + "and s corresponds to the amount of sticks to be removed\n"
                + "Only use this if it is your turn!.\n";
    private final static String SWITCH_CMD_HELP_MSG =
                "SWITCH \n"
                + "Changes the player who starts the next game.\n"
                + "You can call this command during an active game\n"
                + "which is not affected by this command.\n";
    private final static String PRINT_CMD_HELP_MSG =
                "PRINT \n"
                + "Prints the current representation of the board\n";
    private final static String VERBOSE_CMD_HELP_MSG =
                "VERBOSE <o>\n"
                + "Changes how the print function outputs the board state.\n"
                + "When o = ON, the bit representation of each row and\n"
                + "the nim sum of the board is included in the output.\n"
                + "When o = OFF, the print function works in the regular way\n";
    private final static String HELP_CMD_HELP_MSG =
                "HELP \n"
                + "Prints out this help menu\n";
    private final static String QUIT_CMD_HELP_MSG =
                "QUIT \n"
                + "Quits the program.";
    private final static String[] HELP_MESSAGES =
            {NEW_CMD_HELP_MSG, MISERE_CMD_HELP_MSG, REMOVE_CMD_HELP_MSG,
            SWITCH_CMD_HELP_MSG, PRINT_CMD_HELP_MSG, VERBOSE_CMD_HELP_MSG,
            HELP_CMD_HELP_MSG, QUIT_CMD_HELP_MSG };


    private Shell() { }

    /**
     * The entry point of the whole program.
     * It starts the interaction with the user on the command line
     *
     * @param args the default args for the main method
     */
    public static void main(String[] args) {
        System.out.println("The human makes the"
                + " initial move of the next new game.");
        runShellLoop();
    }

    /**
     * Runs the main loop of the program.
     * This loop listens to input from the user until it is
     * closed with the 'quit' command. Although it is sufficient
     * to only test the first letter of the command, the extra effort
     * of checking the first letter or the whole word is taken because
     * it seems counterintuitive to access the help menu with the command
     * 'hafhslkfuahsl'.
     */
    private static void runShellLoop() {
        Scanner sc = new Scanner(System.in);
        while (isRunning) {
            System.out.print("nim> ");
            String[] userInput = sc.nextLine().split(" ");
            String cmd = userInput[0].toLowerCase();
            String[] args = Arrays.copyOfRange(userInput, 1, userInput.length);

            switch (cmd) {
                case "new": //fall through
                case "n": createNewNimGame(args);
                         break;
                case "misere": //fall through
                case "m": createNewMisereGame(args);
                    break;
                case "remove": //fall through
                case "r": handleHumanMove(args);
                    break;
                case "switch": //fall through
                case "s": switchStarter(args);
                    break;
                case "print": //fall through
                case "p": printBoard(args);
                    break;
                case "verbose": //fall through
                case "v": setVerbose(args);
                    break;
                case "help": //fall through
                case "h": printHelp(args);
                    break;
                case "quit": //fall through
                case "q": quitProgram(args);
                    break;
                default: error("no such command!");
                    
            }
        }
    }

    /**
     * Creates a regular new nim game with the specified
     * board state and automatically
     * executes a machine move if the machine moves first
     *
     * Needs n parameter
     * where args[i] is the number of sticks
     * in the i-th row
     *
     * @param args the arguments of the user command
     */
    private static void createNewNimGame(String[] args) {
        if (args.length == 0) {
            error("the new command needs at least one argument");
            return;
        }
        //get all the rows and create a new nim game
        int[] rows = parseRows(args);
        if (rows != null) {
            game = new Nim(Arrays.copyOf(rows, rows.length), first);
            isActiveGame = true;
            if (first == Player.COMPUTER) {
                executeMachineMove();
            }
        }
    }

    /**
     * Creates a new game in misere mode with the specified
     * board state and automatically
     * executes a machine move if the machine moves first
     *
     * Needs n parameter
     * where args[i] is the number of sticks
     * in the i-th row
     *
     * @param args the arguments of the user command
     */
    private static void createNewMisereGame(String[] args) {
        if (args.length == 0) {
            error("the new command needs at least one argument");
            return;
        }
        int[] rows = parseRows(args);
        if (rows != null) {
            game = new Misere(Arrays.copyOf(rows, rows.length), first);
            isActiveGame = true;
            if (first == Player.COMPUTER) {
                executeMachineMove();
            }
        }
    }

    /**
     * Handles the move of the user.
     * Checks if the user entered a valid move at all,
     * then proceeds to see whether the user has won
     * with that move and finally, if the user hast not won,
     * executes immediately a machine as response to the user move
     *
     * Needs 2 parameter:
     * args[0] is the row where the player wants to make his move
     * args[1] is the amount of sticks he wants to remove
     *
     * @param args the arguments of the user command
     */
    private static void handleHumanMove(String[] args) {
        if (correctArgsLength(args, 2)
          && isValidRow(args[0])
          && isValidNumber(args[1])
          && isActiveGame) {
            int row = Integer.parseInt(args[0]);
            int sticks = Integer.parseInt(args[1]);
            if (hasEnoughSticks(row, sticks)) {
                game.remove(row - 1, sticks);
                if (!isAVictory()) {
                    executeMachineMove();
                }
            }
        }
    }

    /**
     * Switches the starting player for the next game.
     *
     * Needs 0 parameter.
     *
     * @param args the arguments of the user command
     */
    private static void switchStarter(String[] args) {
        if (correctArgsLength(args, 0)) {
            if (first == Player.COMPUTER) {
                first = Player.HUMAN;
                System.out.println("The human makes the initial"
                        + " move of the next new game.");
            } else {
                first = Player.COMPUTER;
                System.out.println("The machine makes the initial"
                        + " move of the next new game.");
            }
        }
    }

    /**
     * Prints the current state of the board to the console.
     * Depending on whether the verbose flag was set earlier,
     * the output contains more information, in detail it also
     * outputs the amount of sticks per row in binary representation.
     *
     * Needs 0 parameter.
     *
     * @param args the arguments of the user command
     */
    private static void printBoard(String[] args) {
        if (correctArgsLength(args, 0)
                && game != null) {
            System.out.println(game);
        }
    }

    /**
     * Sets the verbose flag of the program to the user input.
     * If the verbose flag is set to true the user gets more
     * information when he uses the print command, as described
     * in the {@link #printBoard(String[])} method.
     *
     * Needs 1 parameter
     * args[0] String which is either ON or OFF
     *
     * @param args the arguments of the user command
     *
     */
    private static void setVerbose(String[] args) {
        if (correctArgsLength(args, 1)) {
            String arg = args[0];
            if (arg.equalsIgnoreCase("ON")) {
                Nim.setVerbose(true);
            } else if (arg.equalsIgnoreCase("OFF")) {
                Nim.setVerbose(false);
            } else {
                error(arg + " is not a valid argument!");
            }
        }
    }

    /**
     * Prints a help menu to the screen.
     * This lists all available commands including
     * a description of the correct use and functions
     * of each command
     *
     * Needs 0 parameter
     *
     * @param args the arguments of the user command
     */
    private static void printHelp(String[] args) {
        if (correctArgsLength(args, 0)) {
            for (String helpMsg : HELP_MESSAGES) {
                System.out.println(helpMsg);
            }
        }
    }

    /**
     * Quits the program.
     *
     * Needs 0 parameter.
     *
     * @param args the arguments of the user command
     */
    private static void quitProgram(String[] args) {
        if (correctArgsLength(args, 0)) {
            isRunning = false;
        }
    }

    /**
     * Parses an array to an array of rows for the board.
     * Makes sure the user entered String array is a valid
     * integer array where the i-th entry is a valid number of sticks
     *
     * @param args the user entered arguments
     * @return an int array of the user specified sticks per row
     */
    private static int[] parseRows(String[] args) {
        int[] rows = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            if (isValidNumber(args[i])) {
                rows[i] = Integer.parseInt(args[i]);
            } else {
                return null;
            }
        }
        return rows;
    }

    /**
     * Makes sure the user entered the right number of arguments
     *
     * @param args the user entered arguments
     * @param supposedLength the supposed count of arguments
     * @return if the length of the arguments is the supposed length
     */
    private static boolean correctArgsLength(String[] args,
                                             int supposedLength) {
        if (args.length == supposedLength) {
            return true;
        } else {
            error("You gave " + args.length + " arguments, while "
                    + supposedLength + " arguments were required");
            return false;
        }
    }

    //make sure the user entered a valid number
    //greater than or equal to zero
    private static boolean isValidNumber(String n) {
        try {
            int parsed = Integer.parseInt(n);
            if (parsed >= 1) {
                return true;
            } else {
                error("not a valid number: " + n);
                return false;
            }
        } catch (NumberFormatException e) {
            error("not a number: " + n);
            return false;
        }
    }

    //make sure the user entered a valid number
    //that corresponds to a row on the nim board
    private static boolean isValidRow(String n) {
        try {
            int parsed = Integer.parseInt(n);
            if (parsed >= 1 && parsed <= game.getRowCount()) {
                return true;
            } else {
                error("not a valid row: " + n);
                return false;
            }
        } catch (NumberFormatException e) {
            error("not a number: " + n);
            return false;
        }
    }

    /**
     * Checks if the move is valid by assuming the existence
     * of the row was tested already and only the correctness
     * of the amounts of sticks has to be checked
     *
     * @param row
     * @param sticks
     * @return
     */
    private static boolean hasEnoughSticks(int row, int sticks) {
        if (game.getSticks(row - 1) >= sticks) {
            return true;
        } else {
            error("row " + row + " does not"
                    + " contain at least " + sticks + " sticks");
            return false;
        }
    }

    //helper method that handles all aspects of
    //a move made by the machine
    //including the actual removal of the sticks
    //as well as the handling of the potential victory of the machine
    private static void executeMachineMove() {
        game.machineRemove();
        System.out.println(game.getLastMove());
        isAVictory();
    }

    //Tests if the game is over and if so
    //prints out the according victory message
    private static boolean isAVictory() {
        if (game.isGameOver()) {
            isActiveGame = false;
            Player winner = game.getWinner();
            if (winner == Player.COMPUTER) {
                System.out.println(COMPUTER_WIN_MSG);
            } else {
                System.out.println(HUMAN_WIN_MSG);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Prints an error message to the command line
     *
     * @param err the error message
     */
    private static void error(String err) {
        System.out.println("Error! " + err);
    }
}
