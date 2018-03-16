package PriorityCars;

import java.util.ArrayList;

public class MinHeap<E extends Comparable<E>>  {
	//It has a heap size and a heap
	int heapSize;
	ArrayList<E> heap;
	
	//constructor
	public MinHeap(){
		heapSize = 0;
		heap = new ArrayList<>();
	}
	
	//Makes the mini- heap valid
	private void minHeapify(int i){
		int l = i * 2+1;
		int r = i * 2+2;
		int largest = -1;
		if (l <= heapSize - 1 && heap.get(l).compareTo(heap.get(i)) == -1) {
			largest = l;
		}
		else {
			largest = i;
		}
		if(r <= heapSize - 1 && heap.get(r).compareTo(heap.get(largest)) == -1) {
			largest = r;
		}
		if (largest != i) {
			E temp = heap.get(i);
			heap.set(i, heap.get(largest));
			heap.set(largest, temp);
			minHeapify(largest);
		}
	}
	//Returns the minimum 
	public E heapMin() {
		return heap.get(0);
	}
	//Extract the min, and then heapify it
	public E heapExtractMin() {
		if (heapSize < 1) {
			return null;
		}
		E min = heap.get(0);
		heap.set(0, heap.get(heapSize -1));
		heap.set(heapSize-1, null);
		heapSize--;
		minHeapify(0);
		return min;
	}
	//Decrease the key of something, which is legal in a min heap
	public void heapDecreaseKey(int i, E key) throws Exception {
		if(heap.get(i) != null && key.compareTo(heap.get(i)) == 1) {
			throw new Exception("Bad heap decrease");
		}
		heap.set(i, key);
		while(i > 0 && heap.get((i-1)/2).compareTo(heap.get(i)) == 1) {
			E temp = heap.get(i);
			heap.set(i, heap.get((i-1)/2));
			heap.set((i-1)/2, temp);
			i = (i-1)/2;
		}
	}
	//Basically add a node if needed (conceptually with the value of positive infinity, then decrease the key value to the node)
	public void minHeapInsert(E key) throws Exception {
		heapSize++;
		heap.add(null);
		heapDecreaseKey(heapSize - 1, key);
		
		
	}
	//This is just indicating if the heap has something left in it
	public boolean hasNext() {
		if(heapSize == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	//Prints out the contents of the heap
	public String toString() {
		return "" + heapSize + " " + heap;
	}

	

}
