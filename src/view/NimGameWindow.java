package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import model.NimLogic;

public class NimGameWindow extends JFrame{
	private JPanel gameContainer;
	private ButtonContainer buttonContainer;
	private NimLogic nimLogic;
	private int selectedSticks;
	private String previousInput;
	
	public NimGameWindow() {
		super();
		
		setLayout(new BorderLayout());
		this.setTitle("Nim Game");
		selectedSticks = 0;
		
		previousInput = "";
		nimLogic = new NimLogic(new int[] { 1, 2, 3, 5 }, false, true);
		
		gameContainer = new GameContainer(nimLogic.getBoardState());
		setupButtonContainer();
		
		add(gameContainer, BorderLayout.CENTER);
		add(buttonContainer, BorderLayout.EAST);
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(900,600);
		setVisible(true);
		
	}
	
	private ButtonContainer setupButtonContainer() {
		Action newButtonAction = new NewButtonAction("New");
		Action undoButtonAction = new UndoButtonAction("Undo");
		Action quitButtonAction = new QuitButtonAction("Quit");
		buttonContainer = new ButtonContainer(newButtonAction, 
											  undoButtonAction,
											  quitButtonAction);
		
		buttonContainer.getInputMap().put(KeyStroke.getKeyStroke('c'), "createNewGame");
		buttonContainer.getInputMap().put(KeyStroke.getKeyStroke('u'), "undoMove");
		buttonContainer.getInputMap().put(KeyStroke.getKeyStroke('q'), "quitGame");
		buttonContainer.getActionMap().put("createNewGame", newButtonAction);
		buttonContainer.getActionMap().put("undoMove", undoButtonAction);
		buttonContainer.getActionMap().put("quitGame", quitButtonAction);
		return buttonContainer;
	}
	
	private class NewButtonAction extends AbstractAction {
		public NewButtonAction(String text) {
			super(text);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			showNewGameDialog();
		}
	}
	
	private class UndoButtonAction extends AbstractAction {
		public UndoButtonAction(String text) {
			super(text);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			nimLogic.undo();
			((GameContainer) gameContainer).updatePlayingField(nimLogic.getBoardState());
			revalidate();
		}
	}
	
	private class QuitButtonAction extends AbstractAction {
		public QuitButtonAction(String text) {
			super(text);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	
	public void updateSelectedSticks(int selectedSticks) {
		this.selectedSticks = selectedSticks;
		buttonContainer.setSelectedSticks(selectedSticks);
	}
	
	public void removeSticks(int row) {
		System.out.println("row: " + row + " sticks: " + selectedSticks);
		nimLogic.removeSticks(row, selectedSticks);
		
		((GameContainer) gameContainer).updatePlayingField(nimLogic.getBoardState());
		if(nimLogic.isGameOver()) {
			showWinningDialog(nimLogic.getWinningMessage());
		}
		updateSelectedSticks(0);
		revalidate();
	}
	
	private void showWinningDialog(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	
	private void showNewGameDialog() {
		String userInput = JOptionPane.showInputDialog
				(null, "Enter the sticks for each row seperated by a space"
					 + " or random for a random configuration", previousInput);
		if(userInput != null) {
			if(userInput.equalsIgnoreCase("random")) {
				previousInput = "random";
				createNewGame(getRandomRows());
			} else {
				int[] rows = parseUserInput(userInput);
				if(rows != null) {
					previousInput = userInput;
					createNewGame(rows);
				} else {
					showInvalidInputDialog();
					showNewGameDialog();
				}
			}
		}
	}
	
	private void showInvalidInputDialog() {
		JOptionPane.showMessageDialog(this, "Invalid input. Please try again");
	}
	
	private int[] getRandomRows() {
		Random random = new Random();
		int size = random.nextInt(9) + 2;
		int[] randomArr = new int[size];
		for(int i = 0; i < size; i++) {
			randomArr[i] = random.nextInt(9) + 1;
		}
		return randomArr;
	}
	
	private void createNewGame(int[] rows) {
		boolean isMisere = ((ButtonContainer) buttonContainer).isMisereBoxChecked();
		boolean isHumanFirst = !((ButtonContainer) buttonContainer).isFirstMoveBoxChecked();
		nimLogic = new NimLogic(rows, isMisere, isHumanFirst);
		remove(gameContainer);
		gameContainer = new GameContainer(nimLogic.getBoardState());
		add(gameContainer);
		revalidate();
	}
	
	public int[] parseUserInput(String input) {
		String[] tokens = input.split("\\s+");
		if(tokens.length == 1) {
			return null;
		}
		int[] rows = new int[tokens.length];
		for(int i = 0; i < tokens.length; i++) {
			if(tokens[i].matches("\\d+")) {
				int parsedValue = Integer.parseInt(tokens[i]);
				if(parsedValue < 0) {
					return null;
				}
				rows[i] = parsedValue;
			} else {
				return null;
			}
		}
		return rows;
	}
	
	public static void main(String[] args) {
		JFrame f = new NimGameWindow();
		
	}
}
