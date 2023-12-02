/**
 * Part two of
 * <a href="https://adventofcode.com/2023/day/2">Day two</a>
 *
 * @author Nerijus
 */
public class Day02_2 extends Day02_1 {
    public static void main(String[] args) {
        System.out.println("Sum of the powers: " + new Day02_2().getResult());
    }

    private int getResult() {
        return getGames().stream().mapToInt(this::power).sum();
    }

    private int power(Game game) {
        return game.sets.stream().mapToInt(s -> s.red).max().getAsInt() *
        game.sets.stream().mapToInt(s -> s.green).max().getAsInt() *
        game.sets.stream().mapToInt(s -> s.blue).max().getAsInt();
    }
}
