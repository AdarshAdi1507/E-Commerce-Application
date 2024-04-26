package com.retail.ecom.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserNotLoggedInException extends RuntimeException {
private String message;

@Override
	public String getMessage() {
		return message;
	}
}
