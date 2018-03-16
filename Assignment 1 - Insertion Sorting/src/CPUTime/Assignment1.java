package CPUTime;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * CS2370 Programming Assignment 1 Sample Code
 *
 * @author [Thomas Marks]
 *
 * Insertion Sort, which can also find the CPU time of the program if it is modified.
 * Test File.txt is the example file  
 *
 */

public class Assignment1 {

  /**
   * Program main
   *
   * @param args The name of the file that you want to read in, otherwise it defaults to the default case which here is the file "Test File.txt"
 * @throws FileNotFoundException 
   */
  public static void main(String[] args) throws FileNotFoundException {
	  //Retrive File from command prompt
    File file;
    if (args.length == 1) {
    	file = new File(args[0]);
    }
    else {
    	file = new File("Test File.txt");
    }
    
    //Make scanner, and put all values into an array list
    Scanner numbers = new Scanner(file);
    ArrayList<Integer> A = new ArrayList<>();
    while (numbers.hasNext()) {
    	A.add(numbers.nextInt());
    }
    
	//CpuTimer timer = new CpuTimer();
	int key = 0;
	int i = 0;
	//Insertion sort (using the cloning way)
	for (int j = 1; j < A.size(); j++) {
		key = A.get(j);
		i = j - 1;
		while (i > -1 && A.get(i) > key) {
			A.set(i + 1, A.get(i));
			i--;
		}
		A.set(i+1, key);
	}
	//Print the sorted list
	for(i = 0; i< A.size(); i++) {
		System.out.println(A.get(i));
	}
    //System.out.println("CPU time = " + timer.getElapsedCpuTime());
    numbers.close();
  }

}
