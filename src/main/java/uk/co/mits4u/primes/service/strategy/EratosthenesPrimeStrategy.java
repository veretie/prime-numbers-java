package uk.co.mits4u.primes.service.strategy;

import org.springframework.stereotype.Component;
import uk.co.mits4u.primes.service.PrimeStrategy;

import javax.annotation.concurrent.ThreadSafe;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Boolean.*;

@Component
@ThreadSafe
public class EratosthenesPrimeStrategy implements PrimeStrategy {

    private static int FIRST_PRIME = 2;

    @Override
    public boolean isPrime(int numberToCheck) {
        Collection<Integer> primes = generatePrimes(numberToCheck);
        return primes.contains(numberToCheck);
    }

    @Override
    public Collection<Integer> generatePrimes(int maxPrime) {

        Vector<Boolean> primeFlags = initAllAsPrime(maxPrime);

        markNonPrimes(maxPrime, primeFlags);

        Collection<Integer> primes = collectPrimes(primeFlags);

        return primes;

    }

    private Vector<Boolean> initAllAsPrime(int limit) {
        int capacity = limit + 1;
        Vector<Boolean> flags = new Vector<>(capacity);
        IntStream.range(0, capacity).forEach(i -> flags.add(i, TRUE));
        return flags;
    }

    private void markNonPrimes(int limit, Vector<Boolean> primeFlags) {

        int maxPotentialPrime = (int) Math.sqrt(limit);

        Stream.iterate(FIRST_PRIME, i -> ++i).limit(maxPotentialPrime - 1)
                .parallel().forEach(potentialPrime -> markMultiplesAsNonPrimes(potentialPrime, primeFlags)
        );

    }

    private void markMultiplesAsNonPrimes(int prime, Vector<Boolean> primeFlags) {
        for (int i = prime * prime; i < primeFlags.size(); i += prime) {
            primeFlags.set(i, FALSE);
        }
    }

    private Collection<Integer> collectPrimes(Vector<Boolean> primeFlags) {
        return IntStream.range(FIRST_PRIME, primeFlags.size())
                .filter(index -> primeFlags.get(index) == TRUE)
                .mapToObj(prime -> prime)
                .collect(Collectors.toList());
    }


}
