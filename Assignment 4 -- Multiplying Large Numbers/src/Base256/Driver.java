package Base256;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Driver {
	//Declare the base of the multiplication
	public static int base = 256;
	
	public static void main(String args[]) throws FileNotFoundException {
		//This array keeps track of the input numbers into the algorithms
		int[] numbers = new int[9];
		int start = 2;
		//initialize to multiples of 2
		for(int i = 0; i < numbers.length; i++) {
			start *= 2;
			numbers[i] = start;
		}
		//Make the output file
		PrintWriter output = new PrintWriter(new File("output.txt"));
		
		for (int j = 0; j < numbers.length; j++) {
			//Make file name
			String fileName = "p4_" + numbers[j] + ".txt";
			Scanner file = new Scanner(new File (fileName));
			int turns = file.nextInt();
			
			//Make u and v, the arrays that need to be multiplied
			int[] u = new int[turns];
			int[] v = new int[turns];
			
			//Assign the number to the array
			for(int i = 1; i <= turns; i++) {
				u[turns - i] = file.nextInt();
			}
			//Assign the number to the array
			for(int i = 1; i <= turns; i++) {
				v[turns - i] = file.nextInt();
			}
			
			//Start clock
			CpuTimer bruteTimer = new CpuTimer();
			//solve with brute force
			int[] w = bruteForce(u, v, turns);
			double bruteTime = bruteTimer.getElapsedCpuTime();
			
			CpuTimer DACTimer = new CpuTimer();
			//solve with 3 multiplication recursion
			int[] t = multiplyRecursive(u, v);
			double DACTime = DACTimer.getElapsedCpuTime();
			
			//Check to make sure they agree (will need to pass -enableassertions
			assert Arrays.equals(w, t);
			
			//Print out the run time
			output.print("" + numbers[j]+",M,"+bruteTime+",\"");
			for (int i = w.length - 1; i >= 0; i--) {
				output.print("" + w[i] + " ");
			}
			output.println("\"");
			
			output.print("" + numbers[j]+",R,"+DACTime+",\"");
			for (int i = t.length - 1; i >= 0; i--) {
				output.print("" + t[i] + " ");
			}
			output.println("\"");
			file.close();
		}
		output.close();
	}
	
	
	public static int[] multiplyRecursive(int[] u, int[] v) {
		//Main recursive method
		//Return the small array if were at the base case, of two numbers being multiplied by eachother
		if(u.length == 1) {
			int[] w = new int[2];
			w[0] =  (u[0] * v[0]) % base;
			w[1] = (u[0] * v[0]) / base;
			return w;
		}
		//Declare the arrays
		int n = u.length;
		int[] w = new int[2 * u.length];
		int[] u1 = new int[u.length / 2];
		int[] vdiff = new int[u.length / 2];
		int[] u2 = new int[u.length / 2];
		int[] udiff = new int[u.length / 2];
		int[] v1 = new int[u.length / 2];
		int[] v2 = new int[u.length / 2];
		//Assign arrays as is needed
		for(int k = 0; k < u.length/2; k++) {
			u1[k] = u[k];
			udiff[k] = u[k+n/2];
			u2[k] = u[k + n /2];
			vdiff[k] = v[k];
			v1[k] = v[k];
			v2[k] = v[k+n/2];
			w[k+n ] = 0;
			w[k + n + n/2]= 0;
		}
		//Subtract u1 from u2 and put it in udiff, the usign shows if it is posive or negative
		int usign = shiftedSubtractFrom(udiff, u1, 0);
		//same for v, but v2 from v1
		int vsign = shiftedSubtractFrom(vdiff, v2, 0);
		
		int[] w1 = new int[n];
		int[] w2 = new int[n];
		int[] w3 = new int[n];
		//Calculate the three products 
		w1 = multiplyRecursive(u2, v2);
		w2 = multiplyRecursive(udiff,vdiff);
		w3 = multiplyRecursive(u1, v1);
		//Put result in w
		for (int i = 0; i < n; i++) {
			w[i] = w3[i];
		}
		
		shiftedAddTo(w, w3, n/2);
		shiftedAddTo(w, w1, n/2);
		shiftedAddTo(w, w1, n);
		
		//If the signs agree add, otherwise subtract
		if(usign == 0 && usign == vsign || usign != 0 && vsign != 0) {
			shiftedAddTo(w, w2, n/2);
		}
		else {
			shiftedSubtractFrom(w, w2, n/2);
		}
		
		return w;
		
		
	}
	//Subtracts b from a
	public static int shiftedSubtractFrom(int[] a, int[] b, int k) {
		int borrow = 0;
		int[] c = new int[a.length];
		for(int i = 0; i < b.length; i++) {
			c[i] = b[i];
		}
		for (int i = 0; i  + k< c.length; i++) {
			int t = a[i + k] - c[i] - borrow;
			if (t >= 0) {
				a[i+k] = t;
				borrow = 0;
			}
			else {
				a[i+k] = base + t;
				borrow = 1;
			}
		}
		
		
		if(borrow != 0) {
			twosComplement(a);
		}
		return borrow;
	}
	//Really retuns the complimake of the static variable base, but just called two complement because 
	//in binary that's what this method would do
	public static void twosComplement(int[] a) {
		int t = base - a[0];
		a[0] = t % base;
		int c = t / base;
		
		for (int i = 1; i < a.length; i++) {
			t = base - 1 - a[i] + c;
			a[i] = t % base;
			c = t/base;
		}
	}
	//Adds b to a
	public static void shiftedAddTo(int[] a, int[] b, int k) {
		int c = 0;
		
		for (int i = 0; i < b.length; i++) {
			int t = a[i + k] + b[i] + c;
			a[i+k] = t % base;
			c = t / base;
		}
		int i = b.length ;
		while (c != 0 && i + k < a.length) {
			int t = a[i+k] + c;
			a[i+k] = t % base;
			c = t/base;
			i++;
		}
		assert c == 0;
		return;
	}
	
	//The brute force method, like we as humans do by hand
	public static int[] bruteForce(int[] u, int[] v, int turns) {
		int[] w = new int[turns * 2];
		
		for(int j = 0; j < turns; j++) {
			int c = 0;
			for(int i = 0; i < turns; i++) {
				int t = u[i] * v[j] + w[i+j] + c;
				w[i+j] = t % base;
				c = t / base;
			}
			int k = turns + j;
			while(k <= 2 * turns && c != 0) {
				int t = w[k] + c;
				w[k] = t % base;
				c = t/base;
				k++;
			}
			
			
		}
		
		
		return w;
	}
	

}
