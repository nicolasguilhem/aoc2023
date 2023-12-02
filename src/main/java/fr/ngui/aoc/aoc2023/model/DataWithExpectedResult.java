package fr.ngui.aoc.aoc2023.model;

public class DataWithExpectedResult extends InputDatas {

	private final String expectedResultPart1;
	private final String expectedResultPart2;
	
	public DataWithExpectedResult(DatasFileType dataFileType, String expectedResultPart1, String expectedResultPart2) {
		super(dataFileType);
		this.expectedResultPart1 = expectedResultPart1;
		this.expectedResultPart2 = expectedResultPart2;
	}

	public String getExpectedResult(PartOfDay partOfDay) {
		return partOfDay == PartOfDay.ONE ? expectedResultPart1 : expectedResultPart2;
	}
}
