package com.assignment.accountRegistrationAPI.exception;

public class AccountRegistrationAPIException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountRegistrationAPIException(String resourceName, String message) {
		super(String.format("%s not found with %s", resourceName, message)); 
		
	}
	
	public AccountRegistrationAPIException(String message) {
		super(String.format(message)); 
	}

}
