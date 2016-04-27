package uk.co.mits4u.primes.service.strategy;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import uk.co.mits4u.primes.service.PrimeStrategy;
import java.util.*;

@Component
public class EratosthenesPrimeStrategy implements PrimeStrategy {

    @Override
    public boolean isPrime(int numberToCheck) {
        Collection<Integer> primes = generatePrimes(numberToCheck);
        return primes.contains(numberToCheck);
    }

    @Override
    public Collection<Integer> generatePrimes(int limit) {

        boolean[] numbers = new boolean[limit + 1];
        Arrays.fill(numbers, true);

        int prime = 2;

        while (prime <= Math.sqrt(limit)) {
            crossOff(numbers, prime);
            prime = findNextPrime(numbers, prime);
            System.out.println("processed prime: " + prime);
        }


        return collectPrimes(numbers);

    }

    private int findNextPrime(boolean[] numbers, int prime) {
        int nextPrime = prime + 1;
        while (nextPrime < numbers.length && !numbers[nextPrime]) {
            nextPrime++;
        }
        return nextPrime;
    }

    private void crossOff(boolean[] numbers, int prime) {
        for (int i = prime * prime; i < numbers.length; i += prime) {
            numbers[i] = false;
        }
    }

    private Collection<Integer> collectPrimes(boolean[] numbers) {
        List<Integer> primes = Lists.newLinkedList();
        for (int i = 2; i < numbers.length; i++) {
            if (numbers[i]) {
                primes.add(i);
            }
        }
        return primes;
    }


}
