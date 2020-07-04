package corejava;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Refer java-anagrams-English.pdf from ext folder for problem statement
 * {@link www.hackerrank.com}
 * @author Ashish Tulsankar
 *
 */
public class AnagramCheck {

	public static void main(String[] args) {
		System.out.println("Enter two string seperated by space & numeric choice for logical ways\n1. Using Array\n2. Using Map\n3. Using String.replaceAll()");
		Scanner sc =new Scanner(System.in);
		String str1=sc.next();
		String str2=sc.next();
		int choice=sc.nextInt();
		
		if(isAnagram(str1.toLowerCase(),str2.toLowerCase(),choice)) {System.out.println("Anagram !");
		}else {System.out.println("Not an Anagram !");}
		
		sc.close();
	}

	
	
	
	
	private static boolean isAnagram(String str1, String str2,int noOfWays) {
		boolean isAnagram;
	
		
		
		switch (noOfWays) {
		case 1:
			isAnagram=usingArrayEquality(str1, str2);
			break;
		case 2:
			isAnagram=usingMapEquality(str1, str2);
			break;
		case 3:
			isAnagram=usingLogic(str1, str2);
			break;
		default:
			isAnagram=false;
			break;
		}
			
		return isAnagram;
	}

	private static boolean usingLogic(String a, String b) {

		boolean isAnagram=false;
        if(a.length()==b.length()){
              char[] char1=a.toCharArray();
              isAnagram=true;      
              for (char c : char1)
               {
                    String s1=a.replaceAll(String.valueOf(c), "");
                    String s2=b.replaceAll(String.valueOf(c), "");
                    if(s1.length()!=s2.length()){
                      isAnagram=false;  
                      break;
                    }
                }


        }else{
          isAnagram=false;
        }

        return isAnagram;	
	}

	private static boolean usingMapEquality(String str1, String str2) {
		char[] char1=str1.toCharArray();
		char[] char2=str2.toCharArray();
		
		Map<String,Integer> m1=getOccurrences(char1);
		Map<String,Integer> m2=getOccurrences(char2);
		return m1.equals(m2);
	}

	private static Map<String,Integer> getOccurrences(char[] char1) {
		Set<String> set=new HashSet<String>();
		Map<String,Integer> map=new HashMap<String,Integer>();
		
		for (char curChar : char1) {
			set.add(String.valueOf(curChar));
		}
		
		int counter;
		for (String string : set) {
			counter=0;
			for (char curChar : char1) {
				if(String.valueOf(curChar).equals(string)) {
					counter++;
				}
			}
			map.put(string, counter);
		}
		
		return map;
	}

	private static boolean usingArrayEquality(String str1, String str2) {
		char[] char1=str1.toCharArray();
		char[] char2=str2.toCharArray();
		Arrays.sort(char1);
		Arrays.sort(char2);
		return Arrays.equals(char1, char2);
	}

}
