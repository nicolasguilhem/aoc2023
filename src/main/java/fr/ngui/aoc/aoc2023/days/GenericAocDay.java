package fr.ngui.aoc.aoc2023.days;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ngui.aoc.aoc2023.exceptions.ReadingFileException;
import fr.ngui.aoc.aoc2023.exceptions.ResourceNotFoundException;
import fr.ngui.aoc.aoc2023.model.AocDay;
import fr.ngui.aoc.aoc2023.model.DatasFileType;
import fr.ngui.aoc.aoc2023.model.PartOfDay;

public abstract class GenericAocDay {

	protected static final Logger log = LoggerFactory.getLogger(GenericAocDay.class);
	private final AocDay aocDay;

	protected GenericAocDay(int day, String expectedTestResultP1, String expectedFinalResultP1,
			String expectedTestResultP2, String expectedFinalResultP2) {
		this.aocDay = new AocDay(day, expectedTestResultP1, expectedFinalResultP1, expectedTestResultP2,
				expectedFinalResultP2);
	}

	public AocDay getAocDay() {
		return aocDay;
	}

	public abstract String run(PartOfDay partOfDay, Stream<?> datas);

	public String getResult(PartOfDay partOfDay, DatasFileType dataType)
			throws URISyntaxException, ResourceNotFoundException, ReadingFileException {
		long start = System.nanoTime();
		String result = this.run(partOfDay, this.getAocDay().getDatas(dataType));
		long time = System.nanoTime() - start;
		log.info("    result is \033[1m{}\033[0m calculated in \033[1m{}\033[0m ms", result, time / 1000000);
		return result;
	}

	private static String getAocDayClassFullyQualifiedName(int day) {
		return String.format("%s.day%02d.AocDay%02d", GenericAocDay.class.getPackageName(), day, day);
	}

	public static void main(String[] args) {
		try {
			log.info("Starting Advent of code for day {}", args[0]);
			GenericAocDay instance = getInstance(args);

			for (PartOfDay partOfDay : PartOfDay.values()) {
				for (DatasFileType type : DatasFileType.values()) {
					final var expectedResult = instance.getAocDay().getExpectedResult(type, partOfDay);
					if (expectedResult != null) {
						log.info("<<< Getting result for part {}-{}, expecting {}", partOfDay, type, expectedResult);
						String result = instance.getResult(partOfDay, type);
						assertThat(result).isEqualTo(expectedResult);
						log.info(">>> \u001B[32m\033[1mResult is the one expected\033[0m !!!");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static GenericAocDay getInstance(String[] args) throws ReflectiveOperationException {

		int day = Integer.parseInt(args[0]);
		String expectedTestResultP1 = null;
		String expectedFinalResultP1 = null;
		String expectedTestResultP2 = null;
		String expectedFinalResultP2 = null;
		if (args.length > 1) {
			expectedTestResultP1 = args[1];
			if (args.length > 2) {
				expectedFinalResultP1 = args[2];
				if (args.length > 3) {
					expectedTestResultP2 = args[3];
					if (args.length > 4) {
						expectedFinalResultP2 = args[4];
					}
				}
			}
		}

		Class<GenericAocDay> clazz = (Class<GenericAocDay>) Class
				.forName(GenericAocDay.getAocDayClassFullyQualifiedName(day));
		return clazz.getDeclaredConstructor(int.class, String.class, String.class, String.class, String.class).newInstance(
				day, expectedTestResultP1, expectedFinalResultP1, expectedTestResultP2, expectedFinalResultP2);
	}

}
