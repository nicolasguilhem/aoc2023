package fr.ngui.aoc.aoc2023.days.day05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import fr.ngui.aoc.aoc2023.days.GenericAocDay;
import fr.ngui.aoc.aoc2023.model.PartOfDay;

public class AocDay05 extends GenericAocDay {

	private String seedsLine;
	private List<MapperOrder> listSeedToSoilMapper = new ArrayList<>();
	private List<MapperOrder> listSoilToFertilizerMapper = new ArrayList<>();
	private List<MapperOrder> listFertilizerToWaterMapper = new ArrayList<>();
	private List<MapperOrder> listWaterToLightMapper = new ArrayList<>();
	private List<MapperOrder> listLightToTemperatureMapper = new ArrayList<>();
	private List<MapperOrder> listTemperatureToHumidityMapper = new ArrayList<>();
	private List<MapperOrder> listHumidityToLocationMapper = new ArrayList<>();

	public AocDay05(int day, String expectedTestResultP1, String expectedFinalResultP1, String expectedTestResultP2,
			String expectedFinalResultP2) {
		super(day, expectedTestResultP1, expectedFinalResultP1, expectedTestResultP2, expectedFinalResultP2);
	}

	@Override
	public String run(PartOfDay partOfDay, Stream<?> datas) {

		initDatas(datas, partOfDay);
		// 2567694082
		
		AtomicLong i = new AtomicLong();
		
		long result = buildStreamOfSeeds(partOfDay)
				.peek(value -> {
					if (i.getAndIncrement() % 100000000L == 0) { 
						log.info(i.get() + " -> " + ((i.get() / 2567694082L) * 100) + " %");
					}
				})
				.map(getDestinationFunction(listSeedToSoilMapper)
					.andThen(getDestinationFunction(listSoilToFertilizerMapper))
					.andThen(getDestinationFunction(listFertilizerToWaterMapper))
					.andThen(getDestinationFunction(listWaterToLightMapper))
					.andThen(getDestinationFunction(listLightToTemperatureMapper))
					.andThen(getDestinationFunction(listTemperatureToHumidityMapper))
					.andThen(getDestinationFunction(listHumidityToLocationMapper)))
				.min()
				.orElse(0);

		return Long.toString(result);

	}

	private void initDatas(Stream<?> datas, PartOfDay partOfDay) {

		List<String> lines = ((Stream<String>) datas).filter(line -> !line.isEmpty()).toList();

		listSeedToSoilMapper = new ArrayList<>();
		listSoilToFertilizerMapper = new ArrayList<>();
		listFertilizerToWaterMapper = new ArrayList<>();
		listWaterToLightMapper = new ArrayList<>();
		listLightToTemperatureMapper = new ArrayList<>();
		listTemperatureToHumidityMapper = new ArrayList<>();
		listHumidityToLocationMapper = new ArrayList<>();

		boolean readingSeedToSoilMapper = false;
		boolean readingSoilToFertilizerMapper = false;
		boolean readingFertilizerToWaterMapper = false;
		boolean readingWaterToLightMapper = false;
		boolean readingLightToTemperatureMapper = false;
		boolean readingTemperatureToHumidityMapper = false;
		boolean readingHumidityToLocationMapper = false;
		for (String line : lines) {
			if (line.startsWith("seeds: ")) {
				seedsLine = line;
			} else if (line.startsWith("seed-to-soil map:")) {
				readingSeedToSoilMapper = true;
			} else if (line.startsWith("soil-to-fertilizer map:")) {
				readingSoilToFertilizerMapper = true;
			} else if (line.startsWith("fertilizer-to-water map:")) {
				readingFertilizerToWaterMapper = true;
			} else if (line.startsWith("water-to-light map:")) {
				readingWaterToLightMapper = true;
			} else if (line.startsWith("light-to-temperature map:")) {
				readingLightToTemperatureMapper = true;
			} else if (line.startsWith("temperature-to-humidity map:")) {
				readingTemperatureToHumidityMapper = true;
			} else if (line.startsWith("humidity-to-location map:")) {
				readingHumidityToLocationMapper = true;
			} else if (readingHumidityToLocationMapper) {
				listHumidityToLocationMapper.add(new MapperOrder(line));
			} else if (readingTemperatureToHumidityMapper) {
				listTemperatureToHumidityMapper.add(new MapperOrder(line));
			} else if (readingLightToTemperatureMapper) {
				listLightToTemperatureMapper.add(new MapperOrder(line));
			} else if (readingWaterToLightMapper) {
				listWaterToLightMapper.add(new MapperOrder(line));
			} else if (readingFertilizerToWaterMapper) {
				listFertilizerToWaterMapper.add(new MapperOrder(line));
			} else if (readingSoilToFertilizerMapper) {
				listSoilToFertilizerMapper.add(new MapperOrder(line));
			} else if (readingSeedToSoilMapper) {
				listSeedToSoilMapper.add(new MapperOrder(line));
			}
		}
	}

	private LongStream buildStreamOfSeeds(PartOfDay partOfDay) {
		LongStream streamOfValues = Arrays.asList(seedsLine.split(":")[1].split(" ")).stream()
				.filter(value -> !value.isBlank())
				.mapToLong(Long::parseLong);
		if (partOfDay == PartOfDay.ONE) {
			return streamOfValues.distinct();
		}

		final AtomicInteger incrementSplit = new AtomicInteger(0);
		List<Long> listStratingElement = new ArrayList<>();
		List<Long> listLengthOfElement = new ArrayList<>();
		streamOfValues.forEach(value -> {
			if (incrementSplit.getAndIncrement() % 2 == 0) {
				listStratingElement.add(value);
			} else {
				listLengthOfElement.add(value);
			}
		});

		final AtomicInteger increment = new AtomicInteger(0);
		return listStratingElement.stream()
				.distinct()
				.mapToLong(value -> value)
				.flatMap(value -> {
					Long endExclusive = listLengthOfElement.get(increment.getAndIncrement());
					return LongStream.range(value, value + endExclusive);
				});
	}
	
	private LongUnaryOperator getDestinationFunction(List<MapperOrder> listMapper) {
		return value -> getDestination(value, listMapper);
	}

	private long getDestination(long source, List<MapperOrder> listMapper) {
		return listMapper.stream()
			.filter(mapper -> mapper.isCandidate(source))
			.map(mapper -> mapper.getDestination(source))
			.findFirst()
			.orElse(source);
	}

}
