package fr.ngui.aoc.aoc2023.days.day02;

import java.util.function.Function;
import java.util.stream.Stream;

import fr.ngui.aoc.aoc2023.days.GenericAocDayWithMapper;
import fr.ngui.aoc.aoc2023.model.PartOfDay;

public class AocDay02 extends GenericAocDayWithMapper<Game> {

	public AocDay02(int day, String expectedTestResultP1, String expectedFinalResultP1, String expectedTestResultP2, String expectedFinalResultP2) {
		super(day, expectedTestResultP1, expectedFinalResultP1, expectedTestResultP2, expectedFinalResultP2);
	}
	
	@Override
	public String run(PartOfDay partOfDay, Stream<?> datas) {
		Stream<Game> games = (Stream<Game>) datas;
		
		int result = games.filter(game -> game.isPossible(partOfDay))
				.mapToInt(game -> game.getValue(partOfDay))
				.sum();
		
		return Long.toString(result);

	}

	@Override
	public Function<String, Game> getMapper(PartOfDay partOfDay) {
		return Game::new;
	}
	
}
