package uk.co.mits4u.primes.service.strategy;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class EratosthenesPrimeStrategyTest {

    @InjectMocks
    private EratosthenesPrimeStrategy eratosthenesPrimeStrategy;
    private Collection<Integer> primes;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        primes = Lists.newArrayList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97);
    }

    @Test
    public void testIsPrime() throws Exception {

        for (int prime : primes) {
            boolean isPrime = eratosthenesPrimeStrategy.isPrime(prime);
            assertThat(isPrime).isTrue();
        }

    }

    @Test
    public void testGenerateRange() throws Exception {
        Collection<Integer> primeResults = eratosthenesPrimeStrategy.generatePrimes(100);
        assertThat(primeResults).containsOnlyElementsOf(primes);
    }

}