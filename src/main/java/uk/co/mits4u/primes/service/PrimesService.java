package uk.co.mits4u.primes.service;

import com.google.common.collect.ImmutableSortedSet;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import uk.co.mits4u.primes.api.AlgorithmName;
import uk.co.mits4u.primes.api.PrimesApi;

import javax.annotation.Resource;
import java.util.*;

import static java.lang.String.format;

@Service
public class PrimesService implements PrimesApi {

    private static Logger logger = Logger.getLogger(PrimesService.class);
    @Resource
    private NumberValidator numberValidator;
    @Resource
    private PrimeStrategyFactory primeStrategyFactory;

    @Override
    public boolean isPrime(int numberToCheck, AlgorithmName algorithmName) {

        String params = format("number=[%s], algorithm=[%s]", numberToCheck, algorithmName);
        logger.info("starting isPrime with [" + params + "]");
        long start = System.currentTimeMillis();

        numberValidator.validateNumber(numberToCheck);
        PrimeStrategy primeStrategy = resolveStrategy(algorithmName);
        boolean isPrime = primeStrategy.isPrime(numberToCheck);

        long timeTaken = System.currentTimeMillis() - start;
        logger.info(format("finished isPrime with [%s] in %s ms -> result: [%s]", params, timeTaken, isPrime));

        return isPrime;
    }

    @Override
    public Collection<Integer> getPrimesInRange(int floor, int ceiling, AlgorithmName algorithmName) {

        String params = format("range=[%s, %s], algorithm=[%s]", floor, ceiling, algorithmName);
        logger.info("starting calculation with [" + params + "]");
        long start = System.currentTimeMillis();

        numberValidator.validateRange(floor, ceiling);
        PrimeStrategy primeStrategy = resolveStrategy(algorithmName);

        Collection<Integer> allPrimes = primeStrategy.generatePrimes(ceiling);
        ImmutableSortedSet<Integer> primesInRange = ImmutableSortedSet.copyOf(allPrimes).subSet(floor, true, ceiling, true);

        long timeTaken = System.currentTimeMillis() - start;
        logger.info(format("ended calculation with [%s] in %s ms -> found %s primes ", params, timeTaken, primesInRange.size()));

        return primesInRange;

    }

    private PrimeStrategy resolveStrategy(AlgorithmName algorithm) {

        PrimeStrategy primeStrategy = primeStrategyFactory.getStrategy(algorithm.name());

        Validate.notNull(primeStrategy, "Could note resolve prime strategy for '" + algorithm + "' algorithm");

        return primeStrategy;
    }
}
