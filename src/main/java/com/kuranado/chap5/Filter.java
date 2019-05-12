package com.kuranado.chap5;

import com.kuranado.chap4.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: Xinling Jing
 * @Date: 2019-05-12 21:19
 */
public class Filter {

    public static void main(String[] args) {
        // filter 筛选
        List<Dish> vegatarianDishes = Dish.MENU.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
        System.out.println(vegatarianDishes);

        // distinct 筛选各异元素，根据类的 hashCode 和 equals 方法判断对象是否相同
        List<Integer> numbers = Arrays.asList(1, 2, 3, 2, 1, 3, 4, 2, 3);
        numbers.stream().filter(i -> i % 2 == 0).distinct().forEach(System.out::println);

        // limit 截断，limit(n) 保留前 n 个元素（从 1 计数）
        List<Dish> highCalories = Dish.MENU.stream().filter(dish -> dish.getCalories() > 400).limit(3).collect(Collectors.toList());
        System.out.println(highCalories);

        // skip 跳过，skip(n) 跳过前 n 个元素（从 1 计数），刚好和 limit 互补
        List<Dish> highCalories2 =
            Dish.MENU.stream().filter(dish -> dish.getCalories() > 400).skip(3).collect(Collectors.toList());
        System.out.println(highCalories2);

        // 映射 map
        List<String> dishNames = Dish.MENU.stream().map(Dish::getName).collect(Collectors.toList());
        System.out.println(dishNames);

        List<String> words = Arrays.asList("Java8", "Lambdas", "In", "Action");
        List<Integer> wordsLength = words.stream().map(String::length).collect(Collectors.toList());
        System.out.println(wordsLength);

        // 扁平化映射 flatMap
        List<String> uniqueCharacter =
            words.stream().map(w -> w.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        System.out.println(uniqueCharacter);
        // 将列表转换成数组流
        Stream<String[]> arrayStream = words.stream().map(w -> w.split(""));
        // 把数组流扁平化为字符串流，得到的字符串流中全是单个字符
        Stream<String> stringStream = arrayStream.flatMap((String[] arr) -> Arrays.stream(arr));
        // 获取流中各异的字符
        List<String> uniqueCharacter2 = stringStream.distinct().collect(Collectors.toList());
        System.out.println(uniqueCharacter2);

        // 匹配
        // anyMatch 只要有一个满足条件，返回 true
        boolean haveVegartarian = Dish.MENU.stream().anyMatch(Dish::isVegetarian);
        System.out.println(haveVegartarian);

        // allMatch 所有都满足条件，返回 true
        boolean isHealthy = Dish.MENU.stream().allMatch(dish -> dish.getCalories() < 1000);
        System.out.println(isHealthy);

        // noneMatch 所有都不满足，返回 true
        boolean isHealthy2 = Dish.MENU.stream().noneMatch(dish -> dish.getCalories() >= 1000);
        System.out.println(isHealthy2);

        // 查找
        Optional<Dish> dishOptional = Dish.MENU.stream().filter(Dish::isVegetarian).findAny();
        // isPresent 在 Optional 包含值的时候返回 true，否则返回 false
        System.out.println(dishOptional.isPresent());
        // ifPresent 在 optional 包含值的时候执行给定的代码块
        Dish.MENU.stream().filter(Dish::isVegetarian).findAny().ifPresent(System.out::println);
        // orElse 在 Option 存在值时返回值，否则返回一个默认值
        Dish highCaloriesDish =
            Dish.MENU.stream().filter(dish -> dish.getCalories() > 1000).findAny().orElse(new Dish(
            "default", true, 1500,
        Dish.TypeEnum.OTHER));
        System.out.println(highCaloriesDish);
        // findFirst 查找第一个元素（findFirst 相比 findAny 在并行上会有更多限制，如果不关心返回的元素是哪一个，建议使用 findAny）
        List<Integer> numbers2 = Arrays.asList(1, 2, 3, 4, 5, 6);
        Optional<Integer> numOptional = numbers2.stream().map(x -> x * x).filter(x -> x % 3 == 0).findFirst();
        numOptional.ifPresent(System.out::println);

        // 规约
        int sum = numbers2.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);
    }

}
