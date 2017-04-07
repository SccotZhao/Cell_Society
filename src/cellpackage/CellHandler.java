package cellpackage;

import grid.Grid;

/**
 * 
 * This class is the Super-Class of each of the specific CellHandler classes.
 * Each one of those classes manipulate their cells in the way that is
 * representative of the respective simulation.
 * 
 * @author Alexander Zapata
 *
 */
public abstract class CellHandler {
	private Grid grid;
	private NeighborTool myTool;

	public CellHandler(Grid g) {
		setGrid(g);
		myTool = new NeighborTool(g);
	}

	public CellHandler() {
		this(null);
	}

	public abstract int getNeighbors(Cell c);

	public abstract boolean doesChange(Cell c);

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public abstract void switchState(Cell c, Grid g);

	/**
	 * Updates a new grid based on old grid states and then passes a new grid back.
	 * 
	 * @return new and improved grid.
	 */
	public Grid updateGrid() {
		Grid oldGrid = this.getGrid();
		Grid newGrid = oldGrid.clone();
			for (int i = 0; i < oldGrid.getSize(); i++) {
				for (int j = 0; j < oldGrid.getSize(); j++) {
					if (doesChange(oldGrid.getCell(i, j))) {
						switchState(newGrid.getCell(i, j), newGrid);
					}
				}
			}
		grid = newGrid;
		getMyTool().setGrid(grid);
		return newGrid;
	}

	/**
	 * Checks if valid element.
	 * 
	 * @param Locations
	 * @return boolean to see if valid
	 */
	public boolean inGrid(int xLocation, int yLocation) {
		if (getGrid().getCellShape().equals("triangle")) {
			if (xLocation < getGrid().getSize() && xLocation >= 0 && yLocation < getGrid().getSize()
					&& yLocation >= 0) {
				return isValidCell(xLocation, yLocation);
			}
		} else {
			if (xLocation < getGrid().getSize() && xLocation >= 0 && yLocation < getGrid().getSize()
					&& yLocation >= 0) {
				return isValidCell(xLocation, yLocation);
			}
		}
		return false;

	}

	private boolean isValidCell(int xLocation, int yLocation) {
		return this.getGrid().getCell(xLocation, yLocation) != null;
	}

	public boolean isType(int xLocation, int yLocation, String type) {
		if (inGrid(xLocation, yLocation)) {
			return this.getGrid().getCell(xLocation, yLocation).getState().equals(type);
		}
		return false;
	}

	public NeighborTool getMyTool() {
		return this.myTool;
	}

	/**
	 * Decides what neighbortool method helps us.
	 * 
	 * @param Cell
	 *            the cell in question
	 * @return NeighborList
	 */
	public NeighborList getMyNeighbors(Cell c, boolean isTorus, String neighborHandling) {
		if (getGrid().getCellShape().equals("rectangle") && neighborHandling.equals("sugar"))
			return this.getMyTool().getSugarNeighborList(c, isTorus);
		if (getGrid().getCellShape().equals("rectangle") && neighborHandling.equals("surrounding"))
			return this.getMyTool().getRectSurroundingNeighborList(c, isTorus);
		if (getGrid().getCellShape().equals("rectangle") && neighborHandling.equals("side"))
			return this.getMyTool().getRectSideNeighborList(c, isTorus);
		if (getGrid().getCellShape().equals("triangle") && neighborHandling.equals("surrounding"))
			return this.getMyTool().getTriSurroundingNeighborList(c, isTorus);
		if (getGrid().getCellShape().equals("triangle") && neighborHandling.equals("side"))
			return this.getMyTool().getTriSideNeighborList(c, isTorus);
		return null;
	}
}