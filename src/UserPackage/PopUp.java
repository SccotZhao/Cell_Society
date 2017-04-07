package UserPackage;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PopUp{
	private Scene scene;
	public PopUp(int width, int height){
		Pane root = new Pane();
		// create a place to see the shapes
		scene = new Scene(root, width, height);
		
		Text splash = new Text();
		splash = new Text(10,50, "The State is Saved in The Data Package!\n                 Good Job!\n                  Team 20");
		splash.setFont(Font.font(25));
		splash.setFill(Color.DARKVIOLET);
		root.getChildren().addAll(splash);
	}
	public Scene getScene(){
		return scene;
	}
}
