package com.kuranado.chap3;

/**
 * @Author: Xinling Jing
 * @Date: 2019-03-16 20:26
 */
public class ExecuteAround {

    public static void main(String[] args) {

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
    }

    private static void process(Runnable runnable) {
        runnable.run();
    }

}
