package fr.ngui.aoc.aoc2023.days.day04;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;

public class Card {

	private final List<Integer> listWinnings;
	private final List<Integer> listNumbers;
	private int numberOfCopies = 1;
	
	public Card(String line) {
		
		String[] split1 = line.split("\\|");
		String[] split2 = split1[0].split(":");
		String[] winingsArray = split2[1].split(" ");
		String[] numbersArray = split1[1].split(" ");
		
		listWinnings = new ArrayList<>(Arrays.asList(winingsArray).stream()
			.map(String.class::cast)
			.filter(value -> !value.isEmpty())
			.map(Integer::parseInt)
			.toList());

		listNumbers = new ArrayList<>(Arrays.asList(numbersArray).stream()
			.map(String.class::cast)
			.filter(value -> !value.isEmpty())
			.map(Integer::parseInt)
			.toList());
	}

	public int getNumberOfPoint() {
		return getNumberOfPoint(countWinningNumbersOfCard());
	}
	
	public int getNumberOfPoint(int numberOfWinnings) {
		if (numberOfWinnings == 0) {
			return 0;
		}
		int result = 1;
		for (int i=1; i<numberOfWinnings; i++) {
			result = result * 2;
		}
		return result;
	}
	
	public int countWinningNumbersOfCard() {
		listNumbers.retainAll(listWinnings);
		return listNumbers.size();
	}
	
	public void addCopyToCurrentAndExtraCopiesToNextCards(int indexCurrentCard, List<Card> listCards) {
		numberOfCopies++;
		int countWinningNumbersOfCard = countWinningNumbersOfCard();
		if (countWinningNumbersOfCard > 0) {
			for (int j=1; j<countWinningNumbersOfCard + 1 && indexCurrentCard+j < listCards.size(); j++) {
				listCards.get(indexCurrentCard+j).addCopyToCurrentAndExtraCopiesToNextCards(indexCurrentCard+j, listCards);
			}
		}
	}
	
	public int getNumberOfCopies() {
		return numberOfCopies;
	}
	
	public String toString() {
		return listWinnings.stream().map(value -> value.toString()).collect(Collectors.joining(", "))
				+ " | "
				+ listNumbers.stream().map(value -> value.toString()).collect(Collectors.joining(", "))
				+ " => "
				+ countWinningNumbersOfCard()
				+ " : "
				+ getNumberOfPoint(countWinningNumbersOfCard())
				+ " x "
				+ getNumberOfCopies();
	}
}
