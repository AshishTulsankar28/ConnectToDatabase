package corejava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 * Refer Java ArrayList.pdf from ext folder for problem statement
 * Reference - HackerRank
 * @author Ashish Tulsankar
 *
 */
public class ArrayOper {
	public static void main(String[] args) {
		Scanner sc =new Scanner(System.in);
		int noOfRows=sc.nextInt();
		List<Integer> list;//=new ArrayList<Integer>();
		Map<Integer,List<Integer>> tab=new HashMap<Integer,List<Integer>>(noOfRows);

		// storing user input
		for(int r=1,col=0;r<=noOfRows;r++){
			col=sc.nextInt();
			list=new ArrayList<Integer>(col);

			for(int c=0;c<col;c++){
				list.add(sc.nextInt());
			}
			tab.put(r, list);
		}
		//storage finished

		//Now get x y positions to print
		int matrixHt=sc.nextInt();
		int[][] pos=new int[matrixHt][2];
		for(int r=0;r<matrixHt;r++){
			pos[r][0]=sc.nextInt();pos[r][1]=sc.nextInt();
		}


		sc.close();
		System.out.println("\n*****Data Stored in desired format*****\nGenerating output below:\n");
		
		//Prepare output
		for (int i=0,x,y;i<matrixHt;i++) {
			x=pos[i][0];
			y=pos[i][1];
			if(!tab.get(x).isEmpty() && y<=tab.get(x).size()) {
				System.out.println(tab.get(x).get(y-1));
			}else {System.out.println("Error");}
			
		}

	}
}
