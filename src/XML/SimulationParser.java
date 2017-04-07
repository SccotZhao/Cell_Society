package XML;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SimulationParser extends XMLReader{
	public SimulationParser(File cellFile) {
		super(cellFile);
	}

	private String simulation;
	private int dimensions;
	private List<String> states = new ArrayList<String>();
	
	
	private void getSimulationValues(Element e, String tagName) {
		NodeList nodeList = e.getElementsByTagName(tagName);
		if (nodeList != null && nodeList.getLength() > 0) {
			switch (tagName) {
			case "name":
				try {
					simulation = nodeList.item(0).getTextContent();
				} catch (Exception ee) {
					throw new XMLException(errors.getString("noSimulation"));
				}
				break;
			case "dimensions":
				try {
					if (Integer.parseInt(nodeList.item(0).getTextContent()) > 0) {
						dimensions = Integer.parseInt(nodeList.item(0).getTextContent());
					} else {
						throw new XMLException(errors.getString("negativeDimension"));
					}
				} catch (NumberFormatException ee) {
					throw new NumberFormatException(errors.getString("notIntDimension"));
				}
				break;
			case "states":
				setCellLayout(e, nodeList);
				break;
			}
		} else {
			throw new XMLException(errors.getString("invalidTag"), nodeList);

		}
	}

	private void setCellLayout(Element e, NodeList nodeList) {
		String s = nodeList.item(0).getTextContent();
		try{
		switch (getAttribute(e, LAYOUT_ATTRIBUTE)) {
		case "specific":
			specificLayout(s);
			break;
		case "random":
			randomLayout();
			break;
		case "concentration":
			concentrationLayout(s);
			break;
		}
		}catch(Exception ee){
			throw new XMLException(errors.getString("invalidLayoutMethod"));
		}
	}

	private void concentrationLayout(String stateConcentrations) {
		Scanner scan = new Scanner(stateConcentrations);
		SimulationFile file = fileType(simulation);
			states = file.concentrationLayout(scan, dimensions);
	}

	private void randomLayout() {
		SimulationFile file = fileType(simulation);
			states = file.randomLayout(dimensions);
	}

	private void specificLayout(String statesList) {
		Scanner scan = new Scanner(statesList);
		SimulationFile file = fileType(simulation);
		states = file.readStates(scan);
	}

	/**
	 * @return dimensions of Grid to be created
	 */
	public int getDimensions() {
		return dimensions;
	}

	/**
	 * @return simulation type to be displayed
	 */
	public String getSimulation() {
		return simulation;
	}

	/**
	 * @return states of Cells used to construct simulation
	 */
	public List<String> getStates() {
		return states;
	}
}
