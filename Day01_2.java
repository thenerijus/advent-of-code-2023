import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Part two of
 * <a href="https://adventofcode.com/2023/day/1">Day one</a>
 *
 * @author Nerijus
 */
public class Day01_2 extends Day01_1 {
    private static final Map<String, Integer> NUMBER_MAP = Map.of(
            "one", 1,
            "two", 2,
            "three", 3,
            "four", 4,
            "five", 5,
            "six", 6,
            "seven", 7,
            "eight", 8,
            "nine", 9);
    private static final Pattern NUMBER_PATTERN = Pattern.compile("(?<=(one|two|three|four|five|six|seven|eight|nine|[1-9]))");

    public static void main(String[] args) {
        System.out.println("Sum of all of the calibration values: " + new Day01_2().getResult());
    }

    private int getResult() {
        return Inputs.readStrings("Day01")
                .stream()
                .map(this::extractNumbers)
                .mapToInt(numbers -> Integer.parseInt("" + numbers.get(0) + numbers.get(numbers.size() - 1)))
                .sum();
    }

    private List<Integer> extractNumbers(String inputLine) {
        List<Integer> numbers = new ArrayList<>();
        Matcher matcher = NUMBER_PATTERN.matcher(inputLine);
        while (matcher.find()) {
            numbers.add(toNumber(matcher.group(1)));
        }
        return numbers;
    }

    int toNumber(String stringNumber) {
        Integer mapped = NUMBER_MAP.get(stringNumber);
        return mapped != null ? mapped : Integer.parseInt(stringNumber);
    }
}
