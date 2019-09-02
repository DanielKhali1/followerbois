package followerbois;

import java.util.LinkedList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class GUI extends Application
{
	
	
	Vector StartPosition = new Vector(100, 350);
	Vector GoalPosition = new Vector(600, 350);
	//	public GeneticAlgorithm(double mutationRate, int populationSize, int brainSize, Vector StartPosition, Vector GoalPosition)
	GeneticAlgorithm GA = new GeneticAlgorithm(0.003, 250, 300, StartPosition, GoalPosition);
	LinkedList<Circle> border = new LinkedList<Circle>();

	Pane pane = new Pane();
	
	
	Circle DisplayAgents[];
	boolean StartFollowMouse = false;
	boolean EndFollowMouse = false;
	
	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage primaryStage)
	{
		Scene scene = new Scene(pane, 700, 700);
		
		pane.setStyle("-fx-background-color: 'black';");
		
		Pane borderPane = new Pane();
		pane.getChildren().add(borderPane);
		
		Button resetBtn = new Button("Reset");
		resetBtn.setLayoutX(20);
		resetBtn.setLayoutY(140);
		pane.getChildren().add(resetBtn);
		

		
		Button clearBtn = new Button("Clear");
		clearBtn.setLayoutX(80);
		clearBtn.setLayoutY(140);
		pane.getChildren().add(clearBtn);
		
		clearBtn.setOnAction(e->{
			for(int i = 0; i < border.size(); i++)
			{
				borderPane.getChildren().clear();
				border = new LinkedList<Circle>();
			}
		});
		
		//start point
		Circle startPoint = new Circle(10);
		startPoint.setLayoutX(GA.StartPosition.x);
		startPoint.setLayoutY(GA.StartPosition.y);
		startPoint.setFill(Color.GREENYELLOW);
		pane.getChildren().add(startPoint);
		
		
		TextField mutationTxtF = new TextField("0.003");
		mutationTxtF.setText("0.003");
		mutationTxtF.setLayoutX(20);
		mutationTxtF.setLayoutY(50);
		pane.getChildren().add(mutationTxtF);
		
		
		Slider populationSlider = new Slider(0, 1000, 1);
		populationSlider.setValue(300);
		populationSlider.setShowTickMarks(true);
		populationSlider.setShowTickLabels(true);
		populationSlider.setLayoutX(20);
		populationSlider.setLayoutY(90);
		pane.getChildren().add(populationSlider);
		
		mutationTxtF.setOnKeyPressed(e->{
			GA.mutationRate = Double.parseDouble(mutationTxtF.getText());
		});

		
		populationSlider.setOnDragDetected(e->{
			removeCircles();
			GA = new GeneticAlgorithm(Double.parseDouble(mutationTxtF.getText()), (int) populationSlider.getValue(), 300, StartPosition, GoalPosition);
			generateDisplayCircles();
			GA.border = border;
		});
		
		populationSlider.setOnMouseDragReleased(e->{
			removeCircles();
			GA = new GeneticAlgorithm(Double.parseDouble(mutationTxtF.getText()), (int) populationSlider.getValue(), 300, StartPosition, GoalPosition);
			generateDisplayCircles();
			GA.border = border;
		});
		
		populationSlider.setOnDragDone(e->{
			removeCircles();
			GA = new GeneticAlgorithm(Double.parseDouble(mutationTxtF.getText()), (int) populationSlider.getValue(), 300, StartPosition, GoalPosition);
			generateDisplayCircles();
			GA.border = border;
		});
		
		resetBtn.setOnAction(e->{
			GA = new GeneticAlgorithm(Double.parseDouble(mutationTxtF.getText()), (int) populationSlider.getValue(), 300, StartPosition, GoalPosition);
			GA.border = border;
		});
		
		
		generateDisplayCircles();
		
		
		//endPoint
		Circle endPoint = new Circle(10);
		endPoint.setLayoutX(GA.GoalPosition.x);
		endPoint.setLayoutY(GA.GoalPosition.y);
		endPoint.setFill(Color.PURPLE);
		pane.getChildren().add(endPoint);
		
		Text populationText = new Text("Population:   Step:   Generation: ");
		populationText.setLayoutX(20);
		populationText.setLayoutY(20);
		populationText.setFill(Color.WHITE);

		pane.getChildren().add(populationText);
		
		

		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent t) 
	        {	 
	        	
	    		startPoint.setOnMouseClicked( e->{
	    			StartFollowMouse = !StartFollowMouse;		
	    		});
	    		
	    		endPoint.setOnMouseClicked( e->{
	    			EndFollowMouse = !EndFollowMouse;		
	    		});
	    		
	    		pane.setOnMouseDragged( e->{
	    			Circle tempCircle = new Circle(30);
	    			tempCircle.setFill(Color.WHITE);
	    			tempCircle.setLayoutX(e.getX());
	    			tempCircle.setLayoutY(e.getY());
	    			borderPane.getChildren().add(tempCircle);
	    			
	    			border.add(tempCircle);
	    			
	    			GA.addBorder(tempCircle);
	    		});
	    		
	    		pane.setOnMouseMoved(e->{
	    			if(StartFollowMouse)
		    		{
		    			startPoint.setLayoutX(e.getX());
	    				startPoint.setLayoutY(e.getY());
	    				
	    				StartPosition = new Vector(e.getX(), e.getY());
	    			
	    				GA.StartPosition = new Vector(e.getX(), e.getY());
		    		}
	    			else if(EndFollowMouse)
		    		{
		    			endPoint.setLayoutX(e.getX());
		    			endPoint.setLayoutY(e.getY());
	    				
	    				GoalPosition = new Vector(e.getX(), e.getY());
	    			
	    				GA.GoalPosition = new Vector(e.getX(), e.getY());
		    		}
	    		});
	    		
	        	
	    		
	        	
    			if(GA.isPopDead())
    			{
    				//System.out.println(GA.BestAgent().Fitness());
    				double sum = 0;
    				for(int i = 0; i < GA.getPopulation().length; i++)
    				{
    					sum += GA.getPopulation()[i].Fitness();
    				}
    				
    				System.out.println(sum/GA.getPopulation().length);
    				
    				GA.Evolve();
    			}
	        	//run full life
    			
	    		GA.Move(scene.getWidth(), scene.getHeight());
	    		populationText.setText("MutationR: " + GA.mutationRate + "   Population: " + GA.getPopulation().length + "  Step: " + GA.step + "    Generation: " + GA.Evolution);
    			

	    		for(int i = 0; i < DisplayAgents.length; i++)
	    		{
		    		DisplayAgents[i].setFill(Color.MEDIUMPURPLE);
	    			DisplayAgents[i].setLayoutX(GA.getPopulation()[i].Position.x);
	    			DisplayAgents[i].setLayoutY(GA.getPopulation()[i].Position.y);
	    			
	    		}
	    		
	    		//DisplayAgents[GA.BestAgentIndex()].setFill(Color.RED);
	        }
	      }));
	    timeline.setCycleCount(Timeline.INDEFINITE);
	    timeline.play();
		
		
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("FollowerBois");
		primaryStage.show();
		
	}
	
	public void generateDisplayCircles()
	{
		DisplayAgents = new Circle[GA.getPopulation().length];
		// Agents
		for(int i = 0; i < GA.getPopulation().length; i++)
		{
			Circle tempAgent = new Circle(3);
			tempAgent.setFill(Color.MEDIUMPURPLE);
			tempAgent.setLayoutX(GA.getPopulation()[i].Position.x);
			tempAgent.setLayoutY(GA.getPopulation()[i].Position.y);
			DisplayAgents[i] = tempAgent;
			pane.getChildren().add(DisplayAgents[i]);
		}
	}
	
	public void removeCircles()
	{
		for(int i = 0; i < DisplayAgents.length; i++)
		{
			pane.getChildren().remove(DisplayAgents[i]);
		}
	}
	
	

}
