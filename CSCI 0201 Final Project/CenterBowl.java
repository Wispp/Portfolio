//Jack English and Hannah Donovan
//CS201 Final Project

//CenterBowl.java
//Details the CenterBowl subclass

public class CenterBowl extends Bowl {	
	//constructors
	
	//default
	public CenterBowl()
	{
		super();
	}
	//one parameter
	public CenterBowl(int m)
	{
		super(m);
	}
	
	//returns whether or not the bowl is empty
	public boolean isEmpty()
	{
		return this.getNumPebbles() == 0;
	}

}
