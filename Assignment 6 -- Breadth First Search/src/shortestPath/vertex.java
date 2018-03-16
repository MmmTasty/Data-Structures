package shortestPath;

import java.util.ArrayList;

public class vertex {
	//This has a list of the nodes it's connected to, a predecessor, a distance, and a name, which is the number of the node
	protected ArrayList<vertex> adjList;
	protected vertex predicessor;
	protected int distance;
	protected int name;
	//constructor just needs the name, the rest are initialized to empty things (distance of -1 means it hasn't been discovered)
	public vertex(int name) {
		this.name = name;
		adjList = new ArrayList<>();
		predicessor = null;
		distance = -1;
	}
	
	public String toString() {
		//The two string method just needs the name of the node.
		return "" + name;
	}
	

}
