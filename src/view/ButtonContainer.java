package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The ButtonContainer object hosts a series of buttons that
 * are crucial for the Nim game. These buttons let you create a 
 * new game, undo a move, quit the game or make changes to the 
 * settings of the next game. It also shows how many sticks are
 * selected at every time.
 */
public class ButtonContainer extends JPanel{
	JPanel northPanel;
	JButton newButton;
	JButton undoButton;
	JButton quitButton;
	JCheckBox misereCheckBox;
	JCheckBox firstMoveCheckBox;
	JLabel sticksSelectedCounter;
	private final String MISERE_BUTTON_TEXT = "Misere";
	private final String FIRST_MOVE_BUTTON_TEXT = "Machine Opens";

	
	/**
	 * Creates a new ButtonContainer object whose buttons get
	 * associated with a listener each.
	 * 
	 * @param newButtonListener The listener for the new button.
	 * @param undoButtonListener The listener for the undo button.
	 * @param quitButtonListener The listener for the quit button.
	 */
	public ButtonContainer(Action newButtonAction, 
						   Action undoButtonAction, 
						   Action quitButtonAction) {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		northPanel = new JPanel();
		newButton = new JButton(newButtonAction);
		newButton.setMnemonic(KeyEvent.VK_N);
		undoButton = new JButton(undoButtonAction);
		undoButton.setMnemonic(KeyEvent.VK_U);
		quitButton = new JButton(quitButtonAction);
		quitButton.setMnemonic(KeyEvent.VK_Q);
		misereCheckBox = new JCheckBox(MISERE_BUTTON_TEXT);
		misereCheckBox.setMnemonic(KeyEvent.VK_M);
		firstMoveCheckBox = new JCheckBox(FIRST_MOVE_BUTTON_TEXT);
		firstMoveCheckBox.setMnemonic(KeyEvent.VK_S);
		sticksSelectedCounter = new JLabel("0");
		
		add(newButton);
		add(undoButton);
		add(quitButton);
		add(misereCheckBox);
		add(firstMoveCheckBox);
		
		add(Box.createVerticalGlue());
		add(sticksSelectedCounter);
	}
	
	/**
cd 	 * 
	 * @return Returns the boolean value of the misere check box.
	 */
	public boolean isMisereBoxChecked() {
		return misereCheckBox.isSelected();
	}
	
	/**
	 * Checks whether the first move checkbox is checked.
	 * 
	 * @return Returns the boolean value of the first move checkbox.
	 */
	public boolean isFirstMoveBoxChecked() {
		return firstMoveCheckBox.isSelected();
	}
	
	/**
	 * Sets the currently selected amount of sticks.
	 * 
	 * @param sticksSelected The currently amount of selected sticks.
	 */
	public void setSelectedSticks(int sticksSelected) {
		sticksSelectedCounter.setText(sticksSelected + "");
	}
}
