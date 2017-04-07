package XML;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/* This class creates different layouts of cells based on two possible methods:'
 * random layout of states and concentration of states in random locations.
 * 
 * 
 * @author Kyle Finke
 */
public abstract class SimulationFile {
	private ResourceBundle errors = ResourceBundle.getBundle(XMLReader.XML_RESOURCES);

	private List<String> states = new ArrayList<String>();

	protected abstract boolean isValidState(String state);

	protected abstract ResourceBundle getPossibleStates();

	public List<String> readStates(Scanner scan) {
		String nextState;
		while (scan.hasNext()) {
			nextState = scan.next();
			if (isValidState(nextState)) {
				states.add(nextState);
			} else {
				throw new XMLException(errors.getString("cellState"));
			}
		}
		return states;
	}

	public List<String> randomLayout(int dimensions) {
		Object[] states = getPossibleStates().keySet().toArray();
		List<String> randomStates = new ArrayList<String>();
		setRandomStates(dimensions, states, randomStates);
		return randomStates;
	}

	private void setRandomStates(int dimensions, Object[] states, List<String> randomStates) {
		double numStates;
		for (int row = 0; row < dimensions; row++) {
			for (int column = 0; column < dimensions; column++) {
				numStates = states.length * Math.random();
				randomStates.add(getPossibleStates().getString((String) states[(int) numStates]));
			}
		}
	}

	public List<String> concentrationLayout(Scanner scan, int dimensions) {
		Object[] states = getPossibleStates().keySet().toArray();
		List<Double> concentrations = new ArrayList<Double>();
		List<String> concentrationStates = new ArrayList<String>();
		while (scan.hasNextDouble()) {
			concentrations.add(scan.nextDouble());
		}
		if (states.length != concentrations.size()) {
			throw new XMLException(errors.getString("numConcentrations"));
		}

		setConcentrationStates(dimensions, states, concentrations, concentrationStates);
		return concentrationStates;

	}

	private void setConcentrationStates(int dimensions, Object[] states, List<Double> concentrations,
			List<String> concentrationStates) {
		for (int i = 0; i < dimensions * dimensions; i++) {
			String nextState = null;
			Double random;
			int counter = 0;
			while (nextState == null) {
				random = Math.random();
				if (random <= concentrations.get(counter)) {
					nextState = (String) states[counter];
				} else {
					if (counter == states.length - 1) {
						counter = 0;
					} else {
						counter++;
					}
				}
			}
			concentrationStates.add(nextState);
		}
	}
}
