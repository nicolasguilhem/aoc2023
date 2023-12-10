package fr.ngui.aoc.aoc2023.days.day05;

public class MapperOrder {

	private final long sourceRangeStart;
	private final long sourceRangeEnd;
	private final long movingDelta;

	public MapperOrder(String line) {
		String[] split = line.split(" ");
		Long destinationRangeStart = Long.parseLong(split[0]);
		this.sourceRangeStart = Long.parseLong(split[1]);
		Long rangeLength = Long.parseLong(split[2]);
		this.sourceRangeEnd = sourceRangeStart + rangeLength;
		this.movingDelta = destinationRangeStart - sourceRangeStart;
	}
	
	public boolean isCandidate(long position) {
		return position >= sourceRangeStart && position <= sourceRangeEnd;
	}
	
	public long getDestination(long position) {
		return position + movingDelta;
	}
}
