package UserPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import cellpackage.Cell;
import cellpackage.CellHandler;
import cellpackage.FireCellHandler;
import cellpackage.GameOfLifeCellHandler;
import cellpackage.PredatorPreyCellHandler;
import cellpackage.SegregationCellHandler;
import cellpackage.SugarCellHandler;
import grid.Grid;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

/**
 * the UserInterface class defines the choice of simulation and the buttons
 * 
 * @author Zhiyong
 *
 */
public class UserInterface {

	public static final double WIDTH = 800;
	public static final double HEIGHT = 600;
	public static final int KEY_INPUT_SPEED = 5;
	public static final int NUMBER_OF_BUTTON = 5;
	public static final double DISTANCE = 50;
	public static final String Package_FOR_BUTTON = "resources/Button";
	
	public static final String LABELNAME = "         Welcome To The Cell Society\n Please Choose The Shape For Simulation\n             Then Choose One XML File\n\n  "
			+ "     Creator:      Alex   Kyle    Zhiyong";
	// kind of data files to look for
	public static final String PACKAGE_FOR_COLOR = "resources/Color";
	private Scene scene;
	private Pane root;
	private ResourceBundle buttonResources;

	private CellHandler cellHandler;
	private Grid grid;
	// store all the buttons on the pane
	private Map<String, Button> buttonList;
	private Map<String, TextField> textFieldList;
	private String simulationName;
	private int dimension;

	private Slider slider;
	private String shapeOfSimulation;
	//store the state and list of percentage of that state
	private Map<String, List<Double>> statePercentage;
	private Draw draw;
	private List<String> stateList;
	private double space;
	private PercentageHandler percentageHandler;
	private ShapeManager shapeManager;
	private ResourceBundle colorResources;
	private ButtonAdder buttonAdder;
	private TextFieldAdder textFieldAdder;
	private SliderSetUp sliderSetUp;
	private LabelSetUp labelSetUp;
	
	
	public UserInterface() {
		initInterface();

		scene = new Scene(root, WIDTH, HEIGHT);
		scene.setFill(Color.RED);
		buttonResources = ResourceBundle.getBundle(Package_FOR_BUTTON);
		
		initialButton("fileselection");
		initialButton("triangle");
		initialButton("rectangle");
		
		Label label = new Label(LABELNAME);
		label.setLayoutX(200);
		label.setLayoutY(10);
		label.setTextFill(Color.BLUE);
		label.setFont(Font.font("Cambria", 20));
		root.getChildren().add(label);

	}
	
	public UserInterface(int dim, String simName, List<String> states, String shape){
		initInterface(states);

		grid = new Grid(dim, states, simName, shape);

		switch (simName) {
		case "Fire":
			cellHandler = new FireCellHandler(grid);
			break;
		case "GameOfLife":
			cellHandler = new GameOfLifeCellHandler(grid);
			break;
		case "PredatorPrey":
			cellHandler = new PredatorPreyCellHandler(grid);
			break;
		case "Segregation":
			cellHandler = new SegregationCellHandler(grid);
			break;
		case "Sugar":
			cellHandler = new SugarCellHandler(grid);
			break;
		}

		colorResources = ResourceBundle.getBundle(PACKAGE_FOR_COLOR);
		shapeManager = new ShapeManager();
		
		initialState(dim, simName, states, shape);
		buttonAdder = new ButtonAdder();
		
		buttonAdder.setUpButton();
		buttonList = buttonAdder.getButtonList();
		root.getChildren().add(buttonAdder.getVBox());
		
		textFieldAdder = new TextFieldAdder();
		textFieldAdder.setUpTextfield(simName);
		textFieldList = textFieldAdder.getTextField();
		root.getChildren().addAll(textFieldAdder.getLabelList());
		for(TextField t : textFieldAdder.getTextField().values()){
			root.getChildren().add(t);
		}
		
		sliderSetUp = new SliderSetUp();
		sliderSetUp.setUpSlider();
		slider = sliderSetUp.getSlider();
		root.getChildren().add(sliderSetUp.getSlider());
		root.getChildren().add(sliderSetUp.getLabelForSlider());
		
		labelSetUp = new LabelSetUp();
		labelSetUp.setUpLabel(650, 15, "Percentage");
		root.getChildren().addAll(labelSetUp.getLabelList());
		
		scene = new Scene(root, WIDTH, HEIGHT);

	}
	
	private void initialButton(String s){
		Button btnSelect = new Button(buttonResources.getString(s));
		btnSelect.setLayoutX(10);
		btnSelect.setLayoutY(10 + space);
		space +=50;
		buttonList.put(buttonResources.getString(s), btnSelect);
		root.getChildren().add(btnSelect);
	}



	public void initInterface(List<String> states) {
		
		initInterface();
		percentageHandler = new PercentageHandler();
		statePercentage = new HashMap<>();
		statePercentage = percentageHandler.calculatePercentage(states, statePercentage);
		
		draw = new Draw(statePercentage);

	}

	public void initInterface(){
		
		root = new Pane();
		buttonList = new HashMap<>();
		textFieldList = new HashMap<>();

	}

	private void initialState(int dim, String simName, List<String> states, String shape) {
		
		
		switch(shape){
		case "rectangle":
			shapeManager.setRec(dim, states);			
			root.getChildren().addAll(shapeManager.getShapeList());
			break;
		case "triangle":
			shapeManager.setTri(dim, states);
       		root.getChildren().addAll(shapeManager.getShapeList());
			break;
		case "hexagon":
			shapeManager.setHex(dim, states);
			root.getChildren().addAll(shapeManager.getShapeList());
			break;
		default:
			shapeManager.setRec(dim, states);
			root.getChildren().addAll(shapeManager.getShapeList());
			break;
		}
		
		simulationName = simName;
		dimension = dim;
		shapeOfSimulation = shape;
		stateList = states;
		
	}

	// update the state of each grid after each step of animation
	public void updateDisplay() {
		// here initialize the name of CellHandler according to the
		// simulationName
		grid = cellHandler.updateGrid();
		updateShape(shapeManager.getShapeList());

	}
	
	private void updateShape(List<Polygon> list){
		root.getChildren().removeAll(list);
		root.getChildren().removeAll(draw.getPath());
		
		stateList.clear();
		
		for (int r = 0; r < dimension; r++) {
			for (int c = 0; c < dimension; c++) {
				Cell cell = grid.getCell(r, c);
				String stateTemp = cell.getState();
				stateList.add(stateTemp);
				list.get(dimension * r + c).setFill(Color.web(colorResources.getString(stateTemp)));
			}
		}
		statePercentage = percentageHandler.calculatePercentage(stateList, statePercentage);
		//draw = new Draw(statePercentage);
		root.getChildren().addAll(draw.getPath());
		root.getChildren().addAll(list);
		
	}


	public Scene getScene() {
		return scene;
	}

	public Map<String, Button> getButton() {
		return buttonList;
	}
	
	public Map<String, TextField> getTextField(){
		return textFieldList;
	}
	public String getSimName(){
		return simulationName;
	}
	public Slider getSlider(){
		return slider;
	}
	public int getDimension(){
		return dimension;
	}
	public List<String> getStateList(){
		return stateList;
	}

	//update the state randomly after the user click on the shape
	public void mouseUpdate(double x, double y) {
		int index = -1;
		for(int i = 0; i < shapeManager.getShapeList().size(); i++){
			if(shapeManager.getShapeList().get(i).contains(x, y)){
				index = i;
				break;
			}
		}
		
		if(index > 0){
			
			Random random = new Random();
			int newIndex = random.nextInt(stateList.size());
			stateList.remove(index);
			stateList.add(index, stateList.get(newIndex));
			shapeManager.getShapeList().get(index).setFill(Color.web(colorResources.getString(stateList.get(newIndex))));
		}
		updateShape(shapeManager.getShapeList());
	}

}