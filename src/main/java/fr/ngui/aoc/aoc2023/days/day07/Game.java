package fr.ngui.aoc.aoc2023.days.day07;

import java.util.Comparator;

import fr.ngui.aoc.aoc2023.model.PartOfDay;

public record Game(Hand hand, int bid) {

	private static final String CARDS_LIST_ORDERED_BY_VALUE_PART_ONE = "23456789TJQKA";
	private static final String CARDS_LIST_ORDERED_BY_VALUE_PART_TWO = "J23456789TQKA";

	public Game(String line, PartOfDay partOfDay) {
		this(new Hand(line.split(" ")[0], partOfDay), Integer.parseInt(line.split(" ")[1]));
	}
	
	public int getValue(int winningPosition) {
		return bid * winningPosition;
	}
	
	public static final Comparator<Game> getComparatorByBestWinningLast(PartOfDay partOfDay) {
		return (game2, game1) -> {
		int compareBestWinnings = game1.hand.getBestWinningType(partOfDay).compareTo(game2.hand.getBestWinningType(partOfDay));
		if (compareBestWinnings != 0) {
			return compareBestWinnings;
		}
		
		String cardsListByValue = partOfDay == PartOfDay.ONE ? CARDS_LIST_ORDERED_BY_VALUE_PART_ONE : CARDS_LIST_ORDERED_BY_VALUE_PART_TWO;
		
		for (int i=0; i < game1.hand.cards.length; i++) {
			int diff = cardsListByValue.indexOf(game2.hand.cards[i])
					- cardsListByValue.indexOf(game1.hand.cards[i]);
			if (diff != 0) {
				return diff;
				}
			}
			return 0;
		};
	}
	
	private record Hand(char[] cards, PartOfDay partOfDay) {

		public Hand(String cardListAsString, PartOfDay partOfDay) {
			this(cardListAsString.toCharArray(), partOfDay);
		}
		
		public String toString() {
			return new String(cards) + " -> " + getBestWinningType(partOfDay);
		}
		
		public WinningType getBestWinningType(PartOfDay partOfDay) {
			return WinningType.bestWinningTypeFromHand(partOfDay, cards);
		}
	}
}
