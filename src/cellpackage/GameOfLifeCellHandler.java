package cellpackage;
import grid.Grid;
/**
 * 
 * This class is the designated object that can manage the Cells for the Game of
 * Life simulation, and is the way in which the states of the cells interact
 * with the grid. This is purposed to protect the states of the cells, and hide
 * the logic, the result of which is passed to the simulation's grid.
 * 
 * @author Alexander Zapata
 *
 */

public class GameOfLifeCellHandler extends CellHandler {
	/**
	 * Constructor for the Game of Life CellHandler.
	 * 
	 * @param Grid
	 *            in which Cell is displayed to user
	 */
	public GameOfLifeCellHandler(Grid g) {
		this.setGrid(g);
		this.getMyTool().setGrid(g);
	}

	private boolean isAlive(int xLocation, int yLocation) {
		if (inGrid(xLocation, yLocation)) {
			return this.getGrid().getCell(xLocation, yLocation).getState().equals("alive");
		}
		return false;
	}
	
	/**
	 * Method that returns the surrounding neighbors of a particular cell.
	 * 
	 * @param Cell
	 *            the cell to get the neighbors of.
	 * @return the number of neighbors surrounding the cell that are alive.
	 */
	@Override
	public int getNeighbors(Cell c) {
		NeighborList myNeighbors = new NeighborList();
		myNeighbors = getMyNeighbors(c, false, "surrounding");
		int neighbors = 0;
		for(int i = 0; i<myNeighbors.size(); i++){
			if(isAlive(myNeighbors.getX(i), myNeighbors.getY(i))){
				neighbors++;
			}
		}
		return neighbors;
	}
	/**
	 * Method that determines by the amount of neighbors if the cell changes state.
	 * 
	 * @param Cell
	 *            to be manipulated
	 * @return True or False depending if Cell should changes.
	 */
	@Override
	public boolean doesChange(Cell c) {
		int neighborNumber = getNeighbors(c);
		if (c != null) {
			if ((neighborNumber < 2 || neighborNumber > 3) && c.getState().equals("alive"))
				return true;
			if (neighborNumber == 3 && c.getState().equals("dead"))
				return true;
		}
		return false;
	}
	/**
	 * Method that actually switches the state of the cell.
	 * 
	 * @param Cell
	 *            to be manipulated.
	 */
	@Override
	public void switchState(Cell c, Grid g) {
		if (c != null) {
			if (c.getState().equals("alive")) {
				c.setState("dead");
			} else if (c.getState().equals("dead")) {
				c.setState("alive");
			}
		}
	}
}