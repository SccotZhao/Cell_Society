package XML;

import java.util.ResourceBundle;

/* Subclass of SimulationFile for handling Fire simulations.
 * 
 * @author Kyle Finke
 */
public class FireFile extends SimulationFile {

	private final String FIRE_RESOURCES = "resources/Fire";

	private ResourceBundle possibleStates = ResourceBundle.getBundle(FIRE_RESOURCES);

	public FireFile() {

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
