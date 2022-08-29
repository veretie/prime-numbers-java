package uk.co.mits4u.primes.service.strategy;

import org.junit.jupiter.api.BeforeAll;

public class EratosthenesPrimeStrategyStrategyTest extends AbstractPrimeStrategyTester {

    private static EratosthenesPrimeStrategy eratosthenesPrimeStrategy;

    @BeforeAll
    public static void setUp() {
        eratosthenesPrimeStrategy = new EratosthenesPrimeStrategy();
    }

    public EratosthenesPrimeStrategyStrategyTest() {
        super(eratosthenesPrimeStrategy);
    }


}