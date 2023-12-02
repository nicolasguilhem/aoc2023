package fr.ngui.aoc.aoc2023.days.day00;

import java.util.stream.Stream;

import fr.ngui.aoc.aoc2023.days.GenericAocDay;
import fr.ngui.aoc.aoc2023.model.PartOfDay;

public class AocDay00 extends GenericAocDay {

	public AocDay00(int day, String expectedTestResultP1, String expectedFinalResultP1, String expectedTestResultP2, String expectedFinalResultP2) {
		super(day, expectedTestResultP1, expectedFinalResultP1, expectedTestResultP2, expectedFinalResultP2);
	}
	
	@Override
	public String run(PartOfDay partOfDay, Stream<?> datas) {
		var sum = ((Stream<String>) datas).mapToLong(Long::parseLong).sum();
		return Long.toString(sum);
	}
}
