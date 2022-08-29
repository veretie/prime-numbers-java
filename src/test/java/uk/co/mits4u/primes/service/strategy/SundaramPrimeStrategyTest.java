package uk.co.mits4u.primes.service.strategy;

//import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SundaramPrimeStrategyTest extends AbstractPrimeStrategyTester {

    private static SundaramPrimeStrategy sundaramPrimeStrategy;

    @BeforeAll
    public static void setUpBeforeClass() {
        sundaramPrimeStrategy = new SundaramPrimeStrategy();
        sundaramPrimeStrategy.setThreadCount(2);
        sundaramPrimeStrategy.construct();
    }

    @AfterAll
    public static void tearDown() {
        sundaramPrimeStrategy.destroy();
    }

    public SundaramPrimeStrategyTest() {
        super(sundaramPrimeStrategy);
    }

    @Test
    public void testIsPrimeWithDifferentNumberOfThreads() {

        IntStream.of(1, 2, 3, 100).forEach((threadCount) -> {

            sundaramPrimeStrategy.setThreadCount(threadCount);
            sundaramPrimeStrategy.destroy();
            sundaramPrimeStrategy.construct();

            IntStream.of(2, 3, 101, 100003).forEach((nonPrime) -> {
                        boolean isPrime = sundaramPrimeStrategy.isPrime(nonPrime);
                        assertThat(isPrime).as("checking big prime " + nonPrime + " with " + threadCount + " threads").isTrue();
                    }
            );

        });

    }


    @Test
    public void testIsNotPrimeWithDifferentNumberOfThreads() {

        IntStream.of(1, 2, 3, 100).forEach((threadCount) -> {

            sundaramPrimeStrategy.setThreadCount(threadCount);
            sundaramPrimeStrategy.destroy();
            sundaramPrimeStrategy.construct();

            IntStream.of(0, 1, 4, 100000).forEach((nonPrime) -> {
                        boolean isPrime = sundaramPrimeStrategy.isPrime(nonPrime);
                        assertThat(isPrime).as("checking non big prime " + nonPrime + " with " + threadCount + " threads").isFalse();
                    }
            );

        });

    }

}