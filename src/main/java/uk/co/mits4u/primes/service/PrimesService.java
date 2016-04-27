package uk.co.mits4u.primes.service;

import com.google.common.collect.ImmutableSortedSet;
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
    private NumberValidator numberValidator;
    @Resource
    private PrimeStrategy eratosthenesPrimeStrategy;

    @PostConstruct
    protected void postConstruct() {
        strategies = new EnumMap<>(AlgorithmName.class);
        strategies.put(AlgorithmName.ERATOSTHENES, eratosthenesPrimeStrategy);
    }

    @Override
    public boolean isPrime(int numberToCheck, AlgorithmName algorithmName) {

        numberValidator.validateNumber(numberToCheck);
        PrimeStrategy primeStrategy = resolveStrategy(algorithmName);

        return primeStrategy.isPrime(numberToCheck);

    }

    @Override
    public Collection<Integer> getPrimesInRange(int floor, int ceiling, AlgorithmName algorithmName) {

        numberValidator.validateRange(floor, ceiling);
        PrimeStrategy primeStrategy = resolveStrategy(algorithmName);

        Collection<Integer> allPrimes = primeStrategy.generatePrimes(ceiling);
        ImmutableSortedSet primesInRange = ImmutableSortedSet.copyOf(allPrimes).subSet(floor, true, ceiling, true);

        return primesInRange;

    }

    private PrimeStrategy resolveStrategy(AlgorithmName algorithm) {

        PrimeStrategy primeStrategy = strategies.get(algorithm);

        Validate.notNull(primeStrategy, "Could note resolve prime strategy for '" + algorithm + "' algorithm");

        return primeStrategy;
    }
}
