import common.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Part one of
 * <a href="https://adventofcode.com/2023/day/3">Day three</a>
 *
 * @author Nerijus
 */
public class Day03_1 {
    public static void main(String[] args) {
        System.out.println("Sum of all of the part numbers: " + new Day03_1().getResult());
    }

    private int getResult() {
        EngineCell[][] engine = readEngine();
        return readNumbers(engine)
                .stream()
                .filter(n -> n.adjacentToSymbol(engine))
                .mapToInt(n -> n.getValue(engine))
                .sum();
    }

    private List<EnginePartNumber> readNumbers(EngineCell[][] engine) {
        List<EnginePartNumber> numbers = new ArrayList<>();
        for (EngineCell[] engineRow : engine) {
            EnginePartNumber currentNumber = null;
            for (int x = 0; x < engineRow.length; x++) {
                EngineCell cell = engineRow[x];
                if (cell.isNumber()) {
                    if (currentNumber == null) {
                        // start of new number
                        currentNumber = new EnginePartNumber();
                    }
                    // add current coordinated
                    currentNumber.coordinates.add(cell.coordinates);
                    if (x == engineRow.length - 1) {
                        // last cell, add number
                        numbers.add(currentNumber);
                    }
                } else {
                    // not a number, add what we have so far
                    if (currentNumber != null) {
                        numbers.add(currentNumber);
                        currentNumber = null;
                    }
                }

            }
        }

        return numbers;
    }

    private EngineCell[][] readEngine() {
        List<String> rows = Inputs.readStrings("Day03");
        EngineCell[][] engine = new EngineCell[rows.size()][rows.get(0).length()];

        for (int y = 0; y < rows.size(); y++) {
            String[] parts = rows.get(y).split("");
            for (int x = 0; x < parts.length; x++) {
                engine[y][x] = new EngineCell(new Coordinates(x, y), parts[x]);
            }
        }

        return engine;
    }

    record EngineCell(Coordinates coordinates, String value) {
        boolean isEmpty() {
            return ".".equals(this.value);
        }
        boolean isNumber() {
            return this.value.matches("\\d");
        }
        boolean isSymbol() {
            return !isNumber() && !isEmpty();
        }

        @Override
        public String toString() {
            return "(" + coordinates.x + "," + coordinates.y + ") " + value;
        }
    }

    record EnginePartNumber(List<Coordinates> coordinates) {
        EnginePartNumber() {
            this(new ArrayList<>());
        }

        EnginePartNumber(List<Coordinates> coordinates) {
            this.coordinates = coordinates;
        }

        Set<Coordinates> getAllAdjacent(int maxX, int maxY) {
            return this.coordinates.stream()
                    .flatMap(c -> c.getAllValidAdjacentAndDiagonal(maxX, maxY).stream())
                    .filter(c -> !coordinates.contains(c))
                    .collect(Collectors.toSet());
        }

        int getValue(EngineCell[][] engine) {
            return Integer.parseInt(this.coordinates
                    .stream()
                    .map(c -> engine[c.y][c.x].value())
                    .collect(Collectors.joining()));
        }

        public boolean adjacentToSymbol(EngineCell[][] engine) {
            return getAllAdjacent(engine[0].length, engine.length)
                    .stream()
                    .map(c -> engine[c.y][c.x])
                    .anyMatch(EngineCell::isSymbol);
        }
    }
}
