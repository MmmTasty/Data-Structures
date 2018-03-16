package PriorityCars;

public class Car implements Comparable<Car>{
	//A car has a name, a time when it arrived, and it's priority
	public final String name;
	public final int priority;
	public final int arrival;
	
	//Constructor
	public Car(String n, int a, int p) {
		name = n;
		priority = p;
		arrival = a;
	}
	//To string printing all things out
	public String toString() {
		return name + " " + arrival + " " + priority;
	}
	
	//it implements a comparable interface, to see which one is smaller between two cars
	@Override
	public int compareTo(Car o) {
		if(this.priority < ((Car)o).priority) {
			return -1;
		}
		else if (this.priority > ((Car)o).priority) {
			return 1;
		}
		else if (this.arrival < ((Car)o).arrival) {
			return -1;
		}
		else if(this.arrival > ((Car)o).arrival){
			return 1;
		}
		return 0;
	}

}
