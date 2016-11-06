package uk.co.mits4u.primes.service;

public interface PrimeStrategyFactory {

    PrimeStrategy getStrategy(String algorithmName);

}