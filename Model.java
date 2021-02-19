/**
 * This file is to be completed by you.
 *
 * s2063615 <Please enter your matriculation number, not your name>
 */
public final class Model
{
	// ===========================================================================
	// ================================ CONSTANTS ================================
	// ===========================================================================
	// The most common version of Connect Four has 7 rows and 6 columns.
	public static final int DEFAULT_NR_ROWS = 6;
	public static final int DEFAULT_NR_COLS = 7;
	public static final int WIN_LEN = 4;
	//Step 3 from the worksheet says they should be the other way around so I have changed them

	
	// ========================================================================
	// ================================ FIELDS ================================
	// ========================================================================
	// The size of the board.
	private int nrRows;
	private int nrCols;
	private int winLen;
	private int[][] board;
	private boolean turn; //player1 is true, player2 is false

	
	// =============================================================================
	// ================================ CONSTRUCTOR ================================
	// =============================================================================
	public Model()
	{
		// Initialise the board size to its default values.

		setGridSize(DEFAULT_NR_ROWS, DEFAULT_NR_COLS);
		setWinningLength(WIN_LEN);

		//player one goes first at the the start of every game
		turn = true;

		//initialising the empty board the empty board
		for (int i = 0; i < nrRows; i++) {
			for (int j = 0; j < nrCols; j++) {
				board[i][j] = 0;
			}
		}
	}
	
	// ====================================================================================
	// ================================ MODEL INTERACTIONS ================================
	// ====================================================================================

	public boolean isMoveValid(int move)
	{
		boolean allowed = false;

		//checks that the column is within the dimensions and that that column is not full.
		if (move < nrCols && move >= 0 && board[nrRows -1][move] == 0) {
			allowed = true;
		}
		return allowed;
	}
	
	public void makeMove(int move) {
		// going from the bottom row up

		for (int r = 0; r < nrRows; r++) {
			if (board[r][move] == 0) { // finds the lowest empty space
				if (turn) {
					board[r][move] = 1; // applies move based on player
				} else {
					board[r][move] = 2;
				}
				break;
			}
		}

		if (!checkGameOver()) {
			turn = !turn;
		}
	}

	public boolean checkMove(int col) {
		//used by NPC to check if a winning move is available

		for (int r = 0; r < nrRows; r++) { // checking rows from bottom up
			if (board[r][col] == 0) { // if we find an empty space
				board[r][col] = 2; // play a move
				if (winner()) { //if this shot results in a win
					board[r][col] = 0; // undo the move
					return true;
				} else {
					board[r][col] = 0;
					return false;
				}
			}
		}
		return false;
	}

	//also used by the NPC to check if the opponent has a winning move
	public boolean checkOpponentsMove(int col) {

		turn = !turn;

		for (int r = 0; r < nrRows; r++) { // checking rows from bottom up
			if (board[r][col] == 0) { // if we find an empty space
				board[r][col] = 1; // play a move as the opponent
				if (winner()) { // if this results in a win
					turn = !turn;
					board[r][col] = 0; // undo the move
					return true;
				} else {
					turn = !turn;
					board[r][col] = 0;
					return false;
				}
			}
		}
		turn = !turn;
		return false;
	}



	public boolean checkGameOver() {
		return (checkBoardFull() || winner());
	}

	//used to check if there is a draw by checking if the counter matches the spaces available
	public boolean checkBoardFull() {

		int count = 0;
		for (int r =0; r < nrRows; r++) {
			for (int c =0; c < nrCols; c++) {
				if (board[r][c] != 0) {
					count++;
				}
			}
		}

		return count >= nrCols * nrRows;
	}


	//resets the board to the empty board when called upon i.e for a new game
	public void reset() {
		turn = true;

		for (int r = 0; r < nrRows; r++) {
			for (int c = 0; c < nrCols; c++) {
				board[r][c] = 0;
			}
		}
	}


	//win detection method
	public boolean winner() {

		int player;
		if (getTurn()) {
			player = 1;
		} else {
			player = 2;
		}

		//Horizontal Check
		int hCounter;
		for (int r = 0; r < nrRows; r++) { //looping through rows then the cols as we want to check horizontal
			hCounter = 0;
			for (int c = 0; c < nrCols; c++) {
				if (board[r][c] == player) {
					hCounter++;
					if (hCounter >= winLen) {
						System.out.println();
						return true;
					}
				} else hCounter = 0;
			}
		}


		//Vertical Checker
		int vCounter;
		for (int c = 0; c < nrCols; c++) { //looping through cols then rows as we want to check vertical
			vCounter = 0;
			for (int r = 0; r < nrRows; r++) {
				if (board[r][c] == player) {
					vCounter++;
					if (vCounter >= winLen) {
						System.out.println();
						return true;
					}
				} else vCounter = 0;
			}
		}




		//Diagonal Checker


		/* My diagonal checkers use while loops because if we find a match we will continue
		going in that direction until either we find X in a row or we don't find a match.
		When a match is not found we break and we check the next direction.
		 */

		int dCounter = 0;

		//descending diagonal
		for (int r = 0; r < nrRows; r++) {
			for (int c = 0; c <nrCols; c++) {

				if (board[r][c] == player) {
					int dr = r; //using these variables to represent the change in rows / columns
					int dc = c; // as using the 'r' and 'c' would mess up the loop values

					dCounter = 1; //start cell counts as one

					//checks bottom right - my row 0 is geometrically on the bottom
					while (inBounds(dr - 1, dc + 1)) {//in bounds checks that the space we are checking is valid
						if (board[dr - 1][dc + 1] == player) {
							dCounter++;
							if (dCounter >= winLen) {
								System.out.println();
								return true;
							}
							dr--; //keeps checking the next bottom right until it is either out of bounds
							dc++; //or the cell is not equal to that player.
						} else {
							break;
						}
					}

					dr = r; //resetting the change values
					dc = c;

					//checks top left - my row 0 is geometrically on the bottom
					 while (inBounds(dr + 1, dc - 1)) {
						if (board[dr + 1][dc - 1] == player) {
							dCounter++;
							if (dCounter >= winLen) {
								System.out.println();
								return true;
							}
							dr++;
							dc--;
						} else {
							break;
						}
					}

					 //ascending diagonal - this follows the same design as the descending diagonal
					dr = r;
					dc = c;

					dCounter = 1;

					//checks top right
					while (inBounds(dr + 1, dc + 1)) {
						if (board[dr + 1][dc + 1] == player) {
							dCounter++;
							if (dCounter >= winLen) {
								System.out.println();
								return true;
							}
							dr++;
							dc++;
						} else {
							break;
						}
					}

					dr = r;
					dc = c;

					//checks bottom left
					while (inBounds(dr - 1, dc - 1)) {
						if (board[dr - 1][dc - 1] == player) {
							dCounter++;
							if (dCounter >= winLen) {
								System.out.println();
								return true;
							}
							dr--;
							dc--;
						} else {
							break;
						}
					}

				}

				}
			}
		return dCounter >= winLen;
	}


	private boolean inBounds(int x, int y) {
		return x > 0 && x < nrRows && y > 0 && y < nrCols;
	}



	// =========================================================================
	// ================================ GETTERS ================================
	// =========================================================================


	public int getNrRows()
	{
		return nrRows;
	}

	public int getNrCols()
	{
		return nrCols;
	}

	public int[][] getBoard() {
		return board;
	}

	public boolean getTurn() {
		return turn;
	}



	// =========================================================================
	// ================================ SETTERS ================================
	// =========================================================================

	//sets the grid size based on what the user inputs
	public void setGridSize(int newRows, int newCols) {
		board = new int[newRows][newCols];
		nrRows = newRows;
		nrCols = newCols;
	}

	//sets the win length *connect x* based on what the user inputs
	public void setWinningLength(int newLength) {
		winLen = newLength;
	}


}
