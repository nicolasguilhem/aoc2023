package fr.ngui.aoc.aoc2023.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URISyntaxException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import fr.ngui.aoc.aoc2023.exceptions.ResourceNotFoundException;
import fr.ngui.aoc.aoc2023.model.DatasFileType;
import fr.ngui.aoc.aoc2023.model.InputDatas;

class InputDatasTest {

	@Test
	void test_getFilePath_Test() throws URISyntaxException, ResourceNotFoundException {
		InputDatas inputDatas = new InputDatas(DatasFileType.TEST);
		Path result = inputDatas.getFilePath(2);

		assertThat(result).isNotNull();
		assertThat(result.getFileName()).hasToString("D02_test");
	}

	@Test
	void test_getFilePath_Final() throws URISyntaxException, ResourceNotFoundException {
		InputDatas inputDatas = new InputDatas(DatasFileType.FINAL);
		Path result = inputDatas.getFilePath(2);

		assertThat(result).isNotNull();
		assertThat(result.getFileName()).hasToString("D02_final");
	}

	@Test
	void test_getFilePath_ResourceNotFoundException() throws URISyntaxException, ResourceNotFoundException {
		InputDatas inputDatas = new InputDatas(DatasFileType.TEST);

		assertThrows(ResourceNotFoundException.class, () -> inputDatas.getFilePath(999));
	}
}
