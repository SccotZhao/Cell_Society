package XML;

import java.util.ResourceBundle;

public class SugarFile extends SimulationFile {

	private final String SUGAR_RESOURCES = "resources/Sugar";

	private ResourceBundle possibleStates = ResourceBundle.getBundle(SUGAR_RESOURCES);

	public SugarFile() {

	}

	@Override
	public boolean isValidState(String state) {
		return possibleStates.containsKey(state);
	}

	@Override
	public ResourceBundle getPossibleStates() {
		return possibleStates;
	}
}
