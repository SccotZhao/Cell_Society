package XML;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/*
 * @author Kyle Finke
 */
public class StyleSheetParser extends XMLReader{
	public static final String TYPE_ATTRIBUTE = "type";
	public static final String LAYOUT_ATTRIBUTE = "layout";
	public static final String XML_RESOURCES = "resources/XMLErrors";
	private String SIMULATION_FILE_TYPE = "Cell";
	private String STYLE_SHEET_FILE_TYPE = "styleSheet";

	public static final List<String> SIMULATION_FIELDS = Arrays.asList(new String[] { "name", "dimensions", "states" });
	public static final List<String> STYLE_SHEET_FIELDS = Arrays
			.asList(new String[] { "cellShape", "cellSize", "considerNeighbors" });

	private String simulation;
	private int dimensions;
	private int cellDimensions;
	private String neighborHandling;
	private String cellShapes;
	private List<String> states = new ArrayList<String>();
	public static final ResourceBundle errors = ResourceBundle.getBundle(XML_RESOURCES);

	public StyleSheetParser(File cellFile) {
		super(cellFile);
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
					neighborHandling = "fullSide";
				}
				break;
			}
		} else {
			throw new XMLException(errors.getString("invalidTag"), nodeList);

		}
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
