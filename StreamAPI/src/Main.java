import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        task1();
        task2();
    }


    private static void task1() throws IOException {
        Scanner scanner = new Scanner(Paths.get("test.txt"), StandardCharsets.UTF_8)
                .useDelimiter("[^\\p{L}\\p{Digit}]+");

        Map<String, Integer> map = new HashMap<>();
        scanner.forEachRemaining(s -> map.merge(s.toLowerCase(), 1, (a, b) -> a + b));
        map.entrySet().stream()
                .sorted(descendingFrequency())
                .limit(10)
                .map(Map.Entry::getKey)
                .forEach(System.out::println);
    }

    private static void task2() {
        Stream stream = Stream.of("one", "two", "three", "four", "five", "six", "seven", "eight");
        findMinMax(stream, Comparator.comparingInt(String::length));
    }


    private static Comparator<Map.Entry<String, Integer>> descendingFrequency() {
        return Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                .reversed()
                .thenComparing(Map.Entry::getKey);
    }


    private static <T> void findMinMax(
            Stream<? extends T> stream,
            Comparator<? super T> order) {
        MinMaxFinder<T> minMaxFinder = new MinMaxFinder<>(order);
        stream.forEach(minMaxFinder);
        System.out.println(minMaxFinder.min + "|" + minMaxFinder.max);
    }

    private static class MinMaxFinder<T> implements Consumer<T> {
        private final Comparator<? super T> order;
        T min;
        T max;
        private MinMaxFinder(Comparator<? super T> order) {
            this.order = order;
        }
        @Override
        public void accept(T t) {
            if (min == null || order.compare(t, min) < 0) {
                min = t;
            }
            if (max == null || order.compare(max, t) < 0) {
                max = t;
            }
        }
    }
}