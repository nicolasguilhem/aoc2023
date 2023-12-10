package fr.ngui.aoc.aoc2023.days.day03;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EngineLine {
	
	private static final String ANSI_GREEN = "\033[1;32m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_RESET = "\u001B[0m";
	private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d*)");
	private static final Pattern GEAR_PATTERN = Pattern.compile("(\\*)");

	private final String currentLine;
	private final String previousLine;
	private final String nextLine;
	
	private String lineToPrint;

	public EngineLine(String previousLine, String currentLine, String nextLine) {
		super();
		this.lineToPrint = currentLine;
		this.currentLine = currentLine;
		this.previousLine = previousLine;
		this.nextLine = nextLine;
	}

	public String getCurrentLine() {
		return currentLine;
	}

	public String getPreviousLine() {
		return previousLine;
	}

	public String getNextLine() {
		return nextLine;
	}
	
	public int getSumOfNumbersOfCurrentLineWithSymbolArround() {
		return this.getNumbersOfCurrentLineWithSymbolArround()
				.stream().mapToInt(Integer::intValue).sum();
	}
	
	public List<Integer> getNumbersOfCurrentLineWithSymbolArround() {
		
		int indexToSearchNumber = 0;
		int indexForSpecialChars = 0;
		List<Integer> list = new ArrayList<>();
		Matcher matcher = NUMBER_PATTERN.matcher(currentLine);
		while (matcher.find()) {
			String numberAsString = matcher.group(1);
			if (!numberAsString.isEmpty()) {
				
				int indexOfNumber = currentLine.indexOf(numberAsString, indexToSearchNumber);
				
				int endIndexOfNumber = indexOfNumber + numberAsString.length() - 1;
				boolean symbolArroundNumber = isSymbolArroundNumber(indexOfNumber, endIndexOfNumber);
				
				if (symbolArroundNumber) {
					list.add(Integer.parseInt(numberAsString));
				}
				
				String colorToPrint = symbolArroundNumber ? ANSI_GREEN : ANSI_RED;
				
				lineToPrint = lineToPrint.substring(0, indexOfNumber + indexForSpecialChars)
						+ colorToPrint
						+ lineToPrint.substring(indexOfNumber + indexForSpecialChars, endIndexOfNumber + indexForSpecialChars + 1)
						+ ANSI_RESET
						+ lineToPrint.substring(endIndexOfNumber + indexForSpecialChars + 1, lineToPrint.length());
				
				indexForSpecialChars = indexForSpecialChars + colorToPrint.length() + ANSI_RESET.length();
				
				indexToSearchNumber = endIndexOfNumber + 1;
			}
		}
		
		//System.out.println(lineToPrint.replace(".", " "));
		
		return list;
	}
	
	private boolean isSymbolArroundNumber(int startIndexOfNumber, int endIndexOfNumber) {

		int indexBeginSearch = startIndexOfNumber == 0 ? startIndexOfNumber : startIndexOfNumber - 1;
		int indexEndSearch = endIndexOfNumber == currentLine.length() - 1 ? endIndexOfNumber : endIndexOfNumber + 1;
		
		String stringToSearchSymbol = "";
		if (previousLine != null) {
			stringToSearchSymbol += previousLine.substring(indexBeginSearch, indexEndSearch + 1);
		}
		stringToSearchSymbol += currentLine.substring(indexBeginSearch, indexEndSearch + 1);
		if (nextLine != null) {
			stringToSearchSymbol += nextLine.substring(indexBeginSearch, indexEndSearch + 1);
		}
		
		return isSymbolInString(stringToSearchSymbol);
	}
	
	public String toString() {
		return this.lineToPrint + " > " + this.getNumbersOfCurrentLineWithSymbolArround().stream().map(value -> value.toString()).collect(Collectors.joining(" + ")) + " = " + getSumOfNumbersOfCurrentLineWithSymbolArround();
	}
	
	private boolean isSymbolInString(String stringToSearchSymbol) {
		String stringWithoutNumberAndDots = stringToSearchSymbol.replaceAll("\\d|\\.", "");
		return !stringWithoutNumberAndDots.isEmpty();
	}

	public int getSumOfGearRatio() {

		int indexToSearchGear = 0;
		int indexForSpecialChars = 0;
		int sumOfGearRatio = 0;
		Matcher matcher = GEAR_PATTERN.matcher(currentLine);
		while (matcher.find()) {
				
			int indexOfGear = currentLine.indexOf("*", indexToSearchGear);
			
			int gearRatio = gearRatio(indexOfGear);
			sumOfGearRatio += gearRatio;
			
			if (gearRatio != 0) {				
				lineToPrint = lineToPrint.substring(0, indexOfGear + indexForSpecialChars)
						+ ANSI_GREEN
						+ lineToPrint.substring(indexOfGear + indexForSpecialChars, indexOfGear + indexForSpecialChars + 1)
						+ ANSI_RESET
						+ lineToPrint.substring(indexOfGear + indexForSpecialChars + 1, lineToPrint.length());
				
				indexForSpecialChars = indexForSpecialChars + ANSI_GREEN.length() + ANSI_RESET.length();
			}
			
			
			indexToSearchGear = indexOfGear + 1;
		}
		
		System.out.println(lineToPrint.replace(".", " "));
		
		return sumOfGearRatio;
	}

	private int gearRatio(int indexOfGear) {
		
		int indexBeginSearch = indexOfGear == 0 ? indexOfGear : indexOfGear - 1;
		int indexEndSearch = indexOfGear + 2 == currentLine.length() - 1 ? indexOfGear + 1 : indexOfGear + 2;

		List<Integer> numbersOfGear = new ArrayList<>();
		
		if (previousLine != null) {

			int indexToSearchCompleteInPrevious = indexBeginSearch;
			String stringToSearchNumberInPreviousLine = previousLine.substring(indexBeginSearch, indexEndSearch);
			Matcher matcherPreviousLine = NUMBER_PATTERN.matcher(stringToSearchNumberInPreviousLine);
			while (matcherPreviousLine.find()) {
				String numberAsString = matcherPreviousLine.group(1);
				if (!numberAsString.isEmpty()) {
					
					numberAsString = completeNumberWithPreviousAndNextChar(previousLine, indexToSearchCompleteInPrevious, numberAsString);
					numbersOfGear.add(Integer.parseInt(numberAsString));
					
					indexToSearchCompleteInPrevious = indexToSearchCompleteInPrevious + numberAsString.length() - 1;
				}
			}
		}

		int indexToSearchCompleteInCurrent = indexBeginSearch;
		String  stringToSearchNumberInCurrentLine = currentLine.substring(indexBeginSearch, indexEndSearch);
		Matcher matcherCurrentLine = NUMBER_PATTERN.matcher(stringToSearchNumberInCurrentLine);
		while (matcherCurrentLine.find()) {
			String numberAsString = matcherCurrentLine.group(1);
			if (!numberAsString.isEmpty()) {

				numberAsString = completeNumberWithPreviousAndNextChar(currentLine, indexToSearchCompleteInCurrent, numberAsString);
				numbersOfGear.add(Integer.parseInt(numberAsString));
				
				indexToSearchCompleteInCurrent = indexToSearchCompleteInCurrent + numberAsString.length() - 1;
			}
		}
		
		if (nextLine != null) {

			int indexToSearchCompleteInNext = indexBeginSearch;
			String stringToSearchNumberInNextLine = nextLine.substring(indexBeginSearch, indexEndSearch);
			Matcher matcherNexLine = NUMBER_PATTERN.matcher(stringToSearchNumberInNextLine);
			while (matcherNexLine.find()) {
				String numberAsString = matcherNexLine.group(1);
				if (!numberAsString.isEmpty()) {

					numberAsString = completeNumberWithPreviousAndNextChar(nextLine, indexToSearchCompleteInNext, numberAsString);
					numbersOfGear.add(Integer.parseInt(numberAsString));
					
					indexToSearchCompleteInNext = indexToSearchCompleteInNext + numberAsString.length() - 1;
				}
			}
		}
		
		if (numbersOfGear.size() == 2) {
			return numbersOfGear.getFirst() * numbersOfGear.getLast();
		}

		return 0;
	}

	private String completeNumberWithPreviousAndNextChar(String line, int indexBeginSearch, String numberAsString) {
		String completedNumber = numberAsString;
		int startingIndexOfNumber = line.indexOf(numberAsString, indexBeginSearch);
		if (startingIndexOfNumber > 0) {
			String previousCharOfNumber = line.substring(startingIndexOfNumber - 1, startingIndexOfNumber);
			if ("0123456789".contains(previousCharOfNumber)) {
				completedNumber = previousCharOfNumber + completedNumber;
				if (startingIndexOfNumber > 1) {
					String previousPreviousCharOfNumber = line.substring(startingIndexOfNumber - 2, startingIndexOfNumber - 1);
					if ("0123456789".contains(previousPreviousCharOfNumber)) {
						completedNumber = previousPreviousCharOfNumber + completedNumber;
					}
				}
			}
		}

		int endingIndexOfNumber = startingIndexOfNumber + numberAsString.length() - 1;
		if (endingIndexOfNumber < line.length() - 1) {
			String nextCharOfNumber = line.substring(endingIndexOfNumber + 1, endingIndexOfNumber + 2);
			if ("0123456789".contains(nextCharOfNumber)) {
				completedNumber = completedNumber + nextCharOfNumber;
				if (endingIndexOfNumber < line.length() - 2) {
					String nextNextCharOfNumber = line.substring(endingIndexOfNumber + 2, endingIndexOfNumber + 3);
					if ("0123456789".contains(nextNextCharOfNumber)) {
						completedNumber = completedNumber + nextNextCharOfNumber;
					}
				}
			}
		}
		
		return completedNumber;
	}
}
