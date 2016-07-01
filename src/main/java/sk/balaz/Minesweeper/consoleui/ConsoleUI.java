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

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /** Playing field. */
    private Field field;
    
    /** Input reader. */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    
    private String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    
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
            
            if(field.getState() == GameState.SOLVED || field.getState() == GameState.FAILED) {
            	System.out.println("Finishing Game");
                System.exit(0);
            }
            
            //throw new UnsupportedOperationException("Resolve the game state - winning or loosing condition.");
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
    	
    	for(int i = 0; i < field.getRowCount(); i++) {
    		System.out.print(getNextAlphabet(i));
    		for(int j = 0; j < field.getColumnCount(); j++) {
    			
    			Tile tile = field.getTile(i, j);
    			
    			if(tile instanceof Mine && Tile.State.OPEN.equals(tile.getState())) {
    				System.out.print(" X ");
    				System.out.print(field.getTile(i, j));
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
    	
    	Pattern exitPattern = Pattern.compile("(X)");
    	Pattern markPattern = Pattern.compile("M([A-I])([0-8])");
    	Pattern openPattern = Pattern.compile("O([A-I])([0-8])");
    	
    	String userEnteredInput;
    	
    	System.out.println("Please enter your selection <X> EXIT, <MA1> MARK, <OB4> OPEN :");
    	userEnteredInput = readLine();
    	
    	Matcher  exitMatcher = exitPattern.matcher(userEnteredInput);
    	Matcher  openMatcher = openPattern.matcher(userEnteredInput);
    	
    	if(exitMatcher.matches()) {
    		field.setState(GameState.FAILED);
    	}
    	else if(openMatcher.matches()) {
    		
    		char[] fieldAccessIndexes = getFieldAccessIndexes(userEnteredInput);
    		int rowIndex = translateRowCharToNumber(fieldAccessIndexes[0]);
    		int columnIndex = Character.getNumericValue(fieldAccessIndexes[1]);
    				
    		field.openTile(rowIndex, columnIndex);	
    	}
    }
    
    private char[] getFieldAccessIndexes(String userEnteredInput) {
    	char[] fieldAccessIndexes = new char[2];
    	fieldAccessIndexes[0] = userEnteredInput.charAt(1);
    	fieldAccessIndexes[1] = userEnteredInput.charAt(2);

    	return fieldAccessIndexes;
    }
    
    private int translateRowCharToNumber(char rowChar) {
    	
    	switch (rowChar) {
		case 'A':
			return 0;
			
		case 'B':
			return 1;
			
		case 'C':
			return 2;
			
		case 'D':
			return 3;
			
		case 'E':
			return 4;
			
		case 'F':
			return 5;
			
		case 'G':
			return 6;
			
		case 'H':
			return 7;
			
		case 'I':
			return 8;
			
		default:
			return 1000;
		}
    }
    
    private String getNextAlphabet(int index) {
    	return alphabet[index];
    }
}
