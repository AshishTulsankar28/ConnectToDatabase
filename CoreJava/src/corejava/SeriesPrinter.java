package corejava;

import java.util.Scanner;
/**
 * Print a number of series where the next number is sum of previous two numbers.
 * for ex. - 1 2 3 5 8 13 21
 * @author Ashish Tulsankar
 *
 */
public class SeriesPrinter {

	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the initial number to start the series");
		
		if(!sc.hasNextInt()) {
			System.out.println("Invalid Input, Try again");
			sc.close();
			return;
		}
		
		int prev=sc.nextInt();
		int next=prev>0?prev+1:prev-1;
		
		System.out.println("How many numbers you want to print in a series");
		
		if(!sc.hasNextInt()) {
			System.out.println("Invalid Input, Try again");
			sc.close();
			return;
		}
		
		int limit=sc.nextInt();
		sc.close();
		
		
		System.out.println("\n"+prev+"\n"+next);
        printSeries(prev,next,limit-2);		

	}

	/**
	 * 
	 * @param prev represents previous number from NumberSeries
	 * @param next represents next number from NumberSeries
	 * @param limit represents limit of NumberSeries
	 */
	private static void printSeries(int prev,int next,int limit) {
		int numToPrint=prev+next;
		System.out.println(numToPrint);
	
		if(limit>1) {
			prev=next;
			next=numToPrint;
			limit--;
			printSeries(prev, next, limit);
		}
		
	}

}
