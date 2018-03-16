package stringSorting;
//A quicksort algoirhtm that sorts an arraylist in the range specified
import java.util.ArrayList;
import java.util.Random;

public class QuickSort {
	
	//Quicksorts the array list that is passed to this method
	public static <E extends Comparable<E>> void quickSort(ArrayList<E> A) {
		randomizedQuickSort(A, 0, A.size() -1);
	}
	
	//Will quicksort all elements in A between p and r
	public static <E extends Comparable<E>> void randomizedQuickSort(ArrayList<E> A, int p, int r){
		if(p < r) {
			int q = randomizedPartition(A, p, r);
			randomizedQuickSort(A, p, q-1);
			randomizedQuickSort(A, q+1, r);
		}
		
		return;
	}
	//Makes a random partition
	private static <E extends Comparable<E>> int randomizedPartition(ArrayList<E> A, int p, int r){
		Random rand = new Random();
		int pivotIndex = rand.nextInt(r - p +1) + p;
		E pivot = A.get(pivotIndex);
		A.set(pivotIndex, A.get(r));
		A.set(r, pivot);
		return Partition(A, p, r);
	}
	//Partitions the array properly
	private static <T extends Comparable<T>> int Partition(ArrayList<T> A, int p, int r) {
		T pivot = A.get(r);
		int i = p - 1;
		for(int j = p; j < r; j++) {
			if(A.get(j).compareTo(pivot) <= 0) {
				i++;
				T ith = A.get(i);
				A.set(i, A.get(j));
				A.set(j, ith);
			}
		}
		A.set(r, A.get(i + 1));
		A.set(i + 1, pivot);
		return i + 1;
		
	}
	
	
	
}
