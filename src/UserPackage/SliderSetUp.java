/**
 * 
 */
package UserPackage;

import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * @author Zhiyong
 *
 */
public class SliderSetUp {
	private LabelSetUp setUpLabel;
	private Slider slider;
	private Label label;
	
	public SliderSetUp(){
		setUpLabel = new LabelSetUp();
	}
	
	public void setUpSlider() {
		setUpLabel.setUpLabel(10, 10, "Simulation Speed");
		label = setUpLabel.getLabelList().get(0);
		slider = new Slider(0,2,1);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(0.5f);
		slider.setBlockIncrement(0.1f);
		slider.setLayoutX(10);
		slider.setLayoutY(50);
		
	}
	public Slider getSlider(){
		return slider;
	}
	public Label getLabelForSlider(){
		return label;
	}

}
