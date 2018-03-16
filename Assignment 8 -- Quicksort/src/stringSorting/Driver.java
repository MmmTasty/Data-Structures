package stringSorting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	public static void main(String args[]) throws FileNotFoundException {
		//Get the path from the command prompt
		String path = args[0];
		//Scanner for an input
		Scanner input = new Scanner(new FileInputStream(path));
		//Output for the file
		PrintWriter output = new PrintWriter(new File(path + ".out"));
		//A list of all the lines in the input file
		ArrayList<String> list = new ArrayList<>();
		//Put all the input lines into list
		while(input.hasNextLine()) {
			list.add(input.nextLine());
		}
		//Sort the list 
		QuickSort.randomizedQuickSort(list, 0, list.size()-1);
		//Print to the output
		for(int i = 0; i < list.size(); i++) {
			output.println(list.get(i));
		}
		//Close input and output
		input.close();
		output.close();
	}

}
