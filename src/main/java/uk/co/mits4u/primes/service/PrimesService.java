package uk.co.mits4u.primes.service;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import uk.co.mits4u.primes.api.AlgorithmName;
import uk.co.mits4u.primes.api.PrimesApi;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

@Service
public class PrimesService implements PrimesApi {

    private Map<AlgorithmName, PrimeStrategy> strategies;
    @Resource
    private PrimeStrategy eratosthenesPrimeStrategy;


    @PostConstruct
    private void postConstruct() {
        strategies = new EnumMap<>(AlgorithmName.class);
        strategies.put(AlgorithmName.ERATOSTHENES, eratosthenesPrimeStrategy);
    }

    @Override
    public boolean isPrime(int numberToCheck, AlgorithmName algorithmName) {

        PrimeStrategy primeStrategy = resolveStrategy(algorithmName);

        return primeStrategy.isPrime(numberToCheck);

    }

    @Override
    public Collection<Integer> getPrimesInRange(int floor, int ceiling, AlgorithmName algorithmName) {

        PrimeStrategy primeStrategy = resolveStrategy(algorithmName);

        return primeStrategy.getPrimesInRange(floor, ceiling);

    }

    private PrimeStrategy resolveStrategy(AlgorithmName algorithm) {

        PrimeStrategy primeStrategy = strategies.get(algorithm);

        Validate.notNull(primeStrategy, "Could note resolve prime strategy for '" + algorithm + "' algorithm");

        return primeStrategy;
    }
}
