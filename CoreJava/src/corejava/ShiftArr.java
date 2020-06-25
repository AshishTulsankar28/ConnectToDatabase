package corejava;

import java.io.*;
import java.util.*;


public class ShiftArr {

	
	static int[] rotLeft(int[] a, int d) {

		for(int i=0;i<d;i++)
			shiftToLeftByOne(a);

		return a;
	}

	/**
	 * Array will be shifted to left by 1 position
	 * @param a Integer array
	 * @param d number of shifts 
	 */
	private static void shiftToLeftByOne(int[] a) {

		int first=a[0];
		for(int pos=0;pos<a.length-1;pos++) {
			a[pos]=a[pos+1];
		}
		a[a.length-1]=first;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\tulsa\\Downloads\\temp.txt"));

		System.out.print("Enter the size of array & the number by which array elements should be shifted, seperated by space :");
		String[] nd = scanner.nextLine().split(" ");

		int n = Integer.parseInt(nd[0]);

		int d = Integer.parseInt(nd[1]);
		
		System.out.println("Size of an array is "+n+" & elements will be shifted by positions of "+d);

		int[] a = new int[n];
		System.out.println("Enter "+n+" numbers for array, seperated by space");
		String[] aItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int aItem = Integer.parseInt(aItems[i]);
			a[i] = aItem;
		}

		int[] result = rotLeft(a, d);

		System.out.print("After shifting array elements:\n ");
		for (int i = 0; i < result.length; i++) {
			bufferedWriter.write(String.valueOf(result[i]));
			System.out.print(result[i]+" ");
			if (i != result.length - 1) {
				bufferedWriter.write(" ");
			}
		}

		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}
}
