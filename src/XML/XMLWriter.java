package XML;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import grid.Grid;

/*Source code from https://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/.
 * 
 * The XMLWriter class allows users to save Cell Society simulations based on the
 * current Cell states and locations by passing the Grid to this class, which
 * saves the layout into an XML file that is named based on the name of the simulation
 * and the current time in order to avoid overlap between saving different layouts of the
 * same simulation.
 * 
 * @author Kyle Finke
 * 
 */
public class XMLWriter {

	public static void createXML(Grid g) throws ParserConfigurationException, TransformerException {

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("simulation");
			doc.appendChild(rootElement);

			addXMLElements(g, timeStamp, doc, rootElement);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(
					new File("data/" + g.getSimulation() + "/" + g.getSimulation() + timeStamp + ".xml"));

			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			throw new ParserConfigurationException();
		} catch (TransformerException tfe) {
			throw new TransformerException(tfe);
		}
	}

	private static void addXMLElements(Grid g, String timeStamp, Document doc, Element rootElement) {
		rootElement.setAttribute("type", "Cell");
		rootElement.setAttribute("layout", "specific");

		Element name = doc.createElement("name");
		name.appendChild(doc.createTextNode(g.getSimulation()));
		rootElement.appendChild(name);

		Element title = doc.createElement("title");
		title.appendChild(doc.createTextNode(g.getSimulation() + timeStamp));
		rootElement.appendChild(title);

		Element author = doc.createElement("author");
		author.appendChild(doc.createTextNode("unknown"));
		rootElement.appendChild(author);

		Element dimensions = doc.createElement("dimensions");
		dimensions.appendChild(doc.createTextNode("" + g.getSize()));
		rootElement.appendChild(dimensions);

		Element states = doc.createElement("states");
		states.appendChild(doc.createTextNode(g.toString()));
		rootElement.appendChild(states);
	}
}