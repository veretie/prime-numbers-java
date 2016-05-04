package uk.co.mits4u.primes.service.strategy;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.co.mits4u.primes.service.PrimeStrategy;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component("SUNDARAM")
@ThreadSafe
class SundaramPrimeStrategy implements PrimeStrategy {

    private static final int FIRST_PRIME = 2;

    @Value("${primes.sundaram.thread.count}")
    private int threadCount;
    private ExecutorService threadPool;

    @PostConstruct
    protected void construct() {
        threadPool = Executors.newFixedThreadPool(threadCount);
    }

    @PreDestroy
    protected void destroy() {
        threadPool.shutdown();
    }

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

        try {

            int n = getSundaramLimit(limit);

            Collection<ImmutablePair<Integer, Integer>> ranges = splitToRanges(n, threadCount);
            CountDownLatch countDownLatch = new CountDownLatch(ranges.size());

            ranges.forEach(range ->

                    threadPool.submit(() -> {
                        for (int i = range.getLeft() + 1; i <= range.getRight(); i++) {
                            for (int j = i; j <= (n - i) / (1 + 2 * i); j++) {
                                primeFlags[i + j + 2 * i * j] = false;
                            }
                        }
                        countDownLatch.countDown();
                    })

            );

            countDownLatch.await();

        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }

    }


    private Collection<Integer> collectPrimes(boolean[] primeFlags, int limit) {

        final Collection<Integer> primes = Lists.newLinkedList();
        primes.add(FIRST_PRIME);

        boolean indexForPrime = true;
        IntStream.range(1, getSundaramLimit(limit))
                .filter(flagIndex -> primeFlags[flagIndex] == indexForPrime)
                .forEach(primeFlagIndex -> primes.add(2 * primeFlagIndex + 1));

        return primes;
    }

    private int getSundaramLimit(int limit) {
        return limit / 2;
    }

    private Collection<ImmutablePair<Integer, Integer>> splitToRanges(int n, int slots) {

        int rangeSize = n % slots == 0 ? n / slots : (n / slots) + 1;

        Collection<ImmutablePair<Integer, Integer>> ranges = Stream.iterate(0, (left) -> left + rangeSize)
                .limit(slots)
                .map((left) -> new ImmutablePair<>(left, left + rangeSize < n ? left + rangeSize : n))
                .filter((p) -> p.left < n)
                .collect(Collectors.toList());

        return ranges;
    }

    void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }
}
