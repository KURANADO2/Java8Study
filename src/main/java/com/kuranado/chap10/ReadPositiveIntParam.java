package com.kuranado.chap10;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.Properties;

/**
 * @Author: Xinling Jing
 * @Date: 2019-07-20 20:32
 */
public class ReadPositiveIntParam {

    @Test
    public void test() {

        Properties properties = new Properties();
        properties.setProperty("a", "5");
        properties.setProperty("b", "true");
        properties.setProperty("c", "-3");

        Assert.assertEquals(5, readDurationImperative(properties, "a"));
        Assert.assertEquals(0, readDurationImperative(properties, "b"));
        Assert.assertEquals(0, readDurationImperative(properties, "c"));
        Assert.assertEquals(0, readDurationImperative(properties, "d"));

        Assert.assertEquals(5, readDurationWithOptional(properties, "a"));
        Assert.assertEquals(0, readDurationWithOptional(properties, "b"));
        Assert.assertEquals(0, readDurationWithOptional(properties, "c"));
        Assert.assertEquals(0, readDurationWithOptional(properties, "d"));
    }

    public static int readDurationImperative(Properties properties, String name) {
        String propertyValue = properties.getProperty(name);
        if (propertyValue != null) {
            try {
                int i = Integer.parseInt(propertyValue);
                if (i > 0) {
                    return i;
                }
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public static int readDurationWithOptional(Properties properties, String name) {
        return Optional.ofNullable(properties.getProperty(name)).flatMap(ReadPositiveIntParam::string2Int).filter(i -> i > 0).orElse(0);
    }

    public static Optional<Integer> string2Int(String str) {
        try {
            return Optional.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

}
