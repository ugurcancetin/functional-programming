package org.youtgrow;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class UsingStreams {
    private final List<String> strings = Arrays.asList("this", "is", "a",
            "list", "of", "strings");

    public static void main(String[] args) {
        var instance = new UsingStreams();
        System.out.println( instance.joinStream());
        System.out.println( instance.joinUpperCase());
        System.out.println( instance.getTotalLength());
        System.out.println( instance.sumFirstNBigDecimalsWithPrecision(4));

        // Find first even double between 200 and 400 divisible by 3
        int firstEvenDoubleDivBy3 = IntStream.range(100, 200)
                .filter(n -> n % 3 == 0)
                .map(n -> n * 2)
                .findFirst().orElse(0);
        System.out.println(firstEvenDoubleDivBy3);

        // Demonstrate laziness using print statements
        firstEvenDoubleDivBy3 = IntStream.range(100, 2_000_000)
                // .parallel()
                .filter(UsingStreams::modByThree)
                .map(UsingStreams::multByTwo)
                .findFirst().orElse(0);
        System.out.printf("First even divisible by 3 is %d%n", firstEvenDoubleDivBy3);

        var result = IntStream.range(0, 10)
                .parallel()
//                .peek((int1) -> System.out.println(Thread.currentThread().getName() + " " + int1))
                .reduce(0,Integer::sum);
        System.out.println(result);

        Stream<String> first = Stream.of("a", "b", "c");
        Stream<String> second = Stream.of("X", "Y", "Z");

        Stream<String> both = Stream.concat(first, second);
        both.forEach(System.out::println);

        //play with parallel
        first = Stream.of("a", "b", "c").parallel();
        second = Stream.of("X", "Y", "Z");
        var third = Stream.of("alpha", "beta", "gamma");
        var fourth = Stream.empty();

        var total = Stream.of(first, second, third, fourth)
                .flatMap(Function.identity());
        System.out.println(total.isParallel());
    }

    public String joinStream() {
        return String.join(" ", strings);
    }

    public String joinUpperCase() {
        return strings.stream()
                .map(String::toUpperCase)
                .collect(joining(" "));
    }

    public int getTotalLength() {
        return strings.stream()
                .mapToInt(String::length)
                .sum();
    }

    public BigDecimal sumFirstNBigDecimalsWithPrecision(int num) {
        return Stream.iterate(BigDecimal.ONE, n -> n.add(BigDecimal.ONE))
                .limit(num)
                .peek(x -> System.out.println("The value is " + x))
//                 .reduce(BigDecimal.ZERO, BigDecimal::add);
                .reduce(BigDecimal.ZERO,
                        (accumulator, val) -> accumulator.add(val));
    }

    private static int multByTwo(int n) {
        System.out.println("Inside multByTwo with arg n = " + n +
                " and thread " + Thread.currentThread().getName());
        return n * 2;
    }

    private static boolean modByThree(int n) {
        System.out.println("Inside modByThree with n = " + n +
                " and thread " + Thread.currentThread().getName());
        return n % 3 == 0;
    }


}
