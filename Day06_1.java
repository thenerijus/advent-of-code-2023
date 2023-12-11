import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Part one of
 * <a href="https://adventofcode.com/2023/day/6">Day six</a>
 *
 * @author Nerijus
 */
public class Day06_1 {
    public static void main(String[] args) {
        System.out.println(": " + new Day06_1().getResult());
    }

    private long getResult() {
        List<String> lines = Inputs.readStrings("Day06");
        String[] times = lines.get(0).split(":\\s+")[1].split("\\s+");
        String[] distances = lines.get(1).split(":\\s+")[1].split("\\s+");

        List<Race> races = new ArrayList<>();
        for (int i = 0; i < times.length; i++) {
            races.add(new Race(Integer.parseInt(times[i]), Integer.parseInt(distances[i])));
        }


        long result = 1;
        for (Race race : races) {
            result *= race.waysToWin();
        }
        return result;
    }

    static class Race {
        int time;
        int distance;

        public Race(int time, int distance) {
            this.time = time;
            this.distance = distance;
        }

        long waysToWin() {
            return IntStream.range(0, time).filter(t -> {
                int newDistance = (this.time - t) * t;
                return newDistance > this.distance;
            }).count();
        }
    }
}