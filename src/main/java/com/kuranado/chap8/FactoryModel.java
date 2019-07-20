package com.kuranado.chap8;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @Author: Xinling Jing
 * @Date: 2019-07-20 15:53
 */
public class FactoryModel {

    public static void main(String[] args) {

        Product p = ProductFactory.createProduct("Loan");

        Supplier<Product> supplier = Loan::new;
        Product p2 = supplier.get();

        Product p3 = ProductFactory.createProductWithLambda("Loan");
    }

    private interface Product {
    }

    static private class Loan implements Product {
    }

    static private class Stock implements Product {
    }

    static private class Bond implements Product {
    }

    final static Map<String, Supplier<Product>> MAP = new HashMap<>();

    static {
        MAP.put("Loan", Loan::new);
        MAP.put("Stock", Stock::new);
        MAP.put("Bond", Bond::new);
    }

    static class ProductFactory {

        public static Product createProduct(String name) {
            switch (name) {
                case "Loan":
                    return new Loan();
                case "Stock":
                    return new Stock();
                case "Bond":
                    return new Bond();
                default:
                    throw new IllegalArgumentException("No such product:" + name);
            }
        }

        public static Product createProductWithLambda(String name) {
            Supplier<Product> supplier = MAP.get(name);
            if (supplier != null) {
                return supplier.get();
            }
            throw new IllegalArgumentException("No such product:" + name);
        }
    }
}
