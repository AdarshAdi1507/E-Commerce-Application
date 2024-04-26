package com.retail.ecom.exceptions;

public class OTPdoesnotmatch extends RuntimeException {
private String message ;

public OTPdoesnotmatch(String message) {
	this.message= message;
}
@Override
	public String getMessage() {
		return message;
	}
}
