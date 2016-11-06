package uk.co.mits4u.primes.service.strategy;

import org.junit.BeforeClass;

public class EratosthenesPrimeStrategyStrategyTest extends AbstractPrimeStrategyTester {

    private static EratosthenesPrimeStrategy eratosthenesPrimeStrategy;

    @BeforeClass
    public static void setUp() {
        eratosthenesPrimeStrategy = new EratosthenesPrimeStrategy();
    }

    public EratosthenesPrimeStrategyStrategyTest() {
        super(eratosthenesPrimeStrategy);
    }


}