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
		case 2:
			subtractNum(num1,num2);
			break;
		case 3:
			multNum(num1,num2);
			break;
		case 4:
			divNum(num1,num2);
			break;

		default:
			System.out.println("Wrong User Input");
			break;
		}
		sc.close();
	}

	public static int divNum(int num1, int num2) {
		if(num2==0) {
			return 0;
		}
		return num1/num2;		
	}

	public static int multNum(int num1, int num2) {
		return num1*num2;
		
	}

	public static int subtractNum(int num1, int num2) {
		return num1-num2;
		
	}

	public static int addNum(int num1, int num2) {
		return num1+num2;
		
	}

}
