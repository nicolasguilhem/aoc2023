package fr.ngui.aoc.aoc2023.exceptions;

import java.nio.file.Path;

public class ReadingFileException extends Exception {

	private static final long serialVersionUID = -9082341499922239686L;

	public ReadingFileException(Path filePath, Throwable cause) {
		super(String.format("Error reading file '%s'", filePath.getFileName()), cause);
	}

}
