package fr.ngui.aoc.aoc2023.days.day02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetOfCubes {

	private static final Pattern NUBER_OF_CUBES_PATTERN = Pattern.compile("([0-9]*) (red|blue|green)");

	private static final int MAX_RED = 12;
	private static final int MAX_GREEN = 13;
	private static final int MAX_BLUE = 14;

	private int numberOfBlue;
	private int numberOfRed;
	private int numberOfGreen;

	public SetOfCubes(String set) {

		Matcher matcher = NUBER_OF_CUBES_PATTERN.matcher(set);
		while (matcher.find()) {
			int numberOfColor = Integer.parseInt(matcher.group(1));
			String color = matcher.group(2);
			switch (color) {
				case "red":
					numberOfRed = numberOfColor;
					break;
				case "blue":
					numberOfBlue = numberOfColor;
					break;
				case "green":
					numberOfGreen = numberOfColor;
					break;
				default:
			}
		}
	}

	public int getNumberOfBlue() {
		return numberOfBlue;
	}

	public int getNumberOfRed() {
		return numberOfRed;
	}

	public int getNumberOfGreen() {
		return numberOfGreen;
	}
	
	public boolean isPossible() {
		return numberOfBlue <= MAX_BLUE && numberOfRed <= MAX_RED && numberOfGreen <= MAX_GREEN;
	}
}
