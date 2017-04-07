package UserPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Draw {
	private Map<String, List<Double>> percentage;
	
	public Draw(Map<String, List<Double>> p){
		percentage = p;	
	}
	
	//single path from a list of points
	public Path getPath( List<Double> points){
		Path path = new Path();
				
		
		
		for(int i = 0; i < points.size() - 1; i++){
			MoveTo moveTo = new MoveTo();
			moveTo.setX(100*points.get(i) +650);
			moveTo.setY(10 * i + 50);
			path.getElements().add(moveTo);
			
			
			LineTo lineTo = new LineTo();
			lineTo.setX(100*points.get(i + 1) +650);
			lineTo.setY(10 * (i + 1) + 50);
			path.getElements().add(lineTo);
		}
		return path;
	}
	
	//the paths of all the states
	public List<Path> getPath(){
		List<Path> allPath = new ArrayList<>();
		for(String state : percentage.keySet()){
			allPath.add(getPath(percentage.get(state)));			
		}
		return allPath;
	}

}
