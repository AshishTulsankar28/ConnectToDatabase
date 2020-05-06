/**
 * 
 */
package catalog;

/**
 * @author Ashish Tulsankar
 *
 */
public interface CatlogInterface {
	/**
	 * View records from catalog
	 * @param all true if whole catalog needs to be checked else 
	 * @param index primary key i.e. search value of catalog
	 */
	public void viewCatalog(boolean all,int index);
	
	/**
	 * Modify the catalog
	 * @param action 1.ADD 2.MODIFY 3.DELETE
	 * @return success of action as true or false
	 */
	public boolean modifyCatalog(int action,Catalog obj);
	
	public void closeCatalog();

}
