package AllTheSorting;

import java.util.ArrayList;
import java.util.Random;

public class Driver {

	public static void main(String args[]) throws Exception {
	    //Populate an array of the different sizes of the arrays that need to be sorted
	    int[] elements = new int[11];
	    int start = 8;
	    for (int i = 0; i < 11; i++) {
	    	elements[i] = start;
	    	start *= 2;
	    }
	    //For all the different sizes of arrays sort them
	    for(int i = 0; i < elements.length; i++) {
	    	//Pick the size from the array
	    	int n = elements[i];
	    	//Pick how many times to sort
	    	int iter = Math.max(4, 8192/n);
	    	//Start the clock timer
	    	CpuTimer timer = new CpuTimer();
	    	//Make a random array
	    	ArrayList<Integer> a1 = randomArray(n);
	    	ArrayList<Integer> test = null;
	    	//Do all the testing on insertion sort
	    	for(int j = 0; j < iter; j++) { 
			    test = insertionSort(a1); 
	    	}
	    	//Stop the timer
	    	double total = timer.getElapsedCpuTime();
	    	//Get the average time
	    	double insertionTime = total / iter;
	    	//If insertion didn't sort the array throw an exception
	    	if(!isSorted(test)) {
	    		throw new Exception("Failed insertion");
	    	}
	    	//Start a new timer for merge, and merge sort
	    	CpuTimer timer2 = new CpuTimer();
	    	for(int j = 0; j < iter; j++) { 
	    		mergeSort(a1);
	    	}
	    	//Stop timer
	    	total = timer2.getElapsedCpuTime();
	    	//Get average
	    	double mergeTime = total/ iter;
	    	//Throw exception if not sorted
	    	if(!isSorted(test)) {
	    		throw new Exception("Failed merge");
	    	}
	    	//Print out the time
	    	System.out.println("Average times (in seconds) for n = " + n + " Insertion Sort: " + insertionTime + " Merge Sort: " + mergeTime);
	    	
	    }
	}
	
	//Makes a random array list of n elements
	public static ArrayList<Integer> randomArray(int n){
		ArrayList<Integer> a1 = new ArrayList<>();
	    Random r = new Random();
		for (int i = 0; i < n;  ++i) {
		      a1.add(r.nextInt(100));
		    }
		return a1;
	}
	
	//Takes an array and determines if it is sorted by comparing each element to the one it's next to.
	public static boolean isSorted(ArrayList<Integer> A) {
		for(int i =0; i<A.size()-1; i++) {
			if(A.get(i + 1) < A.get(i)) {
				return false;
			}
		}
		return true;
	}
	
	//Performs an insertion sort (this is the really inefficient one)
	public static ArrayList<Integer> insertionSort(ArrayList<Integer> B){
		//Copy array (so it can be passed to merge)
		ArrayList<Integer> A = new ArrayList<Integer>(B);
		//Perform insertion sort
		for(int j = 1; j < A.size(); j++) {
			int key = A.get(j);
			int i = j-1;
			while(i > -1 && A.get(i) > key) {
				A.set(i+1, A.get(i));
				i = i - 1;
			}
			A.set(i+1, key);
		}
		//Return the sorted array
		return A;
	}
	
	//The merge sort called by the user, used to make the first recursive call.
	public static ArrayList<Integer> mergeSort(ArrayList<Integer> B){
		ArrayList<Integer> A = new ArrayList<>(B);
		mergeHelper(A, 0, A.size()-1);
		
		
		
		return A;
	}
	//Recursive merge sort, that basically sorts the lower, higher part of the array it deals with and then merges these sections
	public static void mergeHelper(ArrayList<Integer> A, int p, int r){
		if(p<r) {
			int q = (p+r)/2;
			mergeHelper(A, p, q);
			mergeHelper(A, q+1, r);
			merge(A, p, q, r);
		}
	}
	//Merges the two array together
	public static void merge(ArrayList<Integer> A, int p, int q, int r) {
		int n1 = q-p+1;
		int n2 = r-q;
		//New arrays for temp holding the values
		ArrayList<Integer> L = new ArrayList<>();
		ArrayList<Integer> R = new ArrayList<>();
		//Copy the values over to temp locations
		for(int i = 0; i < n1; i++) {
			L.add(A.get(p + i ));
		}
		
		for(int j = 0; j < n2; j++) {
			R.add(A.get(q+j+1));
		}
		//Add these to make the code easier (it can't be higher than the max so append this to the end
		L.add(Integer.MAX_VALUE);
		R.add(Integer.MAX_VALUE);
		int i = 0;
		int j = 0;
		//Merge them up
		for(int k = p; k < r + 1; k++) {
			if(L.get(i) <+ R.get(j)) {
				A.set(k, L.get(i));
				i++;
			}
			else {
				A.set(k, R.get(j));
				j++;
			}
		}
	}
}
















