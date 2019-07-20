package com.kuranado.chap8;

import lombok.Data;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * 责任链模式
 *
 * @Author: Xinling Jing
 * @Date: 2019-07-20 15:10
 */
public class ChainOfResponsibilityModel {

    public static void main(String[] args) {

        // 普通写法
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckProcessing();

        p1.setSuccessor(p2);
        String result = p1.handle("Aren't labdas really sexy?!!");
        System.out.println(result);

        // Lambda 写法
        UnaryOperator<String> p3 = (String input) -> "From Raoul, Mario and Alan: " + input;
        UnaryOperator<String> p4 = (String input) -> input.replaceAll("labda", "lambda");
        Function<String, String> p5 = p3.andThen(p4);
        String result2 = p5.apply("Aren't labdas really sexy?!!");
        System.out.println(result2);
    }

    @Data
    static private abstract class ProcessingObject<T> {

        private ProcessingObject<T> successor;

        public T handle(T input) {
            T t = handleWork(input);
            if (successor != null) {
                return successor.handle(t);
            }
            return t;
        }

        abstract protected T handleWork(T input);
    }

    static private class HeaderTextProcessing extends ProcessingObject<String> {

        @Override
        protected String handleWork(String input) {
            return "From Raoul, Mario and Alan: " + input;
        }
    }

    static private class SpellCheckProcessing extends ProcessingObject<String> {

        @Override
        protected String handleWork(String input) {

            return input.replaceAll("labda", "lambda");
        }
    }
}
