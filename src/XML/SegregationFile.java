package XML;

import java.util.ResourceBundle;

/*Subclass of SimulationFile that Segregation simulations.
 * 
 * @author Kyle Finke
 */
public class SegregationFile extends SimulationFile {

	private final String SEGREGATION_RESOURCES = "resources/Segregation";

	private ResourceBundle possibleStates = ResourceBundle.getBundle(SEGREGATION_RESOURCES);

	public SegregationFile() {

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
