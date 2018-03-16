package waysToFindTheArray;

public class MaxSumInfo {
	public int start;
	public int end;
	public int sum;
	
	public MaxSumInfo(int i, int j, int sum) {
		this.start = i;
		this.end = j;
		this.sum = sum;
	}
	
	public static boolean compare(MaxSumInfo one, MaxSumInfo two) {
		if(one.end == two.end && one.start == two.start && one.sum == two.sum) {
			return true;
		}
		return false;
	}
	
	
	public String toString() {
		return "Start: " + start + " End: " + end + " Sum: " + sum;
	}

}
