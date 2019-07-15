package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class NimGameWindow extends JFrame{
	JPanel gameContainer;
	ButtonContainer buttonContainer;
	
	public NimGameWindow() {
		super();
		
		setLayout(new BorderLayout());
		
		gameContainer = new GameContainer(new int[] { 1, 2, 3, 5 });
		buttonContainer = new ButtonContainer();
		
		add(gameContainer, BorderLayout.CENTER);
		add(buttonContainer, BorderLayout.EAST);
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(900,600);
		setVisible(true);
	}
	
	public void updateSelectedSticks(int selectedSticks) {
		buttonContainer.setSelectedSticks(selectedSticks);
	}
	
	public static void main(String[] args) {
		JFrame f = new NimGameWindow();
		
	}
}
