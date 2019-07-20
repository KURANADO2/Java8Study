package com.kuranado.chap10;

import java.util.Optional;

/**
 * @Author: Xinling Jing
 * @Date: 2019-07-20 19:49
 */
public class OptionalUtility {

    public static Optional<Integer> string2Int(String str) {
        try {
            return Optional.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
