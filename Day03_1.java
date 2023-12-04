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

    private long getResult() {
        return readNumbers(readEngine())
                .stream()
                .filter(EnginePartNumber::isAdjacentToAnySymbol)
                .mapToLong(EnginePartNumber::getNumber)
                .sum();
    }

    List<EnginePartNumber> readNumbers(EngineCell[][] engine) {
        List<EnginePartNumber> numbers = new ArrayList<>();
        for (EngineCell[] engineRow : engine) {
            List<Coordinates> numberCoordinates = null;
            for (int x = 0; x < engineRow.length; x++) {
                EngineCell cell = engineRow[x];
                if (cell.isDigit()) {
                    if (numberCoordinates == null) {
                        // start of new number
                        numberCoordinates = new ArrayList<>();
                    }
                    // add current coordinates
                    numberCoordinates.add(cell.coordinates);
                    if (x == engineRow.length - 1) {
                        // last cell, add number
                        numbers.add(new EnginePartNumber(numberCoordinates, engine));
                    }
                } else {
                    // not a number, add what we have so far
                    if (numberCoordinates != null) {
                        numbers.add(new EnginePartNumber(numberCoordinates, engine));
                        numberCoordinates = null;
                    }
                }

            }
        }

        return numbers;
    }

    EngineCell[][] readEngine() {
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
            return is(".");
        }

        boolean isDigit() {
            return this.value.matches("\\d");
        }

        boolean isSymbol() {
            return !isDigit() && !isEmpty();
        }

        public boolean is(String symbol) {
            return symbol.equals(this.value);
        }

        @Override
        public String toString() {
            return "(" + coordinates.x + "," + coordinates.y + ") " + value;
        }
    }

    static class EnginePartNumber {
        List<Coordinates> numberCoordinates;
        EngineCell[][] engine;

        EnginePartNumber(List<Coordinates> numberCoordinates, EngineCell[][] engine) {
            this.numberCoordinates = numberCoordinates;
            this.engine = engine;
        }

        Set<Coordinates> getAllAdjacent() {
            return this.numberCoordinates.stream()
                    .flatMap(c -> c.getAllValidAdjacentAndDiagonal(engine[0].length, engine.length).stream())
                    .filter(c -> !numberCoordinates.contains(c))
                    .collect(Collectors.toSet());
        }

        long getNumber() {
            return Long.parseLong(this.numberCoordinates
                    .stream()
                    .map(c -> engine[c.y][c.x].value())
                    .collect(Collectors.joining()));
        }

        EngineCell getAdjacentSymbol() {
            return getAllAdjacent()
                    .stream()
                    .map(c -> engine[c.y][c.x])
                    .filter(EngineCell::isSymbol)
                    .findFirst()
                    .orElse(null);
        }

        boolean isAdjacentToAnySymbol() {
            return getAllAdjacent()
                    .stream()
                    .map(c -> engine[c.y][c.x])
                    .anyMatch(EngineCell::isSymbol);
        }

        boolean isAdjacentToGear() {
            return getAllAdjacent()
                    .stream()
                    .map(c -> engine[c.y][c.x])
                    .anyMatch(engineCell -> engineCell.is("*"));
        }
    }
}
