package corejava;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Problem from {@link https://www.hackerrank.com/}
 * </br>
 * Refer "sherlock-and-valid-string-English.pdf" from ext folder
 * @author Ashish Tulsankar
 *
 */
public class StringValidator {

	// Complete the isValid function below.
	static String isValid(String s) {

		int counter=0;
		char[] charArr=s.toCharArray();
		
		Set<String> uniqueChars=new HashSet<String>();
		List<Integer> noOfOccur=new ArrayList<Integer>();
		//Map<String,Integer> map=new HashMap<String,Integer>();

		for (char curChar : charArr) {
			uniqueChars.add(String.valueOf(curChar));
		}

		for (String setStr : uniqueChars) {

			for (char arrStr : charArr) {
				if(setStr.equals(String.valueOf(arrStr))) {
					counter++;
				}
			}
			
			//map.put(setStr, counter);
			noOfOccur.add(counter);
			counter=0;
		}
		
		
		Collections.sort(noOfOccur);

		for(int i=0;i<noOfOccur.size();i++) {
			for(int j=i;j<noOfOccur.size();j++) {
				if(!noOfOccur.get(i).equals(noOfOccur.get(j)) ) {
					if(j>i) { 
						
						if(j==noOfOccur.size()-1) {
							if(noOfOccur.get(i).equals(noOfOccur.get(j)-1)) {
							  return "YES";
							}else {return "NO";}
						}else {
							boolean cond1=j==i+1;
							boolean cond2=noOfOccur.get(j).equals(noOfOccur.get(noOfOccur.size()-1));
							boolean cond3=!noOfOccur.get(j).equals(noOfOccur.get(i));
							boolean cond4=noOfOccur.get(i).equals(new Integer(1));
							if(cond1 && cond2 && cond3 && cond4) {
								return "YES";
							}else {return "NO";}
						}

					}
				}
			}
		}
		
		
		
		return "YES";
	}


	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\tulsa\\Downloads\\temp.txt"));

		String s = scanner.nextLine();

		String result = isValid(s);

		System.out.println("result"+result);
		bufferedWriter.write(result);
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}
}
