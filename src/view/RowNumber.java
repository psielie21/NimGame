package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class RowNumber extends JPanel{
	private int rowNumber;
	/**
	 * 
	 * @param n
	 */
	public RowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
		Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
		
		setBackground(Color.LIGHT_GRAY);
		setLayout(new BorderLayout());
	
		add(new JLabel(rowNumber + ":"), BorderLayout.CENTER);
		setBorder(blackLine);
	}
}
