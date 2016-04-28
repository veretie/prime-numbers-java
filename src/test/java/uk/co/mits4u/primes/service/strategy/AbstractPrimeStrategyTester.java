package uk.co.mits4u.primes.service.strategy;

import org.junit.Test;
import uk.co.mits4u.primes.service.PrimeStrategy;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractPrimeStrategyTester {

    private PrimeStrategy primeStrategy;
    private int[] primes;

    public AbstractPrimeStrategyTester(PrimeStrategy primeStrategy) {
        this.primeStrategy = primeStrategy;
        primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};

    }

    @Test
    public void testIsPrime() throws Exception {

        IntStream.of(primes).forEach((prime) -> {
                    boolean isPrime = primeStrategy.isPrime(prime);
                    assertThat(isPrime).as("checking prime " + prime).isTrue();
                }
        );

    }

    @Test
    public void testIsNotPrime() throws Exception {

        int[] nonPrimes = new int[]{0, 1, 4, 6, 8, 9, 10, 12, 14, 16, 18, 20};
        IntStream.of(nonPrimes).forEach((nonPrime) -> {
                    boolean isPrime = primeStrategy.isPrime(nonPrime);
                    assertThat(isPrime).as("checking non prime " + nonPrime).isFalse();
                }
        );

    }

    @Test
    public void testGeneratePrimes() throws Exception {
        Collection<Integer> primeResults = primeStrategy.generatePrimes(100);
        List<Integer> expectedResults = IntStream.of(primes).mapToObj(i -> i).collect(Collectors.toList());
        assertThat(primeResults).containsOnlyElementsOf(expectedResults);
    }


    @Test
    public void testGeneratePrimesZero() throws Exception {
        Collection<Integer> expectedResults = primeStrategy.generatePrimes(0);
        assertThat(expectedResults).isEmpty();
    }


    @Test
    public void testGeneratePrimesOne() throws Exception {
        Collection<Integer> expectedResults = primeStrategy.generatePrimes(1);
        assertThat(expectedResults).isEmpty();
    }


    @Test
    public void testGeneratePrimesTwo() throws Exception {
        Collection<Integer> expectedResults = primeStrategy.generatePrimes(2);
        assertThat(expectedResults).containsExactly(2);
    }

    @Test
    public void testIsPrimeBigNumbers() {

        IntStream.of(16777213, 15485867).forEach((nonPrime) -> {
                    boolean isPrime = primeStrategy.isPrime(nonPrime);
                    assertThat(isPrime).as("checking big prime " + nonPrime).isTrue();
                }
        );

    }

    @Test
    public void testIsNotPrimeBigNumbers() {

        IntStream.of(16777216, 15485866).forEach((nonPrime) -> {
                    boolean isPrime = primeStrategy.isPrime(nonPrime);
                    assertThat(isPrime).as("checking big non prime " + nonPrime).isFalse();
                }
        );

    }

}
