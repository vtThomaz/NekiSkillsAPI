package br.com.neki.neki_skills.exception;

public class NoSuchElementFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NoSuchElementFoundException(String message) {
		super(message);
	}
}