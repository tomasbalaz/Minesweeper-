package sk.balaz.Minesweeper.core;

import java.util.Random;

/**
 * Field represents playing field and game logic.
 */
public class Field {
	/** Playing field tiles. */
	private final Tile[][] tiles;

	/** Field row count. Rows are indexed from 0 to (rowCount - 1). */
	private final int rowCount;

	/** Column count. Columns are indexed from 0 to (columnCount - 1). */
	private final int columnCount;

	/** Mine count. */
	private final int mineCount;

	/** Game state. */
	private GameState state = GameState.PLAYING;

	/**
	 * Constructor.
	 * 
	 * @param rowCount
	 *            row count
	 * @param columnCount
	 *            column count
	 * @param mineCount
	 *            mine count
	 */
	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];
		generate();
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	/**
	 * Opens tile at specified indeces.
	 * 
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void openTile(int row, int column) {
		final Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.OPEN);
			if (tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			}

			if (isSolved()) {
				state = GameState.SOLVED;
			}
		}
	}

	/**
	 * Marks tile at specified indeces.
	 * 
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void markTile(int row, int column) {
		final Tile tile = tiles[row][column];
		if (Tile.State.CLOSED.equals(tile)) {
			tile.setState(Tile.State.MARKED);
		} else if (Tile.State.MARKED.equals(tile)) {
			tile.setState(Tile.State.CLOSED);
		}
	}

	/**
	 * Generates playing field.
	 */
	private void generate() {
		generateMines();
		fillWithClues();
	}

	/**
	 * Returns true if game is solved, false otherwise.
	 * 
	 * @return true if game is solved, false otherwise
	 */
	private boolean isSolved() {
		throw new UnsupportedOperationException("Method isSolved not yet implemented");
	}

	/**
	 * Returns number of adjacent mines for a tile at specified position in the
	 * field.
	 * 
	 * @param row
	 *            row number.
	 * @param column
	 *            column number.
	 * @return number of adjacent mines.
	 */
	private int countAdjacentMines(int row, int column) {
		int count = 0;

		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Mine) {
							count++;
						}
					}
				}
			}
		}

		return count;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}
	
	private void generateMines() {
		Random randomX = new Random();
		Random randomY = new Random();
		
		for(int mines = 0; mines < mineCount; mines++) {
			int x = randomX.nextInt(rowCount);
			int y = randomY.nextInt(columnCount);
			if(tiles[x][y] == null) {
				tiles[x][y] = new Mine();
			}
		}
	}
	
	private void fillWithClues() {
		for(int row = 0; row < rowCount; row++) {
			for(int column = 0; column < columnCount; column++) {
				if(tiles[row][column] == null) {
					tiles[row][column] = new Clue(countAdjacentMines(row, column));
				}
			}
		}
	}
	
}
