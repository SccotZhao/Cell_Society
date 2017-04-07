package cellpackage;

import java.util.Random;
import grid.Grid;

/**
 * 
 * This class is the designated object that can manage the Cells for the fire simulation, and is the way in which the 
 * states of the cells interact with the grid. This is purposed to protect the states of the cells, and hide the logic, the 
 * result of which is passed to the simulation's grid.
 * @author Alexander Zapata
 *
 */

public class FireCellHandler extends CellHandler{

	public FireCellHandler(Grid g){
		this.setGrid(g);
		this.getMyTool().setGrid(g);
	}

	private boolean isFire(int xLocation, int yLocation) {
		if (inGrid(xLocation, yLocation)) {
			return this.getGrid().getCell(xLocation, yLocation).getState().equals("fire");
		}
		return false;
	}
	
	//@Override
	public int getNeighbors(Cell c) {
	//This will just determine if there is a burning tree next to an alive tree. If so, it will find how many of
	//the trees are burning so that this number can later be accounted for during checking for probCatch.
		NeighborList myNeighbors = new NeighborList();
		myNeighbors = getMyNeighbors(c, false, "side");
		int neighbors = 0;
		for(int i = 0; i<myNeighbors.size(); i++){
			if(isFire(myNeighbors.getX(i), myNeighbors.getY(i))){
				neighbors++;
			}
		}
		return neighbors;
	}

	//@Override
	public boolean doesChange(Cell c) {
		if(c != null && c.getState().equals("fire")) return true;
		Random rand = new Random();
		double probCatch = 0.55;
		if(c.getState().equals("tree")){
		for(int i = 0; i < getNeighbors(c); i++){
			if(rand.nextDouble() <= probCatch) return true;
		}
		}
		return false;
	}

	//@Override
	public void switchState(Cell c, Grid g) {
		if(c != null){
		if(c != null && c.getState().equals("fire")){
			c.setState("dead");
		}else if(c != null && c.getState().equals("tree")){
			c.setState("fire");
	   }
	 }
   }
}
