package com.kuranado.chap6;

import com.alibaba.fastjson.JSON;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Xinling Jing
 * @Date: 2019-05-28 22:30
 */
public class Collector {

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
        groupImperatively();
        groupFunctionally();
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

}
