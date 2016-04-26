package uk.co.mits4u.primes.service.strategy;

import org.springframework.stereotype.Component;
import uk.co.mits4u.primes.service.PrimeStrategy;

import java.util.Arrays;
import java.util.Collection;

@Component
public class EratosthenesPrimeStrategy implements PrimeStrategy {

    @Override
    public Collection<Integer> getPrimesInRange(int floor, int ceiling) {
        return Arrays.asList(new Integer[]{1, 2, 3});

    }

    @Override
    public boolean isPrime(int numberToCheck) {
        return false;
    }

}
