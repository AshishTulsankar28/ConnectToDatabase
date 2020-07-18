/**
 * 
 */
package services;
import org.springframework.stereotype.Service;


/**
 * @author Ashish Tulsankar
 *
 */
@Service
public class TestServiceImpl implements TestService {

	@Override
	public String getAppName() {
		return "JUnit5";
	}


}
