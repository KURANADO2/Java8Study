package com.kuranado.chap2;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: Xinling Jing
 * @Date: 2019-03-16 15:28
 */
public class FilterApples {

    public static void main(String[] args) {

        List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120,
            "red"));

        // 最普通写法
        List<Apple> greenApples = filterGreenApples(inventory);
        System.out.println(greenApples);

        List<Apple> redApples = filterApplesByColor(inventory, "red");
        System.out.println(redApples);

        List<Apple> heavyApples = filterApplesByWeight(inventory, 150);
        System.out.println(heavyApples);

        // 使用接口
        List<Apple> greenApples2 = filterApples(inventory, new AppleGreenColorPredicate());
        System.out.println(greenApples2);

        List<Apple> heavyApples2 = filterApples(inventory, new AppleHeavyWeightPredicate());
        System.out.println(heavyApples2);

        List<Apple> redAndHeavyApples2 = filterApples(inventory, new AppleRedAndHeavyPredicate());
        System.out.println(redAndHeavyApples2);

        prettyPrintApples(inventory, new AppleFancyFormatter());

        prettyPrintApples(inventory, new AppleSimpleFormatter());

        // 使用匿名内部类
        List<Apple> greenApples3 = filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "green".equals(apple.getColor());
            }
        });
        System.out.println(greenApples3);

        // 使用 Lambda
        List<Apple> greenApples4 = filterApples(inventory, (Apple apple) -> "green".equals(apple.getColor()));
        System.out.println(greenApples4);

        // 使用泛型
        List<Apple> greenApples5 = filter(inventory, (Apple apple) -> "green".equals(apple.getColor()));
        System.out.println(greenApples5);

        List<Eggplant> eggplants = Arrays.asList(new Eggplant("purple", 10), new Eggplant("cyan", 15), new Eggplant(
            "red", 13));
        List<Eggplant> longEggplants = filter(eggplants, (Eggplant eggplant) -> eggplant.getLength() > 10);
        System.out.println(longEggplants);

        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple apple1, Apple apple2) {
                return apple1.getWeight() - apple2.getWeight();
            }
        });
        System.out.println(inventory);
        inventory.sort((Apple apple1, Apple apple2) -> apple1.getWeight() - apple2.getWeight());
        System.out.println(inventory);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World!");
            }
        });
        thread.start();

        Thread thread2 = new Thread(() -> System.out.println("Hello World!"));
        thread2.start();
    }

    private static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    private static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (color.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    private static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    private static List<Apple> filterApples(List<Apple> inventory, ApplePredicate applePredicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (applePredicate.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    private static void prettyPrintApples(List<Apple> inventory, AppleFormater appleFormater) {
        for (Apple apple : inventory) {
            System.out.println(appleFormater.format(apple));
        }
    }

    @Data
    static class Apple {

        private int weight;
        private String color;

        Apple(int weight, String color) {
            this.weight = weight;
            this.color = color;
        }
    }

    @FunctionalInterface
    interface ApplePredicate {

        boolean test(Apple apple);

    }

    static class AppleGreenColorPredicate implements ApplePredicate {

        @Override
        public boolean test(Apple apple) {
            return "green".equals(apple.getColor());
        }

    }

    static class AppleHeavyWeightPredicate implements ApplePredicate {

        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }

    static class AppleRedAndHeavyPredicate implements ApplePredicate {

        @Override
        public boolean test(Apple apple) {
            return "red".equals(apple.getColor()) && apple.getWeight() > 150;
        }
    }

    @FunctionalInterface
    interface AppleFormater {

        String format(Apple apple);
    }

    static class AppleFancyFormatter implements AppleFormater {

        @Override
        public String format(Apple apple) {
            String str = apple.getWeight() > 150 ? " weiht " : " light ";
            return "A" + str + apple.getColor() + " apple.";
        }
    }

    static class AppleSimpleFormatter implements AppleFormater {

        @Override
        public String format(Apple apple) {
            return "An apple of " + apple.getWeight() + " g.";
        }
    }

    private static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    @Data
    static class Eggplant {

        private String color;
        private int length;

        public Eggplant(String color, int length) {
            this.color = color;
            this.length = length;
        }
    }

    @FunctionalInterface
    interface Predicate<T> {

        boolean test(T t);
    }

}
