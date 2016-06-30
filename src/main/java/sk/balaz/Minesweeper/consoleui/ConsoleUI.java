package sk.balaz.Minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import sk.balaz.Minesweeper.UserInterface;
import sk.balaz.Minesweeper.core.Clue;
import sk.balaz.Minesweeper.core.Field;
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
//        do {
//            update();
//            processInput();
//            //throw new UnsupportedOperationException("Resolve the game state - winning or loosing condition.");
//        } while(true);
    	
    	update();
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
    				System.out.print(clue.getValue());
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
    	System.out.println("Zadaj vstup");
    }
    
    private String getNextAlphabet(int index) {
    	return alphabet[index];
    }
}
