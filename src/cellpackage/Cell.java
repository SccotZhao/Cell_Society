package cellpackage;

/**
 * 
 * This class is the cell class that is the object in which we manipulate throughout the simulation.
 * @author Alexander Zapata
 * 
 */

public class Cell {

	private int grid_X;
	private int grid_Y;
	private String state;
	private int timeSteps;
	
	public Cell(int x, int y, String stateType){
		grid_X = x;
		grid_Y = y;
		state = stateType;
		timeSteps = 0;
	}
	
	public int getX(){
		return this.grid_X;
	}
	
	public int getY(){
		return this.grid_Y;
	}
	
	public void setX(int x){
		this.grid_X = x;
	}
	
	public void setY(int y){
		this.grid_Y = y;
	}
	
	public void setLocation(int newXLoc, int newYLoc){
		this.grid_X = newXLoc;
		this.grid_Y = newYLoc;
	}
	
	public String getState(){
		return this.state;
	}
	
	public void setState(String newState){
		state = newState;
	}
	
	/**
	 * TimeStep handler for pred-prey and Sugar.
	 */
	public void updateTimeSteps(){
		timeSteps++;
	}
	
	public void setTimeSteps(int steps){
		timeSteps = steps;
	}
	
	public int getTimeSteps(){
		return timeSteps;
	}
	
}
