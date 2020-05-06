package catalog;

import java.util.Date;
import java.util.Scanner;

public class CatalogCtrl {

	public static void main(String[] args) {

		Scanner sc=new Scanner(System.in);
		CatalogImpl catImp=new CatalogImpl();
		
		try {
			catalogOps(sc, catImp);
		} catch (Exception e) {
			System.out.println("Error occurred while checking catalog\n");
			e.printStackTrace();
		}
		
	}

	private static void catalogOps(Scanner sc, CatalogImpl catImp) {
		System.out.println("Choose action: \n 1.View Catalog 2.Modify Catalog 3.Search\n");	
		int action=sc.nextInt();

		switch (action) {

		case ActionType.VIEW_CATALOG:
			catImp.viewCatalog(true, 0);
			doContinue(sc, catImp);	
			
			break;
		case ActionType.MODIFY_CATALOG:

			System.out.println("Choose action: \n 1.Add record 2.Modify record 3.Delete record\n");
			action=sc.nextInt();

			switch (action) {
			case ActionType.ADD_RECORD:

				System.out.println("Enter Name, Occupation, Current Address\n");
				catImp.modifyCatalog(ActionType.ADD_RECORD,fillCatalogForm(sc));
				doContinue(sc, catImp);
				
				break;
			case ActionType.UPDATE_RECORD:

				System.out.println("Enter the unique ID to modify the record details\n");
				int uniqueId=sc.nextInt();
				catImp.viewCatalog(false,uniqueId );
				System.out.println("Do you really want to update this record? Press 1 to update else press any key to exit");
				action=sc.nextInt();
				if(action==ActionType.CONTINUE) {
					System.out.println("Enter name, occupation, address\n");
					Catalog catToUpdate=fillCatalogForm(sc);
					catToUpdate.setId(uniqueId);
					catImp.modifyCatalog(ActionType.UPDATE_RECORD,catToUpdate);
					doContinue(sc, catImp);
				}else {
					catImp.closeCatalog();
				}

				break;
			case ActionType.DELETE_RECORD:

				System.out.println("Enter the unique ID to delete the record details\n");
				int id=sc.nextInt();
				Catalog cat=new Catalog();
				cat.setId(id);
				catImp.modifyCatalog(ActionType.DELETE_RECORD,cat);
				doContinue(sc, catImp);	

				break;

			default:
				System.out.println("Invalid Action Selected.");
				doContinue(sc, catImp);
				break;
			}
			
			break;
		case ActionType.SEARCH_CATALOG:

			System.out.println("Enter the unique ID to modify the record details\n");
			int id=sc.nextInt();
			catImp.viewCatalog(false,id );
			doContinue(sc, catImp);

			break;
		default:
			System.out.println("Invalid Action Selected.");
			doContinue(sc, catImp);
			break;
		}
	}

	/**
	 * Method to take user inputs & build the Catalog object
	 * @param sc represents scanner object to get user inputs
	 * @return Catalog object holding user inputs
	 */
	private static Catalog fillCatalogForm(Scanner sc) {
		String name=sc.next();
		String occ=sc.next();
		String addr=sc.next();
		Catalog cat=new Catalog(name, occ, addr, null, new Date(), null);
		return cat;
	}

	/**
	 * Method to take user input & proceed accordingly .
	 * Asks whether user want to continue the activity or exit from it.
	 * @param sc represents scanner object to get user inputs
	 * @param catImp
	 */
	private static void doContinue(Scanner sc, CatalogImpl catImp) {
		System.out.println("\n Press 1 continue or any other key to exit");
		if(sc.hasNextInt() && sc.nextInt()==ActionType.CONTINUE) {	
			catalogOps(sc, catImp);
		}else {
			System.out.println("Thank you");
			sc.close();
			catImp.closeCatalog();
		}
	}

}
