import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Part one of
 * <a href="https://adventofcode.com/2023/day/1">Day one</a>
 *
 * @author Nerijus
 */
public class Day01_1 {
    public static void main(String[] args) {
        System.out.println("Sum of all of the calibration values: " + new Day01_1().getResult());
    }

    private int getResult() {
        return Inputs.readStrings("Day01")
                .stream()
                .map(this::getDigits)
                .mapToInt(numbers -> Integer.parseInt("" + numbers.get(0) + numbers.get(numbers.size() - 1)))
                .sum();
    }

    private List<String> getDigits(String s) {
        return Arrays.stream(s.split(""))
                .filter(c -> c.matches("\\d"))
                .collect(Collectors.toList());
    }
}
