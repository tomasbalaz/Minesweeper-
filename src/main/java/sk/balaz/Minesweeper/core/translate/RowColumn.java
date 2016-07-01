package sk.balaz.Minesweeper.core.translate;

public class RowColumn {
	
	private int row;
	
	private int column;
	
	public RowColumn() {
		
	}
	
	public RowColumn(char row, char column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
}
