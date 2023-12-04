import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Part one of
 * <a href="https://adventofcode.com/2023/day/4">Day four</a>
 *
 * @author Nerijus
 */
public class Day04_1 {
    public static void main(String[] args) {
        System.out.println("Points worth in total: " + new Day04_1().getResult());
    }

    private double getResult() {
        return Inputs.readStrings("Day04")
                .stream()
                .map(Card::new)
                .mapToDouble(Card::score)
                .sum();
    }

    static class Card {
        int number;
        List<Integer> winningNumbers;
        List<Integer> numbers;

        Card(String source) {
            String[] mainParts = source.split(": ");
            this.number = Integer.parseInt(mainParts[0].split("\\s+")[1]);
            String[] allNumbers = mainParts[1].split(" \\| ");
            this.winningNumbers = Arrays.stream(allNumbers[0].trim().split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());
            this.numbers = Arrays.stream(allNumbers[1].trim().split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());
        }

        double score() {
            long matches = numbers.stream().filter(n -> this.winningNumbers.contains(n)).count();
            if (matches == 0L) {
                return 0L;
            }
            return Math.pow(2, matches - 1);
        }
    }
}