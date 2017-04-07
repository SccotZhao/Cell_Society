// This entire file is part of my masterpiece.
// Zhiyong Zhao


package UserPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * This class is used for managing shape for the simulations.
 * Possible choices are Triangle and Rectangle
 * @author Zhiyong
 *
 */
public class ShapeManager {
	
	public static final String PACKAGE_FOR_COLOR = "resources/Color";
	public static final double WIDTH = 800;
	
	private ResourceBundle colorResources;
	//hold all the shapes of simulation
	private List<Polygon> shapeList;
	
	public ShapeManager(){
		colorResources = ResourceBundle.getBundle(PACKAGE_FOR_COLOR);
		shapeList = new ArrayList<>();
	}
	
	public void setHex(int dim, List<String> states) {
		
	}
	//triangle shape set up
	public void setTri(int dim, List<String> states) {
		double sizeOfTri = (WIDTH - 400) / dim;
		double x = 0;
		double y = 0;
		for(int r = 0; r < dim; r++){
			for(int c = 0; c < dim; c++){
				if(c % 2 == 0){
				 x = r * sizeOfTri + 200;
				 y = c * .5 * sizeOfTri + 100;
				}
				if(c % 2 == 0){
				//the first triangle in the rectangle at(r,c)
				addTri(dim, states, sizeOfTri, r, c, x, y);
				}else{
				//the second triangle in the rectangle at (r,c)
				addTri(dim, states, -sizeOfTri, r, c, x + sizeOfTri, y + sizeOfTri);
				}
			}
		}
		
	}

	private void addTri(int dim, List<String> states, double sizeOfTri, int r, int c, double x, double y) {
		Polygon tri = new Polygon();				
		//add the three points of a triangle
		tri.getPoints().addAll(x, y, x + sizeOfTri, y, x, y + sizeOfTri);				
		tri.setFill(Color.web(colorResources.getString(states.get(dim * r + c))));
		tri.setStroke(Color.WHITE);
		tri.setStrokeWidth(1);
		shapeList.add(tri);
		
	}

	//rectangle shape set up
	public void setRec(int dim, List<String> states){
		double sizeOfRec = (WIDTH - 400) / dim;
		for(int r =0; r < dim; r++){
			for(int c =0; c < dim; c++){
				//rectangle is constructed from Polygon, which can be used as a return type for all shapes
				Polygon rec = new Polygon();
				double x = r * sizeOfRec +200;
				double y = c * sizeOfRec + 20;
				rec.getPoints().addAll(x, y, x + sizeOfRec, y, x + sizeOfRec, y + sizeOfRec, x, y + sizeOfRec);
				String state = states.get(dim * r + c);			
				rec.setFill(Color.web(colorResources.getString(state)));
				rec.setStrokeWidth(1);
				rec.setStroke(Color.WHITE);
				shapeList.add(rec);

			}
		}
	}
	
	public List<Polygon> getShapeList(){
		return shapeList;
	}

}
