package XML;

import java.util.ResourceBundle;


/*Subclass of SimulationFile that handles PredatorPrey simulations.
 * 
 * @author Kyle Finke
 */
public class PredatorPreyFile extends SimulationFile {

	private final String PREDATOR_PREY_RESOURCES = "resources/PredatorPrey";

	private ResourceBundle possibleStates = ResourceBundle.getBundle(PREDATOR_PREY_RESOURCES);

	public PredatorPreyFile() {

	}

	@Override
	protected boolean isValidState(String state) {
		return possibleStates.containsKey(state);
	}

	@Override
	protected ResourceBundle getPossibleStates() {
		return possibleStates;
	}
}
