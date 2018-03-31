//Jack English and Hannah Donovan
//CS201 Final Project

//Mancala.java 
//Drives the program and implements the applet

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")

public class Mancala extends Applet implements ActionListener{

	MyCanvas c; //canvas that it's drawn on
	Button restartButton;  //resets the game

	//initializes the applet and canvas
	public void init() 
	{
		setLayout(new BorderLayout());
		restartButton = new Button("Start Over");
		restartButton.setBackground(Color.white);
		restartButton.addActionListener(this);
		Panel p = new Panel();
		p.setBackground(Color.black);
		p.add(restartButton);
		add("South", p);

		//canvas
		Image boardPic = getImage(getDocumentBase(), "images.jpg"); //picture is from http://clipartandscrap.com/wood-clip-art_6716/
		c = new MyCanvas(boardPic);
		Color ltBlue = new Color(240, 248, 255);
		c.setBackground(ltBlue);
		c.addMouseListener(c);
		add("Center", c);		
	}

	//action handler
	public void actionPerformed(ActionEvent event) 
	{
		if (event.getSource() instanceof Button) 
		{
			String label = ((Button)event.getSource()).getLabel();
			if (label.equals("Start Over"))
				c.restart();
		}
	}
} //end class
