package fr.ngui.aoc.aoc2023.days.day03;

import java.util.LinkedList;
import java.util.List;

public class EngineLineBuilder {

    private LinkedList<String> queue = new LinkedList<>();

    public EngineLine build(String input) {

        if (queue.size() == 3) {
            queue.pollLast();
        }

        queue.offerFirst(input);

        if (queue.size() == 1) {
        	return null;
        }

        String previousLine = queue.getLast();
        String currentLine = queue.get(1);
        String nextLine = queue.getFirst();
        if (queue.size() == 2) {
        	previousLine = null;
        }
        
		return new EngineLine(previousLine, currentLine, nextLine);
    }


    public List<String> getLastElements() {
        return queue;
    }
}

