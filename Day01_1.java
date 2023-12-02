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
        List<String> strings = Inputs.readStrings("Day01");
        return strings.stream()
                .map(s -> Arrays.stream(s.split(""))
                        .filter(c -> c.matches("\\d"))
                        .collect(Collectors.toList()))
                .mapToInt(numbers -> Integer.parseInt("" + numbers.get(0) + numbers.get(numbers.size() - 1)))
                .sum();
    }
}
