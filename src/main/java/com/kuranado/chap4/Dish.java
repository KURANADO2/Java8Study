package com.kuranado.chap4;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Xinling Jing
 * @Date: 2019-05-12 20:19
 */
@Data
@AllArgsConstructor
public class Dish {

    private String name;
    private boolean vegetarian;
    private int calories;
    private TypeEnum type;

    public static final List<Dish> MENU =
        Arrays.asList(new Dish("pork", false, 800, Dish.TypeEnum.MEAT),
            new Dish("beef", false, 700, Dish.TypeEnum.MEAT),
            new Dish("chicken", false, 400, Dish.TypeEnum.MEAT),
            new Dish("french fries", true, 530, Dish.TypeEnum.OTHER),
            new Dish("rice", true, 350, Dish.TypeEnum.OTHER),
            new Dish("season fruit", true, 120, Dish.TypeEnum.OTHER),
            new Dish("pizza", true, 550, Dish.TypeEnum.OTHER),
            new Dish("prawns", false, 400, Dish.TypeEnum.FISH),
            new Dish("salmon", false, 450, Dish.TypeEnum.FISH));

    /**
     * 菜单分类
     */
    public enum TypeEnum {
        MEAT,
        FISH,
        OTHER
    }

    /**
     * 热量分类
     */
    public enum CaloricLevelEnum {
        DIET,
        NORMAL,
        FAT
    }
}
