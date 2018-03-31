//Jack English and Hannah Donovan
//CS201 Final Project

//MyCanvas.java 
//Creates the canvas, handles MouseListening,
//does all drawing


import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")

public class MyCanvas extends Canvas implements MouseListener {

	Game g; //holds the happenings of the game
	int[] numPebbles; //stores the number of pebbles in each bowl
	Image boardPic; //background image

	//constructor
	public MyCanvas(Image bP)
	{
		newGame();
		boardPic = bP;
	}

	//resets the game and board
	public void newGame() {
		this.g = new Game();
		this.numPebbles = g.pebbleArray();
	}

	//resets the game
	public void restart() {
		newGame();
		repaint();
	}

	// called by Java when the window is changed (e.g.,
	// uncovered or resized), or when "repaint()" is called.
	public void paint(Graphics gr){
		turnMsg(gr);
		drawBoard(gr);
		drawGoals(gr);
		drawBowls(gr);
		pebbleNums(gr);
	}

	// helper method for paint; draws the board using an image
	private void drawBoard(Graphics gr) {
		for (int i = 0; i < 4; i++) { //draw the board using the image 4 times 
			gr.drawImage(boardPic, 0, 75+i*86, this);
			gr.drawImage(boardPic, 400, 75+i*86, this);
		} 
	}

	//When a bowl is clicked on, pebbles
	//get moved and board is updated
	private void bowlClickedOn(int b) {
		g.bowlClicked(b);
		pebbleUpdate();
	}

	//Updates the pebble drawings to the proper
	//number
	public void pebbleUpdate() {
		numPebbles = g.pebbleArray();
		repaint();
	}

	// displays the message at top of screen
	private void turnMsg(Graphics gr) 
	{
		gr.setFont(new Font("TimesRoman", Font.BOLD, 28));
		FontMetrics fm = gr.getFontMetrics();  // centered using: https://stackoverflow.com/questions/20602027/applet-align-text-to-bottom-middle

		if (g.getOver()) //if the game's over
		{
			String text3 = g.winner();
			int x3 = (getWidth() - fm.stringWidth(text3)) / 2;
			gr.drawString(text3, x3, 50);
		} 
		else if (g.getTurn()) //if it's player 2's turn
		{
			String text2 = "It's player 2's turn!";
			int x2 = (getWidth() - fm.stringWidth(text2)) / 2;
			gr.drawString(text2, x2, 50);
		} 
		else if (!g.getTurn()) //if it's player 1's turn
		{
			String text1 = "It's player 1's turn!";
			int x1 = (getWidth() - fm.stringWidth(text1)) / 2;
			gr.drawString(text1, x1, 50);
		}
	}

	//draw the goals and their pebbles
	private void drawGoals(Graphics gr) 
	{
		Color brown = new Color(160,82,45); //color is from https://www.rapidtables.com/web/color/brown-color.html
		gr.setColor(brown);
		gr.fillOval(10, 125, 80, 270); //row 1 goal
		gr.fillOval(710, 125, 80, 270); //row 2 goal
		gr.setColor(Color.black);
		gr.drawOval(10, 125, 80, 270);
		gr.drawOval(710, 125, 80, 270);
		drawGoalPebs(gr, numPebbles[0], 10, 125);
		drawGoalPebs(gr, numPebbles[7], 710, 125);	
	}

	//helper method for drawGoals
	//draws the goal pebbles
	private void drawGoalPebs(Graphics gr, int numPebbles, int x, int y)
	{
		for (int i = 0; i < numPebbles; i++) //loop to draw the pebbles
		{
			double randX = Math.random() * 35 + (x+10); //random x location within bound
			double randY = Math.random() * 210 + (y+20); //random y location within bound
			bluePebble(gr, (int) randX, (int) randY); //draw a blue pebble at the location
		}
	}



	// helper method for paint; draws the bowls
	private void drawBowls(Graphics gr) {
		Color brown = new Color(160,82,45); //color is from https://www.rapidtables.com/web/color/brown-color.html
		int x=20;
		for (int i = 0; i < 6; i++) { //draw the bowls
			x = 110 + i*100; //x location of the oval
			gr.setColor(brown);
			gr.fillOval(x, 125, 80, 80); //row 1 bowl
			gr.fillOval(x, 315, 80, 80); //row 2 bowl
			drawPebbles(gr, numPebbles[i+1], x, 315);
			drawPebbles(gr, numPebbles[-i+ (13)], x, 125);
			gr.setColor(Color.black);
			// the following if statements are used to 
			// highlight the current player's bowls in green
			if (g.getTurn()==true) 
			{
				gr.drawOval(x, 315, 80, 80);
				gr.setColor(Color.green);
				gr.drawOval(x, 125, 80, 80);
			} 
			else if (g.getTurn() == false) 
			{
				gr.drawOval(x, 125, 80, 80);
				gr.setColor(Color.green);
				gr.drawOval(x, 315, 80, 80);
			}
		}
	}

	//draws a blue pebble at location x, y
	//Helper for drawPebbles
	private void bluePebble(Graphics gr, int x, int y) 
	{
		gr.setColor(Color.blue);
		gr.fillOval(x, y, 25, 25);
		gr.setColor(Color.black);
		gr.drawOval(x, y, 25, 25);
	}

	// method to draw different numbers of pebbles by calling helper methods
	public void drawPebbles(Graphics gr, int numPebbles, int x, int y)
	{
		if (numPebbles == 1) 
			bluePebble(gr, x+30, y+30);
		else if (numPebbles == 2) {
			bluePebble(gr, x+20, y+20);
			bluePebble(gr, x+40, y+40);
		} else if (numPebbles == 3) {
			bluePebble(gr, x+10, y+20);
			bluePebble(gr, x+50, y+20);
			bluePebble(gr, x+30, y+50);
		} else if (numPebbles == 4) {
			bluePebble(gr, x+13, y+13);
			bluePebble(gr, x+13, y+43);
			bluePebble(gr, x+43, y+43);
			bluePebble(gr, x+43, y+13);
		} else if (numPebbles == 5) {
			bluePebble(gr, x+13, y+13);
			bluePebble(gr,x+13, y+43);
			bluePebble(gr, x+43, y+43);
			bluePebble(gr, x+43, y+13);
			bluePebble(gr,x+30, y+30);
		} else if (numPebbles == 6)
			drawSix(gr, x, y);
		else if (numPebbles == 7) {
			drawSix(gr, x, y);
			bluePebble(gr, x+30, y+17);
		} else if (numPebbles == 8) {
			drawSix(gr, x, y);
			bluePebble(gr, x+30, y+17);
			bluePebble(gr, x+30, y+47);
		} else if (numPebbles != 0) {
			drawSix(gr, x, y);
			bluePebble(gr, x+30, y+17);
			bluePebble(gr, x+30, y+47);
			bluePebble(gr, x+30, y+30);
		}
	}

	// helper method for drawPebbles; draws six pebbles. This method 
	// helps save code because the methods to draw seven, eight, and
	// more pebbles all build on the model for drawing six
	private void drawSix(Graphics gr, int x, int y) {
		bluePebble(gr,x+13, y+13);
		bluePebble(gr, x+13, y+43);
		bluePebble(gr, x+43, y+43);
		bluePebble(gr, x+43, y+13);
		bluePebble(gr,x+20, y+30);
		bluePebble(gr, x+40, y+30);
	}

	// helper method for paint; draws the integer number of pebbles per bowl
	private void pebbleNums(Graphics gr) {
		int y3 = 225; //height for row 1 text
		int y4 = 305; //height for row 2 text
		gr.setFont(new Font("TimesRoman", Font.BOLD, 18));
		for (int i = 0; i < 14; i++) {	
			gr.setColor(Color.black);
			if (i == 0) 
			{ //left goal
				gr.drawString(Integer.toString(numPebbles[i]), 10, 135);
			} if (i == 7) 
			{ //right goal
				gr.drawString(Integer.toString(numPebbles[i]), 770, 135);
			} if (i > 0 && i < 7) 
			{ //bottom row
				gr.drawString(Integer.toString(numPebbles[i]), 50 + i*100, y4);
			} else if (i < 14 && i > 7) 
			{ //top row
				gr.drawString(Integer.toString(numPebbles[i]), 650 - (100 * (i-8)), y3);
			}
			else
			{
				
			}
		}
	}

	// handles mouse events
	public void mousePressed(MouseEvent event) {
		Point p = event.getPoint();
		int x = p.x;
		int y = p.y;
		// check if clicked in box area
		//Ideally we would have been able to use math to do this a lot more cleanly,
		//but due to the tricky nature of circular shapes and the gaps between the bowls
		//we decided to hardcode it
		if (g.getTurn() == true && (y >= 125 && y <= 205)) { //player 2's turn
			if ((x >= 110 && x <= 190)) 
				bowlClickedOn(14);
			if ((x >= 210 && x <= 290)) 
				bowlClickedOn(13);
			if ((x >= 310 && x <= 390)) 
				bowlClickedOn(12);
			if ((x >= 410 && x <= 490)) 
				bowlClickedOn(11);
			if ((x >= 510 && x <= 590)) 
				bowlClickedOn(10);
			if ((x >= 610 && x <= 690)) 
				bowlClickedOn(9);
			pebbleUpdate();
		} if (g.getTurn() == false && (y >= 315 && y <= 395)) { //player 1's turn
			if ((x >= 110 && x <= 190)) 
				bowlClickedOn(2);
			if ((x >= 210 && x <= 290)) 
				bowlClickedOn(3);
			if ((x >= 310 && x <= 390)) 
				bowlClickedOn(4);
			if ((x >= 410 && x <= 490)) 
				bowlClickedOn(5);
			if ((x >= 510 && x <= 590)) 
				bowlClickedOn(6);
			if ((x >= 610 && x <= 690)) 
				bowlClickedOn(7);
			pebbleUpdate();
		}
	}	

	//Necessary methods to implement MouseListener
	public void mouseReleased(MouseEvent event) { }
	public void mouseClicked(MouseEvent event) { }
	public void mouseEntered(MouseEvent event) { }
	public void mouseExited(MouseEvent event) { }


}

