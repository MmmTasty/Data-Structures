package shortestPath;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	public static void main(String args[]) throws FileNotFoundException {
		//Make a map, that holds all the vertices 
		ArrayList<vertex> map = new ArrayList<>();
		//Make a queue for later, holds the nodes that have been found but whose neighbors haven't been explored 
		ArrayList<vertex> queue = new ArrayList<>();
		//Get the file name from the first command line argument and the starting node from the second
		Scanner input = new Scanner(new FileInputStream(args[0]));
		int source = Integer.parseInt(args[1]);
		//Make an output file, with extension .out
		PrintWriter output = new PrintWriter(new File(args[0] + "-" + source + ".out"));
		//How many vertices there are
		int turns = input.nextInt();
		//Add vertices to the map
		for(int i = 0; i < turns; i++) {
			map.add(new vertex(i));
		}
		//while the file still has connections in it, adjust the adjacentcy lists to include it
		while(input.hasNext()) {
			int key = input.nextInt();
			int second = input.nextInt();
			map.get(key).adjList.add(map.get(second));
			map.get(second).adjList.add(map.get(key));
		}
		//Set the source distance as zero
		map.get(source).distance = 0;
		//Add the source to the queue
		queue.add(map.get(source));
		//While there are still nodes to be explored
		while(!queue.isEmpty()) {
			//Deque from the queue
			vertex current = queue.remove(0);
			//For all the vertices in the list of the current node examine them
			for(vertex v : current.adjList) {
				//If they haven't been discovered add them to the queue and update the distance
				if(v.distance == -1) {
					v.distance = current.distance + 1;
					v.predicessor = current;
					queue.add(v);
				}
			}
		}
		//Print out the desired output
		for(int i = 0; i < turns; i++) {
			output.println("" + i + " " + map.get(i).distance+ " " + recursiveStringMaker(i, source, map));
			
		}
		//Close the input and output.
		output.close();
		input.close();
	}
	
	//This helps print the path from the source to another node
	public static String recursiveStringMaker(int v, int source, ArrayList<vertex> map) {
		if(v == source) {
			return "" + source;
		}
		return recursiveStringMaker(map.get(v).predicessor.name, source, map) + "-" + v;
	}

}
