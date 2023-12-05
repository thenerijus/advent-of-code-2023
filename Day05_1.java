import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Part one of
 * <a href="https://adventofcode.com/2023/day/5">Day five</a>
 *
 * @author Nerijus
 */
public class Day05_1 {
    public static void main(String[] args) {
        System.out.println("Lowest location number: " + new Day05_1().getResult());
    }

    private long getResult() {
        String instructions = Inputs.readString("Day05");
        String[] parts = instructions.split("\n\n");
        List<Long> seeds = Arrays.stream(parts[0].split(": ")[1].split(" ")).map(Long::parseLong).toList();

        LinkedList<MappingGroup> groups = new LinkedList<>();
        for (int i = 1; i < parts.length; i++) {
            groups.add(new MappingGroup(parts[i]));
        }

        return seeds.stream().mapToLong(s -> getDestination(s, groups)).min().orElseThrow();
    }

    private long getDestination(Long seed, LinkedList<MappingGroup> groups) {
        long destination = seed;
        for (MappingGroup group : groups) {
            destination = group.getDestination(destination);
        }
        return destination;
    }

    static class MappingGroup {
        List<Range> ranges;

        MappingGroup(String source) {
            ranges = Arrays.stream(source.split("\n")).skip(1).map(Range::new).collect(Collectors.toList());
        }

        long getDestination(long input) {
            Range matchingRange = ranges.stream().filter(r -> r.matches(input)).findFirst().orElse(null);
            return matchingRange != null ? matchingRange.getDestination(input) : input;
        }
    }

    static class Range {
        long destinationRangeStart;
        long sourceRangeStart;
        long rangeLength;

        Range(String source) {
            String[] parts = source.split(" ");
            destinationRangeStart = Long.parseLong(parts[0]);
            sourceRangeStart = Long.parseLong(parts[1]);
            rangeLength = Long.parseLong(parts[2]);
        }

        boolean matches(long input) {
            return input >= sourceRangeStart &&
                    input <= sourceRangeStart + rangeLength;
        }

        long getDestination(long input) {
            return destinationRangeStart + (input - sourceRangeStart);
        }
    }
}