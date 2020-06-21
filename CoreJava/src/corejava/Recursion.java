package corejava;

import java.util.Scanner;

/**
 * Program to remember
 * @author Ashish Tulsankar
 * <dt>Date</dt>
 * <dd>21 July, 2020</dd>
 */
public class Recursion {

	public static void main(String[] args) {

		System.out.println("Pattern : Print numbers till it reaches 0");
		System.out.println("\nEnter an integer value");
		Scanner sc=new Scanner(System.in);

		if(sc.hasNextInt()) {
			System.out.println("PATTERN : -");
			printNumbers(sc.nextInt());
		}else {
			System.out.println("Try again with appropriate input");
		}

		sc.close();
	}

	private static void printNumbers(int nextInt) {

		if(nextInt>0) {
			System.out.println(nextInt);
			printNumbers(--nextInt);
		}

	}

}
