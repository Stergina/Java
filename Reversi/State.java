public class State 
{
	private int dimension;
	private char[][] reversi;

	/* Default State constructor to depict the initial state of the board */
	public State() 
	{
		this.dimension = 8;
		this.reversi = new char[this.dimension][this.dimension];
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				if ((i == 3 & j == 3) || (i == 4 & j == 4)) {
					this.reversi[i][j] = 'X';
				} else if ((i == 4 & j == 3) || (i == 3 & j == 4)) {
					this.reversi[i][j] = 'O';
				} else {
					this.reversi[i][j] = ' ';
				}
			}
		}
	}

	/* State constructor with a character table parameter */
	public State(char[][] reversi) 
	{
		this.dimension = reversi.length;
		this.reversi = new char[this.dimension][this.dimension];
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				this.reversi[i][j] = reversi[i][j];
			}
		}
	}

	/* The printBoard function prints the board in each state of the game
	 * by using horizontal and vertical coordinations */
	public void printBoard() 
	{
		System.out.print("   ");
		for (char i = 'a'; i <= 'h'; i++) {
			System.out.print(i + "  ");
			System.out.print("  ");
		}

		/* Grid construction */
		System.out.println();
		System.out.print("   ");
		for (int j = 0; j < this.dimension; j++) {
			System.out.print("----");
		}
		System.out.print("----");
		for (int row = 0; row < this.dimension; row++) {
			System.out.println();
			System.out.print(row + 1 + "  ");

			for (int col = 0; col < this.dimension; col++) {
				System.out.print(reversi[row][col]);
				if (row < this.dimension) System.out.print(" |");
				System.out.print("  ");
			}
			System.out.print(row + 1 + "  ");
			System.out.println();
			System.out.print("   ");
			System.out.print("----");
			for (int k = 0; k < this.dimension; k++) {
				System.out.print("----");
			}
		}
		System.out.println();
		System.out.print("   ");
		for (char i = 'a'; i <= 'h'; i++) {
			System.out.print(i + "  ");
			System.out.print("  ");
		}
	}

	/* The checkRight function checks if moving your disc right is a valid move */
	public boolean checkRight(int row, int col, char mypawn) 
	{
		char opponent = ' ';
		if (mypawn == 'X') {
			opponent = 'O';
		} else {
			opponent = 'X';
		}
		if (col == this.reversi.length - 1) {
			return false;
		}
		int state = 1;
		int state2 = 1;

		if(this.reversi[row][col]!=' ') 
			return false;

		boolean validMove = false;
		char chr = this.reversi[row][col + 1];
		if (chr == mypawn || chr == ' ') {
			validMove = false;
		} else {
			for (int i = col + 1; i < this.reversi.length; i++) {
				chr = this.reversi[row][i];
				if (chr == opponent) {
					state = 2;
				}
				if(chr==' '){
					state2 = 0;
				}
				if (chr == mypawn && state == 2 && state2==1) {
					state = 3;
					break;
				}
			}
		}
		if (state == 3) validMove = true;
		return validMove;
	}

	/* The checkLeft function checks if moving your disc left is a valid move */
	public boolean checkLeft(int row, int col, char mypawn) 
	{
		int state = 1;
		int state2 = 1;
		char opponent = ' ';
		if (mypawn == 'X') {
			opponent = 'O';
		} else {
			opponent = 'X';
		}

		if (col == 0) 
			return false;

		boolean validMove = false;
		char chr = this.reversi[row][col - 1];
		if (chr == mypawn || chr == ' ') {
			validMove = false;
		} else {
			for (int i = col - 1; i >=0; i--) {
				chr = this.reversi[row][i];
				if (chr == opponent) {
					state = 2;
				}
				if(chr==' '){
					state2 = 0;
				}
				if (chr == mypawn && state == 2 && state2==1) {
					state = 3;
					break;
				}
			}
		}
		if (state == 3) validMove = true;
		return validMove;
	}

	/* The checkDown function checks if moving your disc down is a valid move */
	public boolean checkDown(int row, int col, char mypawn) 
	{
		char opponent = ' ';
		int temprow = row;
		if (mypawn == 'X') {
			opponent = 'O';
		} else {
			opponent = 'X';
		}

		if (row == this.reversi.length) 
			return false;

		int state = 1;
		int state2 = 1;
		boolean validMove = false;
		if (temprow < this.dimension - 1) {
			temprow++;
		}
		char chr = this.reversi[temprow][col];
		if (chr == mypawn || chr == ' ') {
			validMove = false;
		} else {
			for (int i = row + 1; i < this.reversi.length; i++) {
				chr = this.reversi[i][col];
				if (chr == opponent) {
					state = 2;
				}
				if(chr==' '){
					state2 = 0;
				}
				if (chr == mypawn && state == 2 && state2==1) {
					state = 3;
					break;
				}
			}
		}
		if (state == 3) validMove = true;
		return validMove;
	}

	/* The checkUp function checks if moving your disc up is a valid move */
	public boolean checkUp(int row, int col, char mypawn) 
	{
		char opponent = ' ';
		int state2 = 1;
		if (mypawn == 'X') {
			opponent = 'O';
		} else {
			opponent = 'X';
		}

		if (row == 0) 
			return false;

		int state = 1;
		boolean validMove = false;
		char chr = this.reversi[row - 1][col];
		if (chr == mypawn || chr == ' ') {
			validMove = false;
		} else {
			for (int i = row - 1; i >= 0; i--) {
				chr = this.reversi[i][col];
				if (chr == opponent) {
					state = 2;
				}
				if(chr==' '){
					state2 = 0;
				}
				if (chr == mypawn && state == 2 && state2==1) {
					state = 3;
					break;
				}

			}
		}
		if (state == 3) validMove = true;
		return validMove;
	}

	/* The checkDiagon1Up function checks if the disc is eligible to go up, left and diagonal */
	public boolean checkDiagon1Up(int row, int col, char mypawn) 
	{
		char opponent = ' ';
		int state2 = 1;
		if (mypawn == 'X') {
			opponent = 'O';
		} else {
			opponent = 'X';
		}

		if (col == 0 || row == 0) 
			return false;

		int state = 1;
		boolean validMove = false;
		char chr = this.reversi[row - 1][col - 1];
		if (chr == mypawn || chr == ' ') {
			validMove = false;
		} else {
			for (int j = col - 1; j >= 0; j--) {
				if (row > 0)
					row--;
				chr = this.reversi[row][j];
				if (chr == opponent) {
					state = 2;
				}
				if(chr==' '){
					state2 = 0;
				}
				if (chr == mypawn && state == 2 && state2==1) {
					state = 3;
					break;
				}
			}
		}
		if (state == 3) validMove = true;
		return validMove;
	}

	/* The checkDiagon1Down function checks if the disc is eligible to go down, right and diagonal */
	public boolean checkDiagon1Down(int row, int col, char mypawn) 
	{
		char opponent = ' ';
		int state2 = 1;
		if (mypawn == 'X') {
			opponent = 'O';
		} else {
			opponent = 'X';
		}

		if (col == this.reversi.length - 1 || row == this.reversi.length - 1) 
			return false;

		int state = 1;
		boolean validMove = false;
		char chr = this.reversi[row + 1][col + 1];
		if (chr == mypawn || chr == ' ') {
			validMove = false;
		} else {
			for (int j = col + 1; j < this.reversi.length; j++) {
				if (row < this.reversi.length - 1)
					row++;
				chr = this.reversi[row][j];
				if (chr == opponent) {
					state = 2;
				}
				if(chr==' '){
					state2 = 0;
				}
				if (chr == mypawn && state == 2 && state2==1) {
					state = 3;
					break;
				}
			}
		}
		if (state == 3) validMove = true;
		return validMove;
	}

	/* The checkDiagon2Up function checks if the disc is eligible to go up, right and diagonal */
	public boolean checkDiagon2Up(int row, int col, char mypawn) 
	{
		char opponent = ' ';
		int temprow = row;
		int tempcol = col;
		int state2 = 1;
		if (mypawn == 'X') {
			opponent = 'O';
		} else {
			opponent = 'X';
		}

		if (col == this.reversi.length || row == 0) 
			return false;

		int state = 1;
		boolean validMove = false;
		if (temprow > 0)
			temprow = row - 1;
		if (tempcol < this.reversi.length - 1)
			tempcol = col + 1;
		char chr = this.reversi[temprow][tempcol];
		if (chr == mypawn || chr == ' ') {
			validMove = false;
		} else {
			for (int j = col + 1; j < this.reversi.length; j++) {
				if (row > 0)
					row--;
				chr = this.reversi[row][j];
				if (chr == opponent) {
					state = 2;
				}
				if(chr==' '){
					state2 = 0;
				}
				if (chr == mypawn && state == 2 && state2==1) {
					state = 3;
					break;
				}
			}
		}
		if (state == 3) validMove = true;
		return validMove;
	}

	/* The checkDiagon2Down function checks if the disc is eligible to go down, left and diagonal */
	public boolean checkDiagon2Down(int row, int col, char mypawn) 
	{
		char opponent = ' ';
		int state2 = 1;
		if (mypawn == 'X') {
			opponent = 'O';
		} else {
			opponent = 'X';
		}
		if (row == this.reversi.length - 1 || col == 0) {
			return false;
		}
		int state = 1;
		boolean validMove = false;
		char chr1 = this.reversi[row][col];

		if (chr1 == opponent) 
			return false;

		char chr = this.reversi[row + 1][col - 1];
		if (chr == mypawn || chr == ' ') {
			validMove = false;
		} else {
			for (int j = col - 1; j > 0; j--) {
				if (row < this.dimension - 1) {
					row++;
				}
				chr = this.reversi[row][j];
				if (chr == opponent) {
					state = 2;
				}
				if(chr==' '){
					state2 = 0;
				}
				if (chr == mypawn && state == 2 && state2==1) {
					state = 3;
					break;
				}
			}
		}
		if (state == 3) validMove = true;
		return validMove;
	}

	/* The getReversi function returns a new board */
	public char[][] getReversi() 
	{
		char[][] newBoard = new char[this.dimension][this.dimension];
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				newBoard[i][j] = this.reversi[i][j];
			}
		}
		return newBoard;
	}

	/* The playerScore function calculates the player's score */
	public int playerScore(char mypawn) 
	{
		int count = 0;
		for (int i = 0; i < reversi.length; i++) {
			for (int j = 0; j < reversi.length; j++) {
				if (reversi[i][j] == mypawn) count++;
			}
		}
		return count;
	}

	/* The computerScore function calculates the computer's score */
	public int computerScore(char compawn) 
	{
		int count = 0;
		for (int i = 0; i < reversi.length; i++) {
			for (int j = 0; j < reversi.length; j++) {
				if (reversi[i][j] == compawn) count++;
			}
		}
		return count;
	}

	/* The moveRight function performs the right move if it is eligible */
	public boolean moveRight(int newRow, int newCol, char mypawn) 
	{
		int tempRow = newRow;
		int tempCol = newCol;
		boolean rightMove = true;
		if (!checkRight(newRow, newCol, mypawn)) {
			rightMove = false;
		} else {
			this.reversi[tempRow][tempCol] = mypawn;
			tempCol++;
			for (int j = tempCol; j < this.reversi.length; j++) {
				if (this.reversi[tempRow][j] != mypawn) {
					this.reversi[tempRow][j] = mypawn;
				} else {
					break;
				}
			}
		}
		return rightMove;
	}

	/* The moveLeft function performs the left move if it is eligible */
	public boolean moveLeft(int newRow, int newCol, char mypawn) 
	{
		int tempRow = newRow;
		int tempCol = newCol;
		boolean leftMove = true;
		if (!checkLeft(newRow, newCol, mypawn)) {
			leftMove = false;
		} else {
			this.reversi[tempRow][tempCol] = mypawn;
			tempCol--;
			for (int j = tempCol; j > 0; j--) {
				if (this.reversi[tempRow][j] != mypawn) {
					this.reversi[tempRow][j] = mypawn;
				} else {
					break;
				}
			}
		}
		return leftMove;
	}

	/* The moveDown function performs the down move if it is eligible */
	public boolean moveDown(int newRow, int newCol, char mypawn) 
	{
		int tempRow = newRow;
		int tempCol = newCol;
		boolean downMove = true;
		if (!checkDown(newRow, newCol, mypawn)) {
			downMove = false;
		} else {
			this.reversi[tempRow][tempCol] = mypawn;
			tempRow++;
			for (int i = tempRow; i < this.reversi.length; i++) {
				if (this.reversi[i][tempCol] != mypawn) {
					this.reversi[i][tempCol] = mypawn;
				} else {
					break;
				}
			}
		}
		return downMove;
	}

	/* The moveUp function performs the up move if it is eligible */
	public boolean moveUp(int newRow, int newCol, char mypawn) 
	{
		int tempRow = newRow;
		int tempCol = newCol;
		boolean upMove = true;
		if (!checkUp(newRow, newCol, mypawn)) {
			upMove = false;
		} else {
			this.reversi[tempRow][tempCol] = mypawn;
			if (tempRow > 0)
				tempRow--;
			for (int i = tempRow; i >= 0; i--) {
				if (this.reversi[i][tempCol] != mypawn) {
					this.reversi[i][tempCol] = mypawn;
				} else {
					break;
				}
			}
		}
		return upMove;
	}

	/* The diagon1Up function performs the up, left and diagonal move if it is eligible */
	public boolean diagon1Up(int newRow, int newCol, char mypawn) 
	{
		int temprow = newRow;
		int tempcol = newCol;
		boolean diagon1up = true;
		if (!checkDiagon1Up(newRow, newCol, mypawn)) {
			diagon1up = false;
		} else {
			this.reversi[temprow][tempcol] = mypawn;
			tempcol--;
			temprow--;
			for (int j = tempcol; j >= 0; j--) {
				if (this.reversi[temprow][j] != mypawn) {
					this.reversi[temprow][j] = mypawn;

				} else {
					break;
				}
				if (temprow > 0)
					temprow--;
			}

		}
		return diagon1up;
	}

	/* The diagon1Down function performs the down, right and diagonal move if it is eligible */
	public boolean diagon1Down(int newRow, int newCol, char mypawn) 
	{
		int tempRow = newRow;
		int tempCol = newCol;
		boolean diagon1down = true;
		if (!checkDiagon1Down(newRow, newCol, mypawn)) {
			diagon1down = false;
		} else {
			this.reversi[tempRow][tempCol] = mypawn;
			tempCol++;
			tempRow++;
			for (int j = tempCol; j < this.reversi.length-1; j++) {
				if (this.reversi[tempRow][j] != mypawn) {
					this.reversi[tempRow][j] = mypawn;

				} else {
					break;
				}
				if (tempRow < this.getReversi().length-1)
					tempRow++;
			}
		}
		return diagon1down;
	}

	/* The checkDiagon2Up function performs the up, right and diagonal move if it is eligible */
	public boolean diagon2Up(int newRow, int newCol, char mypawn) 
	{
		int temprow = newRow;
		int tempcol = newCol;
		boolean diagon2up = true;
		if (!checkDiagon2Up(newRow, newCol, mypawn)) {
			diagon2up = false;
		} else {
			this.reversi[temprow][tempcol] = mypawn;
			tempcol++;
			temprow--;
			for (int j = tempcol; j < this.reversi.length; j++) {
				if (this.reversi[temprow][j] != mypawn) {
					this.reversi[temprow][j] = mypawn;
				} else {
					break;
				}
				if (temprow > 0)
					temprow--;
			}
		}
		return diagon2up;
	}

	/* The checkDiagon2Down function performs the down, left and diagonal move if it is eligible */
	public boolean diagon2Down(int newRow, int newCol, char mypawn) 
	{
		int temprow = newRow;
		int tempcol = newCol;
		boolean diagon2down = true;
		if (!checkDiagon2Down(newRow, newCol, mypawn)) {
			diagon2down = false;
		} else {
			this.reversi[temprow][tempcol] = mypawn;
			tempcol--;
			temprow++;
			for (int j = tempcol; j >= 0; j--) {
				if (this.reversi[temprow][j] != mypawn) {
					this.reversi[temprow][j] = mypawn;
				} else {
					break;
				}
				if (temprow < this.reversi.length-1)
					temprow++;
			}
		}
		return diagon2down;
	}

	/* The gameMove function executes all the moves */
	public boolean gameMove(int newRow, int newCol, char mypawn) 
	{
		boolean valideMove = false;
		boolean ml, mr, mu, md, d1u, d1d, d2u, d2d;
		int tempRow, tempCol;
		tempRow = newRow;
		tempCol = newCol;
		ml = moveLeft(tempRow, tempCol, mypawn);
		tempRow = newRow;
		tempCol = newCol;
		mr = moveRight(tempRow, tempCol, mypawn);
		tempRow = newRow;
		tempCol = newCol;
		mu = moveUp(tempRow, tempCol, mypawn);
		tempRow = newRow;
		tempCol = newCol;
		md = moveDown(tempRow, tempCol, mypawn);
		tempRow = newRow;
		tempCol = newCol;
		d1u = diagon1Up(tempRow, tempCol, mypawn);
		tempRow = newRow;
		tempCol = newCol;
		d1d = diagon1Down(tempRow, tempCol, mypawn);
		tempRow = newRow;
		tempCol = newCol;
		d2u = diagon2Up(tempRow, tempCol, mypawn);
		tempRow = newRow;
		tempCol = newCol;
		d2d = diagon2Down(tempRow, tempCol, mypawn);
		if (mr || ml || mu || md || d1u || d1d || d2d || d2u) {
			valideMove = true;
		}
		return valideMove;
	}

	/* The setReversi function sets a new board */
	public void setReversi(char[][] board) 
	{
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				this.reversi[i][j] = board[i][j];
			}
		}
	}

	/* The no_more_moves function checks if there are any available moves left */
	public boolean no_more_moves(char[][] game, char mypawn) 
	{
		boolean r, l, u, d, d1u, d1d, d2u, d2d;
		boolean state_no_move = true;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (game[i][j] == ' ') {
					r = checkRight(i, j, mypawn);
					l = checkLeft(i, j, mypawn);
					u = checkUp(i, j ,mypawn);
					d = checkDown(i, j ,mypawn);
					d1u = checkDiagon1Up(i, j , mypawn);
					d1d = checkDiagon1Down(i, j , mypawn);
					d2u = checkDiagon2Up(i, j, mypawn);
					d2d = checkDiagon2Down(i, j ,mypawn);
					if(r || l || u || d || d1u || d1d || d2u || d2d){
						state_no_move = false;
					}
				}
			}
		}
		return state_no_move;
	}
}