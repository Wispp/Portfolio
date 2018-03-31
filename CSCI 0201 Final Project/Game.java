//Jack English and Hannah Donovan
//CS201 Final Project

//Game.java
//Handles the under the hood aspects of the game


/**The bowls are ordered on the game board as follows:
 * 1 = player 2's end bowl (the one on the left of the screen)
 * 2-7 = player 1's center bowls (bottom of screen)
 * 8 = player 1's end bowl (right of screen)
 * 9-14 = player 2's center bowls (top of screen)
 * 
 * 
 * 	   14 13 12 11 10 9
 *  1				     8
 * 	   2  3  4  5  6  7 
 * 
 */

public class Game 
{

	//instance variables

	protected Bowl[] board; //array of the bowls
	protected boolean turn; //false = P1, true = P2
	protected boolean over = false; //stores whether game is over
	
	//constructors

	//default
	public Game()
	{
		this.board = initializeBoard();
		this.turn = false;
		//this.over = false;
	}

	//Distributes pebbles from bowl to next bowls
	//and handles the results (turns, captures, etc)
	//bowl number that's passed in is the actual bowl number not the
	//index
	protected void bowlClicked(int bowlNum)
	{

		/**
		 * PEBBLE DISTRIBUTION AND CAPTURES
		 */
		if (board[bowlNum-1].getNumPebbles() == 0)
		{
			//System.out.println("whatchu doin");
		}
		else
		{
			bowlNum = bowlNum-1;

			int nextBowl = (bowlNum+1) % 14; //stores the next bowl around the circle
			final int numPebbles = board[bowlNum].getNumPebbles();
			int finalBowl = (bowlNum + numPebbles) % 14;
			board[bowlNum].setNumPebbles(0);
			for (int i = 0; i < numPebbles; i++)
			{
				if (!turn && board[nextBowl] == board[0]) //if player 1
				{
					nextBowl = (nextBowl+1) % 14; //skip player 2's goal
				}
				else if (turn && board[nextBowl] == board[7] ) //if player 2
				{
					nextBowl = (nextBowl + 1) % 14; //skip player 1's goal
				}
				board[nextBowl].increment();
				nextBowl = (nextBowl+1) % 14; //move to next bowl around circle
			}
			//if last pebble is dropped in empty bowl, then the 
			//bowl opposite to it's pebbles get captured.
			//The case where finalBowl is one of the goals doesn't need to be 
			//accounted for as the capture method handles that (by doing nothing)
			if (board[finalBowl].getNumPebbles() == 1) 
			{
				capture(finalBowl);
			}

			/**
			 * TURN HANDLING
			 */
			if (board[finalBowl] == board[7] && !turn) //board[0] = bowl #1
				//if the finalBowl is player 1's goal and it's 
				//player 1's turn
			{
				board[7].increment();

				//Do nothing because it's still player 1's turn
			}
			else if (board[finalBowl] == board[0] && turn)
				//ifFinalBowl is player 2's goal and it's 
				//player 2's turn
			{
				nextBowl = (nextBowl+1) % 14; //move to next bowl around circle
				//do nothing because it's still player 2's turn
			}
			else //otherwise
			{
				this.swapTurn();; //now the other players turn
			}

			isGameOver(); //check to see if the game is over
		}
	}

	//determines who the winner is
	//returns string to be printed at top of game interface
	public String winner()
	{
		if (board[7].getNumPebbles() > board[0].getNumPebbles()) //p2 more than p1
			return "Player 1 Wins!";
		else if (board[0].getNumPebbles() > board[7].getNumPebbles())
			return "Player 2 Wins!";
		else //tie, which should never happen
			return "It's a tie!";
	}

	//returns an array with the number of pebbles in each bowl
	public int[] pebbleArray()
	{
		int[] output = new int[14];
		for(int i = 0; i < 14; i++)
		{
			output[i] = board[i].getNumPebbles();
		}
		return output;
	}

	//takes the pebbles from the opposite bowl and puts them
	//into it
	public void capture(int bowl)
	{
		// BOTTOM = {2, 3, 4, 5, 6, 7};
		//    TOP = {14, 13, 12, 11, 10, 9};
		switch(bowl)
		{
		case 2:
			captureHelp(2, 14);
			break;
		case 3:
			captureHelp(3, 13);
			break;
		case 4:
			captureHelp(4, 12);
			break;
		case 5:
			captureHelp(5, 11);
			break;
		case 6:
			captureHelp(6, 10);
			break;
		case 7:
			captureHelp(7, 9);
			break;
		case 9:
			captureHelp(9, 7);
			break;
		case 10:
			captureHelp(10, 6);
			break;
		case 11:
			captureHelp(11,5);
			break;
		case 12:
			captureHelp(12, 4);
			break;
		case 13:
			captureHelp(13,3);
			break;
		case 14:
			captureHelp(14,2);
			break;
		default: //If goal
			break; //do nothing
		}
	}

	//helper method for capture that does the work of 
	//moving the pebbles over
	public void captureHelp(int b, int opp)
	{
		if (b < 8 && board[opp-2].getNumPebbles() != 0)
		{
			board[7].increment(board[opp-2].getNumPebbles() + board[b].getNumPebbles()); //proper indexing
			board[opp-2].setNumPebbles(0);
			board[b--].setNumPebbles(0);
		}
		else if (b > 8 && board[opp-2].getNumPebbles() != 0)
		{
			board[0].increment(board[opp-2].getNumPebbles() + board[b].getNumPebbles()); //proper indexing
			board[opp-2].setNumPebbles(0);
			board[b--].setNumPebbles(0);
		}

	}

	//resets the game to its starting point
	public void resetGame()
	{
		this.board = initializeBoard();
		turn = false; //Player 1's turn
	}

	//sets over to whether or not the game is over
	//and returns 1 if the top was empty, and 2 if the bottom was empty
	public void isGameOver()
	{
		boolean bottom = true;
		boolean top = true;

		//Check to see if bottom and top rows are empty
		
		for (int i = 1; i < 7; i++) //bottom row
		{
			if (board[i].getNumPebbles() != 0)
			{
				bottom = false;
				break;
			}
		}
		for (int j = 8; j < 14; j++) //top row
		{
			if (board[j].getNumPebbles() != 0)
			{
				top = false;
				break;
			}
		}

		//If the game's over, act accordingly
		
		if (top) //if the top is what's empty
		{
			gameOverPebbles(2); //move pebbles on bottom to goal
			over = true;
		}
		else if (bottom) //if the bottom is what's empty
		{
			gameOverPebbles(1); //move pebbles on top to goal
			over = true;
		}
	}

	//When the game ends, move the player with pebbles
	//left in the center bowls to their goal
	//helper method for isGameOver()
	public void gameOverPebbles(int empty)
	{
		if (empty == 1) //if player 1's side is empty
		{
			for (int i = 8; i < 14; i++) //top bowls
			{
				board[0].increment(board[i].getNumPebbles()); //add pebbles from centerbowls to goal
				board[i].setNumPebbles(0);
			}
		}
		else if (empty == 2)//if player 2's side is empty
		{
			for (int i = 1; i < 7; i++) //bottom bowls
			{
				board[7].increment(board[i].getNumPebbles()); //add pebbles from centerbowls to goal
				board[i].setNumPebbles(0);
			}
		}
	}

	//returns whether or not a bowl has zero pebbles
	//helper for isGameOver
	public boolean hasZeroPebbles(int i)
	{
		if(board[i].getNumPebbles() == 0)
			return true;
		else
			return false;
	}

	//switches turn from player to the other
	public void swapTurn()
	{
		this.turn = !turn;
	}

	//accessor method for board
	public Bowl[] getBoard()
	{
		return this.board;
	}

	//returns which turn it is
	public boolean getTurn()
	{
		return this.turn;
	}
	
	//returns if it's over or not
	public boolean getOver()
	{
		return this.over;
	}

	//initializes all of the bowls
	protected Bowl[] initializeBoard()
	{
		EndBowl b1 = new EndBowl(); //player 1's end bowl
		EndBowl b2 = new EndBowl(); //player 2's end bowl
		CenterBowl b3 = new CenterBowl(4);
		CenterBowl b4 = new CenterBowl(4);
		CenterBowl b5 = new CenterBowl(4);
		CenterBowl b6 = new CenterBowl(4);
		CenterBowl b7 = new CenterBowl(4);
		CenterBowl b8 = new CenterBowl(4);
		CenterBowl b9 = new CenterBowl(4);
		CenterBowl b10 = new CenterBowl(4);
		CenterBowl b11 = new CenterBowl(4);
		CenterBowl b12 = new CenterBowl(4);
		CenterBowl b13 = new CenterBowl(4);
		CenterBowl b14 = new CenterBowl(4);
		Bowl[] result = {b2, b3, b4, b5, b6, b7, b8, b1, b9, b10, b11, b12, b13, b14};
		return result;
	}

}





