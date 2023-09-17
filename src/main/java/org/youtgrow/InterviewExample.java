package org.youtgrow;

import org.youtgrow.model.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class InterviewExample {
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

        //calculate what's gonna cost in total giving a %6 raise to the employees is 125_000 or above it and 10% to the lowers
        BiFunction<Integer, Integer, Double> calculatePercentage = (percentage, salary) -> (double) (salary / 100) * percentage ;

        var underPaidRaiseResult = employees.stream()
                .filter(employee -> employee.getSalary() >= 125_000)
                .map(employee -> calculatePercentage.apply(6, employee.getSalary()))
                .reduce(0.0, Double::sum);

        var overPaidRaiseResult = employees.stream()
                .filter(employee -> employee.getSalary() < 125_000)
                .map(employee -> calculatePercentage.apply(10, employee.getSalary()))
                .reduce(0.0, Double::sum);

        System.out.println(underPaidRaiseResult + overPaidRaiseResult);

    }
}
