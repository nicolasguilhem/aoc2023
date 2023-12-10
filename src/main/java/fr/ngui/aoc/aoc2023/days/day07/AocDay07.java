package fr.ngui.aoc.aoc2023.days.day07;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

import fr.ngui.aoc.aoc2023.days.GenericAocDayWithMapper;
import fr.ngui.aoc.aoc2023.model.PartOfDay;

public class AocDay07 extends GenericAocDayWithMapper<Game> {
	
	public AocDay07(int day, String expectedTestResultP1, String expectedFinalResultP1, String expectedTestResultP2,
			String expectedFinalResultP2) {
		super(day, expectedTestResultP1, expectedFinalResultP1, expectedTestResultP2, expectedFinalResultP2);
	}

	@Override
	public Function<String, Game> getMapper(PartOfDay partOfDay) {
		return line -> new Game(line, partOfDay);
	}
	
	@Override
	public String run(PartOfDay partOfDay, Stream<?> datas) {

		Stream<Game> games = (Stream<Game>) datas;
		
		AtomicInteger increment = new AtomicInteger();
		int result = games.sorted(Game.getComparatorByBestWinningLast(partOfDay))
			.peek(game -> {
				log.info(game.toString() + " -> " +increment.incrementAndGet() + " = " + game.getValue(increment.get()));
				})
			.mapToInt(game -> game.getValue(increment.get()))
			.sum();

		return Long.toString(result);

	}
}
