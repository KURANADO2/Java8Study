package com.kuranado.chap4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Xinling Jing
 * @Date: 2019-05-12 20:12
 */
public class StreamBasic {

    public static void main(String[] args) {
        // 筛选前两名低卡路里饭菜的名称 - Java 7 方式
        List<Dish> lowCalories = new ArrayList<>();
        for (Dish dish : Dish.MENU) {
            if (dish.getCalories() < 400) {
                lowCalories.add(dish);
            }
        }

        Collections.sort(lowCalories, new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return o1.getCalories() - o2.getCalories();
            }
        });

        List<String> lowCaloriesDishNames = new ArrayList<>(lowCalories.size());

        int count = 1;
        for (Dish dish : lowCalories) {
            if (count > 2) {
                break;
            }
            lowCaloriesDishNames.add(dish.getName());
            count ++;
        }

        System.out.println(lowCaloriesDishNames);

        // 筛选前两名低卡路里饭菜的名称 - Java 8 Stream 流方式
        List<String> lowCaloriesDishNames2 =
            Dish.MENU.stream().
                filter(dish -> dish.getCalories() < 400).
                sorted(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName)
                .limit(2)
                .collect(Collectors.toList());
        System.out.println(lowCaloriesDishNames2);
    }

}
