package catalog;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class CatalogImpl implements CatlogInterface {

	
	
	
	@Override
	public void viewCatalog(boolean all, int index) {
		Session s1=HibernateUtil.getSessionFactory().openSession();
		if(all) {
			List<Catalog> r1=s1.createQuery("FROM Catalog").getResultList();
			for (Catalog catalog : r1) {
				System.out.println("UID: "+catalog.getId()+" Name: "+catalog.getName());
			}	
		}else {
			Catalog catalog=s1.get(Catalog.class, index);
			System.out.println("UID: "+catalog.getId()+" Name: "+catalog.getName());
		}
		
		s1.close();
		
	}

	@Override
	public boolean modifyCatalog(int action,Catalog cat) {
		Session s1=HibernateUtil.getSessionFactory().openSession();
		Transaction t1=s1.beginTransaction();
		
		switch (action) {
		case ActionType.ADD_RECORD:	
			
			s1.save(cat);
			t1.commit();
			break;
			
		case ActionType.UPDATE_RECORD:
			
			Catalog catToUpdate= s1.get(Catalog.class, cat.getId());
			catToUpdate.setName(cat.getName());
			catToUpdate.setAddress(cat.getAddress());
			catToUpdate.setOccupation(cat.getOccupation());
			catToUpdate.setDob(cat.getDob());
			catToUpdate.setEmail(cat.getEmail());
			catToUpdate.setCtctNo(cat.getCtctNo());
			t1.commit();
			
			break;
				
		case ActionType.DELETE_RECORD:
			
			Catalog delRecord=s1.get(Catalog.class, cat.getId());
			s1.delete(delRecord);
			t1.commit();
			
		default:
			break;
		}
		
		
		return false;
	}

	@Override
	public void closeCatalog() {
		HibernateUtil.shutdown();
		
	}

}
