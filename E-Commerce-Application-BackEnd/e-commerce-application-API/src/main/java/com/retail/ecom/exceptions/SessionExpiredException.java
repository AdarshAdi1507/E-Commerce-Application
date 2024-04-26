package com.retail.ecom.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SessionExpiredException extends RuntimeException {
private String message;
@Override
	public String getMessage() {
		return message;
	}
}
