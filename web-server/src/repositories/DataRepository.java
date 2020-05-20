package repositories;

import org.hibernate.Session;

public interface DataRepository {
	
	public Session getSession();
	
	//public void save(Object entity);

}
