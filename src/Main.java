
import java.io.File;
import java.util.ResourceBundle;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import XML.XMLException;
import XML.XMLReader;
import XML.XMLWriter;
import grid.Grid;
import UserPackage.PopUp;
import UserPackage.SaveXML;
import UserPackage.UserInterface;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

/**
 * This is the main function of the cell society 
 * @author Zhiyong
 *
 */
public class Main extends Application{
	
    public static final String DATA_FILE_EXTENSION = "*.xml";
	public static final int FRAMES_PER_SECOND = 1;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/Button";
    //the rate for speedup or slow down
	public static final double RATE = 2.0;
	public static final String MUSIC = "Canon.mp3";
	public static final Paint BACKGROUND = Color.LAWNGREEN;
	
	private FileChooser myChooser;
	private XMLReader reader;
	private UserInterface u;
	private Set<String> buttonLabel;
	private ResourceBundle myResources;
	private KeyFrame  frame;
	private Timeline animation;
	private double delay;
	private boolean didSelectOriginally;
	private String shape;

	@Override
	public void start(Stage s) throws Exception {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
		u = new UserInterface();
		buttonLabel = u.getButton().keySet();
		setButtonAction(buttonLabel);	
		
		
		//play music 
//		Media sound = new Media(getClass().getResource(MUSIC).toURI().toString());
//		AudioClip mediaPlayer = new AudioClip(sound.getSource());
//		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//	    mediaPlayer.play();
	    
		s.setScene(u.getScene());
		s.setTitle("Cell Society");
		s.show();
	}


	private void setSliderAction(Slider slider) {
		slider.valueProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				if(newValue.doubleValue()==0){				
					delay = delay * oldValue.doubleValue() / 0.001 ;
					animationReset(Timeline.INDEFINITE);
				}else{
					delay = delay * oldValue.doubleValue() / newValue.doubleValue() ;
					animationReset(Timeline.INDEFINITE);
				}
				
			}
			
		});
		
	}


	private void frameSetUp(double delay, int cycle) {
		frame = new KeyFrame(Duration.millis(delay),
                                      e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(cycle);
        animation.getKeyFrames().add(frame);
        animation.play();
	}
	

	private void setButtonAction(Set<String> buttonNames) {
		for(String s : buttonNames){
			if(s.equals(myResources.getString("stepforward"))){
				
				u.getButton().get(s).setOnAction(e -> stepForward());
				
			}else if(s.equals(myResources.getString("resume"))){
				
				u.getButton().get(s).setOnAction(e -> resume());
				
			}else if(s.equals(myResources.getString("pause"))){
				
				u.getButton().get(s).setOnAction(e -> pause());
				
			}else if(s.equals(myResources.getString("speedup"))){
				
				u.getButton().get(s).setOnAction(e -> speedUp());
				
			}else if(s.equals(myResources.getString("slowdown"))){
				
				u.getButton().get(s).setOnAction(e -> slowDown());
				
			}else if(s.equals(myResources.getString("fileselection"))){
				
				u.getButton().get(s).setOnAction(e -> select());
			
			}else if(s.equals(myResources.getString("clear"))){


				u.getButton().get(s).setOnAction(e -> clear());

			}else if(s.equals(myResources.getString("save"))){

				u.getButton().get(s).setOnAction(e -> {
					try {
						save();
					} catch (ParserConfigurationException e1) {
						e1.printStackTrace();
					} catch (TransformerException e1) {
						e1.printStackTrace();
					}
				});

			}else if(s.equals(myResources.getString("triangle"))){
				
				u.getButton().get(s).setOnAction(e -> tri());
				
				
			}else if(s.equals(myResources.getString("rectangle"))){
				
				u.getButton().get(s).setOnAction(e -> rec());		
				
			}


		}

	}


	private void rec() {
		shape = "rectangle";
	}


	private void tri() {
		shape = "triangle";
	}


	private void save() throws ParserConfigurationException, TransformerException {
		Grid grid = new Grid(u.getDimension(), u.getStateList(), u.getSimName(), "triangle");
		XMLWriter.createXML(grid);
		// attach scene to the stage and display it
		PopUp p = new PopUp(450,200);
		Scene scene = p.getScene();
		Stage s = new Stage();
		s.setScene(scene);
		s.setTitle("save");
		s.show();
	}


	private void clear() {
		for(TextField text : u.getTextField().values()){
			text.clear();
		}
	}


	private void select() {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
		myChooser = makeChooser(DATA_FILE_EXTENSION);
		Stage s = new Stage();
		File dataFile = myChooser.showOpenDialog(s);
		
	    
		//initialize the delay for the frame
		delay = MILLISECOND_DELAY;
		
        if (dataFile != null) {
            try {
            	//add information from the XMLReader class
            	if(didSelectOriginally) animation.stop();
            	reader = new XMLReader(dataFile);
            	//read shape from the user input
        		u = new UserInterface(reader.getDimensions(), reader.getSimulation(), reader.getStates(), shape);
        		
        		buttonLabel = u.getButton().keySet();
        		setButtonAction(buttonLabel);
        		
        		setSliderAction(u.getSlider());
        		u.getScene().setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        		s.setScene(u.getScene());
        		s.setTitle(u.getSimName());
        		s.show();
        		didSelectOriginally = true;
            }
            catch (XMLException e) {
                Alert a = new Alert(AlertType.ERROR);
                a.setContentText(String.format("ERROR reading file %s", dataFile.getPath()));
                a.showAndWait();
            }
        }
//        else {
//            // nothing selected, so quit the application
//            Platform.exit();
//        }
        
        // attach loop to timeLine to play it
        frameSetUp(MILLISECOND_DELAY, Timeline.INDEFINITE);


	}

	private void handleMouseInput(double x, double y) {
		
		//update the state randomly after the user click on the shape
		u.mouseUpdate(x, y);

	}


	private void step(double secondDelay) {			
		u.updateDisplay();	
	}

	
	private void slowDown() {
		
		delay = RATE * delay;
		animationReset(Timeline.INDEFINITE);
	}

	private void speedUp() {
		
		delay = delay / RATE;
		animationReset(Timeline.INDEFINITE);

	}

	private void pause() {
		animation.stop();
	}

	private void resume() {
		animationReset(Timeline.INDEFINITE);
	}

	private void stepForward() {
		animationReset(1);
		
	}
	
	//reset the animation after we stop the animation
	private void animationReset(int cycle) {
		animation.stop();
		animation.getKeyFrames().clear();
		frameSetUp(delay, cycle);
	}

	// set some sensible defaults when the FileChooser is created
    private FileChooser makeChooser (String extensionAccepted) {
        FileChooser result = new FileChooser();
        result.setTitle("Open Data File");
        // pick a reasonable place to start searching for files
        result.setInitialDirectory(new File(System.getProperty("user.dir")));
        result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
        return result;
    }
    public String getShape(){
    	return shape;
    }

	
    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        launch(args);
    }

}