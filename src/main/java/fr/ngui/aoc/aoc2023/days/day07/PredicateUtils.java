package fr.ngui.aoc.aoc2023.days.day07;

import java.util.function.Predicate;

public class PredicateUtils {
	
	private PredicateUtils() {}

	public static final Predicate<char[]> FIVE_SAME_CHAR =
			hand -> new String(hand).chars().filter(ch -> ch == hand[0]).count() == 5;

			
	public static final Predicate<char[]> FOUR_SAME_CHAR =
			hand -> new String(hand).chars().anyMatch(
					card -> new String(hand).chars().filter(ch -> ch == card).count() == 4);

	public static final Predicate<char[]> THREE_AND_TWO_SAME_CHAR =
			hand -> {
				int[] candidateCards = new String(hand).chars().distinct().toArray();
				if (candidateCards.length != 2) {
					return false;
				}
				long count = new String(hand).chars().filter(ch -> ch == candidateCards[0]).count();
				return count == 2 || count == 3;
			};
		
	public static final Predicate<char[]> THREE_SAME_CHAR =
			hand -> new String(hand).chars().anyMatch(
				card -> new String(hand).chars().filter(ch -> ch == card).count() == 3);

	public static final Predicate<char[]> DOUBLE_TWO_SAME_CHAR = 
			hand -> {
				int[] candidateCards = new String(hand).chars().distinct().toArray();
				if (candidateCards.length != 3) {
					return false;
				}
				long count1 = new String(hand).chars().filter(ch -> ch == candidateCards[0]).count();
				long count2 = new String(hand).chars().filter(ch -> ch == candidateCards[1]).count();
				return count1 == 2 || count2 == 2;
			};

	public static final Predicate<char[]> TWO_SAME_CHAR = 
			hand -> new String(hand).chars().anyMatch(
					card -> new String(hand).chars().filter(ch -> ch == card).count() == 2);
			
}
