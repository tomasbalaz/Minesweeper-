package sk.balaz.Minesweeper.core.translate;

public class RowColumnTranslator {
	
	private final int INVALID_ROW_INDEX = 1000;

	/** Alphabet which will be printed on user input */
	private String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
								  "R", "S", "T", "U", "V", "X", "Y", "X", };

	
    public String getRowCharacter(int rowNumber) {
    	return alphabet[rowNumber];
    }
    
    public int rowCharToNumber(char rowChar) {
    	
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
			return INVALID_ROW_INDEX;
		}
    }
    
    public int columnCharToNumber(char columnChar) {
    	
    	return Character.getNumericValue(columnChar);
    }
    
    public RowColumn translate(String userEnteredInput) {
    	char row = userEnteredInput.charAt(1);
    	char column = userEnteredInput.charAt(2);
    	
    	RowColumn rowColumn = new RowColumn();
    	rowColumn.setRow(rowCharToNumber(row));
    	rowColumn.setColumn(columnCharToNumber(column));

    	return rowColumn;
    }
}
