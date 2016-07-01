package sk.balaz.Minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sk.balaz.Minesweeper.UserInterface;
import sk.balaz.Minesweeper.core.Clue;
import sk.balaz.Minesweeper.core.Field;
import sk.balaz.Minesweeper.core.GameState;
import sk.balaz.Minesweeper.core.Mine;
import sk.balaz.Minesweeper.core.Tile;
import sk.balaz.Minesweeper.core.translate.RowColumn;
import sk.balaz.Minesweeper.core.translate.RowColumnTranslator;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /** Playing field. */
    private Field field;
    
    /** Input reader. */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    
    
    private RowColumnTranslator rowColumnTranslator =  new RowColumnTranslator();
    
    
    /**
     * Reads line of text from the reader.
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     * Starts the game.
     * @param field field of mines and clues
     */
    public void newGameStarted(Field field) {
        this.field = field;
        do {
            update();
            processInput();
            
            if(isGameFinished()) {
            	finishGame();
            }
        } while(true);
    }
    
    /**
     * Updates user interface - prints the field.
     */
    public void update() {
    	
    	System.out.print(" ");
    	for(int i = 0; i < field.getRowCount(); i++) {
    		System.out.print(" ");
    		System.out.print(i);
    		System.out.print(" ");
    	}
    	System.out.println();
    	
    	for(int row = 0; row < field.getRowCount(); row++) {
    		System.out.print(rowColumnTranslator.getRowCharacter(row));
    		for(int column = 0; column < field.getColumnCount(); column++) {
    			
    			Tile tile = field.getTile(row, column);
    			
    			if(tile instanceof Mine && Tile.State.OPEN.equals(tile.getState())) {
    				System.out.print(" X ");
    				System.out.print(field.getTile(row, column));
    			}
    			else if(tile instanceof Clue && Tile.State.OPEN.equals(tile.getState())) {
    				Clue clue = (Clue)tile;
    				System.out.print(" ");
    				System.out.print(clue.getValue());
    				System.out.print(" ");
    			}
    			else if(Tile.State.MARKED.equals(tile.getState())) {
    				System.out.print(" X ");
    			}
    			else if(Tile.State.CLOSED.equals(tile.getState())) {
    				System.out.print(" - ");
    			}
    		}
    		System.out.println();
    	}
    }
    
    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
    	printUserInvitation();
    	try {
        	handleInput(readLine());
		} catch (WrongFormatException e) {
			System.err.println(e.getMessage());
		}
    }
    
    private void finishGame() {
    	System.out.println("Finishing Game");
        System.exit(0);
    }
    
    private boolean isGameFinished() {
    	return field.getState() == GameState.SOLVED || field.getState() == GameState.FAILED;
    }
    
    private void handleInput(String userEnteredInput) throws WrongFormatException {
    	
    	Pattern exitPattern = Pattern.compile("(X)");
    	Pattern markPattern = Pattern.compile("M([A-I])([0-8])");
    	Pattern openPattern = Pattern.compile("O([A-I])([0-8])");
    	
    	Matcher  exitMatcher = exitPattern.matcher(userEnteredInput);
    	Matcher  markMatcher = markPattern.matcher(userEnteredInput);
    	Matcher  openMatcher = openPattern.matcher(userEnteredInput);
    	
    	if(exitMatcher.matches()) {
    		field.setState(GameState.FAILED);
    	}
    	else if(openMatcher.matches()) {    		
    		RowColumn rowColumn = rowColumnTranslator.translate(userEnteredInput);
    		field.openTile(rowColumn.getRow(), rowColumn.getColumn());
    	}
    	else if(markMatcher.matches()) {    		
    		RowColumn rowColumn = rowColumnTranslator.translate(userEnteredInput);
    		field.markTile(rowColumn.getRow(), rowColumn.getColumn());
    	}
    	else {
			throw new WrongFormatException(String.format("%s is not valid input", userEnteredInput));
		}
    }
    
    private void printUserInvitation() {
    	System.out.println("Please enter your selection <X> EXIT, <MA1> MARK, <OB4> OPEN :");
    }
}
