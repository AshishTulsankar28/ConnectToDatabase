package views;

import org.springframework.http.HttpStatus;
/**
 * Generic response view model to make response data consistent for the client application
 * @author Ashish Tulsankar
 *
 */
public class ResponseVM {
	
	private HttpStatus status;
	private Object responseData;
	

	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public Object getResponseData() {
		return responseData;
	}
	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}
	
	

}
