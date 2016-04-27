package uk.co.mits4u.primes.service.strategy;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class EratosthenesPrimeStrategyTest {



    @InjectMocks
    private EratosthenesPrimeStrategy eratosthenesPrimeStrategy;
    private int[] primes;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
    }

    @Test
    public void testZero() throws Exception {
        boolean isPrime = eratosthenesPrimeStrategy.isPrime(0);
        assertThat(isPrime).isFalse();
    }

    @Test
    public void testIsPrime() throws Exception {

        IntStream.of(primes).mapToObj(prime -> eratosthenesPrimeStrategy.isPrime(prime)).forEach(
                isPrime -> assertThat(isPrime).isTrue()
        );

    }

    @Test
    public void testIsNotPrime() throws Exception {

        int[] nonPrimes = new int[]{1, 4, 6, 8, 9, 10, 12, 14, 16, 18, 20};
        IntStream.of(nonPrimes).mapToObj(prime -> eratosthenesPrimeStrategy.isPrime(prime)).forEach(
                isPrime -> assertThat(isPrime).isFalse()
        );

    }


    @Test
    public void testGenerateRange() throws Exception {
        Collection<Integer> primeResults = eratosthenesPrimeStrategy.generatePrimes(100);
        List<Integer> expectedResults = IntStream.of(primes).mapToObj(i -> i).collect(Collectors.toList());
        assertThat(primeResults).containsOnlyElementsOf(expectedResults);
    }

    @Test
    public void testIsNotPrimeBigNumbers() {

        int bigPrime = 171748941;
        printMemoryDetails(bigPrime);

        boolean isPrime = eratosthenesPrimeStrategy.isPrime(bigPrime);

        assertThat(isPrime).isFalse();

    }


    private void printMemoryDetails(int bigPrime) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memoryMbNeeded = bigPrime / 8 / 1024 / 1024;
        long memoryAvailable = runtime.freeMemory() / 1024 / 1024;
        System.out.println("approximate memory needed " + memoryMbNeeded + " MB; available: " + memoryAvailable + " MB");
    }

}