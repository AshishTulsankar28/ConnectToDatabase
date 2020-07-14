package test;

import java.util.Scanner;

public class MathUtil {
	
	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		System.out.println("Select Math Operation:\n1.Add\n2.Subtract\n3.Multiply\n4.Divide");
		int oper=sc.nextInt();
		System.out.println("Enter 2 numbers");
		int num1=sc.nextInt();
		int num2=sc.nextInt();
		switch (oper) {
		case 1:
			addNum(num1,num2);
			break;

		default:
			break;
		}
		sc.close();
	}

	public static int addNum(int num1, int num2) {
		return num1+num2;
		
	}

}
