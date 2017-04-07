package cellpackage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class NeighborList implements Collection{

	private ArrayList<int[]> neighbors;
	
	public NeighborList(ArrayList<int[]> buddies){
		neighbors = buddies;
	}
	
	public NeighborList(){
		this(new ArrayList<int[]>());
	}

	@Override
	public int size() {
		if(this.neighbors != null) return this.neighbors.size();
		return 0;
	}
	
	public int getX(int element){
		return neighbors.get(element)[0];
	}
	
	public int getY(int element){
		return neighbors.get(element)[1];
	}

	@Override
	public boolean isEmpty() {
		return this.neighbors.equals(null) || this.neighbors.size() == 0;
	}

	@Override
	public boolean contains(Object o) {
		return neighbors.contains(o);
	}

	@Override
	public Iterator<?> iterator() {
		neighbors.iterator();
		return null;
	}

	@Override
	public Object[] toArray() {
		return null;
	}

	@Override
	public Object[] toArray(Object[] a) {
		return null;
	}

	@Override
	public boolean add(Object e) {
		neighbors.add((int[]) e);
		return true;
	}

	@Override
	public boolean remove(Object o) {
		neighbors.remove(o);
		return true;
	}

	@Override
	public boolean containsAll(Collection c) {
		return neighbors.containsAll(c);
	}

	@Override
	public boolean addAll(Collection c) {
		neighbors.addAll(c);
		return true;
	}

	@Override
	public boolean removeAll(Collection c) {
		neighbors.removeAll(c);
		return true;
	}

	@Override
	public boolean retainAll(Collection c) {
		return false;
	}

	@Override
	public void clear() {
		neighbors.clear();
	}
	
}
