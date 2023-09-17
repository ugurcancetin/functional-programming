package org.youtgrow;

import org.youtgrow.model.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.*;

public class UsingFunctions {
    private static int add2(int x) {
        return x + 2;
    }

    private static int mult3(int x) {
        return x * 3;
    }

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Cersei", 250_000, "Lannister"),
                new Employee("Jamie", 150_000, "Lannister"),
                new Employee("Tyrion", 1_000, "Lannister"),
                new Employee("Tywin", 1_000_000, "Lannister"),
                new Employee("Jon Snow", 75_000, "Stark"),
                new Employee("Robb", 120_000, "Stark"),
                new Employee("Eddard", 125_000, "Stark"),
                new Employee("Sansa", 0, "Stark"),
                new Employee("Arya", 1_000, "Stark"));

        //Consumer -> Bi-Consumer
        //Predicate -> Bi-Predicate
        //Function -> Bi-Function, Unary Operator, Binary Operator
        //Supplier
        BiFunction<String, String, Integer> function = (x, y) -> x.length() + y.length();
        System.out.println(function.apply("Ugurcan", "Cetin"));

        UnaryOperator<String> function2 = name -> "Hey " + name;
        System.out.println(function2.apply("Ugurcan"));

        BinaryOperator<String> function3 = (firstName, lastName) -> firstName + " " + lastName;
        System.out.println(function3.apply("Ugurcan", "Cetin"));

        Function<Employee, Employee> highSalaryEmployees = employee -> {
            if (employee.getSalary() >= 150_000) return employee;
            else return null;
        };

        employees.stream().map(highSalaryEmployees).filter(Objects::nonNull).forEach(System.out::println);

        Function<Integer, Integer> a2 = x -> x + 2;
        Function<Integer, Integer> m3 = x -> x * 3;
        Function<Integer, Integer> m3a2 = a2.compose(m3);
        Function<Integer, Integer> a2m3 = a2.andThen(m3);
        System.out.println("m3a2(1): " + m3a2.apply(1));
        System.out.println("a2m3(1): " + a2m3.apply(1));

        Function<Integer, Integer> mult3add2 = ((Function<Integer, Integer>) (x -> x + 2))
                .compose(x -> x * 3);
        System.out.println(mult3add2.apply(1));

        Function<Integer, Integer> add2mult3 = ((Function<Integer, Integer>) (x -> x + 2))
                .andThen(x -> x * 3);
        System.out.println(add2mult3.apply(1));

        mult3add2 = ((UnaryOperator<Integer>) UsingFunctions::add2).compose(UsingFunctions::mult3);
        add2mult3 = ((UnaryOperator<Integer>) UsingFunctions::add2).andThen(UsingFunctions::mult3);

        System.out.println(mult3add2.apply(1));
        System.out.println(add2mult3.apply(1));

        Function<Integer, String> plus2toString = a2.andThen(Object::toString);
        System.out.println(plus2toString.apply(1).getClass().getName());

        Function<String, Integer> parseThenAdd2 = a2.compose(Integer::parseInt);
        System.out.println(parseThenAdd2.apply("1"));

    }
}
