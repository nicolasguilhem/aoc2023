package fr.ngui.aoc.aoc2023.days.day08;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import fr.ngui.aoc.aoc2023.days.GenericAocDay;
import fr.ngui.aoc.aoc2023.model.PartOfDay;

public class AocDay08 extends GenericAocDay {
	
	private String navigationOrders;
	private HashMap<String, NodeDirection> navigatioNetwork;
	
	public AocDay08(int day, String expectedTestResultP1, String expectedFinalResultP1, String expectedTestResultP2,
			String expectedFinalResultP2) {
		super(day, expectedTestResultP1, expectedFinalResultP1, expectedTestResultP2, expectedFinalResultP2);
	}
	
	@Override
	public String run(PartOfDay partOfDay, Stream<?> datas) {

		Stream<String> lines = (Stream<String>) datas;
		
		initDatas(lines);
		
		List<String> nodes;
		if (partOfDay == PartOfDay.ONE) {			
			nodes = new ArrayList<>();
			nodes.add("AAA");
		} else  {
			nodes = navigatioNetwork.keySet().stream()
				.filter(node -> node.endsWith("A"))
				.toList();
		}
		
		long nbStep = nodes.stream()
				.map(node -> {
					AtomicInteger steps = new AtomicInteger();
					countSteps(node, steps);
					return steps.get();
				})
				.map(BigInteger::valueOf)
				.reduce((a, b) -> {
					BigInteger gcd = a.gcd(b);
			        return a.multiply(b).divide(gcd);
				})
				.map(BigInteger::longValue)
				.orElseThrow();
		
		return Long.toString(nbStep);
	}

	private void countSteps(String node, AtomicInteger steps) {
		for (char direction : navigationOrders.toCharArray()) {
			node = navigatioNetwork.get(node).getNextDestination(direction);
			steps.incrementAndGet();
			if (node.endsWith("Z")) {
				return;
			}
		}
		
		// not found, restarting searching
		countSteps(node, steps);
	}
	
	private record NodeDirection(String nextLeft, String nextRight) {
		
		public String getNextDestination(char direction) {
			return direction == 'L' ? nextLeft : nextRight;
		}
	}

	private void initDatas(Stream<String> lines) {

		navigatioNetwork = new HashMap<>();
		navigationOrders = null;
		lines.forEach(line -> {
			if (navigationOrders == null) {
				navigationOrders = line;
			} else if (!line.isBlank()) {
				String[] split = line.split(" = ");
				String[] directions = split[1].replace("(", "").replace(")", "").split(", ");
				navigatioNetwork.put(split[0], new NodeDirection(directions[0], directions[1]));
			}
		});
	}
}
