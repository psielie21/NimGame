package view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ButtonContainer extends JPanel{
	JPanel northPanel;
	JButton newButton;
	JButton undoButton;
	JButton quitButton;
	JCheckBox misereCheckBox;
	JCheckBox firstMoveCheckBox;
	JLabel sticksSelectedCounter;
	
	public ButtonContainer(ActionListener newButtonListener) {
		super();
		
		setLayout(new BorderLayout());
		
		northPanel = new JPanel();
		newButton = new JButton("New");
		newButton.addActionListener(newButtonListener);
		undoButton = new JButton("Undo");
		quitButton = new JButton("Quit");
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
	
	public void setSelectedSticks(int sticksSelected) {
		sticksSelectedCounter.setText(sticksSelected + "");
	}
}
