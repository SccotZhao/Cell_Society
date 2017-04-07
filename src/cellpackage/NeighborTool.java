package cellpackage;

import java.util.ArrayList;

import grid.Grid;

public class NeighborTool {

	private Grid grid;
	// private String shape;

	public NeighborTool(Grid g) {
		grid = g;
		// shape = grid.getCellShape();
	}

	/**
	 * Gets surrounding neighbors for rectangle simulations.
	 * 
	 * @param Cell
	 *            the cell in question
	 * @param isTorus
	 * 			  is the map a torus or not.
	 * @return NeighborList
	 */
	public NeighborList getRectSurroundingNeighborList(Cell c, boolean isTorus) {
		NeighborList neighbors = new NeighborList();
		if (c != null) {
			int[] yes;
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (i == 0 && j == 0)
						continue;
					if (isTorus && !inGrid(c.getX() + i, c.getY() + j)) {
						rectangleTorusHandler(neighbors, c.getX() + i, c.getY() + j);
					}
					if (inGrid(c.getX() + i, c.getY() + j)
							&& this.getGrid().getCell(c.getX() + i, c.getY() + j) != null) {
						yes = new int[2];
						yes[0] = c.getX() + i;
						yes[1] = c.getY() + j;
						neighbors.add(yes);
					}
				}
			}
		}
		return neighbors;
	}
	/**
	 * Gets side neighbors for rectangle simulations.
	 * 
	 * @param Cell
	 *            the cell in question
	 * @param isTorus
	 * 			  is the map a torus or not.
	 * @return NeighborList
	 */
	public NeighborList getRectSideNeighborList(Cell c, boolean isTorus) {
		NeighborList neighbors = new NeighborList();
		if (c != null) {
			int[] yes;
			for (int i = -1; i < 2; i++) {
				if (i == 0)
					continue;
				if (isTorus && !inGrid(c.getX() + i, c.getY() + i)) {
					rectangleTorusHandler(neighbors, c.getX() + i, c.getY() + i);
					continue;
				}
				if (inGrid(c.getX() + i, c.getY()) && this.getGrid().getCell(c.getX() + i, c.getY()) != null) {
					yes = new int[2];
					yes[0] = c.getX() + i;
					yes[1] = c.getY();
					neighbors.add(yes);
				}
				if (inGrid(c.getX(), c.getY() + i) && this.getGrid().getCell(c.getX(), c.getY() + i) != null) {
					yes = new int[2];
					yes[0] = c.getX();
					yes[1] = c.getY() + i;
					neighbors.add(yes);
				}
			}
		}
		return neighbors;
	}
	/**
	 * Gets surrounding neighbors for triangle simulations.
	 * 
	 * @param Cell
	 *            the cell in question
	 * @param isTorus
	 * 			  is the map a torus or not.
	 * @return NeighborList
	 */
	public NeighborList getTriSurroundingNeighborList(Cell c, boolean isTorus) {
		NeighborList neighbors = new NeighborList();
		if (c != null) {
			int[] yes;
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (i == 0 && j == 0)
						continue;
					if (isTorus && !inGrid(c.getX() + i, c.getY() + j)) {
						triangleTorusHandler(neighbors, c.getX() + i, c.getY() + j);
					}
					if (inGrid(c.getX() + i, c.getY() + j)
							&& this.getGrid().getCell(c.getX() + i, c.getY() + j) != null) {
						yes = new int[2];
						yes[0] = c.getX() + i;
						yes[1] = c.getY() + j;
						neighbors.add(yes);
					}
				}
			}
		}
		return neighbors;
	}
	/**
	 * Gets side neighbors for triangle simulations.
	 * 
	 * @param Cell
	 *            the cell in question
	 * @param isTorus
	 * 			  is the map a torus or not.
	 * @return NeighborList
	 */
	public NeighborList getTriSideNeighborList(Cell c, boolean isTorus) {
		NeighborList neighbors = new NeighborList();
		if (c != null) {
			int[] yes;
			if (inGrid(c.getX() + 1, c.getY()) && this.getGrid().getCell(c.getX() + 1, c.getY()) != null) {
				yes = new int[2];
				yes[0] = c.getX() + 1;
				yes[1] = c.getY();
				neighbors.add(yes);
			} else if (isTorus) {
				triangleTorusHandler(neighbors, c.getX() + 1, c.getY());
			}
			if (inGrid(c.getX() - 1, c.getY()) && this.getGrid().getCell(c.getX() - 1, c.getY()) != null) {
				yes = new int[2];
				yes[0] = c.getX() - 1;
				yes[1] = c.getY();
				neighbors.add(yes);
			} else if (isTorus) {
				triangleTorusHandler(neighbors, c.getX() - 1, c.getY());
			}
			if (c.getX() % 2 == 1) {
				if (inGrid(c.getX() + 1, c.getY() - 1) && this.getGrid().getCell(c.getX() + 1, c.getY() - 1) != null) {
					yes = new int[2];
					yes[0] = c.getX() + 1;
					yes[1] = c.getY() - 1;
					neighbors.add(yes);
				} else if (isTorus) {
					triangleTorusHandler(neighbors, c.getX() + 1, c.getY() - 1);
				}
			} else {
				if (inGrid(c.getX() - 1, c.getY() + 1) && this.getGrid().getCell(c.getX() - 1, c.getY() + 1) != null) {
					yes = new int[2];
					yes[0] = c.getX() - 1;
					yes[1] = c.getY() + 1;
					neighbors.add(yes);
				} else if (isTorus) {
					triangleTorusHandler(neighbors, c.getX() - 1, c.getY() + 1);
				}
			}
		}
		return neighbors;
	}

	public void setGrid(Grid g) {
		this.grid = g;
	}

	public Grid getGrid() {
		return grid;
	}

	private boolean inGrid(int xLocation, int yLocation) {
		if (xLocation < getGrid().getSize() && xLocation >= 0 && yLocation < getGrid().getSize() && yLocation >= 0) {
			return true;
		}
		return false;
	}

	private void rectangleTorusHandler(NeighborList neighbors, int i, int j) {
		int[] yes;
		if (i < 0) {
			yes = new int[2];
			yes[0] = this.getGrid().getSize() - 1;
			yes[1] = j;
			neighbors.add(yes);
		}
		if (i > this.grid.getSize()) {
			yes = new int[2];
			yes[0] = 0;
			yes[1] = j;
			neighbors.add(yes);
		}
		if (j < 0) {
			yes = new int[2];
			yes[0] = i;
			yes[1] = this.getGrid().getSize() - 1;
			neighbors.add(yes);
		}
		if (j > this.grid.getSize()) {
			yes = new int[2];
			yes[0] = i;
			yes[1] = 0;
			neighbors.add(yes);
		}
	}

	private void triangleTorusHandler(NeighborList neighbors, int i, int j) {
		int[] yes;
		if (i < 0) {
			yes = new int[2];
			yes[0] = this.getGrid().getSize() - 1;
			yes[1] = j;
			neighbors.add(yes);
		}
		if (i > this.grid.getSize()) {
			yes = new int[2];
			yes[0] = 0;
			yes[1] = j;
			neighbors.add(yes);
		}
		if (j < 0) {
			yes = new int[2];
			yes[0] = i;
			yes[1] = this.getGrid().getSize() - 1;
			neighbors.add(yes);
		}
		if (j > this.grid.getSize()) {
			yes = new int[2];
			yes[0] = i;
			yes[1] = 0;
			neighbors.add(yes);
		}
	}
	/**
	 * Gets neighbors for Sugar simulation.
	 * 
	 * @param Cell
	 *            the cell in question
	 * @param isTorus
	 * 			  is the map a torus or not.
	 * @return NeighborList
	 */
	public NeighborList getSugarNeighborList(Cell c, boolean isTorus) {
		NeighborList neighbors = new NeighborList();
		if (c != null) {
			int[] yes;
			for (int i = -grid.getSize(); i < grid.getSize(); i++) {
				if (!(i == 0 || !inGrid(c.getX() + i, c.getY()))) {
					if (isTorus && !inGrid(c.getX() + i, c.getY())) {
						rectangleTorusHandler(neighbors, c.getX() + i, c.getY());
					}
					if (inGrid(c.getX() + i, c.getY()) && this.getGrid().getCell(c.getX() + i, c.getY()) != null) {
						yes = new int[2];
						yes[0] = c.getX() + i;
						yes[1] = c.getY();
						neighbors.add(yes);
					}
				} else if (!(i == 0 || !inGrid(c.getX(), c.getY() + 1))) {
					if (isTorus && !inGrid(c.getX(), c.getY() + i)) {
						rectangleTorusHandler(neighbors, c.getX(), c.getY() + i);
					}
					if (inGrid(c.getX(), c.getY() + i) && this.getGrid().getCell(c.getX(), c.getY() + i) != null) {
						yes = new int[2];
						yes[0] = c.getX();
						yes[1] = c.getY() + i;
						neighbors.add(yes);
					}
				}
			}
		}
		return neighbors;
	}
}
