package fr.ngui.aoc.aoc2023.days.day02;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.assertj.core.util.Arrays;

import fr.ngui.aoc.aoc2023.model.PartOfDay;

public class Game {

	private static final Pattern ID_PATTERN = Pattern.compile("Game (\\d*)");

	private final int id;
	private final List<SetOfCubes> setsOfCubes;

	public Game(String line) {
		super();
		Matcher matcher = ID_PATTERN.matcher(line);
		matcher.find();
		String gameId = matcher.group(1);
		this.id = Integer.parseInt(gameId);
		this.setsOfCubes = buildSetsOfCubes(line);
	}

	private List<SetOfCubes> buildSetsOfCubes(String line) {
		String[] sets = line.split(":");
		String[] setOfCube = sets[1].split(";");
		return Arrays.asList(setOfCube).stream().map(s -> (String) s).map(SetOfCubes::new).toList();
	}

	private int getId() {
		return id;
	}
	
	public boolean isPossible(PartOfDay partOfDay) {
		return partOfDay == PartOfDay.TWO || setsOfCubes.stream().allMatch(SetOfCubes::isPossible);
	}
	
	private int getPower() {
		int maxBlue = setsOfCubes.stream().mapToInt(SetOfCubes::getNumberOfBlue).max().getAsInt();
		int maxRed = setsOfCubes.stream().mapToInt(SetOfCubes::getNumberOfRed).max().getAsInt();
		int maxGreen = setsOfCubes.stream().mapToInt(SetOfCubes::getNumberOfGreen).max().getAsInt();
		return maxBlue * maxRed * maxGreen;
	}
	
	public int getValue(PartOfDay partOfDay) {
		return partOfDay == PartOfDay.ONE ? getId() : getPower();
	}
}
