package uk.co.mits4u.primes.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.mits4u.primes.api.AlgorithmName;
import uk.co.mits4u.primes.service.strategy.EratosthenesPrimeStrategy;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class PrimesServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @InjectMocks
    private PrimesService primesService;
    @Mock
    private NumberValidator numberValidator;
    @Mock
    private EratosthenesPrimeStrategy eratosthenesPrimeStrategy;
    @Mock
    private Collection<Integer> primes;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        primesService.postConstruct();
        doNothing().when(numberValidator).validateNumber(anyInt());
        when(eratosthenesPrimeStrategy.isPrime(anyInt())).thenReturn(true);
        when(eratosthenesPrimeStrategy.getPrimesInRange(anyInt(), anyInt())).thenReturn(primes);

    }

    @Test
    public void testIsPrime() throws Exception {
        boolean isPrime = primesService.isPrime(1, AlgorithmName.ERATOSTHENES);
        verify(numberValidator).validateNumber(1);
        assertThat(isPrime).isTrue();
    }

    @Test
    public void testGetPrimesInRange() throws Exception {
        Collection<Integer> results = primesService.getPrimesInRange(1, 10, AlgorithmName.ERATOSTHENES);
        verify(numberValidator).validateRange(1, 10);
        assertThat(results).isSameAs(primes);
    }

    @Test
    public void testUnknownStrategy() throws Exception {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Could note resolve prime strategy for 'SUNDARAM' algorithm");
        primesService.getPrimesInRange(1, 10, AlgorithmName.SUNDARAM);
    }

}