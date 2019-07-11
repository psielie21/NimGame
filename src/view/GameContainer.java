package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.JPanel;

public class GameContainer extends JPanel{
	JPanel rowNumbers;
	JPanel playingField;
	
	public GameContainer(int[] sticks) {
		super();
		
		setLayout(new BorderLayout());
		
		rowNumbers = new JPanel();
		rowNumbers.setLayout(new GridLayout(sticks.length, 1));
		rowNumbers.setBackground(Color.GREEN);
		
		playingField = new JPanel();
		int maxSticks = Arrays.stream(sticks).max().getAsInt();
		playingField.setLayout(new GridLayout(sticks.length, maxSticks));
		playingField.setBackground(Color.BLUE);
		
		for(int i = 0; i < sticks.length; i++) {
			for(int j = 0; j < maxSticks; j++) {
				playingField.add(new StickView(i, j, j < sticks[i]));
			}
		}
		
		add(rowNumbers, BorderLayout.WEST);
		add(playingField, BorderLayout.CENTER);
		this.setBackground(Color.RED);
	}
}
