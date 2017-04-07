package cellpackage;

import java.util.Random;
import grid.Grid;

/**
 * 
 * This class is the designated object that can manage the Cells for the
 * sugarscape simulation, and is the way in which the states of the cells
 * interact with the grid. This is purposed to protect the states of the cells,
 * and hide the logic, the result of which is passed to the simulation's grid.
 * 
 * @author Alexander Zapata
 *
 */

public class SugarCellHandler extends CellHandler {
	/**
	 * Constructor for the Sugar CellHandler.
	 * 
	 * @param Grid
	 *            to be used to get cells.
	 */
	public SugarCellHandler(Grid g) {
		this.setGrid(g);
		this.getMyTool().setGrid(g);
	}

	private boolean isHighSugar(int xLocation, int yLocation) {
		if (inGrid(xLocation, yLocation)) {
			return this.getGrid().getCell(xLocation, yLocation).getState().equals("high");
		}
		return false;
	}
	/**
	 *  This will just determine if there is a burning tree next to an alive tree. If so, it will find how many of
 	 *	the trees are burning so that this number can later be accounted for
 	 *	during checking for probCatch.
	 * 
	 * @param Cell
	 *            the cell in question
	 * @return number of neighbors with high sugar.
	 */
	public int getNeighbors(Cell c) {
		
		NeighborList myNeighbors = new NeighborList();
		myNeighbors = getMyNeighbors(c, false, "sugar");
		int neighbors = 0;
		for (int i = 0; i < myNeighbors.size(); i++) {
			if (isHighSugar(myNeighbors.getX(i), myNeighbors.getY(i))) {
				neighbors++;
			}
		}
		return neighbors;
	}
	/**
	 * Determines that agents move and low cells advance.
	 * 
	 * @param Cell
	 *            the cell in question
	 * @return boolean to see if changes
	 */
	public boolean doesChange(Cell c) {
		if (c != null && (c.getState().equals("agent")) || (c.getState().equals("low")))
			return true;
		return false;
	}
	/**
	 * Advances cells.
	 * 
	 * @param Cell
	 *            the cell in question
	 * @param Grid
	 * 			  grid that contains cell
	 */
	public void switchState(Cell c, Grid g) {
		if (c != null) {
			Random rand = new Random();
			int randomNumber;
			NeighborList goHere = new NeighborList();
			goHere = getHighNeighbors(c);
			if (goHere.size() > 0) {
				randomNumber = rand.nextInt(goHere.size());
				if (c.getState().equals("agent")) {
					g.getCell(goHere.getX(randomNumber), goHere.getY(randomNumber)).setState("agent");
					c.setState("low");
				}
			}
			if (c.getState().equals("low") && rand.nextInt(10) > 5) {
				c.setState("high");
			}
		}
	}

	private NeighborList getHighNeighbors(Cell c) {
		NeighborList myNeighbors = new NeighborList();
		NeighborList highNeighbors = new NeighborList();
		myNeighbors = getMyNeighbors(c, false, "sugar");
		for (int i = 0; i < myNeighbors.size(); i++) {
			if (isHighSugar(myNeighbors.getX(i), myNeighbors.getY(i))) {
				int[] yes = new int[2];
				yes[0] = myNeighbors.getX(i);
				yes[1] = myNeighbors.getY(i);
				highNeighbors.add(yes);
			}
		}
		return highNeighbors;
	}

}