package com.kuranado.chap3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * @Author: Xinling Jing
 * @Date: 2019-03-16 20:26
 */
public class Lambdas {

    public static void main(String[] args) throws IOException {

        // 普通写法
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello!");
            }
        };
        runnable.run();

        // Lambda
        Runnable runnable2 = () -> System.out.println("Hello!");
        runnable2.run();

        process(runnable);
        process(runnable2);
        process(() -> System.out.println("Hello!"));

        System.out.println(processFile());

        System.out.println(processFile((BufferedReader bufferedReader) -> bufferedReader.readLine()));
        System.out.println(processFile((BufferedReader bufferedReader) -> bufferedReader.readLine() + bufferedReader.readLine()));
        BufferedReaderProcessor bufferedReaderProcessor =
            (BufferedReader bufferedReader) -> bufferedReader.readLine() + bufferedReader.readLine() + bufferedReader.readLine();
        System.out.println(processFile(bufferedReaderProcessor));

        List<String> stringList = Arrays.asList("", "a", "", "b");
        System.out.println(filter(stringList, new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return !s.isEmpty();
            }
        }));
        Predicate<String> predicate = (String s) -> !s.isEmpty();
        System.out.println(filter(stringList, predicate));

        Consumer<Integer> consumer = (Integer i) -> System.out.println(i);
        forEach(Arrays.asList(1, 2, 3, 4, 5), consumer);

        System.out.println(map(Arrays.asList("kuranado", "hello", "lambda"), new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        }));
        System.out.println(map(Arrays.asList("kuranado", "hello", "lambda"), (String s) -> s.length()));

        IntPredicate intPredicate = (int i) -> i % 2 == 0;
        System.out.println(intPredicate.test(1000));
        Predicate<Integer> predicate2 = (Integer i) -> i % 2 == 0;
        System.out.println(predicate2.test(1000));

        int a = 3;
        IntConsumer intConsumer = b -> System.out.println(a);
        intConsumer.accept(1);
    }

    private static void process(Runnable runnable) {
        runnable.run();
    }

    private static String processFile() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/jing/Code/GitHub/Java8Study/src/main/resources/data.txt"))) {
            return bufferedReader.readLine();
        }
    }

    private static String processFile(BufferedReaderProcessor bufferedReaderProcessor) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/jing/Code/GitHub/Java8Study/src/main/resources/data.txt"))) {
            return bufferedReaderProcessor.process(bufferedReader);
        }
    }

    @FunctionalInterface
    interface BufferedReaderProcessor {

        String process(BufferedReader bufferedReader) throws IOException;
    }

    private static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>(list.size());
        for (T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    private static <T> void forEach(List<T> list, Consumer<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        List<R> result = new ArrayList<>(list.size());
        for (T t : list) {
            R r = function.apply(t);
            result.add(r);
        }
        return result;
    }
}
