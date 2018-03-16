package minSpanningTree;

import java.io.File;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Driver {
	public static void main(String args[]) throws Exception {
		//Make a map, that holds all the vertices 
		ArrayList<vertex> map = new ArrayList<>();
		//Get the file name from the first command line argument and the starting node from the second
		Scanner input = new Scanner(new FileInputStream(args[0]));
		int source = Integer.parseInt(args[1]);
		//Make an output file, with extension .out
		//TODO change to .dot
		PrintWriter output = new PrintWriter(new File(args[0].substring(0, args[0].length() - 4) + "-" + source + ".out"));
		//How many vertices there are
		int turns = input.nextInt();
		//Add vertices to the map
		for(int i = 0; i < turns; i++) {
			map.add(new vertex(i));
		}
		//Make the cost function, which is just a hashmap using the two vertexes
		HashMap<String, Integer> costFunction = new HashMap<>();
		//while the file still has connections in it, adjust the adjacentcy lists to include it
		while(input.hasNext()) {
			int key = input.nextInt();
			int second = input.nextInt();
			int cost = input.nextInt();
			map.get(key).adjList.add(map.get(second));
			map.get(second).adjList.add(map.get(key));
			costFunction.put("" + key + second , cost);
		}
		//Close the input
		input.close();
		//Set the source key to 0
		map.get(source).key = 0;
		//Make a minheap for all the vertexes min priority queue
		MinHeap<vertex> queue = new MinHeap<>();
		//Make the minheap
		for(vertex v : map) {
			queue.minHeapInsert(v);
		}
		//While some node hasn't been added to the min spanning tree
		while(queue.hasNext()) {
			//Remove the vertex with the smallest weight, and this safe
			vertex current = queue.heapExtractMin();
			//Relax all edges of this node
			for(vertex v : current.adjList) {
				//Determine the hash value for the costFunction
				int first = 0;
				int second = 0;
				if(current.name > v.name) {
					first = v.name;
					second = current.name;
				}
				else {
					first = current.name;
					second = v.name;
				}
				//If the node hasn't been removed yet, and the cost function is smaller, relax the edge
				if(queue.contains(v) != -1 && costFunction.get("" + first + second) < v.key) {
					v.predicessor = current;
					v.key = costFunction.get("" + first + second);
					//making sure to decrease the key
					queue.heapDecreaseKey(queue.contains(v), v);
				}
			}
			
			
			
		}
		
		//Print out the map as requested
		for(int i = 0; i < map.size(); i++) {
			if(map.get(i).predicessor == null) {
				output.println("" + map.get(i) + " " + map.get(i).key + " " + (-1));
			}
			else {
				output.println("" + map.get(i) + " " + map.get(i).key + " " + map.get(i).predicessor);
			}
		}
		//Calculate the cost and print it out
		int cost = 0;
		for(vertex v : map) {
			cost += v.key;
		}
		output.println(cost);
		//close the output
		output.close();
	}

}
