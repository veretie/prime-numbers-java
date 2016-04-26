package uk.co.mits4u.primes.service;

import java.util.Collection;

public interface PrimeStrategy {

    Collection<Integer> getPrimesInRange(int floor, int ceiling);

    boolean isPrime(int numberToCheck);

}
