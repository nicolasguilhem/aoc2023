package fr.ngui.aoc.aoc2023.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;

import fr.ngui.aoc.aoc2023.exceptions.ReadingFileException;

public final class FileReaderUtils {

	private FileReaderUtils() {
	}

	public static Stream<String> readFileAsStreamString(Path path) throws ReadingFileException {
		
		if (path == null) {
			throw new IllegalArgumentException("path must not be null");
		}
		
		try {
			return Files.lines(path, StandardCharsets.UTF_8);
		} catch (IOException ioe) {
			throw new ReadingFileException(path, ioe);
		}
	}

    public static <T> Stream<T> readFileAsStreamObj(Path path, Function<String, T> mapper) throws ReadingFileException {

		if (path == null) {
			throw new IllegalArgumentException("path must not be null");
		}
		if (mapper == null) {
			throw new IllegalArgumentException("mapper must not be null");
		}
		
    	return FileReaderUtils.readFileAsStreamString(path).map(mapper);
    }
}
