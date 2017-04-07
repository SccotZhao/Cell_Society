package grid;

import java.util.ArrayList;
import java.util.List;

import cellpackage.Cell;

/**
 * The grid class holds the Cells in order of their arrangement on the screen
 * for their respective simulation. Additionally, the class maintains the size
 * of the simulation, in terms of number of Cells per side of the simulation,
 * the name of the simulation, and the states of the Cells in the simulation.
 * 
 * @author Kyle Finke
 *
 */
public class Grid {

	private int dimensions;
	private String simulation;
	private String cellShape = "triangle";
	private String neighbors = "sides";
	private ArrayList<Cell> cellGrid = new ArrayList<Cell>();

	/**
	 * Public constructor of Grid based on the Grids size, the states of the
	 * Cells that will be contained in the Grid, and the type of simulation
	 * using the Grid.
	 * 
	 * @param length
	 *            of sides of the Grid
	 * @param states
	 *            List of states used to construct Cells in Grid
	 * @param sim
	 *            type of simulation using the Grid
	 */
	public Grid(int length, List<String> states, String sim, String shape, String neighborHandling) {
		dimensions = length;
		simulation = sim;
		cellShape = shape;
		neighbors = neighborHandling;
		for (int row = 0; row < dimensions; row++) {
			for (int column = 0; column < dimensions; column++) {
				cellGrid.add(new Cell(row, column, states.get(row * dimensions + column)));
			}
		}
	}
	
	public Grid(int length, List<String> states, String sim, String shape){
		dimensions = length;
		simulation = sim;
		for (int row = 0; row < dimensions; row++) {
			for (int column = 0; column < dimensions; column++) {
				cellGrid.add(new Cell(row, column, states.get(row * dimensions + column)));
			}
		}		
	}

	private Grid(int length, ArrayList<Cell> cells, String sim) {
		dimensions = length;
		cellGrid = new ArrayList<Cell>(cells);
		simulation = sim;
	}

	/**
	 * Provides access to individual Cells in the Grid based on their row and
	 * column as is displayed in the UserInterface
	 * 
	 * @param row
	 *            in which Cell is displayed to user
	 * @param column
	 *            in which Cell is displayed to user
	 * @return Cell object at specified location in the Grid
	 */
	public Cell getCell(int row, int column) {
		return cellGrid.get(row * dimensions + column);
	}

	/**
	 * 
	 * @return Length of sides of the Grid (all sides same length since grid is
	 *         a square)
	 */
	public int getSize() {
		return dimensions;
	}

	/**
	 * 
	 * @return String of type of simulation
	 */
	public String getSimulation() {
		return simulation;
	}

	/**
	 * Clones a Grid so that the new Grid object does not reference the same
	 * places in memory for any of its elements
	 * 
	 * @return Grid copy of Grid on which the method is called
	 * 
	 */
	@Override
	public Grid clone() {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for (Cell c : cellGrid) {
			cells.add(new Cell(c.getX(), c.getY(), c.getState()));
		}
		return new Grid(dimensions, cells, simulation);
	}
	
	@Override
	public String toString(){
		String statesList = new String();
		for(Cell c:cellGrid){
			statesList += c.getState() + " ";
		}
		return statesList;
	}
	
	public String getCellShape(){
		return cellShape;
	}
	
	public void setCellShape(String shape){
		cellShape = shape;
	}
	
	public String getNeighborHandling(){
		return neighbors;
	}
	
	public void setNeighborHandling(String neighborHandling){
		neighbors = neighborHandling;
	}

}
