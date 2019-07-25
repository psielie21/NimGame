package view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.Action;
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
		
		setLayout(new BorderLayout());
		
		northPanel = new JPanel();
		newButton = new JButton(newButtonAction);
		undoButton = new JButton(undoButtonAction);
		quitButton = new JButton(quitButtonAction);
		misereCheckBox = new JCheckBox("Misere");
		firstMoveCheckBox = new JCheckBox("Machine Opens");
		sticksSelectedCounter = new JLabel("0");
		
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
		northPanel.add(newButton);
		northPanel.add(undoButton);
		northPanel.add(quitButton);
		northPanel.add(misereCheckBox);
		northPanel.add(firstMoveCheckBox);
		
		add(northPanel, BorderLayout.NORTH);
		add(sticksSelectedCounter, BorderLayout.SOUTH);
	}
	
	public boolean isMisereBoxChecked() {
		return misereCheckBox.isSelected();
	}
	
	public boolean isFirstMoveBoxChecked() {
		return firstMoveCheckBox.isSelected();
	}
	
	public void setSelectedSticks(int sticksSelected) {
		sticksSelectedCounter.setText(sticksSelected + "");
	}
}
