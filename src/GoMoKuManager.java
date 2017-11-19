
public class GoMoKuManager {
	private final int ROW = 15;
	private final int COL = 15;
	
	private int[][] board;
	private final int EMPTY = 0, WHITE = 1, BLACK = 2;
	
	private boolean gameInProgress;
	
	public boolean isGameInProgress() {
		return gameInProgress;
	}

	public void setGameInProgress(boolean gameInProgress) {
		this.gameInProgress = gameInProgress;
	}

	public int getROW() {
		return ROW;
	}

	public int getCOL() {
		return COL;
	}

	public int getBoardState(int row, int col) {
		return board[row][col];
	}
	
	public void setBoardState(int row, int col, int state){
		board[row][col] = state;
	}

	public int getEMPTY() {
		return EMPTY;
	}

	public int getWHITE() {
		return WHITE;
	}

	public int getBLACK() {
		return BLACK;
	}

	int win_r1, win_c1, win_r2, win_c2;
	
	int currentPlayer;
	
	public GoMoKuManager(){
		board = new int[15][15];
	}
	
	public int getWin_r1() {
		return win_r1;
	}

	public void setWin_r1(int win_r1) {
		this.win_r1 = win_r1;
	}

	public int getWin_c1() {
		return win_c1;
	}

	public void setWin_c1(int win_c1) {
		this.win_c1 = win_c1;
	}

	public int getWin_r2() {
		return win_r2;
	}

	public void setWin_r2(int win_r2) {
		this.win_r2 = win_r2;
	}

	public int getWin_c2() {
		return win_c2;
	}

	public void setWin_c2(int win_c2) {
		this.win_c2 = win_c2;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void initBoard(){
		for (int row = 0; row < ROW; row++)
			for (int col = 0; col < COL; col++)
				board[row][col] = EMPTY;
	}
	
	public void setCurrentPlayer(int currentPlayer){
		this.currentPlayer = currentPlayer;
	}
	
	public boolean winner(int row, int col) {

		if (count(board[row][col], row, col, 1, 0) == 5)
			return true;
		if (count(board[row][col], row, col, 0, 1) == 5)
			return true;
		if (count(board[row][col], row, col, 1, -1) == 5)
			return true;
		if (count(board[row][col], row, col, 1, 1) == 5)
			return true;

		win_r1 = -1;
		return false;
	}
	
	public boolean isThreeThree(int row, int col) {
		int count = 0;
		if (isThreeInALine(row, col, 1, 0, currentPlayer)) {
			count++;
		}
		if (isThreeInALine(row, col, 0, 1, currentPlayer)) {
			count++;
		}
		if (isThreeInALine(row, col, 1, -1, currentPlayer)) {
			count++;
		}
		if (isThreeInALine(row, col, 1, 1, currentPlayer)) {
			count++;
		}

		if (count >= 2)
			return true;
		else
			return false;
	}

	
	
	public boolean isFourFour(int row, int col) {
		int count = 0;

		count += isFourInALine(row, col, 1, 0, currentPlayer);
		count += isFourInALine(row, col, 0, 1, currentPlayer);
		count += isFourInALine(row, col, 1, -1, currentPlayer);
		count += isFourInALine(row, col, 1, 1, currentPlayer);

		if (count >= 2)
			return true;
		else
			return false;
	}

	public int isFourInALine(int row, int col, int dirX, int dirY, int player) {
		int r, c;
		int ct = 1;
		int serialPlaceWithoutBlocked = 1;
		int blockedSide = 0;
		int previousPlaceState = player;
		
		int index=1;
		
		int isXOO = 0;
		int isOXOO = 0;
		int isXO = 0;
		int isOOXO = 0;
		int isOXO = 0;
		

		r = row + dirX;
		c = col + dirY;

		while (r >= 0 && r < ROW && c >= 0 && c < COL) {
			if (board[r][c] == player) {
				if (previousPlaceState == EMPTY) {
					if(index == 2){
						isXOO++;
						isXO++;
					}
					else if(index == 3){
						isOXOO++;
						isOXO++;
					}
					else if(index == 4){
						isOOXO++;
					}
				}
				else if(previousPlaceState == player){
					if(index == 3){
						isXOO++;
					}
					else if(index == 1){
						isOXOO++;
						isOOXO++;
						isOXO++;
					}
					else if(index == 2){
						isOOXO++;
					}
					else if(index == 4){
						isOXOO++;
					}
				}
				ct++;
				serialPlaceWithoutBlocked++;
				previousPlaceState = player;
			} else if (board[r][c] == EMPTY) {
				if (previousPlaceState == EMPTY) {
					break;
				}
				else if(previousPlaceState == player){
					if(index == 1){
						isXOO++;
						isXO++;
					}
					else if(index == 2){
						isOXO++;
						isOXOO++;
					}
					else if(index == 3){
						isOOXO++;
					}
						
				}
				serialPlaceWithoutBlocked++;
				previousPlaceState = EMPTY;
			} else {
				if (previousPlaceState == player){
					blockedSide++;
					break;
				}
			}
			r += dirX;
			c += dirY;
			index++;
		}

		r = row - dirX;
		c = col - dirY;
		previousPlaceState = player;
		index = 1;

		while (r >= 0 && r < ROW && c >= 0 && c < COL) {
			if (board[r][c] == player) {
				if (previousPlaceState == EMPTY) {
					if(index == 2){
						isXOO++;
						isXO++;
					}
					else if(index == 3){
						isOXOO++;
						isOXO++;
					}
					else if(index == 4){
						isOOXO++;
					}
				}
				else if(previousPlaceState == player){
					if(index == 3){
						isXOO++;
					}
					else if(index == 1){
						isOXOO++;
						isOOXO++;
						isOXO++;
					}
					else if(index == 2){
						isOOXO++;
					}
					else if(index == 4){
						isOXOO++;
					}
				}
				ct++;
				serialPlaceWithoutBlocked++;
				previousPlaceState = player;
				
			} else if (board[r][c] == EMPTY) {
				if (previousPlaceState == EMPTY) {
					break;
				}
				else if(previousPlaceState == player){
					if(index == 1){
						isXOO++;
						isXO++;
					}
					else if(index == 2){
						isOXO++;
						isOXOO++;
					}
					else if(index == 3){
						isOOXO++;
					}
						
				}
				serialPlaceWithoutBlocked++;
				previousPlaceState = EMPTY;
			} else {
				if (previousPlaceState == player){
					blockedSide++;
					break;
				}
			}

			r -= dirX;
			c -= dirY;
			index++;
		}
		
		System.out.println("XO : " +isXO);
		System.out.println("OOXO : " +isOOXO);
		System.out.println("ct : " +ct);
		
		int result = 0;
		
		if (ct == 4 && blockedSide < 2 && serialPlaceWithoutBlocked>=5)
			result += 1;
		if(ct == 6 &&isXOO == 3 && isOXOO == 4)
			result += 2;
		if(ct == 5 && isXO == 2 && isOOXO >= 4)
			result += 2;
		if(ct == 5 && isOXO == 6)
			result += 2;
		
		return result;
	}
	
	
	public boolean isThreeInALine(int row, int col, int dirX, int dirY, int player) {
		int r, c;
		int ct = 1;
		int gap = 0;
		int blockedWithGap = 0;
		int previousPlaceState = 3;

		r = row + dirX;
		c = col + dirY;

		while (r >= 0 && r < ROW && c >= 0 && c < COL && gap <= 1) {
			if (board[r][c] == player) {
				if (previousPlaceState == EMPTY) {
					gap++;
				}
				ct++;
				previousPlaceState = player;
			} else if (board[r][c] == EMPTY) {
				if (previousPlaceState == EMPTY) {
					break;
				}
				if (dirX != 0 && dirY != 0) {
					if ((r == 0 || c == 0 || r == ROW - 1 || c == COL - 1) && gap == 0) {
						blockedWithGap++;
					}
				} else if (dirX == 0) {
					if ((r == 0 || r == ROW - 1) && gap == 0) {
						blockedWithGap++;
					}
				} else if (dirY == 0) {
					if ((c == 0 || c == COL - 1) && gap == 0) {
						blockedWithGap++;
					}
				}

				previousPlaceState = EMPTY;
			}

			else {
				if (gap == 0 && previousPlaceState == EMPTY) {
					blockedWithGap++;
					if (player == 1)
						previousPlaceState = 2;
					else if (player == 2)
						previousPlaceState = 1;
				} else if (previousPlaceState == EMPTY) {
					if (player == 1)
						previousPlaceState = 2;
					else if (player == 2)
						previousPlaceState = 1;
				} else
					return false;
			}
			r += dirX;
			c += dirY;
		}

		r = row - dirX;
		c = col - dirY;
		previousPlaceState = 3;

		while (r >= 0 && r < ROW && c >= 0 && c < COL && gap <= 1) {
			if (board[r][c] == player) {
				if (previousPlaceState == EMPTY) {
					gap++;
				}
				ct++;
				previousPlaceState = player;
			} else if (board[r][c] == EMPTY) {
				if (previousPlaceState == EMPTY) {
					break;
				}
				if (dirX != 0 && dirY != 0) {
					if ((r == 0 || c == 0 || r == ROW - 1 || c == COL - 1) && gap == 0) {
						blockedWithGap++;
					}
				} else if (dirX == 0) {
					if ((r == 0 || r == ROW - 1) && gap == 0) {
						blockedWithGap++;
					}
				} else if (dirY == 0) {
					if ((c == 0 || c == COL - 1) && gap == 0) {
						blockedWithGap++;
					}
				}

				previousPlaceState = EMPTY;
			} else {
				if (gap == 0 && previousPlaceState == EMPTY) {
					blockedWithGap++;
					if (player == 1)
						previousPlaceState = 2;
					else if (player == 2)
						previousPlaceState = 1;
				} else if (previousPlaceState == EMPTY) {
					if (player == 1)
						previousPlaceState = 2;
					else if (player == 2)
						previousPlaceState = 1;
				} else
					return false;
			}

			r -= dirX;
			c -= dirY;
		}

		if (ct == 3 && gap <= 1 && blockedWithGap < 2)
			return true;
		else
			return false;
	}
	
	

	public int count(int player, int row, int col, int dirX, int dirY) {

		int ct = 1;

		int r, c;

		r = row + dirX;
		c = col + dirY;
		while (r >= 0 && r < ROW && c >= 0 && c < COL && board[r][c] == player) {

			ct++;
			r += dirX;
			c += dirY;
		}

		win_r1 = r - dirX;
		win_c1 = c - dirY;

		r = row - dirX;
		c = col - dirY;
		while (r >= 0 && r < ROW && c >= 0 && c < COL && board[r][c] == player) {
			ct++;
			r -= dirX;
			c -= dirY;
		}

		win_r2 = r + dirX;
		win_c2 = c + dirY;

		return ct;

	}
}
