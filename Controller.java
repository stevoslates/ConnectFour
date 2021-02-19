/**
 * This file is to be completed by you.
 *
 * @author <s2063615>
 */
public final class Controller
{
	public boolean mode;
	private final Model model;
	private final TextView view;
	private final Npc npc;


	
	public Controller(Model model, TextView view)
	{
		this.model = model;
		this.view = view;
		/*initialising the new Npc class, as it is only used in controller
		and passing through model so the npc can access the board and methods
		in order to make and check a move etc
		 */
		this.npc = new Npc(model);


	}
	
	public void startSession() {

		//set up the game session
		setup();

		mode = gameMode();


		view.displayBoard(model);
		view.displayNewGameMessage();
		System.out.println("Enter -1 to exit at any time");

		//decides what mode to be played depending on the user input
		if (mode) {
			player();
		} else playerVsComputer();

	}

	//this is where the general game is set up by user input
	public void setup () {


		System.out.print("Enter the desired amount of Rows: ");
		int nrRows = askForSize();

		//validating user input
		while (nrRows <= 0 || nrRows > 10 ) {
			//I wanted to keep the dimensions smaller so you can see it more clearly on the terminal
			//If a bigger game was needed you would just have to take out '|| nrRows > 10' on the validation.
			System.out.print("Please enter a number between 1 and 10: ");
			nrRows = askForSize();
		}

		System.out.print("Enter the desired amount of Columns: ");
		int nrCols = askForSize();


		while (nrCols <= 0 || nrCols > 10) {
			//I wanted to keep the dimensions smaller so you can see it more clearly on the terminal
			//If a bigger game was needed you would just have to take out '|| nrCols > 10' on the validation.
			System.out.print("Please enter a number between 1 and 10: ");
			nrCols = askForSize();
		}

		//setting the gird size
		model.setGridSize(nrRows, nrCols);

		System.out.print("Enter the desired winning length : ");
		int winLength = askForSize();

		//making sure the winLength is equal to or less than the minimum out of the cols and rows
		//so the game can be won.
		while (winLength > nrRows || winLength > nrCols || winLength < 1) {
			System.out.print("Please enter a number between 1 and " + Math.min(nrCols,nrRows) + " (inclusive) : ");
			winLength = askForSize();
		}
		model.setWinningLength(winLength);

	}

	public final int askForSize() {
		return InputUtil.readIntFromUser();
	}

	public boolean gameMode () {
		System.out.print("Would you like to play another player or the computer (P/C): ");

		while (true) {
			char mode = InputUtil.readCharFromUser();
			if (mode  == 'P') { // Player vs Player
				return true;
			} else if (mode == 'C') { //Player vs Computer
				return false;
			} else {
				System.out.print("Please input either P or C: ");
			}
		}
	}

	public void player() {

		String player;
		int move;

		while (!model.checkGameOver()) {

			if (model.getTurn()) {
				player = "1"; //player 1 is true
			} else player = "2"; //player 2/ npc is false

			System.out.println("Player " + player + "'s turn");
			move = askForMove();

			//how the player exits the game
			if (move == -1) {
				System.out.println("You have ended the game");
				if (option()) {
					newGame();
				} else {
					gameExit();
				}
			}

			//checks move is valid and then plays it
			if (model.isMoveValid(move)) {
				model.makeMove(move);
				view.displayBoard(model);
			} else {
				System.out.println("Please enter a valid column!");
			}


			//if playing against the computer then go back to that method so the next turn can take place
			if (!mode) {
				playerVsComputer();
			}
		}
		gameEnding(mode);
	}

	public final int askForMove()
	{
		System.out.print("Select a free column: ");
		return InputUtil.readIntFromUser();
	}

	public void playerVsComputer() {

		//this while loop alternates the turn of the player and the computer until the game is over
		while (!model.checkGameOver()) {
			if (model.getTurn()) { //if true its the players turn
				player();
			} else { //if false its the computers turn.
				makeMoveNpc();
			}
		}
		gameEnding(mode);

	}

	public void makeMoveNpc() {

		//gets the move from the NPC class
		int npcMove = npc.getMove(model.getNrCols());

		model.makeMove(npcMove);

		System.out.println("Computer's turn...");

		sleep(1000);

		view.displayBoard(model);

		if (model.checkGameOver()) {
			gameEnding(mode);
		}

	}


	/*
	  Here I have implemented a sleep class to make the computer seem more life like
	* as when I was testing the computer was returning its move to quickly that the
	* game felt too fast and jumpy, so I slowed it down to make the game flow more.
	*/

	public void sleep (int time) {
		try
		{
			Thread.sleep(time); //time is ms
		}
		catch(InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
	}

	public void gameEnding(boolean mode) {


		if (model.checkBoardFull() && !model.winner()) {
			System.out.println("Cats Game!");
		} else if (!mode && !model.getTurn()) {
			System.out.println("Computer wins!");
		} else if (!model.getTurn()) {
			System.out.println("Player 2 wins!");
		} else {
			System.out.println("Player 1 wins!");
		}

		if (option()) {
			newGame();
		} else {
			gameExit();
		}

	}

	public boolean option () {

		System.out.print("Do you want to play again? (Y/N): ");

		while (true) {
			char decision = InputUtil.readCharFromUser();
			if (decision == 'Y') {
				return true;
			} else if (decision == 'N') {
				return false;
			} else {
				System.out.print("Please input either Y or N: ");
			}
		}
	}

	public void newGame () {
		//resets the board to empty board and restarts the session
		model.reset();
		startSession();
	}

	public void gameExit() {
		System.out.println("You have successfully exited the game!");
		System.exit(0); //exits the session with code 0 to show that this was intended.
	}
















}



