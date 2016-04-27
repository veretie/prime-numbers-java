package uk.co.mits4u.primes.service;

import java.util.Collection;

public interface PrimeStrategy {

    Collection<Integer> generatePrimes(int limitingNumber);

    boolean isPrime(int numberToCheck);

}
