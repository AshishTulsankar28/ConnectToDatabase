package views;

import org.springframework.http.HttpStatus;

public class ResponseData {

	private Object response;
	private HttpStatus status;


	public ResponseData(Object response) {
		super();
		this.response = response;
		this.status = response==null?HttpStatus.NO_CONTENT:HttpStatus.OK;
	}

	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;

		setStatus(response==null?HttpStatus.NO_CONTENT:HttpStatus.OK);

	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}



}
