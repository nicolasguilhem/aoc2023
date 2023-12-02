package fr.ngui.aoc.aoc2023.model;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.ngui.aoc.aoc2023.exceptions.ResourceNotFoundException;

public enum DatasFileType {

	TEST,
	FINAL;
	
	private static final String ROOT_FOLDER = "datas";
	
	public Path getData(String prefixFileName) throws URISyntaxException, ResourceNotFoundException {
		String resourceToLookFor = String.format(ROOT_FOLDER + "/%s_%s", prefixFileName, this.name().toLowerCase());
		URL resourceUrl = getClass().getClassLoader().getResource(resourceToLookFor);
		if (resourceUrl == null) {
			throw new ResourceNotFoundException(resourceToLookFor);
		}
		return Paths.get(resourceUrl.toURI());
	}
}
