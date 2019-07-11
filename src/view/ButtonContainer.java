package view;

import java.awt.BorderLayout;

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
	JLabel sticksSelected;
	
	public ButtonContainer() {
		super();
		
		setLayout(new BorderLayout());
		
		northPanel = new JPanel();
		newButton = new JButton("New");
		undoButton = new JButton("Undo");
		quitButton = new JButton("Quit");
		misereCheckBox = new JCheckBox("Misere");
		firstMoveCheckBox = new JCheckBox("Machine Opens");
		sticksSelected = new JLabel("0");
		
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
		northPanel.add(newButton);
		northPanel.add(undoButton);
		northPanel.add(quitButton);
		northPanel.add(misereCheckBox);
		northPanel.add(firstMoveCheckBox);
		
		add(northPanel, BorderLayout.NORTH);
		add(sticksSelected, BorderLayout.SOUTH);
		
		
	}
}
