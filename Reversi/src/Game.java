import java.util.ArrayList;

public class Game 
{
	private int level;
	private State board;

	/* Default Game constructor sets the depth level to 1 */
	public Game()
	{
		level = 1;
		board = new State();
	}

	/* The setLevel function sets the depth level */
	public void setLevel(int level) 
	{
		this.level = level;
	}

	/* The getLevel function returns the depth level */
	public int getLevel() 
	{
		return level;
	}

	/* The getBoard function returns the board */
	public State getBoard() 
	{
		return this.board;
	}

	/* The playerMove function realizes all the moves of the player */
	public State playerMove(State aState, int newRow, int newCol, char mypawn) 
	{
		State newState;
		boolean validMove = false;
		char[][] game;
		game = new char[8][8];
		boolean noMoreMoves;

		if (newRow > 7 || newRow < 0) return null;
		if (newCol > 7 || newCol < 0) return null;

		validMove = aState.gameMove(newRow,newCol,mypawn);
		if(!validMove){
			newState = null;
		} else {
			newState = new State(aState.getReversi());
		}
		game = aState.getReversi();
		if(aState.no_more_moves(game, mypawn)){
			noMoreMoves = true;
		} else {
			noMoreMoves = false;
		}
		this.board = newState;
		return newState;
	}

	/* The oppositeMove function makes the computer play implementing the minimax alpha-beta pruning algorithm */
	public State oppositeMove(State aState, char opposite) 
	{
		int score = -1;
		int count = 0;
		int scr;
		State tempState = new State(aState.getReversi());
		State state, state1, temp1;
		State bestState = null;
		State newState = null;		
		ArrayList<State> states;
		states = new ArrayList<State>();
		ArrayList<State> game;
		game = new ArrayList<State>();
		ArrayList<State> minimax;
		minimax = new ArrayList<State>();
		char[][] board = new char[8][8];
		char[][] board1 = new char[8][8];
		char playerPawn, comPawn;
		board = aState.getReversi();

		if(opposite=='O') {
			comPawn = 'O';
			playerPawn = 'X';
		} else {
			comPawn = 'X';
			playerPawn = 'X';
		}

		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				if(board[i][j]==' ') {
					if(tempState.gameMove(i,j,opposite)) {
						if (count <= this.getLevel()) {
							states.add(tempState);
							game.add(tempState);
						}
						count++;
					}
				}
				tempState = new State(aState.getReversi());
			}
			tempState = new State(aState.getReversi());
		}

		if(!states.isEmpty()) {
			for(int i = 0; i < states.size(); i++) {
				state = states.get(i);
				scr = state.computerScore(opposite);
				if(scr > score) {
					score = scr;
					bestState = state;
				}
				newState = bestState;
			}
		} else {
			newState = tempState;
		}

		if(!game.isEmpty()){
			for(int j = 0; j < game.size(); j++) {
				state1 = game.get(j);
				temp1 = new State(state1.getReversi());
				board1 = temp1.getReversi();
				for(int i1 = 0; i1 < board1.length; i1++) {
					for(int j1 = 0; j1 < board1.length; j1++) {
						if(board1[i1][j1]==' ') {
							if(temp1.gameMove(i1,j1,comPawn)); {
								minimax.add(state1);
							}
						}
					}
				}
			}
		}
		return newState;
	}
}