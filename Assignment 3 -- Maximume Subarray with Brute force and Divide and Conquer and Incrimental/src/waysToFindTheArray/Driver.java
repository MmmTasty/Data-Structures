package waysToFindTheArray;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	
	public static void main(String[] args) throws Exception {
		//Declare input sizes
		String baseName = "Data_";
		int[] sizes = new int[6];
		sizes[0] = 8;
		sizes[1] = 16;
		sizes[2] = 64;
		sizes[3] = 1024;
		sizes[4] = 4096;
		sizes[5] = 65536;
		//Make the PrintWriter that output the information to the file Output
		PrintWriter outputFile = new PrintWriter(new File("Output.txt"));
		//For all sizes of input files
		for(int i = 0; i < sizes.length; i++) {
			//Open the file that we're working on
			String filename = baseName + sizes[i] + ".txt";
			Scanner file = new Scanner(new File(filename));
			//Read it into an array list
			ArrayList<Integer> A = new ArrayList<>();
			while (file.hasNext()) {
				A.add(file.nextInt());
			}
			//Calculate how many times we should average them
			int averageTimes = Math.max(2, 1024/sizes[i]);
			double totalBF = 0;
			double totalDAC = 0;
			double totalInc = 0;
			//Find the time it takes using the different methods
			for(int j = 0; j < averageTimes; j++) {
				CpuTimer start = new CpuTimer();
				
				MaxSumInfo BruteAnswer = BruteForce(A);
				
				totalBF += start.getElapsedCpuTime();
				
				start = new CpuTimer();
				
				MaxSumInfo DACAnswer = DivideAndConquer(A);
				
				totalDAC += start.getElapsedCpuTime();
				
				start = new CpuTimer();
				
				MaxSumInfo IncAnswer = Incrimental(A);
				
				totalInc += start.getElapsedCpuTime();
				//Make sure all the methods agree on the sum
				if(!(IncAnswer.sum == DACAnswer.sum && DACAnswer.sum == BruteAnswer.sum)) {
					System.out.println(DACAnswer);
					System.out.println(IncAnswer);
					System.out.println(BruteAnswer);
					file.close();
					throw new Exception("Not all equal");
				}
			}
			
			//Print out the information
			outputFile.println(""+ sizes[i] + ", " + "\"BF\""  + ", " + (totalBF/averageTimes));
			outputFile.println(""+ sizes[i] + ", " + "\"DAC\""  + ", " + (totalDAC/averageTimes));
			outputFile.println(""+ sizes[i] + ", " + "\"KAD\""  + ", " + (totalInc/averageTimes));
			
			
			
			
			file.close();
		}
		outputFile.close();
		
		

	}
	//Brute force, check all sub array combinations. This is a O(n^2)
	public static MaxSumInfo BruteForce(ArrayList<Integer> A) {
		int maxSum = Integer.MIN_VALUE;
		int left = 0;
		int right = 0;
		for(int i = 0; i < A.size(); i++) {
			int sum = A.get(i);
			if (maxSum < sum) {
				maxSum = sum;
				left = i;
				right = i;
			}
			for (int j = i+1; j < A.size() - 1; j++) {
				sum += A.get(j);
				if (maxSum < sum) {
					maxSum = sum;
					left = i;
					right = j;
				}
			}
		}
		
		return new MaxSumInfo(left, right, maxSum);
	}
	
	//The public method that perform a divide 
	public static MaxSumInfo DivideAndConquer(ArrayList<Integer> A) {
		return findMaxSubArray(A, 0, A.size() - 1);
	}
	//The recursive call that checks if the largest sum is either in the lower half, the higher half, or some array that includes the middle element. This is O(n lg(n))
	public static MaxSumInfo findMaxSubArray(ArrayList<Integer> A, int low, int high) {
		if (high == low) {
			return new MaxSumInfo(low, high, A.get(low));
		}
		else {
			int mid = (low + high)/ 2;
			MaxSumInfo left = findMaxSubArray(A, low, mid);
			MaxSumInfo right = findMaxSubArray(A, mid+1, high);
			MaxSumInfo cross = MaxCrossingSubArray(A, low, mid, high);
			if(left.sum >= right.sum && left.sum >= cross.sum) {
				return left;
			}
			else if (right.sum >= left.sum && right.sum >= cross.sum) {
				return right;
			}
			else {
				return cross;
			}
		}
	}
	//Checks to see if the maximum sub-array crosses the middle point. This is the O(n lg(n))
	public static MaxSumInfo MaxCrossingSubArray(ArrayList<Integer> A, int low, int mid, int high) {
		int leftSum = Integer.MIN_VALUE;
		int sum = 0;
		int maxLeft = 0;
		for( int i = mid; i >= low; i--) {
			sum = sum + A.get(i);
			if (sum > leftSum) {
				leftSum = sum;
				maxLeft = i;
			}
		}
		
		int rightSum = Integer.MIN_VALUE;
		sum = 0;
		int maxRight = 0;
		for( int j = mid + 1; j <= high; j++) {
			sum = sum + A.get(j);
			if (sum> rightSum) {
				rightSum = sum;
				maxRight = j;
			}
		}
		return new MaxSumInfo(maxLeft, maxRight, leftSum + rightSum);
	}
	
	//The incremental, that adds the values as you go, and resets if the total is ever zero. This is a linear time algorithm O(n)
	public static MaxSumInfo Incrimental(ArrayList<Integer> A){
		int maxSeen = Integer.MIN_VALUE;
		int maxCurrent = 0;
		int currentStart = 0;
		int i = 0;
		int j = 0;
		
		for(int k = 0; k < A.size(); k++) {
			maxCurrent += A.get(k);
			if(maxCurrent < 0) {
				maxCurrent = 0;
				currentStart = k + 1;
			}
			if(maxSeen < maxCurrent) {
				maxSeen = maxCurrent;
				j = k;
				i = currentStart;
			}
		}
		
		return new MaxSumInfo(i,j,maxSeen);
	}

}
