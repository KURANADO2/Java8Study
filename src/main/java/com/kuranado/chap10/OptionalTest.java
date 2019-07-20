package com.kuranado.chap10;



import lombok.Data;

import java.time.chrono.IsoChronology;
import java.util.Optional;

/**
 * @Author: Xinling Jing
 * @Date: 2019-07-20 17:47
 */
public class OptionalTest {

    public static void main(String[] args) {

        // 声明空的 Optional
        Optional<Car> optionalCar = Optional.empty();

        // 根据非空值创建 Optional
        Car car = new Car();
        // 如果值为空，此处会报 NullPointerException
        Optional<Car> optionalCar2 = Optional.of(car);

        // 允许 null 值的 Optional
        Car car2 = null;
        Optional<Car> optionalCar3 = Optional.ofNullable(car2);

        // map 从 Optional 对象中提取和转换值
        Insurance insurance = new Insurance();
        insurance.setName("车险");
        Optional<Insurance> optionalInsurance = Optional.of(insurance);
        Optional<String> optionalName = optionalInsurance.map(Insurance::getName);

        Person person = new Person();
        Car car3 = new Car();
        Insurance insurance2 = new Insurance();
        insurance2.setName("路费险");
        car3.setInsurance(Optional.of(insurance2));
        person.setCar(Optional.of(car3));
        Optional<Person> optionalPerson = Optional.of(person);
        System.out.println(getCarInsuranceName(optionalPerson));
    }

    public static String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar).flatMap(Car::getInsurance).map(Insurance::getName).orElse("Unkonow");
    }

    @Data
    private static class Person {
        private Optional<Car> car;
    }

    @Data
    private static class Car {
        private Optional<Insurance> insurance;
    }

    @Data
    private static class Insurance {
        private String name;
    }
}
