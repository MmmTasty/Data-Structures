package minSpanningTree;

import java.util.ArrayList;

public class vertex implements Comparable<vertex>{
	//This has a list of the nodes it's connected to, a predecessor, a distance, and a name, which is the number of the node
	protected ArrayList<vertex> adjList;
	protected vertex predicessor;
	protected int key;
	protected int name;
	//constructor just needs the name, the rest are initialized to empty things (distance of -1 means it hasn't been discovered)
	public vertex(int name) {
		this.name = name;
		adjList = new ArrayList<>();
		predicessor = null;
		key = Integer.MAX_VALUE;
	}
	
	
	public String toString() {
		//The two string method just needs the name of the node.
	
		return "" + name;
	}

	@Override
	public int compareTo(vertex arg0) {
		//returns the index of the equal vertex
		if(this.key > arg0.key) {
			return 1;
		}
		else if(this.key == arg0.key) {
			return 0;
		}
		return -1;
	}
	//Sees if all parameters are equal and if so determines the two are the same
	public boolean equals(vertex v) {
		if(this.adjList.equals(v.adjList)) {
			if(predicessor == v.predicessor) {
				if(key == v.key) {
					if(name == v.name) {
						return true;
					}
				}
			}
		}
		return false;
	}

	
	

}
