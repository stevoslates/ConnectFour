/**
 * This file is used to display the board when called upon.
 *
 * s2063615
 */
public final class TextView
{
	public TextView()
	{
	
	}

	//displays at the start of every new game.
	public final void displayNewGameMessage()
	{
		System.out.println("---- NEW GAME STARTED ----");
	}
	

	
	public final void displayBoard(Model model)
	{
		//passing through the useful board dimensions and the board array itself.
		int nrRows = model.getNrRows();
		int nrCols = model.getNrCols();
		int[][] board = model.getBoard();

		//separates the rows by a line of dashes
		String rowDivider = "-".repeat(4 * nrCols + 1);
		
		// A StringBuilder is used to assemble longer Strings more efficiently.
		StringBuilder sb = new StringBuilder();

		sb.append(rowDivider);

		// Loop through rows backwards, so graphically the bottom row is row 0 and top is nrRows - 1
		for (int r = nrRows - 1; r >= 0; r--) { //
			sb.append("\n");
			sb.append("| ");
			for (int c = 0; c < nrCols; c++) {
				//if the cell on the board has a zero value e.g not been played in yet we display it as a blank space.
				if (board[r][c] == 0) sb.append(" ");
				//if the cell has been played by player one then we show a 1 on the board.
				else if (board[r][c] == 1) sb.append("1");
				//if the cell has been played by player two (the computer) then we show a 1 on the board.
				else sb.append("2");
				sb.append(" | ");
			}
			sb.append("\n");
			sb.append(rowDivider);

		}
		//this displays the row numbers at the bottom for the users reference
		sb.append("\n  ");
		for (int c = 0; c < nrCols; c++) {
			sb.append(c);
			sb.append("   ");
		}
		System.out.println(sb);
	}
}
