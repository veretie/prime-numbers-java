package uk.co.mits4u.primes.service;

import org.springframework.stereotype.Component;

@Component
public class NumberValidator {

    public static final int MAX_NUMBER = 16777216;

    public void validateRange(int floor, int ceiling) {
        validateNumber(floor);
        validateNumber(ceiling);
        if (floor > ceiling) {
            throw new IllegalArgumentException("floor [" + floor + "] cannot be higher then ceiling [" + ceiling + "]");
        }
    }

    public void validateNumber(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("prime cannot be negative. [" + number + "] is invalid");
        }
        if (number > MAX_NUMBER) {
            throw new IllegalArgumentException("[" + number + "] is invalid. Select number <= 16777216 = 2^24. ");
        }
    }

}
