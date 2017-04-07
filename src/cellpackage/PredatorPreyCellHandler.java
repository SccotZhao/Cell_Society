package cellpackage;

import java.util.ArrayList;
import java.util.Random;

import grid.Grid;

/**
 * 
 * This class is the designated object that can manage the Cells for the
 * PredatorPrey simulation, and is the way in which the states of the cells
 * interact with the grid. This is purposed to protect the states of the cells,
 * and hide the logic, the result of which is passed to the simulation's grid.
 * 
 * @author Alexander Zapata
 * @author Kyle Finke
 *
 */

public class PredatorPreyCellHandler extends CellHandler {

	private int BREED_STEPS = 5;
	private String PREDATOR = "shark";
	private String PREY = "fish";
	private String ENVIRONMENT = "water";

	public PredatorPreyCellHandler(Grid grid) {
		this.setGrid(grid);
	}

	@Override
	public int getNeighbors(Cell cell) {
		return 0;
	}

	@Override
	public boolean doesChange(Cell cell) {
		return (doesMove(cell) || sharkDoesEat(cell));
	}

	private void addLocation(ArrayList<int[]> openCells, int xLoc, int yLoc) {
		int[] cellLocation = new int[2];
		cellLocation[0] = xLoc;
		cellLocation[1] = yLoc;
		openCells.add(cellLocation);
	}

	private ArrayList<int[]> neighborCells(Cell cell, String type) {
		ArrayList<int[]> neighbors = new ArrayList<int[]>();
		if (cell != null) {
			if (isType(cell.getX() + 1, cell.getY(), type)) {
				addLocation(neighbors, cell.getX() + 1, cell.getY());
			}
			if (isType(cell.getX() - 1, cell.getY(), type)) {
				addLocation(neighbors, cell.getX() - 1, cell.getY());
			}
			if (isType(cell.getX(), cell.getY() + 1, type)) {
				addLocation(neighbors, cell.getX(), cell.getY() + 1);
			}
			if (isType(cell.getX() + 1, cell.getY() - 1, type)) {
				addLocation(neighbors, cell.getX(), cell.getY() - 1);
			}
			if (!inGrid(cell.getX() + 1, cell.getY()) && isType(0, cell.getY(), type)) {
				addLocation(neighbors, 0, cell.getY());
			}
			if (!inGrid(cell.getX() - 1, cell.getY()) && isType(this.getGrid().getSize() - 1, cell.getY(), type)) {
				addLocation(neighbors, this.getGrid().getSize() - 1, cell.getY());
			}
			if (!inGrid(cell.getX(), cell.getY()) && isType(cell.getX(), 0, type)) {
				addLocation(neighbors, this.getGrid().getSize(), 0);
			}
			if (!inGrid(cell.getX(), cell.getY() - 1) && isType(cell.getX(), this.getGrid().getSize() - 1, type)) {
				addLocation(neighbors, this.getGrid().getSize(), this.getGrid().getSize() - 1);
			}
		}
		return neighbors;

	}

	private boolean sharkDoesEat(Cell cell) {
		if (cell != null && cell.getState().equals(PREDATOR)) {
			return neighborsContainType(cell, PREY);
		}
		return false;
	}

	private boolean neighborsContainType(Cell cell, String type) {
		if (!isType(cell.getX() + 1, cell.getY(), type)) {
			if (!isType(cell.getX() - 1, cell.getY(), type)) {
				if (!isType(cell.getX(), cell.getY() + 1, type)) {
					if (!isType(cell.getX() + 1, cell.getY() - 1, type)) {
						if (!(!inGrid(cell.getX() + 1, cell.getY()) && isType(0, cell.getY(), type))) {
							if (!(!inGrid(cell.getX() - 1, cell.getY())
									&& isType(this.getGrid().getSize() - 1, cell.getY(), type))) {
								if (!(!inGrid(cell.getX(), cell.getY()) && isType(cell.getX(), 0, type))) {
									return !inGrid(cell.getX(), cell.getY() - 1)
											&& isType(cell.getX(), this.getGrid().getSize() - 1, type);
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	private boolean doesMove(Cell cell) {
		if (cell != null && (cell.getState().equals(PREY) || cell.getState().equals(PREDATOR))) {
			return neighborsContainType(cell, ENVIRONMENT);
		}
		return false;
	}

	@Override
	public void switchState(Cell cell, Grid grid) {
		if (!breeds(cell, grid, neighborCells(cell, ENVIRONMENT))) {
			if (!sharkEats(cell, grid, neighborCells(cell, PREY))) {
				move(cell, grid, neighborCells(cell, ENVIRONMENT));
			}
		}
	}

	private void updateTimeStep(Cell cell, Grid grid, String cellType, String neighborType, int xLoc, int yLoc) {
		if (!cellType.equals(neighborType)) {
			grid.getCell(xLoc, yLoc).setTimeSteps(this.getGrid().getCell(cell.getX(), cell.getY()).getTimeSteps() + 1);
		}
	}

	private boolean updateRandomNeighbor(Cell cell, Grid grid, ArrayList<int[]> neighbors, String cellType,
			String oldNeighborType, String newNeighborType) {
		int randomMove = (int) (Math.random() * neighbors.size());
		int possibleLocations = neighbors.size();
		int triedLocations = 0;
		while (triedLocations < possibleLocations) {
			int[] possibleFish = neighbors.get(randomMove);
			int xLoc = possibleFish[0];
			int yLoc = possibleFish[1];
			if (cell != null && isType(xLoc, yLoc, oldNeighborType)
					&& grid.getCell(xLoc, yLoc).getState().equals(oldNeighborType)) {
				grid.getCell(xLoc, yLoc).setState(newNeighborType);
				cell.setState(cellType);

				if (cellType.equals(ENVIRONMENT)) {
					updateTimeStep(cell, grid, cellType, newNeighborType, xLoc, yLoc);
				} else {
					updateTimeStep(cell, grid, cellType, newNeighborType, cell.getX(), cell.getY());
				}

				return true;
			}
			if (randomMove < neighbors.size() - 1) {
				randomMove++;
			} else {
				randomMove = 0;
			}
			triedLocations++;
		}
		return false;

	}

	private boolean breeds(Cell cell, Grid grid, ArrayList<int[]> neighbors) {
		return (canBreed(this.getGrid().getCell(cell.getX(), cell.getY()))
				&& updateRandomNeighbor(cell, grid, neighbors, cell.getState(), ENVIRONMENT, cell.getState()));
	}

	private boolean canBreed(Cell cell) {
		return cell.getTimeSteps() >= BREED_STEPS;
	}

	private void move(Cell cell, Grid grid, ArrayList<int[]> neighbors) {
		updateRandomNeighbor(cell, grid, neighbors, ENVIRONMENT, ENVIRONMENT, cell.getState());
	}

	private boolean sharkEats(Cell cell, Grid grid, ArrayList<int[]> fishNeighbors) {
		if (cell.getState().equals(PREDATOR)) {
			return updateRandomNeighbor(cell, grid, fishNeighbors, cell.getState(), PREY, ENVIRONMENT);
		}
		return false;
	}

}