package fr.ngui.aoc.aoc2023.exceptions;

public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 2165487448548510542L;

	public ResourceNotFoundException(String resource) {
		super(String.format("Resource '%s' not found.", resource));
	}
}
