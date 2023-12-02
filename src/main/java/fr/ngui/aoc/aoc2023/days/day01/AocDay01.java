package fr.ngui.aoc.aoc2023.days.day01;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import fr.ngui.aoc.aoc2023.days.GenericAocDay;
import fr.ngui.aoc.aoc2023.model.PartOfDay;

public class AocDay01 extends GenericAocDay {

	private static final String DIGIT_AS_STRING_REGEXP = "(one)|(two)|(three)|(four)|(five)|(six)|(seven)|(eight)|(nine)";
	private static final Pattern DIGIT_PATTERN_PART_ONE = Pattern.compile("(?=(\\d))");
	private static final Pattern DIGIT_PATTERN_PART_TWO = Pattern.compile("(?=(\\d|"+DIGIT_AS_STRING_REGEXP+"))");
	private static final Pattern DIGIT_AS_STRING_PATTERN = Pattern.compile(DIGIT_AS_STRING_REGEXP);

	public AocDay01(int day, String expectedTestResultP1, String expectedFinalResultP1, String expectedTestResultP2, String expectedFinalResultP2) {
		super(day, expectedTestResultP1, expectedFinalResultP1, expectedTestResultP2, expectedFinalResultP2);
	}
	
	@Override
	public String run(PartOfDay partOfDay, Stream<?> datas) {
		return Long.toString(this.sumAllLinesValues(datas, partOfDay));
	}
	
	private int sumAllLinesValues(Stream<?> datas, PartOfDay partOfDay) {
		return datas.mapToInt(line -> this.getLineValue((String) line, partOfDay)).sum();
	}
	
	private int getLineValue(String line, PartOfDay partOfDay) {
		Pattern pattern = partOfDay == PartOfDay.ONE ? DIGIT_PATTERN_PART_ONE : DIGIT_PATTERN_PART_TWO;
		Matcher matcher = pattern.matcher(line);
		boolean found = matcher.find();
		String firstDigit = found ? matcher.group(1) : "0";
		String lastDigit = "";
		while (matcher.find()) {
			lastDigit = matcher.group(1);
		}

		Matcher matcherDigitAsString = DIGIT_AS_STRING_PATTERN.matcher(firstDigit);
		if (matcherDigitAsString.matches()) {			
			firstDigit = convertStringValueToNumericValue(firstDigit);
		}

		Matcher matcherDigitAsString2 = DIGIT_AS_STRING_PATTERN.matcher(lastDigit);
		if (matcherDigitAsString2.matches()) {			
			lastDigit = convertStringValueToNumericValue(lastDigit);
		}
		
		if (lastDigit.equals("")) {
			lastDigit = firstDigit;
		}

		return Integer.parseInt(firstDigit + lastDigit);
	}
	
	private String convertStringValueToNumericValue(String stringValue) {
		return switch (stringValue) {
			case "one" -> "1";
			case "two" -> "2";
			case "three" -> "3";
			case "four" -> "4";
			case "five" -> "5";
			case "six" -> "6";
			case "seven" -> "7";
			case "eight" -> "8";
			case "nine" -> "9";
			default -> "nan";
		};
	}
}
