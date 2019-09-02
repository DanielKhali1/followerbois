package followerbois;

import java.util.Arrays;
import java.util.LinkedList;

import javafx.scene.shape.Circle;

public class GeneticAlgorithm 
{
	private Agent[] population;
	public double mutationRate;
	int brainSize;
	public int Evolution = 1;
	public int step = 0;
	
	LinkedList<Circle> border = new LinkedList<Circle>();
	
	
	Vector StartPosition = new Vector(100, 350);
	Vector GoalPosition = new Vector(600, 350);
	
	public GeneticAlgorithm(double mutationRate, int populationSize, int brainSize, Vector StartPosition, Vector GoalPosition)
	{
		
		this.mutationRate = mutationRate;
		this.GoalPosition = GoalPosition;
		this.StartPosition = StartPosition.clone();
		this.brainSize = brainSize;
		generatePopulation(populationSize, StartPosition);
	}
	
	public void addBorder(Circle circle)
	{
		border.add(circle);
	}

	public void generatePopulation(int populationSize, Vector StartPosition)
	{
		setPopulation(new Agent[populationSize]);
		for(int i = 0; i < getPopulation().length; i++)
		{
			getPopulation()[i] = new Agent(StartPosition.clone(), brainSize, GoalPosition);
		}
	}
	
	public void Move(double width, double height)
	{
		for(int i = 0; i < population.length; i++)
		{
			if(!population[i].Dead)
			{
				population[i].update( population[i].Brain[population[i].currentIndex] );
				population[i].currentIndex++;
			
				if(population[i].Position.x > 700 || population[i].Position.x < 0 || population[i].Position.y > 700 || population[i].Position.y < 0 )
				{
					population[i].crashed = true;
					population[i].Dead = true;
				}
				else if(population[i].currentIndex >= brainSize)
				{
					population[i].Dead = true;
				}
				
				for(int j = 0; j < border.size(); j++)
				{
					if(population[i].Position.x > border.get(j).getLayoutX()-30 && population[i].Position.x < border.get(j).getLayoutX()+30 && population[i].Position.y > border.get(j).getLayoutY()-30 && population[i].Position.y < border.get(j).getLayoutY()+30 )
					{
						population[i].crashed = true;
						population[i].Dead = true;
					}
				}
				

			}
			
		}
		step++;
	}
	
	public Agent BestAgent()
	{
		Agent best = population[0];
		
		for(int i = 0; i < population.length; i++)
		{
			if(population[i].Fitness() > best.Fitness())
			{
				best = population[i];
			}
		}
		
		return best;
	}
	
	public int BestAgentIndex()
	{
		Agent best = population[0];
		int index = 0;
		
		for(int i = 0; i < population.length; i++)
		{
			if(population[i].Fitness() > best.Fitness())
			{
				best = population[i];
				index = i;
			}
		}
		
		return index;
	}
	
	public void Evolve()
	{
		step = 0;
		Agent[] newPopulation = new Agent[getPopulation().length];
		
		/*newPopulation[0] = BestAgent();
		newPopulation[0].Dead = false;
		newPopulation[0].currentIndex = 0;
		newPopulation[0].Position = StartPosition.clone();
		*/
		for(int i = 0; i < getPopulation().length; i++)
		{
			Agent Mom = SelectParent();
			Agent Dad = SelectParent();
			
			Agent Child = Crossover(Mom, Dad);
			
			Agent MutatedChild = Mutation(Child);
			
			newPopulation[i] = MutatedChild;
		}
		
		Evolution++;
		population = newPopulation;
	}
	
	public Agent SelectParent()
	{
		//tournament
		
		//pick 2 random Agents
		Agent Fighter1 = getPopulation()[(int) ( Math.random()*(getPopulation().length-1 ) ) ];
		Agent Fighter2 = getPopulation()[(int) ( Math.random()*(getPopulation().length-1 ) ) ];
		
		//FitterAgent will be selected
		return (Fighter1.Fitness() > Fighter2.Fitness()) ? Fighter1 : Fighter2;
	}
	
	public Agent Crossover(Agent Mom, Agent Dad)
	{
		/*int randomCrossoverPoint = (int) (Math.random() * (brainSize-1) );
		
		
		
		for(int i = 0; i < Child.Brain.length; i++)
		{
			 Child.Brain[i] = (i <= randomCrossoverPoint) ? Mom.Brain[i] : Dad.Brain[i]; 
		}*/
		
		Agent Child = new Agent(StartPosition.clone(), brainSize, GoalPosition.clone());
		
		for(int i = 0; i < Child.Brain.length; i++)
		{
			Child.Brain[i] = Mom.Brain[i].Avg(Dad.Brain[i]);
		}
		
		return Child;
		
	}
	
	public Agent Mutation(Agent NormalAgent)
	{
		Agent MutatedAgent = NormalAgent;
		
		for(int i = 0; i < MutatedAgent.Brain.length; i++)
		{
			if(mutationRate > Math.random())
			{
				//System.out.println(i);
				MutatedAgent.Brain[i] = new Vector( (Math.random()*2.5) - 1.25, (Math.random()*2.5) - 1.25);
			}
		}
		
		return MutatedAgent;
	}

	public boolean isPopDead()
	{
		for(int i = 0; i < population.length; i++)
		{
			if(!population[i].Dead)
			{
				return false;
			}
		}
		return true;
	}
	
	
	
	public Agent[] getPopulation() {
		return population;
	}

	public void setPopulation(Agent[] population) {
		this.population = population;
	}
	
	
	
}
