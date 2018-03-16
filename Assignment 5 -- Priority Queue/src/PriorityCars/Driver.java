package PriorityCars;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	public static void main(String args[]) throws Exception {
		//Make an array list of array list of cars, which are read from the file
		ArrayList<ArrayList<Car>> cars = new ArrayList<ArrayList<Car>>();
		//Input from the file
		Scanner input = new Scanner( new File (args[0]));
		//Find the number of stations
		int stations = input.nextInt();
		//Make a min head
		MinHeap<Car> heap = new MinHeap<>();
		//This is to max value of the array list of array lists, to make sure we dont 
		//Index outside of this range, basically just need for the array list
		int max = 0;
		//Output is here
		PrintWriter output = new PrintWriter(new File (args[0] + ".csv"));
		//Read the cars into the array list, with the first indexing what time the car comes,
		//And the second array list holding all cars that arrive at that time
		while(input.hasNext()) {
			//Make a car and put it into the right arraylist 
			Car car = new Car(input.next(),input.nextInt(), input.nextInt());
			int current = car.arrival;
			while(max <= current) {
				cars.add( new ArrayList<Car>());
				max++;
			}
			cars.get(current).add(car);
		}
		//Close the input file
		input.close();
		//Make a counter variable for loop
		int i = 0;
		//This loop runs while there is stuff in the heap or as long as cars still need to be added to the heap
		while(heap.hasNext() || i < cars.size()) {
			//If there are cars left add them.
			if(i < cars.size()) {
				ArrayList<Car> currentCarList = cars.get(i);
				for(int j = 0; j < currentCarList.size(); j++) {
					heap.minHeapInsert(currentCarList.get(j));
				}
			}
			//Remove the correct number of cars from the heap, as if they are fueled 
			for(int j = 0; j < stations && heap.hasNext(); j++) {
				Car filled = heap.heapExtractMin();
				output.println("\"" + filled.name+ "\"," + filled.arrival + "," + i);
			}
			i++;
		}
		//Close the output
		output.close();
	}
	
	
}
