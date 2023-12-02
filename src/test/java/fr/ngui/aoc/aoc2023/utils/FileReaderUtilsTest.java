package fr.ngui.aoc.aoc2023.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import fr.ngui.aoc.aoc2023.exceptions.ReadingFileException;
import fr.ngui.aoc.aoc2023.utils.FileReaderUtils;

class FileReaderUtilsTest {

	@Test
	void test_readFileAsStreamString_IllegalArgumentException() throws ReadingFileException {
		assertThrows(IllegalArgumentException.class, () -> FileReaderUtils.readFileAsStreamString(null));
	}

	@Test
	void test_readFileAsStreamString_ReadingFileException() throws ReadingFileException {
		assertThrows(ReadingFileException.class, () -> FileReaderUtils.readFileAsStreamString(Path.of("invalid_path")));
	}

	@Test
	void test_readFileAsStreamString_Ok() throws ReadingFileException, URISyntaxException {
		Path resourceFromClassPath = Paths.get(getClass().getClassLoader().getResource("oneLineTestFile").toURI());
		Stream<String> result = FileReaderUtils.readFileAsStreamString(resourceFromClassPath);
		assertThat(result).isNotNull().hasSize(1);
	}

	@Test
	void test_readFileAsStreamObj_IllegalArgumentException() throws ReadingFileException {
		assertThrows(IllegalArgumentException.class, () -> FileReaderUtils.readFileAsStreamObj(null, null));
	}

	@Test
	void test_readFileAsStreamObj_ReadingFileException() throws ReadingFileException {
		Path resourceFromClassPath = Path.of("invalid_path");
		assertThrows(IllegalArgumentException.class, () -> FileReaderUtils.readFileAsStreamObj(resourceFromClassPath, null));
	}

	@Test
	void test_readFileAsStreamObj_Ok() throws ReadingFileException, URISyntaxException {
		Path resourceFromClassPath = Paths.get(getClass().getClassLoader().getResource("oneLineTestFile").toURI());
		Stream<String> result = FileReaderUtils.readFileAsStreamObj(resourceFromClassPath, (line) -> line);
		assertThat(result).isNotNull().hasSize(1);
	}
}
