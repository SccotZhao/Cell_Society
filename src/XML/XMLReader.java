package XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * This class reads XML files for different Cell Society simulations. It store
 * the information read and allows the user to access it to create their
 * simulations. It can also read in StyleSheet files to determine the style of
 * the simulations.
 *
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 * @author Kyle Finke
 */
public class XMLReader {
	public static final String TYPE_ATTRIBUTE = "type";
	public static final String LAYOUT_ATTRIBUTE = "layout";
	public static final String XML_RESOURCES = "resources/XMLErrors";
	private String SIMULATION_FILE_TYPE = "Cell";
	private String STYLE_SHEET_FILE_TYPE = "styleSheet";
	public static final List<String> SIMULATION_FIELDS = Arrays.asList(new String[] { "name", "dimensions", "states" });
	public static final List<String> STYLE_SHEET_FIELDS = Arrays
			.asList(new String[] { "cellShape", "cellSize", "considerNeighbors" });
	private static final DocumentBuilder DOCUMENT_BUILDER = getDocumentBuilder();
	private String simulation;
	private int dimensions;
	private int cellDimensions;
	private String neighborHandling;
	private String cellShapes;
	private List<String> states = new ArrayList<String>();
	public static final ResourceBundle errors = ResourceBundle.getBundle(XML_RESOURCES);

	public XMLReader(File cellFile) {
		readXMLFile(cellFile);
	}

	/**
	 * Read information contained in XMLFile.
	 */
	public void readXMLFile(File dataFile) {
		Element root = getRootElement(dataFile);
		if (isSimulationFile(root, SIMULATION_FILE_TYPE)) {
			for (String field : SIMULATION_FIELDS) {
				getSimulationValues(root, field);
			}
		} else if (isStyleSheetFile(root, STYLE_SHEET_FILE_TYPE)) {
			for (String field : STYLE_SHEET_FIELDS) {
				getStyleSheetValues(root, field);
			}
		} else {
			throw new XMLException(errors.getString("invalidFileType"));
		}
	}

	private void getStyleSheetValues(Element e, String tagName) {
		NodeList nodeList = e.getElementsByTagName(tagName);
		if (nodeList != null && nodeList.getLength() > 0) {
			switch (tagName) {
			case "cellShape":
				try {
					cellShapes = nodeList.item(0).getTextContent();
				} catch (Exception ee) {
					cellShapes = "square";
				}
				break;
			case "cellSize":
				try {
					if (Integer.parseInt(nodeList.item(0).getTextContent()) > 0) {
						cellDimensions = Integer.parseInt(nodeList.item(0).getTextContent());
					} else {
						cellDimensions = 10;
					}
				} catch (NumberFormatException ee) {
					throw new NumberFormatException(errors.getString("notIntDimension"));
				}
				break;
			case "considerNeighbors":
				try {
					neighborHandling = nodeList.item(0).getTextContent();
				} catch (Exception ee) {
					neighborHandling = "sides";
				}
				break;
			}
		} else {
			throw new XMLException(errors.getString("invalidTag"), nodeList);

		}
	}

	private boolean isStyleSheetFile(Element root, String type) {
		return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
	}

	private Element getRootElement(File xmlFile) {
		try {
			DOCUMENT_BUILDER.reset();
			Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
			return xmlDocument.getDocumentElement();
		} catch (SAXException | IOException e) {
			throw new XMLException(e);
		}
	}

	private boolean isSimulationFile(Element root, String type) {
		return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
	}

	protected String getAttribute(Element e, String attributeName) {
		return e.getAttribute(attributeName);
	}

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
		try {
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
		} catch (Exception ee) {
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

	protected SimulationFile fileType(String simulation) {
		SimulationFile file = null;
		switch (simulation) {
		case "GameOfLife":
			file = new GameOfLifeFile();
			break;
		case "PredatorPrey":
			file = new PredatorPreyFile();
			break;
		case "Segregation":
			file = new SegregationFile();
			break;
		case "Fire":
			file = new FireFile();
			break;
		case "Sugar":
			file = new SugarFile();
			break;
		}

		if (file == null) {
			throw new XMLException(errors.getString("invalidSimulation"));
		}

		return file;
	}

	private static DocumentBuilder getDocumentBuilder() {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new XMLException(e);
		}
	}


	public int getDimensions() {
		return dimensions;
	}


	public String getSimulation() {
		return simulation;
	}


	public List<String> getStates() {
		return states;
	}

	public String getNeighborHandling() {
		return neighborHandling;
	}

	public int getCellSize() {
		return cellDimensions;
	}

	public String getCellShape() {
		return cellShapes;
	}
}