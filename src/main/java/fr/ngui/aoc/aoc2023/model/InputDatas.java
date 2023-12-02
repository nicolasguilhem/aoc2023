package fr.ngui.aoc.aoc2023.model;

import java.net.URISyntaxException;
import java.nio.file.Path;

import fr.ngui.aoc.aoc2023.exceptions.ResourceNotFoundException;

public class InputDatas {

	private final DatasFileType dataFileType;

	public InputDatas(DatasFileType dataFileType) {
		super();
		this.dataFileType = dataFileType;
	}

	public Path getFilePath(int dayNumber) throws URISyntaxException, ResourceNotFoundException {
		return this.dataFileType.getData(String.format("D%02d", dayNumber));
	}
}
