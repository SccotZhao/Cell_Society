package UserPackage;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import XML.XMLWriter;
import grid.Grid;

public class SaveXML {
	private Grid grid;
	
	public SaveXML(UserInterface u){
		grid = new Grid(u.getDimension(), u.getStateList(), u.getSimName(), "triangle");
	}
	public void Save() throws ParserConfigurationException, TransformerException
	{
		XMLWriter.createXML(grid);
	}
}
