/**
 * 
 */
package UserPackage;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author Zhiyong
 *
 */
public class LabelSetUp {
	private List<Label> labelList;
	public LabelSetUp(){
		labelList = new ArrayList<>();
	}
	
	public void setUpLabel( double x, double y, String name) {
		DropShadow drop = new DropShadow();
		drop.setOffsetX(10);
		drop.setOffsetY(10);
		drop.setRadius(10);

		Label label = new Label(name);
		label.setLayoutX(x);
		label.setLayoutY(y);
		label.setEffect(drop);
		label.setTextFill(Color.BLUE);
		label.setFont(Font.font("Cambria", 20));
		labelList.add(label);
	}
	public List<Label> getLabelList(){
		return labelList;
	}

}
