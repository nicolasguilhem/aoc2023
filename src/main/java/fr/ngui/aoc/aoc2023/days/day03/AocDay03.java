package fr.ngui.aoc.aoc2023.days.day03;

import java.util.List;
import java.util.stream.Stream;

import fr.ngui.aoc.aoc2023.days.GenericAocDay;
import fr.ngui.aoc.aoc2023.model.PartOfDay;

public class AocDay03 extends GenericAocDay {

	public AocDay03(int day, String expectedTestResultP1, String expectedFinalResultP1, String expectedTestResultP2, String expectedFinalResultP2) {
		super(day, expectedTestResultP1, expectedFinalResultP1, expectedTestResultP2, expectedFinalResultP2);
	}
	
	@Override
	public String run(PartOfDay partOfDay, Stream<?> datas) {
		Stream<String> lines = (Stream<String>) datas;
		
		EngineLineBuilder builder = new EngineLineBuilder();
		
		List<EngineLine> list = lines.map(builder::build).filter(o -> o != null).toList();
		EngineLine lastLine = new EngineLine(list.getLast().getCurrentLine(), list.getLast().getNextLine(), null);
		
		int result = list.stream()
			//.peek(line -> System.out.println(line.toString()))
			.mapToInt(engineLine -> partOfDay == PartOfDay.ONE ? engineLine.getSumOfNumbersOfCurrentLineWithSymbolArround() : engineLine.getSumOfGearRatio())
			.sum();
		
		//System.out.println(lastLine.toString());
		
		result += partOfDay == PartOfDay.ONE ? lastLine.getSumOfNumbersOfCurrentLineWithSymbolArround() : lastLine.getSumOfGearRatio();
		
		return Long.toString(result);

	}
}
