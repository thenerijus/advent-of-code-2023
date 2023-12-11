import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

/**
 * Part two of
 * <a href="https://adventofcode.com/2023/day/6">Day six</a>
 *
 * @author Nerijus
 */
public class Day06_2 extends Day06_1 {
    public static void main(String[] args) {
        System.out.println("Ways can you beat the record: " + new Day06_2().getResult());
    }

    private BigDecimal getResult() {
        List<String> lines = Inputs.readStrings("Day06");
        BigDecimal time = BigDecimal.valueOf(Long.parseLong(lines.get(0).split(":\\s+")[1].replaceAll("\\s+", "")));
        BigDecimal distance = BigDecimal.valueOf(Long.parseLong(lines.get(1).split(":\\s+")[1].replaceAll("\\s+", "")));

        return time.multiply(time).subtract(distance.multiply(BigDecimal.valueOf(4))).sqrt(MathContext.DECIMAL64);
    }
}
