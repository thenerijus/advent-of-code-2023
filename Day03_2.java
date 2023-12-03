import common.Coordinates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Part two of
 * <a href="https://adventofcode.com/2023/day/3">Day three</a>
 *
 * @author Nerijus
 */
public class Day03_2 extends Day03_1 {
    public static void main(String[] args) {
        System.out.println("Sum of all of the gear ratios: " + new Day03_2().getResult());
    }

    private long getResult() {
        List<EnginePartNumber> numbersNextToGear =
                readNumbers(readEngine())
                .stream()
                .filter(EnginePartNumber::isAdjacentToGear)
                .toList();

        return toPairs(numbersNextToGear)
                    .stream()
                    .mapToLong(Pair::ratio)
                    .sum();
    }

    private List<Pair> toPairs(List<EnginePartNumber> numbers) {
        List<Pair> pairs = new ArrayList<>();
        Set<Coordinates> gearPositions = new HashSet<>();
        numbers.forEach(number -> {
            Coordinates gearPosition = number.getAdjacentSymbol().coordinates();
            if (!gearPositions.contains(gearPosition)) {
                EnginePartNumber match = numbers.stream().filter(n -> !n.equals(number) && n.getAdjacentSymbol().coordinates().isSame(gearPosition)).findFirst().orElse(null);
                if (match != null) {
                    pairs.add(new Pair(number, match));
                }
                gearPositions.add(gearPosition);
            }
        });
        return pairs;
    }

    record Pair(EnginePartNumber one, EnginePartNumber two) {
        long ratio() {
            return one.getNumber() * two.getNumber();
        }
    }
}
