package com.retail.ecom.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidUserCredentials extends RuntimeException {
private String message;

@Override
	public String getMessage() {
		return message;
	}
}
