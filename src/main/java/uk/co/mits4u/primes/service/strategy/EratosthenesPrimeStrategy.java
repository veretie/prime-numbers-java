package uk.co.mits4u.primes.service.strategy;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import uk.co.mits4u.primes.api.AlgorithmName;
import uk.co.mits4u.primes.api.PrimesApi;
import uk.co.mits4u.primes.service.PrimeStrategy;

import javax.annotation.concurrent.ThreadSafe;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component("ERATOSTHENES")
@ThreadSafe
class EratosthenesPrimeStrategy implements PrimeStrategy {

    private static int FIRST_PRIME = 2;

    @Override
    public boolean isPrime(int numberToCheck) {
        Collection<Integer> primes = generatePrimes(numberToCheck);
        return primes.contains(numberToCheck);
    }

    @Override
    public Collection<Integer> generatePrimes(int maxPrime) {

        if (maxPrime == 0) {
            return Lists.newLinkedList();
        }

        boolean[] primeFlags = initAllAsPrime(maxPrime);

        markNonPrimes(maxPrime, primeFlags);

        Collection<Integer> primes = collectPrimes(primeFlags);

        return primes;

    }

    private boolean[] initAllAsPrime(int limit) {
        boolean[] flags = new boolean[limit + 1];
        Arrays.fill(flags, true);
        return flags;
    }

    private void markNonPrimes(int limit, boolean[] primeFlags) {

        int maxPotentialPrime = (int) Math.sqrt(limit);

        Stream.iterate(FIRST_PRIME, i -> ++i).limit(maxPotentialPrime - 1)
                .parallel().forEach(potentialPrime -> markMultiplesAsNonPrimes(potentialPrime, primeFlags)
        );

    }

    private void markMultiplesAsNonPrimes(int prime, boolean[] primeFlags) {
        for (int i = prime * prime; i < primeFlags.length; i += prime) {
            primeFlags[i] = false;
        }
    }

    private Collection<Integer> collectPrimes(boolean[] primeFlags) {
        return IntStream.range(FIRST_PRIME, primeFlags.length)
                .filter(index -> primeFlags[index])
                .mapToObj(prime -> prime)
                .collect(Collectors.toList());
    }


}
