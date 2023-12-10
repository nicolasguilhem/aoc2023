package fr.ngui.aoc.aoc2023.days.day09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fr.ngui.aoc.aoc2023.days.GenericAocDayWithMapper;
import fr.ngui.aoc.aoc2023.model.PartOfDay;

public class AocDay09 extends GenericAocDayWithMapper<IntStream> {
	
	public AocDay09(int day, String expectedTestResultP1, String expectedFinalResultP1, String expectedTestResultP2,
			String expectedFinalResultP2) {
		super(day, expectedTestResultP1, expectedFinalResultP1, expectedTestResultP2, expectedFinalResultP2);
	}
	
	@Override
	public String run(PartOfDay partOfDay, Stream<?> datas) {

		Stream<IntStream> lines = (Stream<IntStream>) datas;
		
		int result = lines.mapToInt(line -> AocDay09.getPrediction(line, accessorNumberToProcess(partOfDay), partOperator(partOfDay)))
			.sum();

		return Long.toString(result);
	}

	@Override
	public Function<String, IntStream> getMapper(PartOfDay partOfDay) {
		return line -> Arrays.stream(line.split(" "))
						.mapToInt(Integer::parseInt);
	}
	
	private static int getPrediction(IntStream values, ToIntFunction<List<Integer>> accessorNumberToProcess, IntBinaryOperator partOperator) {
		List<Integer> listValues = values.mapToObj(Integer::valueOf).toList();
				
		int numberToProcess = accessorNumberToProcess.applyAsInt(listValues);
		
		List<Integer> newList = new ArrayList<>();
		for (int i = 1; i < listValues.size(); i++) {
			newList.add(listValues.get(i) - listValues.get(i-1));
		}

		if (newList.stream().allMatch(value -> value == 0)) {
			return numberToProcess;
		}
		
		int prediction = getPrediction(newList.stream().mapToInt(Integer::intValue), accessorNumberToProcess, partOperator);
		return partOperator.applyAsInt(numberToProcess, prediction);
	}

	private static IntBinaryOperator partOperator(PartOfDay partOfDay) {
		return (a, b) -> partOfDay == PartOfDay.ONE ?  a + b : a - b;
	}
	
	private static ToIntFunction<List<Integer>> accessorNumberToProcess(PartOfDay partOfDay) {
		return values -> partOfDay == PartOfDay.ONE ? values.get(values.size() - 1) : values.get(0);
	}
}
