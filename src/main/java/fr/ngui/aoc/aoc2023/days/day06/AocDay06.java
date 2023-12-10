package fr.ngui.aoc.aoc2023.days.day06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import fr.ngui.aoc.aoc2023.days.GenericAocDay;
import fr.ngui.aoc.aoc2023.model.PartOfDay;

public class AocDay06 extends GenericAocDay {
	
	private List<Race> listRace;

	public AocDay06(int day, String expectedTestResultP1, String expectedFinalResultP1, String expectedTestResultP2,
			String expectedFinalResultP2) {
		super(day, expectedTestResultP1, expectedFinalResultP1, expectedTestResultP2, expectedFinalResultP2);
	}

	@Override
	public String run(PartOfDay partOfDay, Stream<?> datas) {

		initDatas(datas, partOfDay);
		
		long result = listRace.stream()
			.peek(race -> log.info(race.toString()))
			.mapToLong(Race::getNumberOfWinningWays)
			.reduce(1, (a, b) -> a * b);

		return Long.toString(result);

	}

	private void initDatas(Stream<?> datas, PartOfDay partOfDay) {

		List<String> lines = ((Stream<String>) datas)
				.map(line -> line.split(":")[1])
				.toList();
		
		IntFunction<List<Long>> getValuesFromLine = index -> {
			List<String> dataToProcess;
			if (partOfDay == PartOfDay.ONE) {
				dataToProcess = Arrays.asList(lines.get(index).split(" "));				
			} else {
				dataToProcess = Arrays.asList(lines.get(index).replace(" ", ""));
			}
			return dataToProcess.stream()
				.filter(value -> !value.isBlank())
				.map(Long::parseLong)
				.toList();
		};

		List<Long> listTimes = getValuesFromLine.apply(0);
		List<Long> listRecords = getValuesFromLine.apply(1);
		
		listRace = new ArrayList<>();
		for (int i=0; i<listTimes.size(); i++) {
			listRace.add(new Race(listTimes.get(i), listRecords.get(i)));
		}
	}

	private record Race(long totalTime, long recordDistance) {
		
		public long getNumberOfWinningWays() {
			return getLastWayWinning() - getFirstWayWinning() + 1;
		}
		
		private long getFirstWayWinning() {
			for (int i=1; i<totalTime; i++) {
				if (getDistanceReachForTime(i) > recordDistance) {
					return i;
				}
			}
			return 0;
		}

		private long getLastWayWinning() {
			for (long i=totalTime; i>0; i--) {
				if (getDistanceReachForTime(i) > recordDistance) {
					return i;
				}
			}
			return 0;
		}
		
		private long getDistanceReachForTime(long timeHolding) {
			long remainingTimeAfterHolding = totalTime - timeHolding;
			return timeHolding * remainingTimeAfterHolding;
		}
		
		public String toString() {
			return totalTime + " - " + recordDistance 
					+ " -> " + getFirstWayWinning() + " - " + getLastWayWinning() 
					+ " = " + getNumberOfWinningWays(); 
		}
	}
	
}
