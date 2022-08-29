package uk.co.mits4u.primes.service;

import com.google.common.collect.ImmutableSortedSet;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.co.mits4u.primes.api.AlgorithmName;
import uk.co.mits4u.primes.api.PrimesApi;

import javax.annotation.Resource;
import java.util.*;

import static java.lang.String.format;

@Service
public class PrimesService implements PrimesApi {

    private static Logger LOGGER = LoggerFactory.getLogger(PrimesService.class);
    @Resource
    private NumberValidator numberValidator;
    @Resource
    private PrimeStrategyFactory primeStrategyFactory;

    @Override
    public boolean isPrime(int numberToCheck, AlgorithmName algorithmName) {

        var params = format("number=[%s], algorithm=[%s]", numberToCheck, algorithmName);
        LOGGER.info("starting isPrime with [" + params + "]");
        var start = System.currentTimeMillis();

        numberValidator.validateNumber(numberToCheck);
        var primeStrategy = resolveStrategy(algorithmName);
        var isPrime = primeStrategy.isPrime(numberToCheck);

        var timeTaken = System.currentTimeMillis() - start;
        LOGGER.info(format("finished isPrime with [%s] in %s ms -> result: [%s]", params, timeTaken, isPrime));

        return isPrime;
    }

    @Override
    public Collection<Integer> getPrimesInRange(int floor, int ceiling, AlgorithmName algorithmName) {

        var params = format("range=[%s, %s], algorithm=[%s]", floor, ceiling, algorithmName);
        LOGGER.info("starting calculation with [" + params + "]");
        var start = System.currentTimeMillis();

        numberValidator.validateRange(floor, ceiling);
        var primeStrategy = resolveStrategy(algorithmName);

        var allPrimes = primeStrategy.generatePrimes(ceiling);
        var primesInRange = ImmutableSortedSet.copyOf(allPrimes).subSet(floor, true, ceiling, true);

        var timeTaken = System.currentTimeMillis() - start;
        LOGGER.info(format("ended calculation with [%s] in %s ms -> found %s primes ", params, timeTaken, primesInRange.size()));

        return primesInRange;

    }

    private PrimeStrategy resolveStrategy(AlgorithmName algorithm) {

        var primeStrategy = primeStrategyFactory.getStrategy(algorithm.name());

        Validate.notNull(primeStrategy, "Could note resolve prime strategy for '" + algorithm + "' algorithm");

        return primeStrategy;
    }
}
