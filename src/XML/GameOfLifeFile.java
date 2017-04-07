package XML;

import java.util.ResourceBundle;

/*Subclass of SimulationFile that handles Game Of Life simulations.
 * 
 * @author Kyle Finke
 */
public class GameOfLifeFile extends SimulationFile {
	
	private final String GAME_OF_LIFE_RESOURCES = "resources/GameOfLife";
	
	private ResourceBundle possibleStates = ResourceBundle.getBundle(GAME_OF_LIFE_RESOURCES);
	
	public GameOfLifeFile(){
		
	}
	
	@Override
	protected boolean isValidState(String state){
		return possibleStates.containsKey(state);		
	}

	@Override
	protected ResourceBundle getPossibleStates() {
		return possibleStates;
	}
	
}
