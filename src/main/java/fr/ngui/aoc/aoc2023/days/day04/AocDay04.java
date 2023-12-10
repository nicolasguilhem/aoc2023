package fr.ngui.aoc.aoc2023.days.day04;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import fr.ngui.aoc.aoc2023.days.GenericAocDayWithMapper;
import fr.ngui.aoc.aoc2023.model.PartOfDay;

public class AocDay04 extends GenericAocDayWithMapper<Card> {

	public AocDay04(int day, String expectedTestResultP1, String expectedFinalResultP1, String expectedTestResultP2, String expectedFinalResultP2) {
		super(day, expectedTestResultP1, expectedFinalResultP1, expectedTestResultP2, expectedFinalResultP2);
	}
	
	@Override
	public String run(PartOfDay partOfDay, Stream<?> datas) {
		
		Stream<Card> cards = (Stream<Card>) datas;
		
		int result = 0;
		if (partOfDay == PartOfDay.ONE) {
			result = cards.peek(data -> log.info(data.toString()))
				.mapToInt(Card::getNumberOfPoint)
				.sum();
		} else {
			List<Card> listCards = cards.toList();
			for (int i=0; i<listCards.size(); i++) {
				Card card = listCards.get(i);
				log.info(card.toString());
				result += card.getNumberOfCopies(); 
				int countWinningNumbersOfCard = card.countWinningNumbersOfCard();
				if (countWinningNumbersOfCard > 0) {
					card.addCopyToCurrentAndExtraCopiesToNextCards(i, listCards);
				}
			}
		}
		
		return Long.toString(result);

	}

	@Override
	public Function<String, Card> getMapper(PartOfDay partOfDay) {
		return Card::new;
	}
}
