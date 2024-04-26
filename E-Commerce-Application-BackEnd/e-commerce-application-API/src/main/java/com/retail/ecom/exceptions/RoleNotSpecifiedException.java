package com.retail.ecom.exceptions;

public class RoleNotSpecifiedException extends RuntimeException {
private String message ;

public  RoleNotSpecifiedException(String message ) {
	this.message= message;
}

@Override
	public String getMessage() {
		return message;
	}
}
