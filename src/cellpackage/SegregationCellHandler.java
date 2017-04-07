package cellpackage;

import java.util.Random;
import grid.Grid;

/**
 * 
 * This class is the designated object that can manage the Cells for the
 * segregation simulation, and is the way in which the states of the cells
 * interact with the grid. This is purposed to protect the states of the cells,
 * and hide the logic, the result of which is passed to the simulation's grid.
 * 
 * @author Alexander Zapata
 *
 */

public class SegregationCellHandler extends CellHandler {

	public SegregationCellHandler(Grid g) {
		this.setGrid(g);
		this.getMyTool().setGrid(g);
	}

	private boolean isOpen(int xLocation, int yLocation) {
		if (inGrid(xLocation, yLocation)) {
			return !this.getGrid().getCell(xLocation, yLocation).getState().equals("white");
		}
		return false;
	}

	public int getNeighbors(Cell c) {
		NeighborList myNeighbors = new NeighborList();
		myNeighbors = getMyNeighbors(c, false, "surrounding");
		int neighbors = myNeighbors.size();
		for(int i = 0; i<myNeighbors.size(); i++){
			if(!isOpen(myNeighbors.getX(i), myNeighbors.getY(i))){
				neighbors--;
			}
		}
		return neighbors;
	}
	
	private boolean isSameState(int xLocation, int yLocation, Cell c){
		if (inGrid(xLocation, yLocation)) {
			return this.getGrid().getCell(xLocation, yLocation).getState().equals(c.getState());
		}
		return false;
	}

	private int getSameNeighbors(Cell c) {
			NeighborList myNeighbors = new NeighborList();
			myNeighbors = getMyNeighbors(c, false, "surrounding");
			int neighbors = 0;
			for(int i = 0; i<myNeighbors.size(); i++){
				if(isSameState(myNeighbors.getX(i), myNeighbors.getY(i), c)){
					neighbors++;
				}
			}
			return neighbors;
		}

	public boolean doesChange(Cell c) {
		if (getSameNeighbors(c) == 0)
			return true;
		if (c != null && ((double) getSameNeighbors(c) / (double) getNeighbors(c)) <= .60)
			return true;
		return false;
	}

	@Override
	public void switchState(Cell c, Grid g) {
		Random rand = new Random();
		int changedCheck = 0;
		if (c != null && doesChange(c)) {
			while (changedCheck == 0) {
				int i = rand.nextInt(g.getSize());
				int j = rand.nextInt(g.getSize());
				if (g.getCell(i, j).getState().equals("white")) {
					g.getCell(i, j).setState(c.getState());
					c.setState("white");
					changedCheck++;
				}
			}
		}
	}

}
