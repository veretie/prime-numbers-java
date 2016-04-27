package uk.co.mits4u.primes.service.strategy;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import uk.co.mits4u.primes.service.PrimeStrategy;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Collection;

@Component
@ThreadSafe
public class SundaramPrimeStrategy implements PrimeStrategy {

    @Override
    public boolean isPrime(int numberToCheck) {
        return false;
    }

    @Override
    public Collection<Integer> generatePrimes(int maxPrime) {
        return Lists.newArrayList();
    }

}
