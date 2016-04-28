package uk.co.mits4u.primes.service.strategy;

import org.junit.BeforeClass;

public class SundaramPrimeStrategyTest extends AbstractPrimeStrategyTester {

    private static SundaramPrimeStrategy sundaramPrimeStrategy;

    @BeforeClass
    public static void setUp() {
        sundaramPrimeStrategy = new SundaramPrimeStrategy();
    }

    public SundaramPrimeStrategyTest() {
        super(sundaramPrimeStrategy);
    }

}