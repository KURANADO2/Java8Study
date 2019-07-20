package com.kuranado.chap8;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式
 *
 * @Author: Xinling Jing
 * @Date: 2019-07-20 14:33
 */
public class ObserverModel {

    public static void main(String[] args) {

        // Lambda 写法
        Feed feed = new Feed();
        feed.registerObserver((String pushMsg) -> {
            if (pushMsg != null && pushMsg.contains("money")) {
                System.out.println("Breaking news in NY! " + pushMsg);
            }
        });
        feed.registerObserver((String pushMsg) -> {
            if (pushMsg != null && pushMsg.contains("queen")) {
                System.out.println("Yet another news in London... " + pushMsg);
            }
        });
        feed.registerObserver((String pushMsg) -> {
            if (pushMsg != null && pushMsg.contains("wine")) {
                System.out.println("Yet another news in London... " + pushMsg);
            }
        });

        feed.notifyObservers("The queen said her favourite book is Java 8 in Action!");
    }

    interface Observer {

        void notify(String pushMsg);
    }

    interface Subject {

        void registerObserver(Observer observer);

        void notifyObservers(String pushMsg);
    }

    static class Feed implements Subject {

        private List<Observer> observers = new ArrayList<>();

        @Override
        public void registerObserver(Observer observer) {
            observers.add(observer);
        }

        @Override
        public void notifyObservers(String pushMsg) {
            observers.forEach(observer -> observer.notify(pushMsg));
        }
    }

}
