package corejava;

import java.util.Arrays;

public class StringArr {

	public static void main(String[] args) {
		String[] a= {"A","B","C","A", "D"};
		String[] b= {"D","E","C","E"};

		String[] newArr=new String[a.length+b.length];

		// elements from first array added
		for(int i=0;i<a.length;i++) {
			if(!checkIfExistInArr(newArr,a[i]))
				newArr[i]=a[i];
		}
		// elements from second array added
		int prev=a.length;
		for(int j=0;j<b.length;j++) {

			if(!checkIfExistInArr(newArr,b[j])) {
				newArr[prev]=b[j];
				prev++;
			}
		}

		//Remove nulls if any
		newArr= Arrays.stream(newArr).filter(s->(s!=null && s.length()>0)).toArray(String[]::new);

		/*
		 * List<String> lst=new ArrayList<String>(); for (String string : newArr) {
		 * if(string!=null && string.length()>0) { lst.add(string); } }
		 * 
		 * newArr=lst.toArray(new String[lst.size()]);
		 */




		for (String string : newArr) {
			System.out.print(" "+string);
		}

	}

	/**
	 * Method to check if given string exist in provided array
	 * @param newArr Array in which string needs to be searched if exist
	 * @param strToCheck string to be checked in given newArr
	 * @return true if strToCheck exist in newArr else false
	 */
	private static boolean checkIfExistInArr(String[] newArr,String strToCheck) {

		for (String string : newArr) {
			if(string!=null && string.equalsIgnoreCase(strToCheck)) {
				return true;
			}
		}

		return false;
	}

}
