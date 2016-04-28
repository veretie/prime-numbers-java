package uk.co.mits4u.primes.service.strategy;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import uk.co.mits4u.primes.service.PrimeStrategy;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@ThreadSafe
public class SundaramPrimeStrategy implements PrimeStrategy {

    private static final int FIRST_PRIME = 2;

    @Override
    public boolean isPrime(int numberToCheck) {
        Collection<Integer> primes = generatePrimes(numberToCheck);
        return primes.contains(numberToCheck);
    }

    @Override
    public Collection<Integer> generatePrimes(int maxPrime) {

        if (maxPrime == 0 || maxPrime == 1) {
            return Lists.newLinkedList();
        }

        int maxPrimeInclusive = maxPrime + 1;

        boolean[] primeFlags = initAllAsPrime(maxPrimeInclusive);

        markNonPrimes(maxPrimeInclusive, primeFlags);

        Collection<Integer> primes = collectPrimes(primeFlags, maxPrimeInclusive);

        return primes;

    }

    private boolean[] initAllAsPrime(int limit) {
        boolean[] flags = new boolean[limit];
        Arrays.fill(flags, true);
        return flags;
    }

    private void markNonPrimes(int limit, boolean[] primeFlags) {

        int n = getSundaramRange(limit);

        Stream.iterate(1, i -> ++i).limit(n + 1).parallel().forEach(i -> {

            for (int j = i; j <= (n - i) / (1 + 2 * i); j++) {
                primeFlags[i + j + 2 * i * j] = false;
            }

        });

    }


    private Collection<Integer> collectPrimes(boolean[] primeFlags, int limit) {

        final Collection<Integer> primes = Lists.newLinkedList();
        primes.add(FIRST_PRIME);

        boolean indexForPrime = true;
        IntStream.range(1, getSundaramRange(limit))
                .filter(flagIndex -> primeFlags[flagIndex] == indexForPrime)
                .forEach(primeFlagIndex -> primes.add(2 * primeFlagIndex + 1));

        return primes;
    }

    private int getSundaramRange(int limit) {
        return limit / 2;
    }

}
