package com.kuranado.chap6;

import com.alibaba.fastjson.JSON;
import com.kuranado.chap4.Dish;
import com.kuranado.utils.JsonUtils;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author: Xinling Jing
 * @Date: 2019-05-28 22:30
 */
public class CollectorsTest {

    public static List<Transaction> transactions = Arrays.asList(new Transaction(Currency.EUR, 1500.0),
        new Transaction(Currency.USD, 2300.0),
        new Transaction(Currency.GBP, 9900.0),
        new Transaction(Currency.EUR, 1100.0),
        new Transaction(Currency.JPY, 7800.0),
        new Transaction(Currency.CHF, 6700.0),
        new Transaction(Currency.EUR, 5600.0),
        new Transaction(Currency.USD, 4500.0),
        new Transaction(Currency.CHF, 3400.0),
        new Transaction(Currency.GBP, 3200.0),
        new Transaction(Currency.USD, 4600.0),
        new Transaction(Currency.JPY, 5700.0),
        new Transaction(Currency.EUR, 6800.0));

    public static void main(String[] args) {

        long howManyDishes = Dish.MENU.stream().collect(Collectors.counting());
        long howManyDishes2 = Dish.MENU.stream().count();

        // 归约和汇总
        Optional<Dish> mostCalorieDish =
            Dish.MENU.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)));
        System.out.println(mostCalorieDish);
        Optional<Dish> mostCalorieDish2 =
            Dish.MENU.stream().collect(Collectors.reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1
                : d2));
        System.out.println(mostCalorieDish2);

        int sumCalories = Dish.MENU.stream().collect(Collectors.summingInt(Dish::getCalories));
        System.out.println(sumCalories);
        int sumCalories2 = Dish.MENU.stream().collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i + j));
        System.out.println(sumCalories2);
        int sumCalories3 = Dish.MENU.stream().mapToInt(Dish::getCalories).reduce(Integer::sum).getAsInt();
        System.out.println(sumCalories3);
        int sumCalories4 = Dish.MENU.stream().mapToInt(Dish::getCalories).sum();
        System.out.println(sumCalories4);

        double avergeCalories = Dish.MENU.stream().collect(Collectors.averagingInt(Dish::getCalories));
        System.out.println(avergeCalories);

        // 包含最大值、最小值、总和、平均值
        IntSummaryStatistics intSummaryStatistics =
            Dish.MENU.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println(intSummaryStatistics);

        // 拼接字符串
        String shortMenu = Dish.MENU.stream().map(Dish::getName).collect(Collectors.joining());
        System.out.println(shortMenu);
        // 使用分隔符拼接字符串
        String shortMenu2 = Dish.MENU.stream().map(Dish::getName).collect(Collectors.joining(","));
        System.out.println(shortMenu2);

        // 分组
        // 传统方式分组
        groupImperatively();
        // 函数式编程分组
        groupFunctionally();
        // 按菜单类型分组
        Map<Dish.TypeEnum, List<Dish>> dishesByType = Dish.MENU.stream().collect(Collectors.groupingBy(Dish::getType));
        System.out.println(dishesByType);
        // 自定义分组条件
        Map<Dish.CaloricLevelEnum, List<Dish>> dishesByCalricLevel =
            Dish.MENU.stream().collect(Collectors.groupingBy(dish -> {
                if (dish.getCalories() < 300) {
                    return Dish.CaloricLevelEnum.DIET;
                }
                if (dish.getCalories() >= 300 && dish.getCalories() <= 500) {
                    return Dish.CaloricLevelEnum.NORMAL;
                }
                return Dish.CaloricLevelEnum.FAT;
            }));
        System.out.println(dishesByCalricLevel);
        // 多级分组
        // 先按菜单类型分类，相同菜单类型再按热量分类
        Map<Dish.TypeEnum, Map<Dish.CaloricLevelEnum, List<Dish>>> dishesByTypeAndThenCalricLevel =
            Dish.MENU.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.groupingBy(dish -> {
                if (dish.getCalories() < 400) {
                    return Dish.CaloricLevelEnum.DIET;
                }
                if (dish.getCalories() >= 400 && dish.getCalories() <= 500) {
                    return Dish.CaloricLevelEnum.NORMAL;
                }
                return Dish.CaloricLevelEnum.FAT;
            })));
        System.out.println(JsonUtils.formatJson(JSON.toJSONString(dishesByTypeAndThenCalricLevel)));
        // 每个菜单类型下的菜单数
        Map<Dish.TypeEnum, Long> dishTypesCount = Dish.MENU.stream().collect(Collectors.groupingBy(Dish::getType,
            Collectors.counting()));
        System.out.println(dishTypesCount);
        // 每个菜单类型下热量最高的菜，实际上分组得到的 map 结果，只要有 key，就一定有 value，所以 Optional 在这里是没有意义的
        Map<Dish.TypeEnum, Optional<Dish>> mostCaloricByType =
            Dish.MENU.stream().collect(Collectors.groupingBy(Dish::getType,
                Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println(mostCaloricByType);
        // 避免分组结果中使用 Optional
        Map<Dish.TypeEnum, Dish> mostCaloricByType2 = Dish.MENU.stream().collect(Collectors.groupingBy(Dish::getType,
            Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(mostCaloricByType2);
        // 每个菜单类型下的所有菜单热量总和
        Map<Dish.TypeEnum, Integer> sumCaloriesByType = Dish.MENU.stream().collect(Collectors.groupingBy(Dish::getType,
            Collectors.summingInt(Dish::getCalories)));
        System.out.println(sumCaloriesByType);
        // Collectors.mapping 方法第一个参数会流中的元素做转换，第二个参数将转换的结果收集起来
        Map<Dish.TypeEnum, Set<Dish.CaloricLevelEnum>> caloricLevelByType =
            Dish.MENU.stream().collect(Collectors.groupingBy(Dish::getType,
                Collectors.mapping(dish -> {
                    if (dish.getCalories() < 400) {
                        return Dish.CaloricLevelEnum.DIET;
                    }
                    if (dish.getCalories() >= 400 && dish.getCalories() <= 500) {
                        return Dish.CaloricLevelEnum.NORMAL;
                    }
                    return Dish.CaloricLevelEnum.FAT;
                }, Collectors.toSet())));
        System.out.println(JsonUtils.format(caloricLevelByType));
        // Collectors.toCollection 指定使用具体何种集合
        Map<Dish.TypeEnum, HashSet<Dish.CaloricLevelEnum>> caloricLevelByType2 =
            Dish.MENU.stream().collect(Collectors.groupingBy(Dish::getType,
                Collectors.mapping(dish -> {
                    if (dish.getCalories() < 400) {
                        return Dish.CaloricLevelEnum.DIET;
                    }
                    if (dish.getCalories() >= 400 && dish.getCalories() <= 500) {
                        return Dish.CaloricLevelEnum.NORMAL;
                    }
                    return Dish.CaloricLevelEnum.FAT;
                }, Collectors.toCollection(HashSet::new))));
        System.out.println(JsonUtils.format(caloricLevelByType2));

        // 分区
        // 把素菜和非素菜分开
        Map<Boolean, List<Dish>> partitionedMenu =
            Dish.MENU.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
        System.out.println(partitionedMenu);
        System.out.println(partitionedMenu.get(true));
        System.out.println(Dish.MENU.stream().filter(Dish::isVegetarian).collect(Collectors.toList()));
        // 分组和分区组合使用
        Map<Boolean, Map<Dish.TypeEnum, List<Dish>>> vegetarianDishesByType =
            Dish.MENU.stream().collect(Collectors.partitioningBy(Dish::isVegetarian,
                Collectors.groupingBy(Dish::getType)));
        System.out.println(JsonUtils.format(vegetarianDishesByType));
        // 素菜中热量最高的菜单和非素菜中热量最高的菜单
        Map<Boolean, Dish> mostCaloricPartitionByIsVegetarian =
            Dish.MENU.stream().collect(Collectors.partitioningBy(Dish::isVegetarian,
                Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)),
                    Optional::get)));
        System.out.println(JsonUtils.format(mostCaloricPartitionByIsVegetarian));
        // 素数和非素数
        System.out.println(isPrime(5));

        // Map Key 和 Value 一一对应（和 groupingBy 不同，此处 Value 不是 List）
        List<Person> persions = new ArrayList<>();
        Person p = new Person();
        p.setName("xiaoming");
        p.setAge(18);
        p.setAddress("Shanghai");
        persions.add(p);
        Person p2 = new Person();
        p2.setName("xiaowang");
        p2.setAge(19);
        p2.setAddress("Hangzhou");
        persions.add(p2);
        Map<String, Person> map = persions.stream().collect(Collectors.toMap(Person::getName, Function.identity()));
        System.out.println(JSON.toJSONString(map.get("xiaoming")));
        System.out.println(JSON.toJSONString(map.get("xiaowang")));
    }

    private static boolean isPrime(int n) {
        return IntStream.range(2, (int)Math.sqrt((double) n)).noneMatch(i -> n % i == 0);
    }

    /**
     * 传统方式分组
     */
    private static void groupImperatively() {
        Map<Currency, List<Transaction>> transactionGroupByCurrency = new HashMap<>();
        for (Transaction transaction : transactions) {
            List<Transaction> transactionList = transactionGroupByCurrency.get(transaction.getCurrency());
            if (transactionList == null) {
                transactionList = new ArrayList<>();
                transactionGroupByCurrency.put(transaction.getCurrency(), transactionList);
            }
            transactionList.add(transaction);
        }
        System.out.println(JSON.toJSONString(transactionGroupByCurrency));
    }

    /**
     * 函数式编程分组
     */
    private static void groupFunctionally() {
        Map<Currency, List<Transaction>> transactionGroupByCurrency =
            transactions.stream().collect(Collectors.groupingBy(Transaction::getCurrency));
        System.out.println(JSON.toJSONString(transactionGroupByCurrency));
    }

    @Data
    public static class Transaction {

        private final Currency currency;
        private final double value;

    }

    public enum Currency {
        EUR, USD, JPY, GBP, CHF
    }

    @Data
    static class Person {
        private String name;
        private Integer age;
        private String address;
    }
}
