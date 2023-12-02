import java.util.Arrays;
import java.util.List;

/**
 * Part two of
 * <a href="https://adventofcode.com/2023/day/2">Day two</a>
 *
 * @author Nerijus
 */
public class Day02_1 {
    public static void main(String[] args) {
        System.out.println("Sum of the IDs: " + new Day02_1().getResult());
    }

    private int getResult() {
        return getGames()
                .stream()
                // only 12 red cubes, 13 green cubes, and 14 blue cubes
                .filter(game -> game.isPossible(12, 13, 14))
                .mapToInt(game -> game.number)
                .sum();
    }

    List<Game> getGames() {
        List<String> games = Inputs.readStrings("Day02");
        return games.stream().map(Game::new).toList();
    }

    static class Game {
        int number;
        List<GameSet> sets;

        Game(String gameString) {
            String[] parts = gameString.split(": ");

            this.number = Integer.parseInt(parts[0].split(" ")[1]);
            this.sets = Arrays.stream(parts[1].split("; ")).map(GameSet::new).toList();
        }

        public boolean isPossible(int red, int green, int blue) {
            return sets.stream().allMatch(s -> s.isPossible(red, green, blue));
        }
    }

    static class GameSet {
        int red;
        int green;
        int blue;

        public GameSet(String gameSetString) {
            Arrays.stream(gameSetString.split(", ")).forEach(ballCount -> {
                String[] parts = ballCount.split(" ");
                int count = Integer.parseInt(parts[0]);
                if ("red".equals(parts[1])) {
                    red = count;
                } else if ("blue".equals(parts[1])) {
                    blue = count;
                } else {
                    green = count;
                }
            });
        }

        public boolean isPossible(int red, int green, int blue) {
            return red >= this.red && green >= this.green && blue >= this.blue;
        }
    }
}
