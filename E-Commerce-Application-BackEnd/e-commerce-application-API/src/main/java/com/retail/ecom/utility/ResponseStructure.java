package com.retail.ecom.utility;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class ResponseStructure<T> {
	private int statuscode;
	private String message;
	private T body;
	
	public ResponseStructure<T> setStatuscode(int statuscode) {
		this.statuscode = statuscode;
		return this;
	}
	
	public ResponseStructure<T> setMessage(String message) {
		this.message = message;
		return this;
	}
	
	public ResponseStructure<T> setBody(T body) {
		this.body = body;
		return this;
	}	

}
