package followerbois;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;

public class GUI extends Application
{
	GeneticAlgorithm GA = new GeneticAlgorithm(0.1, 12);
	
	
	
	
	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage primaryStage)
	{
		
		
		
		
		
		Pane pane = new Pane();
		Scene scene = new Scene(pane, 700, 700);
		primaryStage.setScene(scene);
		primaryStage.setTitle("FollowerBois");
		primaryStage.show();
		
	}
	
	

}
