package fr.ngui.aoc.aoc2023.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import fr.ngui.aoc.aoc2023.exceptions.ReadingFileException;
import fr.ngui.aoc.aoc2023.exceptions.ResourceNotFoundException;
import fr.ngui.aoc.aoc2023.model.AocDay;
import fr.ngui.aoc.aoc2023.model.DatasFileType;
import fr.ngui.aoc.aoc2023.model.PartOfDay;

class AocDayTest {

	@Test
	void test_getDatas() throws URISyntaxException, ResourceNotFoundException, ReadingFileException {

		AocDay aocDay = new AocDay(2, "32", "121", "32", "121");
		Stream<String> result = aocDay.getDatas(DatasFileType.TEST);
		assertThat(result).isNotNull().hasSize(3);
	}

	@Test
	void test_getDatasAsObject() throws URISyntaxException, ResourceNotFoundException, ReadingFileException {

		AocDay aocDay = new AocDay(2, "32", "121", "32", "121");
		Stream<Long> result = aocDay.getDatasAsObject(DatasFileType.FINAL, Long::parseLong);
		assertThat(result).isNotNull().hasSize(5);
	}

	@Test
	void test_getExpectedResult() throws URISyntaxException, ResourceNotFoundException, ReadingFileException {

		AocDay aocDay = new AocDay(2, "32", "121", "32", "121");
		String result = aocDay.getExpectedResult(DatasFileType.FINAL, PartOfDay.ONE);
		assertThat(result).isEqualTo("121");
	}
}