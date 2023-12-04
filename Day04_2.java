import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Part two of
 * <a href="https://adventofcode.com/2023/day/4">Day four</a>
 *
 * @author Nerijus
 */
public class Day04_2 extends Day04_1 {
    public static void main(String[] args) {
        System.out.println("Total scratchcards: " + new Day04_2().getResult());
    }

    private long getResult() {
        List<Card> cards = Inputs.readStrings("Day04")
                .stream()
                .map(Card::new)
                .toList();

        // initialize initial counts
        Map<Integer, Integer> countsByNumber = new HashMap<>();
        cards.forEach(c -> countsByNumber.put(c.number, 1));

        // check winnings add more cards
        cards.forEach(c -> {
            long matches = c.countMatches();
            if (matches != 0L) {
                Integer cardCount = countsByNumber.get(c.number);
                for (int i = c.number + 1; i <= c.number + matches; i++) {
                    countsByNumber.put(i, countsByNumber.get(i) + cardCount);
                }
            }
        });

        // sum card counts
        return countsByNumber.values().stream().mapToInt(v -> v).sum();
    }
}
