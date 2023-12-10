package fr.ngui.aoc.aoc2023.days.day07;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;

import fr.ngui.aoc.aoc2023.model.PartOfDay;

public enum WinningType {

	FIVE_OF_KIND(PredicateUtils.FIVE_SAME_CHAR),
	FOUR_OF_KIND(PredicateUtils.FOUR_SAME_CHAR),
	FULL_HOUSE(PredicateUtils.THREE_AND_TWO_SAME_CHAR),
	THREE_OF_KIND(PredicateUtils.THREE_SAME_CHAR),
	TWO_PAIR(PredicateUtils.DOUBLE_TWO_SAME_CHAR),
	ONE_PAIR(PredicateUtils.TWO_SAME_CHAR),
	HIGH_CARD(hand -> true);

	private WinningType(Predicate<char[]> match) {
		this.match = match;
	}

	public final Predicate<char[]> match;

	public static final char JOKER = 'J';
	
	public static WinningType bestWinningTypeFromHand(PartOfDay partOfDay, char[] hand) {
		
		if (partOfDay == PartOfDay.TWO) {
			Stream<String> charsWithoutJoker = new String(hand).chars()
					.filter(ch -> ch != JOKER)
					.distinct()
					.mapToObj(charToReplaceJocker -> (new String(hand)).replace(JOKER, (char) charToReplaceJocker));
			
			Optional<WinningType> potentialFirst = charsWithoutJoker
					.<WinningType>map(handToTest -> bestWinningTypeFromHand(PartOfDay.ONE, handToTest.toCharArray()))
					.sorted()
					.findFirst();

			return potentialFirst
					.orElse(WinningType.FIVE_OF_KIND); // JJJJJ
		}
		
		return Arrays.asList(WinningType.values()).stream()
				.map(WinningType.class::cast)
				.filter(winning -> winning.match.test(hand))
				.findFirst()
				.orElseThrow();
	}
}
