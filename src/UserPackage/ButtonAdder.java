// This entire file is part of my masterpiece.
// Zhiyong Zhao

package UserPackage;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * This class is used for setting up the buttons which will control
 * the simulations through button actions
 * @author Zhiyong
 *
 */
public class ButtonAdder {
	public static final String Package_FOR_BUTTON = "resources/Button";
	
	private ResourceBundle buttonResources;
	//add the buttons on the VBox so that they have the same appearance
	private VBox vbButtons;
	private Map<String, Button> buttonList;
	
	public ButtonAdder(){
		buttonResources = ResourceBundle.getBundle(Package_FOR_BUTTON);
		buttonList = new HashMap<>();
		
		vbButtons = new VBox(20);
		vbButtons.setLayoutY(100);
		vbButtons.setSpacing(20);
		vbButtons.setPadding(new Insets(0, 20, 10, 20)); 

	}
	//add all the buttons needed
	public void setUpButton() {

		addButton(buttonResources.getString("stepforward"));
		addButton(buttonResources.getString("resume"));
		addButton(buttonResources.getString("pause"));
		addButton(buttonResources.getString("speedup"));
		addButton(buttonResources.getString("slowdown"));
		addButton(buttonResources.getString("fileselection"));
		addButton(buttonResources.getString("save"));
		addButton(buttonResources.getString("clear"));

	}

	// add a single  button on the VBox
	private void addButton(String s){

		Button btn = new Button(s);
		btn.setMaxWidth(Double.MAX_VALUE);
		buttonList.put(s, btn);
		vbButtons.getChildren().add(btn);

	}
	
	public VBox getVBox(){
		return vbButtons;
	}
	
	//store all the buttons in a map from the name to the button
	public Map<String, Button> getButtonList(){
		return buttonList;
	}

}
