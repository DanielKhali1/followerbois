package followerbois;

import java.util.Arrays;
import java.util.LinkedList;

import javafx.scene.shape.Circle;

public class Agent 
{
	public Vector Position;
	public Vector Velocity = new Vector(0, 0);
	public Vector Acceleration = new Vector(0, 0);
	
	public Vector[] Brain;
	public Vector GoalPosition;
		
	public boolean Dead = false;
	public boolean crashed = false;
	public boolean won = false;
	public int currentIndex = 0;
		
	public Agent(Vector Position, int brainSize, Vector GoalPosition)
	{
		this.GoalPosition = GoalPosition;
		this.Position = Position;
		randomBrain(brainSize);
		
	}
	
	
	public void randomBrain(int brainSize)
	{
		Brain = new Vector[brainSize];
		for(int i = 0; i < brainSize; i++)
		{
			Brain[i] = new Vector( (Math.random()*2) - 1, (Math.random()*2) - 1);
		}
	}
	
	public void update(Vector a)
	{
		Acceleration = a;
		Velocity.add(Acceleration);
		Position.add(Velocity);
		
		if(Position.x < GoalPosition.x+10 && Position.x > GoalPosition.x-10 && Position.y < GoalPosition.y+10 && Position.y > GoalPosition.y-10)
		{
			Dead = true;
			won = true;
		}
		
	}
	
	
	
	
	public double Fitness()
	{
		double fitness = Math.sqrt( Math.pow(Position.x - GoalPosition.x , 2) + Math.pow(Position.y - GoalPosition.y, 2 ) );
		if(crashed)
		{
			fitness *= 100;
		}
		if(won)
		{
			//System.out.println("I WON");
			fitness /= 100;
		}
		
		
		return  1/fitness;
	}
	
	public String toString()
	{
		return Arrays.toString(Brain);
	}
	
}
