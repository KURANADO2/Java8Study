package com.kuranado.chap8;

/**
 * 策略模式
 *
 * @Author: Xinling Jing
 * @Date: 2019-07-20 13:58
 */
public class StrategyModel {

    public static void main(String[] args) {

        // 普通写法
        Validator validator = new Validator(new IsAllLowerCase());
        System.out.println(validator.validate("abc"));
        Validator validator2 = new Validator(new IsNum());
        System.out.println(validator2.validate("123"));

        // Lambda 写法
        Validator validator3 = new Validator((String s) -> s.matches("[a-z]+"));
        System.out.println(validator3.validate("abc"));
        Validator validator4 = new Validator((String s) -> s.matches("\\d+"));
        System.out.println(validator4.validate("123"));
    }

    private interface ValidationStrategy {
        boolean execute(String s);
    }

    static private class IsAllLowerCase implements ValidationStrategy {

        @Override
        public boolean execute(String s) {
            return s.matches("[a-z]+");
        }
    }

    static private class IsNum implements ValidationStrategy {

        @Override
        public boolean execute(String s) {
            return s.matches("\\d+");
        }
    }

    static private class Validator {

        private final ValidationStrategy validationStrategy;

        public Validator(ValidationStrategy validationStrategy) {
            this.validationStrategy = validationStrategy;
        }

        public boolean validate(String s) {
            return validationStrategy.execute(s);
        }
    }
}
