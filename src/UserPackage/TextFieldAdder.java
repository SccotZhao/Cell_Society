/**
 * 
 */
package UserPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author Zhiyong
 *
 */
public class TextFieldAdder {
	public static final double WIDTH = 800;
	public static final String PACKAGE_FOR_TEXT = "resources/Text";
	
	private ResourceBundle textResources;
	private Map<String, TextField> textFieldList;
	private List<Label> labelList;
	
	public TextFieldAdder(){
		textResources = ResourceBundle.getBundle(PACKAGE_FOR_TEXT);
		textFieldList = new HashMap<>();
		labelList = new ArrayList<>();
	}
	public void setUpTextfield(String simulation) {
		switch(simulation){
		case "Fire": 
			setUpSingleTextField(textResources.getString("tree"), WIDTH/2 - 150, 450);
			setUpSingleTextField(textResources.getString("fire"), WIDTH/2 - 150, 500);
			break;
		case "GameOfLife":
			setUpSingleTextField(textResources.getString("dead"), WIDTH/2 - 150, 450);
			setUpSingleTextField(textResources.getString("alive"), WIDTH/2 - 150, 500);
			break;
		case "PredatorPrey":
			setUpSingleTextField(textResources.getString("shark"), WIDTH/2 - 150, 450);
			setUpSingleTextField(textResources.getString("fish"), WIDTH/2 - 150, 500);
			setUpSingleTextField(textResources.getString("water"), WIDTH/2 - 150, 550);
			break;
		case "Segregation":
			setUpSingleTextField(textResources.getString("white"), WIDTH/2 - 150, 450);
			setUpSingleTextField(textResources.getString("blue"), WIDTH/2 - 150, 500);
			break;
		case "Sugar":
			setUpSingleTextField(textResources.getString("high"), WIDTH/2 - 150, 450);
			setUpSingleTextField(textResources.getString("low"), WIDTH/2 - 150, 500);
			break;
		}

		
	}
	
	
	private void setUpSingleTextField(String s, double x, double y){
		Label label = new Label(s + ":");
		label.setLayoutX(x-60);
		label.setLayoutY(y);
		label.setTextFill(Color.GREEN);
		label.setFont(Font.font("Cambria", 16));

		
		final TextField name = new TextField();
		name.setPromptText("Enter the parameter for the number of " + s);
		name.getText();
		name.setPrefColumnCount(25);
		name.setLayoutX(x);
		name.setLayoutY(y);
		textFieldList.put(s, name);
		labelList.add(label);
	}
	public Map<String, TextField> getTextField(){
		return textFieldList;
	}
	public List<Label> getLabelList(){
		return labelList;
	}
	

}
