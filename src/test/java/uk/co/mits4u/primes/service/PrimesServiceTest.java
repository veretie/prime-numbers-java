package uk.co.mits4u.primes.service;

import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.mits4u.primes.api.AlgorithmName;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PrimesServiceTest {

    @InjectMocks
    private PrimesService primesService;
    @Mock
    private NumberValidator numberValidator;
    @Mock
    private PrimeStrategyFactory primeStrategyFactory;
    @Mock
    private PrimeStrategy eratosthenesPrimeStrategy;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(numberValidator).validateNumber(anyInt());
        when(primeStrategyFactory.getStrategy("ERATOSTHENES")).thenReturn(eratosthenesPrimeStrategy);
        when(eratosthenesPrimeStrategy.isPrime(anyInt())).thenReturn(true);
        when(eratosthenesPrimeStrategy.generatePrimes(anyInt())).thenReturn(Lists.newArrayList(2, 3, 5, 7));

    }

    @Test
    public void testIsPrime() throws Exception {
        boolean isPrime = primesService.isPrime(1, AlgorithmName.ERATOSTHENES);
        verify(numberValidator).validateNumber(1);
        assertThat(isPrime).isTrue();
    }

    public void testIsPrimeWhenValidatorThrowsException() throws Exception {
        doThrow(new IllegalArgumentException()).when(numberValidator).validateNumber(-1);
        Assertions.assertThatThrownBy(() -> primesService.isPrime(-1, AlgorithmName.ERATOSTHENES))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetPrimesInRange() throws Exception {
        Collection<Integer> results = primesService.getPrimesInRange(1, 10, AlgorithmName.ERATOSTHENES);
        verify(numberValidator).validateRange(1, 10);
        assertThat(results).containsExactly(2, 3, 5, 7);
    }

    public void testGetPrimesWithValidationException() throws Exception {
        doThrow(new IllegalArgumentException()).when(numberValidator).validateRange(100, 1);
        Assertions.assertThatThrownBy(() -> primesService.getPrimesInRange(100, 1, AlgorithmName.ERATOSTHENES))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetPrimesInRangeWithExactInclusiveRange() throws Exception {
        Collection<Integer> results = primesService.getPrimesInRange(2, 7, AlgorithmName.ERATOSTHENES);
        verify(numberValidator).validateRange(2, 7);
        assertThat(results).containsExactly(2, 3, 5, 7);
    }

    @Test
    public void testGetPrimesInRangeWithTrimmingResults() throws Exception {
        Collection<Integer> results = primesService.getPrimesInRange(3, 3, AlgorithmName.ERATOSTHENES);
        verify(numberValidator).validateRange(3, 3);
        assertThat(results).containsExactly(3);
    }

    @Test
    public void testUnknownStrategy() throws Exception {
        Assertions.assertThatThrownBy(() -> primesService.getPrimesInRange(1, 10, AlgorithmName.SUNDARAM))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Could note resolve prime strategy for 'SUNDARAM' algorithm");

    }

}