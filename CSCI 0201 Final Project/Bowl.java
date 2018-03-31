//Jack English and Hannah Donovan
//CS201 Final Project

//Bowl.java 
//Superclass of the CenterBowl
//and EndBowl classes

/**The CenterBowl and EndBowl classes have no specific functionality,
   but they help to make the code more modular and would make expanding
   upon the code easier*/

public class Bowl {

	//instance variables
	protected int numPebbles;
	
	//constructors
	
	//default
	public Bowl()
	{
		numPebbles = 0;
	}
	
	//constructor with one parameter
	public Bowl(int m)
	{
		numPebbles = m;
	}
	
	//returns numPebbles
	public int getNumPebbles() 
	{
		return this.numPebbles;
	}
	//mutates NumPebbles
	public void setNumPebbles(int n)
	{
		this.numPebbles = n;
	}
	//Adds 1 to numPebbles
	public void increment()
	{
		this.numPebbles++;
	}
	//Adds n to numPebbles
	public void increment(int n)
	{
		for(int i = 0; i < n; i++)
		{
			this.numPebbles++;
		}
	}
	//Subtracts 1 from numPebbles
	public void decrement()
	{
		this.numPebbles--;
	}
	//Subtracts n from numPebbles
	public void decrement(int n)
	{
		for(int i = 0; i < n; i ++)
		{
			if (this.numPebbles >= 1)
			{
				this.numPebbles--;
			}
			else
			{
				break; //can't have negative Pebbles
			}
		}
	}
	
}
